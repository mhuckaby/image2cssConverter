package com.rf1m.web.image2css;

public interface ContentTemplates {
	// Template to build HTML demo page
	// 1:css link
	// 2:css usages
	public final static String HTML_INDEX_TEMPLATE = "<html>\n\t<head>\n\t\t<link REL=StyleSheet HREF=\"%1$s\" TYPE=\"text/css\" MEDIA=screen>\n\t\t<title>Img2Css</title>\n\t\t<style>%1$s</style>\n\t</head>\n\t<body>%2$s\n\t</body>\n</html>";
	
	// Template for each entry in HTML demo page
	// 1:css class for img element
	public final static String HTML_CSS_ENTRY_TEMPLATE = "\n\t\t<div class=\"%1$s\"></div><br />css-class : %1$s<br /><hr />";
	
	// Template for CSS class
	// 1:css-class name
	// 2:img format (gif or png)
	// 3:img data
	// 4:height
	// 5:width
	public final static String CSS_TEMPLATE = ".%1$s{\n\tbackground:url(data:image/%2$s;base64,%3$s) top left no-repeat;);\n\theight:%4$spx;\n\twidth:%5$spx;\n}";
}
