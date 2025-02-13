package dev.ernandorezende.file_manager.pdf;

import com.itextpdf.text.pdf.PdfPage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfRemovePages {
    public void removePage(String fileName, int pageNumber) throws IOException {
        File file = new File(fileName);

        PDDocument document = PDDocument.load(file);

        // Listing the number of existing pages
        int noOfPages = document.getNumberOfPages();
        System.out.print(noOfPages);

        // Removing the pages
        document.removePage(pageNumber);

        System.out.println("page removed");
        // Saving the document
        document.save(file.getParent()+ "/newFile.pdf");

        // Closing the document
        document.close();
    }
}
