package dev.ernandorezende.file_manager.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PdfPagesManipulating {

    public void removePage(String fileName, int pageNumber) throws IOException {
        File file = new File(fileName);

        PDDocument document = PDDocument.load(file);

        int noOfPages = document.getNumberOfPages();
        System.out.print(noOfPages);

        document.removePage(pageNumber);

        System.out.println("page removed");
        document.save(file.getParent()+ "/newFile.pdf");

        document.close();
    }

    public void addPage(String fileName, int pageNumber) throws IOException {
        File file = new File(fileName);
        PDDocument document = PDDocument.load(file);
        PDPage page = new PDPage();

        document.addPage(page);
        document.save(file.getParent()+ "/newFile.pdf");
        document.close();
    }

}
