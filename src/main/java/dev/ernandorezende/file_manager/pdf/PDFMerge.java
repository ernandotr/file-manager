package dev.ernandorezende.file_manager.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PDFMerge {
    public void mergePdf(List<String> pdfFiles, String outputFile) throws IOException {
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        mergerUtility.setDestinationFileName(outputFile);

        pdfFiles.forEach(pdfFile -> {
            try {
                mergerUtility.addSource(new File(pdfFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }
}
