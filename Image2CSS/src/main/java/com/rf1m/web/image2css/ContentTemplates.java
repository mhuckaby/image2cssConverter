package com.rf1m.web.image2css;

/**
 * Interface, 'TEMPLATE', holds keys to properties found in file, 'image2css.properties'.
 * @see com.rf1m.util.PropertiesUtils
 */
public interface ContentTemplates {
    String ABOUT                = "Image2CSS 1.0.1, Matthew D. Huckaby, 2011";
    String REPORT_CSS_TOTAL 	= "Generated [%1$s] CSS entries";
    String REPORT_CSS_FILE 	    = "Created CSS file, %1$s";
    String REPORT_HTML_FILE 	= "Created HTML file, %1$s";

    public final static String PROPERTIES_FILE = "image2css.properties";

    public interface TEMPLATE{
        public static final String HTML_INDEX       = "template.html.index";
        public static final String HTML_CSS_ENTRY   = "template.html.css.entry";
        public static final String CSS_CLASS        = "template.css.class.def";
    }
}
