package dev.ernandorezende.file_manager.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class WaterMaeksPdfTest {

    @Test
    void givenAPDFFile_thenAddWaterMarkSuccess() throws IOException {
        String out = "src/test/resources/watermarks.pdf";
        String input = "src/test/resources/input-files/contrato.pdf";

        String watermark = "CONFIDENTIAL";
        WaterMarkPdf waterMarkPdf = new WaterMarkPdf();

        try(PdfDocument pdfDocument = new PdfDocument(new PdfReader(input), new PdfWriter(out))) {
            Document document = new Document(pdfDocument);
            Paragraph paragraph = waterMarkPdf.createWatermarkParagraph(watermark, 0.1f, 56);
            PdfExtGState extGState = new PdfExtGState().setFillOpacity(0.5f);
            for (int i = 0; i < document.getPdfDocument().getNumberOfPages(); i++) {
                waterMarkPdf.addWatermarkToExistingPDF(document, i+1, paragraph, extGState, 0f);
            }

        }

        File file = new File(out);
        Assertions.assertTrue(file.exists());

    }
}
