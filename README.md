## Image2CSS generates data URI for use in CSS


### Copyright (c) 2011-2016 Matthew D Huckaby. All rights reserved.
Image2CSS is licensed under Apache 2.0, please see LICENSE file.

Use of this software indicates you agree to the following as well : 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

This product includes software developed by The Apache Software Foundation (http://www.apache.org/).


### Build using Gradle

From root module, build the distribution:

    ./gradlew clean installDist

The installed distribution found in the image2css-cli module includes scripts for *nix and Windows: 

    ./image2css-cli/build/install/image2css-cli/bin/image2css-cli
    
    or 
    
    ./image2css-cli/build/install/image2css-cli/bin/image2css-cli.bat


### Features
1. Convert a single local or remote file or an entire local directory of images to data URI CSS
2. Generate CSS file
3. Generate HTML demonstrating generated CSS classes
4. Output data URI to console
5. Shell and bat scripts included


### Example command line usage

Display the help screen:

	./image2css-cli/build/install/image2css-cli/bin/image2css-cli
	
Convert remote PNG-image to data URI and output to CSS and HTML files:

    ./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
    -f https://upload.wikimedia.org/wikipedia/commons/0/0f/Spotorno-IMG_1772.JPG \
    -o result.css \
    -h result.html
	
Convert PNG-image to data URI and output to CSS and HTML files:

    ./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
    -f image.png \
    -o result.css \
    -h result.html

Convert PNG-image to data URI and output to console:

	./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
	-f image.png \
	-syso
	
Convert PNG-images in directory to data URI and output to CSS and HTML files:

	./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
	-f ./ \
	-i png \
	-o result.css \
	-h result.html
	
Convert GIF & JPG images in directory to data URI and output to CSS and HTML files:

	./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
	-f ./ \
	-i gif jpg \
	-o result.css \
	-h result.html
	
Convert GIF & JPG images in directory to data URI and output to CSS and HTML files:
	
	./image2css-cli/build/install/image2css-cli/bin/image2css-cli \
	-f ./ \
	-i gif jpg \
	-o result.css \
	-h result.html