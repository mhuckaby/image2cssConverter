package com.rf1m.util.bin;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

// Limits JVM to Sun/Oracle distribution
//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Base64Encoder {
    public final static String NL 		= "\n";
    public final static String EMPTY 	= "";

    public String base64EncodeBytes(byte[] bytes) {
        String encoded = new String(encodeBase64(bytes, false));
        encoded = encoded.replaceAll(NL, EMPTY);
        return encoded;
    }
}
