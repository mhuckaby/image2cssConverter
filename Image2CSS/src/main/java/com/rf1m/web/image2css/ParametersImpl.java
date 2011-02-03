package com.rf1m.web.image2css;

import java.io.File;
import java.util.Set;

public class ParametersImpl implements Parameters{
	protected File imageFile 	= null;
	protected File cssFile		= null;
	protected File htmlFile		= null;
	protected Set<SupportedImageTypes> supportedTypes = null;
	protected boolean outputToScreen = false;
	
	protected ParametersImpl(){ }
	public ParametersImpl(File imageFile, File cssFile, File htmlFile,
			Set<SupportedImageTypes> supportedTypes, boolean outputToScreen) {
		super();
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
