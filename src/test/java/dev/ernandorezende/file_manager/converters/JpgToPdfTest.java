package dev.ernandorezende.file_manager.converters;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JpgToPdfTest {

    String tempDir = "src/test/resources/temp/";

    @Test
    public void testJpgToPdfConvert() throws DocumentException, IOException {
        String filename = "image1";
        String fileType = ".jpg";
        ImageToPdf jpgToPdf = new ImageToPdf();
        jpgToPdf.convert(tempDir, filename, fileType);

        Assertions.assertTrue(Files.exists(Paths.get(tempDir + filename+".pdf")));
    }

    @Test
    public void testJpegToPdfConvert() throws DocumentException, IOException {
        String filename = "image2";
        String fileType = ".jpeg";
        ImageToPdf jpgToPdf = new ImageToPdf();
        jpgToPdf.convert(tempDir, filename, fileType);
        Assertions.assertTrue(Files.exists(Paths.get(tempDir + filename+".pdf")));
    }

    @Test
    public void testPngToPdfConvert() throws DocumentException, IOException {
        String filename = "image3";
        String fileType = ".png";
        ImageToPdf jpgToPdf = new ImageToPdf();
        jpgToPdf.convert(tempDir, filename, fileType);
        Assertions.assertTrue(Files.exists(Paths.get(tempDir + filename+".pdf")));
    }

}
