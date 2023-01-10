package org.example;

import com.google.zxing.WriterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class IDCardGeneratorTest {
    private IDCardGenerator idCardGenerator;

    @Before
    public void setUp() {
        idCardGenerator = new IDCardGenerator();
    }

    @Test
    public void testGenerateIDCard() throws IOException, WriterException {
        // Test with valid input
        IDCardGenerator.generateIDCard( 961066778, "skmnjdns", 33 , false);

        // Check that the PDF file was created
        File pdfFile = new File("IDCard.pdf");
        assertTrue(pdfFile.exists());

        // Check that the PDF contains the correct name and age
        PDDocument doc = PDDocument.load(pdfFile);
        String pdfText = new PDFTextStripper().getText(doc);
        assertTrue(pdfText.contains("Name: skmnjdns"));
        assertTrue(pdfText.contains("Age: 33"));
        // Close the PDF document
        doc.close();
    }


    @Test
    public void testCheckPdfExist() throws IOException, WriterException {
        // Test with valid input
        IDCardGenerator.generateIDCard( 961066778, "skmnjdns", 33 , false);

        // Check that the PDF file was created
        File pdfFile = new File("IDCard.pdf");
        assertTrue(pdfFile.exists());

    }


    @Test
    public void testCheckPdfNotExist() throws IOException, WriterException {
        // Test with valid input
        IDCardGenerator.generateIDCard( 961066778, "skmnjdns", 33 , false);

        // Check that the PDF file was created
        File pdfFile = new File("IDCard.pdf");
        assertFalse(!pdfFile.exists());

    }


    @Test
    public void testGenerateIDCardInvalid() throws IOException {


        // Test with valid input
        IDCardGenerator.generateIDCard( 961066778, "skmnjdns", 33 , false);

        // Check that the PDF file was created
        File pdfFile = new File("IDCard.pdf");
        assertTrue(pdfFile.exists());

        // Check that the PDF contains the correct name and age
        PDDocument doc = PDDocument.load(pdfFile);
        String pdfText = new PDFTextStripper().getText(doc);
        assertFalse(pdfText.contains("Name: Mazhar"));
        assertFalse(pdfText.contains("Age: -1"));

        // Close the PDF document
        doc.close();

    }

    @Test

    public  void testqrcode() throws IOException, WriterException {

        IDCardGenerator.barcodeGenerator(961066778, "skmnjdns", 33 );

        File pdfFile = new File(961066778+".png");
        assertTrue(pdfFile.exists());

    }

    @Test

    public  void testqrcodenotExist() throws IOException, WriterException {

        IDCardGenerator.barcodeGenerator(961066778, "skmnjdns", 33 );

        File pdfFile = new File(961066778+".png");
        assertTrue(!pdfFile.exists());
    }

}
