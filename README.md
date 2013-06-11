# Image2CSS is a tool designed to generate data URI for use in CSS files.
[Originally hosted at Google Code](http://code.google.com/p/image2css)


## Copyright (c) 2011 Matthew D Huckaby. All rights reservered.
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


## Build using Maven

Parent module, "image2css":
    mvn clean install

Sub-module, "image2css-cli":
    mvn clean install assembly:single


## Features
1. Convert a single file or an entire directories of images to data URI CSS
2. Generate CSS file
3. Generate HTML demonstrating generated CSS classes
4. Output data URI to console
5. Shell and bat scripts included


## Example command line usage

Display the help screen:

	Linux
	./image2css.sh
	
	Windows
	image2css.bat

Convert PNG-image (ie: boy_with_book.png) to data URI and output to CSS file:

	Linux
	./image2css.sh -f boy_with_book.png -o result.css
	
	Windows
	image2css.bat -f boy_with_book.png -o result.css

Convert PNG-image (ie: boy_with_book.png) to data URI and output to console:

	Linux
	./image2css.sh -f boy_with_book.png -syso
	
	Windows
	image2css.bat -f boy_with_book.png -syso
	
Convert PNG-images in directory to data URI and output to CSS file:

	Linux
	./image2css.sh -f ./ -i png -o result.css
	
	Windows
	image2css.bat -f / -i png -o result.css
	
Convert GIF & JPG images in directory to data URI and output to CSS file:

	Linux
	./image2css.sh -f ./ -i gif jpg -o result.css
	
	Windows
	image2css.bat -f / -i gif jpg -o result.css

Convert GIF & JPG images in directory to data URI and output to CSS and HTML files:
	
	Linux
	./image2css.sh -f ./ -i gif jpg -o result.css -h result.html
	
	Windows
	image2css.bat -f / -i gif jpg -o result.css -h result.html



