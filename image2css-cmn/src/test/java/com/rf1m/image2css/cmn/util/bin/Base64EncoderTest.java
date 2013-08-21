package com.rf1m.image2css.cmn.util.bin;

import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Base64EncoderTest {
    final String encoded = "encoded\n";

    @Mock
    CommonObjectFactory commonObjectFactory;

    Base64Encoder base64Encoder;

    @Before
    public void before() {
        base64Encoder = new Base64Encoder(commonObjectFactory);

        when(commonObjectFactory.newString(any(byte[].class)))
                .thenReturn(encoded);
    }

    @Test
    public void shouldEncodeBytesAndReturnStringWithoutNewLineCharacters() {
        final byte[] bytes = {0};

        final String result = base64Encoder.base64EncodeBytes(bytes);

        assertFalse(result.contains("\n"));
    }
}
