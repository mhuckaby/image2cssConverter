/**
 *
 * Copyright (c) 2011 Matthew D Huckaby. All rights reservered.
 * ------------------------------------------------------------------------------------
 * Image2Css is licensed under Apache 2.0, please see LICENSE file.
 *
 * Use of this software indicates you agree to the following as well :
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * This product includes software developed by The Apache Software Foundation (http://www.apache.org/).
 * ------------------------------------------------------------------------------------
 */
package com.rf1m.image2css;

import com.rf1m.image2css.cli.ImmutableParameters;
import com.rf1m.image2css.cli.Parameters;
import com.rf1m.image2css.cli.SupportedImageTypes;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import com.rf1m.image2css.exception.Image2CssValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.*;

public class Image2CssFunctionalTest {
	final URL urlImageFile 			= this.getClass().getResource("/small_boy_with_book.png");
    final URL urlImageDir				= this.getClass().getResource("/");

    final File imageDir 				= new File(urlImageDir.getFile());
    final File imageFile 				= new File(urlImageFile.getFile());
    final File imageFileNotExist 		= new File("some_file_that_does_not_exist.file");
    final File cssFile 				= new File("boy_with_book.css");
    final File htmlFile 				= new File("css-demo.html");

    ObjectFactory objectFactory;
    Set<SupportedImageTypes> types;

    Image2Css image2Css;

    @Before
	public void before() throws Exception{
		cssFile.delete();
		htmlFile.delete();

        types = new HashSet<SupportedImageTypes>();

        objectFactory = ObjectFactory.getInstance();
        image2Css = objectFactory.instance(BeanType.image2css);
	}
	
	@After
	public void after() throws Exception{
		cssFile.deleteOnExit();
		htmlFile.deleteOnExit();
	}

    @Test(expected = Image2CssValidationException.class)
    public void should() throws Exception {
        final Parameters parameters = new ImmutableParameters(null, null, null, null, false);

        image2Css.execute(parameters);

        fail("expected:Image input file or directory must be specified");
    }

    @Test(expected = Image2CssValidationException.class)
    public void should2() throws Exception {
        final Parameters parameters = new ImmutableParameters(imageFile, null, null, null, false);
        image2Css.execute(parameters);
        fail("expected:CSS output file must be specified");
    }

    @Test(expected = Image2CssValidationException.class)
	public void testValidateParameters() throws Exception{
        final Parameters parameters = new ImmutableParameters(imageFileNotExist, cssFile, null, null, false);
        image2Css.execute(parameters);
        fail("expected:Image input file or directory must be specified");
	}
	
	@Test
	public void testExecute() throws Exception{
		types.add(SupportedImageTypes.png);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		Parameters parameters = new ImmutableParameters(imageDir, cssFile, htmlFile, types, false);
		
		image2Css.execute(parameters);

		assertTrue(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample1() throws Exception{
		// Display the help screen if run from cmd line, IllegalArgumentException otherwise
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());

		try{
			image2Css.execute(null);
		}catch(Image2CssValidationException e){
		}
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample2() throws Exception{
		// Convert PNG-image (ie: boy_with_book.png) to data URI and output to CSS file :
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());

        final Parameters parameters = new ImmutableParameters(imageFile, cssFile, null, null, false);

		image2Css.execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample3() throws Exception{
		// Convert PNG-image (ie: boy_with_book.png) to data URI and output to console :
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());

        final Parameters parameters = new ImmutableParameters(imageFile, null, null, null, true);
        image2Css.execute(parameters);

		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample4() throws Exception{
		// Convert PNG-images in directory to data URI and output to CSS file :
		types.add(SupportedImageTypes.png);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		final Parameters parameters = new ImmutableParameters(imageDir, cssFile, null, types, false);

		image2Css.execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample5() throws Exception{
		// Convert GIF & JPG images in directory to data URI and output to CSS file :
		types.add(SupportedImageTypes.png);
		types.add(SupportedImageTypes.gif);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		final Parameters parameters = new ImmutableParameters(imageDir, cssFile, null, types, false);

		image2Css.execute(parameters);

		assertFalse(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
	
	@Test
	public void testExecuteExample6() throws Exception{
		// Convert GIF & JPG images in directory to data URI and output to CSS and HTML files 
		types.add(SupportedImageTypes.jpg);
		types.add(SupportedImageTypes.gif);
		
		assertFalse(htmlFile.exists());
		assertFalse(cssFile.exists());
		
		final Parameters parameters = new ImmutableParameters(imageDir, cssFile, htmlFile, types, false);

		image2Css.execute(parameters);

		assertTrue(htmlFile.exists());
		assertTrue(cssFile.exists());
	}
}