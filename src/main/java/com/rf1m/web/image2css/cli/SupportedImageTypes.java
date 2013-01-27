package com.rf1m.web.image2css.cli;

import java.util.HashSet;
import java.util.Set;

public enum SupportedImageTypes {
	gif,
    jpg,
    jpeg,
    png;

    public Set<SupportedImageTypes> asSet(){
        Set<SupportedImageTypes> result = new HashSet<SupportedImageTypes>();
        result.add(SupportedImageTypes.gif);
        result.add(SupportedImageTypes.jpg);
        result.add(SupportedImageTypes.png);
        return result;
    }
}
