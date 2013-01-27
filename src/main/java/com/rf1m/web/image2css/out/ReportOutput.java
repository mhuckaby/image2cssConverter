package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.cli.Parameters;

import java.util.List;

public interface ReportOutput {
    void generateReportOutput(Parameters parameters, List<CSSClass> cssClasses);
}
