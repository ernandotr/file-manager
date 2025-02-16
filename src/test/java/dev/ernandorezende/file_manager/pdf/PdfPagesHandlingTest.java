package dev.ernandorezende.file_manager.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class PdfPagesHandlingTest {

    @BeforeEach
    void setUp() {
        File tempDir = new File("src/test/resources/temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        List.of(List.of("Text1", "sample.pdf"))
                .forEach(pair -> {
                    try {
                        createPDFDoc(pair.get(0), pair.get(1));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void createPDFDoc(String content, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        for(int i = 0; i < 3; i++) {
            PDPage page = new PDPage();
            document.addPage(page);
            try(PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.showText(content + ", page:"+i);
                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        document.save("src/test/resources/temp/" + filePath);
        document.close();
    }

    @AfterEach
    void destroy() throws IOException {
        Stream<Path> paths = Files.walk(Paths.get("src/test/resources/temp/"));
        paths.sorted((p1, p2)  -> -p1.compareTo(p2))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    void testRemovePages() throws IOException {
        String filename = "src/test/resources/temp/sample.pdf";
        PdfPagesHandling pdfRemovePages = new PdfPagesHandling();
        pdfRemovePages.removePage(filename, 2);
        PDDocument document = PDDocument.load(new File("src/test/resources/temp/newFile.pdf"));
        Assertions.assertEquals(2, document.getNumberOfPages());
        document.close();
    }

    @Test
    void testAddPages() throws IOException {
        String filename = "src/test/resources/temp/sample.pdf";
        PdfPagesHandling pdfRemovePages = new PdfPagesHandling();
        pdfRemovePages.addPage(filename, 2);
        PDDocument document = PDDocument.load(new File("src/test/resources/temp/newFile.pdf"));
        Assertions.assertEquals(4, document.getNumberOfPages());
        document.close();
    }
}
