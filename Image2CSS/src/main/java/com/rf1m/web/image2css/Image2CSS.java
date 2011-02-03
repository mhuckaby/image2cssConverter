package com.rf1m.web.image2css;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.rf1m.util.ByteUtils;
import com.rf1m.util.FileUtils;
import com.rf1m.util.Utils;

/**
 * Converts image files into Base64 text-based CSS classes using the URL-data field of a background image. 
 * @author Matthew D. Huckaby
 *
 */
public class Image2CSS implements ContentTemplates {
	private final static String ABOUT = "Image2CSS 1.0, Matthew D. Huckaby, 2011";
	private final static String REPORT = "Generated %1$s CSS entries";
	
	/**
	 * Entry point for command line use.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		System.out.println(ABOUT);
		if(0==args.length){
			System.out.println(CommandLineParameters.HELP);
		}else{
			try{
				new Image2CSS().execute(new CommandLineParameters(args));
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println(CommandLineParameters.HELP);
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				System.out.println(CommandLineParameters.HELP);
			}
		}
	}
	
	/**
	 * Begins conversion of image file(s) to CSS classes.
	 * @param parameters
	 * @throws Exception
	 */
	public void execute(Parameters parameters) throws Exception{
		validateParameters(parameters);
		List<String> cssEntries = new ArrayList<String>();
		FileWriter cssWriter = null;
		File[] imagesForConversion = 
			FileUtils.getImagesForConversion(
				parameters.getImageFile(), 
				parameters.getSupportedTypes()
			);
		for(File imageFile : imagesForConversion){
			String cssClassName = imageFile.getName().replaceAll("\\.","_");
			byte[] bytes = FileUtils.getFileBytes(imageFile);
			Dimension dimension = ByteUtils.getImageDimension(bytes);
			
			// Build the CSS class
			String cssEntry = 
				String.format(
					CSS_TEMPLATE, 
					cssClassName,
					FileUtils.getExtension(imageFile.getName()),
					ByteUtils.base64EncodeBytes(bytes),
					(int)dimension.getHeight(),
					(int)dimension.getWidth()
				);
			
			if(parameters.isOutputToScreen()){
				System.out.println(cssEntry);
			}else{
				if(null == cssWriter){
					cssWriter = new FileWriter(parameters.getCssFile());
				}
				cssWriter.write(cssEntry);
				cssWriter.write(Utils.NL);
			}
			
			// Track CSS classes for count and/or HTML output
			cssEntries.add(cssClassName);
		}
		if(null != cssWriter){
			cssWriter.close();
		}
		
		// Write the HTML index/gallery
		generateHtmlOutput(parameters, cssEntries);
		
		// Report
		System.out.println(String.format(REPORT, cssEntries.size()));
	}
	
	/**
	 * Determines if HTML file should be written. If so, writes div elements that demonstrate the 
	 * css styles generated.
	 * @param parameters
	 * @param cssEntries
	 * @throws Exception
	 */
	protected void generateHtmlOutput(Parameters parameters, List<String> cssEntries) throws Exception{
		// Write the HTML index/gallery
		if(null != parameters.getHtmlFile()){
			StringBuffer htmlBuffer = new StringBuffer();
			for(String cssClass : cssEntries){
				htmlBuffer.append(
					String.format(HTML_CSS_ENTRY_TEMPLATE, cssClass)
				);
			}
			
			FileWriter htmlWriter = new FileWriter(parameters.getHtmlFile());
			
			htmlWriter.write(
				String
					.format(
						HTML_INDEX_TEMPLATE,
						parameters.getCssFile().getName(),
						htmlBuffer.toString()
					)
			);
			htmlWriter.close();
		}
	}
	
	/**
	 * Validates inputs.
	 * @param parameters
	 * @throws Exception
	 */
	protected void validateParameters(Parameters parameters) throws Exception{
		if(null == parameters){
			throw new IllegalArgumentException("Parameteres cannot be null");
		}
		if(null == parameters.getImageFile()){
			throw new IllegalArgumentException("Image input file or directory must be specified");
		}
		if(null == parameters.getCssFile() && !parameters.isOutputToScreen()){
			throw new IllegalArgumentException("CSS output to file or screen must be specified");
		}
		if(!parameters.getImageFile().exists()){
			throw new IllegalArgumentException("Image input file or directory not found");
		}
	}
}