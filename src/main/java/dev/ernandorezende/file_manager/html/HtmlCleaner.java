package dev.ernandorezende.file_manager.html;

import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HtmlCleaner {
    public String clean(String input) {
        return clean(input, null, null);
    }

    public String clean(String input, String inputEncoding, String outputEncoding) {
        InputStream inputStream;
        try {
            inputStream = new ByteArrayInputStream(input.getBytes(inputEncoding != null ? inputEncoding : "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(inputEncoding != null ? inputEncoding : "UTF-8");
        tidy.setOutputEncoding(outputEncoding != null ? outputEncoding : "UTF-8");
        tidy.setXHTML(true);
        tidy.parse(inputStream, outputStream);

        try {
            String result = outputStream.toString(inputEncoding != null ? inputEncoding : "UTF-8");
            return result.trim();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String cleanWithInputEncoding(String htmlFile, String inputEncode) {
        return clean(htmlFile, inputEncode, null);
    }

    public String cleanWithOutputEncoding(String input, String outputEncoding) {
        return clean(input, null, outputEncoding);
    }
}
