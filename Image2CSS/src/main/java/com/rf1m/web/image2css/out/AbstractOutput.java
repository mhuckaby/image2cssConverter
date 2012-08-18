package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class AbstractOutput implements Output{
    protected class FileWriterFactory{
        public FileWriter newFileWriter(File file) throws IOException {
            return new FileWriter(file);
        }
    }

    protected class StringBufferFactory{
        public StringBuffer newStringBuffer(){
            return new StringBuffer();
        }
    }

    protected FileWriterFactory fileWriterFactory;
    protected StringBufferFactory stringBufferFactory;

    public AbstractOutput(){
        this.fileWriterFactory = new FileWriterFactory();
        this.stringBufferFactory = new StringBufferFactory();
    }

    protected boolean validate(List<CSSClass> cssClasses) {
        if(null == cssClasses){
            throw new IllegalArgumentException("CSSClasses is null");
        }else{
            return true;
        }
    }


}
