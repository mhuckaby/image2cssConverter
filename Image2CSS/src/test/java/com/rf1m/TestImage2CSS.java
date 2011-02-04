package com.rf1m;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rf1m.web.image2css.Image2CSS;
import com.rf1m.web.image2css.Parameters;
import com.rf1m.web.image2css.Parameters.SupportedImageTypes;
import com.rf1m.web.image2css.ParametersImpl;

public class TestImage2CSS {
	URL urlImageFile 			= this.getClass().getResource("/small_boy_with_book.png");
	URL urlImageDir				= this.getClass().getResource("/");
	
	File imageDir 				= new File(urlImageDir.getFile());
	File imageFile 				= new File(urlImageFile.getFile());
	File imageFileNotExist 		= new File("some_file_that_does_not_exist.file");
	File cssFile 				= new File("boy_with_book.css");
	File htmlFile 				= new File("css-demo.html");
	
	@Before
	public void before() throws Exception{
		cssFile.delete();
		htmlFile.delete();
	}
	
	@After
	public void after() throws Exception{
		cssFile.deleteOnExit();
		htmlFile.deleteOnExit();
	}
	
	@Test
	public void testValidateParameters() throws Exception{
		try{
			Parameters parameters = new ParametersImpl(
				null, null, null, null, false
			);
			new Image2CSS().execute(parameters);
			fail("expected:Image input file or directory must be specified");
		}catch(IllegalArgumentException e){
		}

		try{
			Parameters parameters = new ParametersImpl(
				imageFile, 
				null, 
				null, 
				null, 
				false
			);
			new Image2CSS().execute(parameters);
			fail("expected:CSS output file must be specified");
		}catch(IllegalArgumentException e){
		}

		try{
			Parameters parameters = new ParametersImpl(
				imageFileNotExist, 
				cssFile, 
				null, 
				null, 
				false
			);
			new Image2CSS().execute(parameters);
			fail("expected:Image input file or directory must be specified");
		}catch(IllegalArgumentException e){
		}
	}
	
	@Test
	public void testExecute() throws Exception{
		Set<SupportedImageTypes> types = new HashSet<SupportedImageTypes>();
		types.add(SupportedImageTypes.png);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ParametersImpl(
			imageDir, 
			cssFile, 
			htmlFile, 
			types, 
			false
		);
		
		new Image2CSS().execute(parameters);

		assertTrue(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample1() throws Exception{
		// Display the help screen if run from cmd line, IllegalArgumentException otherwise
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());

		try{
			new Image2CSS().execute(null);
		}catch(IllegalArgumentException e){
		}
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample2() throws Exception{
		// Convert PNG-image (ie: boy_with_book.png) to data URI and output to CSS file :
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());

		Parameters parameters = new ParametersImpl(
			imageFile, 
			cssFile, 
			null, 
			null, 
			false
		);

		new Image2CSS().execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample3() throws Exception{
		// Convert PNG-image (ie: boy_with_book.png) to data URI and output to console :
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ParametersImpl(
			imageFile, 
			null, 
			null, 
			null, 
			true
		);
		new Image2CSS().execute(parameters);

		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample4() throws Exception{
		// Convert PNG-images in directory to data URI and output to CSS file :
		Set<SupportedImageTypes> types = new HashSet<SupportedImageTypes>();
		types.add(SupportedImageTypes.png);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ParametersImpl(
			imageDir, 
			cssFile, 
			null, 
			types, 
			false
		);

		new Image2CSS().execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample5() throws Exception{
		// Convert GIF & JPG images in directory to data URI and output to CSS file :
		Set<SupportedImageTypes> types = new HashSet<SupportedImageTypes>();
		types.add(SupportedImageTypes.png);
		types.add(SupportedImageTypes.gif);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ParametersImpl(
			imageDir, 
			cssFile, 
			null, 
			types, 
			false
		);

		new Image2CSS().execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample6() throws Exception{
		// Convert GIF & JPG images in directory to data URI and output to CSS and HTML files 
		Set<SupportedImageTypes> types = new HashSet<SupportedImageTypes>();
		types.add(SupportedImageTypes.jpg);
		types.add(SupportedImageTypes.gif);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ParametersImpl(
			imageDir, 
			cssFile, 
			htmlFile,
			types, 
			false
		);

		new Image2CSS().execute(parameters);

		assertTrue(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
}