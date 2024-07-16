package com.nhnacademy.document.parser.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.ast.Document;
import org.junit.jupiter.api.Test;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class ApacheAscii {
    @Test
    void name() throws IOException {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        Document document = asciidoctor.loadFile(new File("asciidocs/template.adoc"), Options.builder().build());
        File output = new File("asciidocs/test.html");


        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));

        bufferedWriter.write(document.convert());
        bufferedWriter.flush();

    }
}
