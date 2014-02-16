package com.rf1m.image2css.ioc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:image2css-cmn-context.xml"})
public class ContextWiringTest {

    @Test
    public void shouldStandUpContextSuccessfully() {}

}
