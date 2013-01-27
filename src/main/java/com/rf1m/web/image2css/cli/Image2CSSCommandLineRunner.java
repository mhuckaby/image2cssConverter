package com.rf1m.web.image2css.cli;

import com.rf1m.web.image2css.Image2CSS;
import com.rf1m.web.image2css.cli.CommandLineParameters;

import java.io.PrintStream;

import static com.rf1m.web.image2css.ContentTemplates.ABOUT;
import static com.rf1m.web.image2css.cli.CommandLineParameters.HELP;

public class Image2CSSCommandLineRunner {
    static protected PrintStream syso = System.out;

    /**
	 * Entry point for command line use.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		syso.println(ABOUT);
		if(0 == args.length){
			syso.println(HELP);
		}else{
			try{
				new Image2CSS().execute(new CommandLineParameters().parse(args));
			}catch(ArrayIndexOutOfBoundsException e){
				syso.println(HELP);
			}catch(IllegalArgumentException e){
				syso.println(e.getMessage());
				syso.println(HELP);
			}
		}
	}
}
