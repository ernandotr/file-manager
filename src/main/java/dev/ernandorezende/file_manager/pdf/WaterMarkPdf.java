package dev.ernandorezende.file_manager.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.IOException;

import static com.itextpdf.layout.properties.TextAlignment.CENTER;
import static com.itextpdf.layout.properties.VerticalAlignment.TOP;
import static java.lang.Math.PI;

public class WaterMarkPdf {

    public Paragraph createWatermarkParagraph(String watermark, float opacity, int fontSize) throws IOException {

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Text text = new Text(watermark);
        text.setFont(font);
        text.setFontSize(fontSize);
        text.setOpacity(opacity);
        return new Paragraph(text);
    }

    public void addWatermarkToExistingPDF(Document document, int pageIndex,
                                          Paragraph paragraph, PdfExtGState graphicState, float verticalOffset) {

        PdfDocument pdfDocument = document.getPdfDocument();
        PdfPage pdfPage = pdfDocument.getPage(pageIndex);
        PageSize pageSize = (PageSize) pdfPage.getPageSizeWithRotation();
        float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
        float y = (pageSize.getTop() + pageSize.getBottom()) / 2;

        PdfCanvas over = new PdfCanvas(pdfDocument.getPage(pageIndex));
        over.saveState();
        over.setExtGState(graphicState);
        float xOffset = 14.0f / 2;
        float rotationInRadians = (float) (PI / 180 * 45f);

        document.showTextAligned(paragraph, x - xOffset, y + verticalOffset,
                pageIndex, CENTER, TOP, rotationInRadians);
        document.flush();
        over.restoreState();
        over.release();
    }
}
