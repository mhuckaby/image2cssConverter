package com.rf1m.web.image2css;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rf1m.util.ByteUtils;
import com.rf1m.util.FileUtils;
import com.rf1m.util.Utils;

/**
 * Image2CSS converts image files into Base64 text-based CSS classes using 
 * the URL-data field of a background image. It can be used to convert a single 
 * file or several. For more information on command-line usage see file, "readme.txt".
 * 
 * Distribution can be found at http://code.google.com/p/image2css/
 * 
 * This product includes software developed by
 * The Apache Software Foundation (http://www.apache.org/).
 * 
 * Please see files "NOTICE" and "LICENSE" for more information.
 * 
 * @author Matthew D. Huckaby
 */
public class Image2CSS implements ContentTemplates {
	private final static String ABOUT = "Image2CSS 1.0, Matthew D. Huckaby, 2011";
	private final static String REPORT_CSS_TOTAL 	= "Generated [%1$s] CSS entries";
	private final static String REPORT_CSS_FILE 	= "Created CSS file, %1$s";
	private final static String REPORT_HTML_FILE 	= "Created HTML file, %1$s";
	
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
		List<CSSClass> cssClasses = generateCSSEntries(parameters);
		generateConsoleOutput(parameters, cssClasses);
		generateCssFileOutput(parameters, cssClasses);
		generateHtmlOutput(parameters, cssClasses);
		generateReportOutput(parameters, cssClasses);
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
	
	/**
	 * Builds CSS class entries. CSS class name is the image-filename with all periods replaced by underscores.
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	protected List<CSSClass> generateCSSEntries(Parameters parameters) throws IOException{
		List<CSSClass> cssEntries = new ArrayList<CSSClass>();
		
		File[] imagesForConversion = 
			FileUtils.getImagesForConversion(
				parameters.getImageFile(), 
				parameters.getSupportedTypes()
			);
		
		for(File imageFile : imagesForConversion){
			CSSClass cssClass = new CSSClass();
			cssClass.setName(imageFile.getName().replaceAll("\\.","_"));
			byte[] bytes = FileUtils.getFileBytes(imageFile);
			Dimension dimension = ByteUtils.getImageDimension(bytes);
			
			// Build the CSS class
			cssClass.setBody(
				String.format(
					CSS_TEMPLATE, 
					cssClass.getName(),
					FileUtils.getExtension(imageFile.getName()),
					ByteUtils.base64EncodeBytes(bytes),
					(int)dimension.getHeight(),
					(int)dimension.getWidth()
				)
			);
			cssEntries.add(cssClass);
		}

		return cssEntries;
	}
	
	/**
	 * Produces console output if parameters dictate.
	 * @param parameters
	 * @param cssClasses
	 */
	protected void generateConsoleOutput(Parameters parameters, List<CSSClass> cssClasses){
		if(parameters.isOutputToScreen()){
			for(CSSClass cssClass : cssClasses){
				System.out.println(cssClass.getBody());
			}
		}
	}

	/**
	 * Produces the CSS file if parameters dictate.
	 * @param parameters
	 * @param cssClasses
	 * @throws IOException
	 */
	protected void generateCssFileOutput(Parameters parameters, List<CSSClass> cssClasses) throws IOException{
		if(null != parameters.getCssFile()){
			FileWriter cssWriter = new FileWriter(parameters.getCssFile());
			for(CSSClass cssClass : cssClasses){
				cssWriter.write(cssClass.getBody());
				cssWriter.write(Utils.NL);
			}
			cssWriter.close();
		}
	}
	
	/**
	 * Determines if HTML file should be written. If so, outputs HTML to demonstrate the generated CSS classes according to the templates.
	 * @param parameters
	 * @param cssClasses
	 * @throws Exception
	 */
	protected void generateHtmlOutput(Parameters parameters, List<CSSClass> cssClasses) throws IOException{
		if(null != parameters.getHtmlFile()){
			StringBuffer htmlBuffer = new StringBuffer();
			
			for(CSSClass cssClass : cssClasses){
				htmlBuffer.append(
					String.format(HTML_CSS_ENTRY_TEMPLATE, cssClass.getName())
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
	 * Outputs a summary of the work performed to the console. Includes number of CSS classes generated, CSS file generated, HTML file generated.
	 * @param parameters
	 * @param cssClasses
	 */
	protected void generateReportOutput(Parameters parameters, List<CSSClass> cssClasses){
		System.out.println(String.format(REPORT_CSS_TOTAL, cssClasses.size()));
		if(null != parameters.getCssFile()){
			System.out.println(String.format(REPORT_CSS_FILE, parameters.getCssFile().getName()));
		}
		if(null != parameters.getHtmlFile()){
			System.out.println(String.format(REPORT_HTML_FILE, parameters.getHtmlFile().getName()));
		}
	}
}