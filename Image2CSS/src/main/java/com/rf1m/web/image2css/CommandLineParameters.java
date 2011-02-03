package com.rf1m.web.image2css;

import java.io.File;
import java.util.HashSet;

/**
 * Class to process command line arguments.
 * @author Matthew D. Huckaby
 *
 */
public class CommandLineParameters extends ParametersImpl{
	static final String PARAM_F 	= "-f";	
	static final String PARAM_H 	= "-h";
	static final String PARAM_I 	= "-i";
	static final String PARAM_O 	= "-o";
	static final String PARAM_SYSO 	= "-syso";
	
	private final static String EXAMPLE1 	= "./image2CSS.sh -f ./ -h demo.html -o demo.css";
	private final static String EXAMPLE2 	= "./image2CSS.sh -f ./ -o demo.css -i png";
	private final static String EXAMPLE3 	= "./image2CSS.sh -f ./image.png -syso";
	
	static final String HELP = 
		"\nUsage: Img2CSS [OPTIONS]\n"
		+	PARAM_F + " 	: image-file or directory containing images\n"
		+	PARAM_H + " 	: generate HTML index for CSS classes\n"
		+	PARAM_I + " 	: {png gif jpg} types to include when path is a directory (default all)\n"
		+	PARAM_O + " 	: CSS-file to be output\n"
		+	PARAM_SYSO + " 	: output CSS to screen\n"
		+	"\nExamples\n"
		+ 	EXAMPLE1 + "\n"
		+ 	EXAMPLE2 + "\n"
		+ 	EXAMPLE3 + "\n";
	
	public CommandLineParameters(String args[]){
		super();
		for(int i=0;i<args.length;i++){
			String param = args[i];
			
			if(PARAM_F.equalsIgnoreCase(param)){
				this.imageFile = new File(args[i+1]);
			}else if(PARAM_H.equalsIgnoreCase(param)){
				this.htmlFile = new File(args[i+1]);
			}else if(PARAM_I.equalsIgnoreCase(param)){
				this.supportedTypes = new HashSet<SupportedImageTypes>();
				for(int j=0;j<args.length;j++){
					if(SupportedImageTypes.gif.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(SupportedImageTypes.gif);
					}else if(SupportedImageTypes.jpg.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(SupportedImageTypes.jpg);
					}else if(SupportedImageTypes.jpeg.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(SupportedImageTypes.jpeg);
					}else if(SupportedImageTypes.png.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(SupportedImageTypes.png);
					}
				}
				if(0 == this.supportedTypes.size()){
					throw new IllegalArgumentException("-i requires file types be supplied");
				}
			}else if(PARAM_O.equalsIgnoreCase(param)){
				this.cssFile = new File(args[i+1]);
			}else if(PARAM_SYSO.equalsIgnoreCase(param)){
				this.outputToScreen = true;
			}
		}
	}
}