package dev.ernandorezende.file_manager.converters;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageToPdf {

    public static final String OUTPUT_EXTENSION_FILE = ".pdf";

    public  void convert(String tempDir, String fileName, String fileType) throws IOException, DocumentException {
        File root = new File(tempDir);
        if (!root.exists()) {
            root.mkdirs();
        }
        String outputFile = fileName + OUTPUT_EXTENSION_FILE;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
        document.open();

        document.newPage();
        Image image = Image.getInstance(new File(root,fileName+fileType).getAbsolutePath());
        image.setAbsolutePosition(0, 0);
        image.setBorderWidth(0);
        image.scaleAbsolute(PageSize.A4);
        document.add(image);
        document.close();
    }
}
