package com.rf1m.util;

import com.rf1m.web.image2css.ContentTemplates;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static com.rf1m.util.ValidatorUtils.isEmpty;

public class PropertiesUtils implements ContentTemplates {
    protected static Properties properties;

    static {
        try{
            URL url = ClassLoader.getSystemResource(PROPERTIES_FILE);
            properties = new Properties();
            properties.load(url.openStream());
        }catch(IOException ioException){
            String exceptionMessage =
                String.format("unable to read properties file, \"%1$s\"", PROPERTIES_FILE);
            throw new RuntimeException(exceptionMessage, ioException);
        }
    }

    public static String getProperty(String parameter){
		return getProperties(parameter)[0];
	}

	public static String[] getProperties(String ... parameters){
		if(isEmpty(parameters)){
			throw new IllegalArgumentException("parameters is null or empty");
		}else{
			String[] result = new String[parameters.length];
			int i=0;
                for(i=0;i<parameters.length;i++){
					if(isEmpty(parameters[i])){
						throw new IllegalArgumentException("parameter is null or empty");
					}else{
						result[i] = properties.getProperty(parameters[i]);
					}
				}
				return result;
		}
	}
}