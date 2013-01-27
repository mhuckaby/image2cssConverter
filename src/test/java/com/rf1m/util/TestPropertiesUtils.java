package com.rf1m.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static junit.framework.Assert.assertEquals;

public class TestPropertiesUtils {
    final Properties testProperties = new Properties();
    final String key1 = "key1";
    final String value1 = "value1";

    @Before
    public void before(){
        testProperties.put(key1, value1);
        PropertiesUtils.properties = testProperties;
    }

    @Test
    public void testGetProperty(){
        assertEquals(value1, PropertiesUtils.getProperty(key1));
    }

    @Test
    public void testGetProperties(){
        assertEquals(value1, PropertiesUtils.getProperties(key1)[0]);
    }
}