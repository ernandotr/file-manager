package dev.ernandorezende.file_manager.pdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class WaterMaeksPdfTest {

    @Test
    void givenAPDFFile_thenAddWaterMarkSuccess() {
        File output = new File("src/test/resources/watermarks.pdf");
        File input = new File("src/test/resources/input-files/contrato.pdf");

        WaterMarkPdf waterMarkPdf = new WaterMarkPdf();
        waterMarkPdf.addWaterMark(input, output);
        Assertions.assertTrue(output.exists());

    }
}
