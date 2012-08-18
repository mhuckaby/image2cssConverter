package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.cli.Parameters;

import java.io.IOException;
import java.util.List;

public interface Output {
    void out(Parameters parameters, List<CSSClass> cssClasses) throws IOException;
}
