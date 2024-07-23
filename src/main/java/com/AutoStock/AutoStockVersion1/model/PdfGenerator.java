package com.AutoStock.AutoStockVersion1.model;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfGenerator {

    public void generatePdf(String filename, String content) throws IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph(content));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Error generating PDF", e);
        } finally {
            document.close();
        }
    }
}
