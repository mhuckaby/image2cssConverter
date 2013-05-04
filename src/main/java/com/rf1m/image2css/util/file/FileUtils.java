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
package com.rf1m.image2css.util.file;

import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils{
    protected final ObjectFactory objectFactory;

    public FileUtils(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
	 * Return the file-extension, null if there is none.
	 * @param path
	 * @return
	 */
	public String getExtension(final String path){
        final int notFound = -1;
        final int positionsAfterLastIndex = 1;
		final int lastIndex = path.lastIndexOf('.');

        if(notFound == lastIndex || path.length() == lastIndex){
			return "";
		}else{
            final int afterLastPeriod = lastIndex + positionsAfterLastIndex;
			return path.substring(afterLastPeriod).toLowerCase();
		}
	}

	/**
	 * Read the bytes of a file into a byte array and return the array.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public byte[] getFileBytes(final File file) throws IOException{
		final FileInputStream fileInputStream = this.objectFactory.instance(BeanType.fileInputStream, file);
		final byte[] bytes = this.objectFactory.instance(BeanType.byteArray, file.length());

        fileInputStream.read(bytes);
		fileInputStream.close();

        return bytes;
	}

}