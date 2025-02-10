package dev.ernandorezende.file_manager.pdf;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


public class SplitPdf {

    private final Logger logger = Logger.getLogger("SplitPdf");

    public void splitPdf(String inputFile, String filesDir) throws IOException {
        File pdfFile = new File(inputFile);

        try (PDDocument document = PDDocument.load(pdfFile)) {
            Splitter splitter = new Splitter();

            List<PDDocument> pages = splitter.split(document);
            Iterator<PDDocument> iterator = pages.iterator();
            int pageNumber = 1;
            while (iterator.hasNext()) {
                PDDocument page = iterator.next();
                page.save(filesDir+"page-" + pageNumber++ +".pdf");
                page.close();
            }

            logger.info("Finished splitting PDF file");
        };

    }
}
