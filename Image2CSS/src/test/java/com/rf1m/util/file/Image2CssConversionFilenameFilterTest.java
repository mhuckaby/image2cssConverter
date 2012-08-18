package com.rf1m.util.file;

import com.rf1m.web.image2css.cli.SupportedImageTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class Image2CssConversionFilenameFilterTest {
    Image2CssConversionFilenameFilter image2CssConversionFilenameFilter;

    Set<SupportedImageTypes> supportedTypes;

    @Mock File directory;

    @Before
    public void before(){
        supportedTypes = new HashSet<SupportedImageTypes>();
        image2CssConversionFilenameFilter = new Image2CssConversionFilenameFilter(supportedTypes);
    }

    @Test
    public void shouldAcceptOnlySupportedTypes(){
        final String acceptedFileName = "kite.gif";
        final String rejectedFileName = "panda.jpg";

        supportedTypes.add(SupportedImageTypes.gif);

        assertTrue(image2CssConversionFilenameFilter.accept(directory, acceptedFileName));
        assertFalse(image2CssConversionFilenameFilter.accept(directory, rejectedFileName));
    }

    @Test
    public void shouldRejectFilesWithNoExtension(){
        final String rejectedFileNameWithDot = "kite.";
        final String rejectedFileName = "panda";

        supportedTypes.add(SupportedImageTypes.gif);

        assertFalse(image2CssConversionFilenameFilter.accept(directory, rejectedFileNameWithDot));
        assertFalse(image2CssConversionFilenameFilter.accept(directory, rejectedFileNameWithDot));
    }


}