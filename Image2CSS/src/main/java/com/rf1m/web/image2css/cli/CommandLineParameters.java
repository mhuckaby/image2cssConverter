package com.rf1m.web.image2css.cli;

import java.io.File;
import java.util.HashSet;

import static com.rf1m.web.image2css.cli.SupportedImageTypes.*;

/**
 * Class to process command line arguments.
 * @author Matthew D. Huckaby
 *
 */
public class CommandLineParameters extends Image2CSSParameters {
	public static final String PARAM_F 	= "-f";
	public static final String PARAM_H 	= "-h";
	public static final String PARAM_I 	= "-i";
	public static final String PARAM_O 	= "-o";
	public static final String PARAM_SYSO 	= "-syso";
	
	public static final String EXAMPLE1 	= "./image2CSS.sh -f ./ -h demo.html -o demo.css";
	public static final String EXAMPLE2 	= "./image2CSS.sh -f ./ -o demo.css -i png";
	public static final String EXAMPLE3 	= "./image2CSS.sh -f ./image.png -syso";
	
	public static final String HELP =
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

    protected FileFactory fileFactory;
    protected String[] args;

    class FileFactory{
        public File newFile(String path){
            return new File(path);
        }
    }

    class HashSetFactory{
        public HashSet newHashSet(){
            return new HashSet();
        }
    }

    protected HashSetFactory hashSetFactory;

	public CommandLineParameters(){
		super();
        this.fileFactory = new FileFactory();
        this.hashSetFactory = new HashSetFactory();
	}

    public CommandLineParameters parse(String[] args){
		for(int i=0;i<args.length;i++){
			String param = args[i];

			if(PARAM_F.equalsIgnoreCase(param)){
				this.imageFile = fileFactory.newFile(args[i+1]);
			}else if(PARAM_H.equalsIgnoreCase(param)){
				this.htmlFile = fileFactory.newFile(args[i+1]);
			}else if(PARAM_I.equalsIgnoreCase(param)){
				this.supportedTypes = hashSetFactory.newHashSet();
				for(int j=i;j<args.length;j++){
					if(gif.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(gif);
					}else if(jpg.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(jpg);
					}else if(jpeg.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(jpeg);
					}else if(png.toString().equalsIgnoreCase(args[j])){
						this.supportedTypes.add(png);
					}
				}
				if(0 == this.supportedTypes.size()){
					throw new IllegalArgumentException("-i requires file types be supplied");
				}
			}else if(PARAM_O.equalsIgnoreCase(param)){
				this.cssFile = fileFactory.newFile(args[i+1]);
			}else if(PARAM_SYSO.equalsIgnoreCase(param)){
				this.outputToScreen = true;
			}
		}
        return this;
    }
}