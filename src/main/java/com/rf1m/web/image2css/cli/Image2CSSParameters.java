package com.rf1m.web.image2css.cli;

import java.io.File;
import java.util.Set;

public class Image2CSSParameters implements Parameters{
	protected File imageFile 	= null;
	protected File cssFile		= null;
	protected File htmlFile		= null;
	protected Set<SupportedImageTypes> supportedTypes = null;
	protected boolean outputToScreen = false;
	
	protected Image2CSSParameters(){ }
	public Image2CSSParameters(File imageFile, File cssFile, File htmlFile, Set<SupportedImageTypes> supportedTypes, boolean outputToScreen) {
		this.imageFile = imageFile;
		this.cssFile = cssFile;
		this.htmlFile = htmlFile;
		this.supportedTypes = supportedTypes;
		this.outputToScreen = outputToScreen;
	}
	
	@Override
	public boolean isOutputToScreen() {
		return outputToScreen;
	}

	@Override
	public File getImageFile() {
		return imageFile;
	}

	@Override
	public File getCssFile() {
		return cssFile;
	}

	@Override
	public File getHtmlFile() {
		return htmlFile;
	}

	@Override
	public Set<SupportedImageTypes> getSupportedTypes() {
		return supportedTypes;
	}
}
