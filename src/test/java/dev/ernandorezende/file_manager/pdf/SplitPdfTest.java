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

public class SplitPdfTest {


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
    void splitPdf() throws IOException {
        Stream<Path> paths = Files.list(Paths.get("src/test/resources/temp/"));
        Assertions.assertEquals(1, paths.count());
        String tempDir = "src/test/resources/temp/";
        String pdfDir = "src/test/resources/temp/sample.pdf";
        SplitPdf splitPdf = new SplitPdf();
        splitPdf.splitPdf(pdfDir, tempDir);
        Assertions.assertEquals(4, Files.list(Paths.get("src/test/resources/temp/")).count());

    }
}
