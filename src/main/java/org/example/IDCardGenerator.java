package org.example;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCAWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class IDCardGenerator {

    public static long generateRandom() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // first not 0 digit
        sb.append(random.nextInt(9) + 1);

        // rest of 11 digits
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }

        return Long.valueOf(sb.toString()).longValue();
    }

    public static void generateIDCard(long idNumber, String name, int age, boolean qrGenrator ) throws IOException {


        // Create a new PDF document
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        // Set the font and font size
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 18);

        // Draw the ID card header
        contentStream.beginText();


        contentStream.newLineAtOffset(25, 700);
        contentStream.showText("ID Card");
        contentStream.endText();



        // Draw the ID card data
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 650);

        contentStream.showText("ID: " + idNumber);

        contentStream.appendRawCommands(18 + " TL\n");
        contentStream.newLine();

        contentStream.showText("Name: " + name);

        contentStream.appendRawCommands(18 + " TL\n");
        contentStream.newLine();



        contentStream.showText("Age: " + age);

        contentStream.newLine();
        //  contentStream.showText("Address: " + address);
        contentStream.endText();

        if (qrGenrator ){
            PDImageXObject pdImage = PDImageXObject.createFromFile(idNumber+".png",doc);

            contentStream.drawImage(pdImage, 250, 600);
        }


        // Close the content stream
        contentStream.close();

        // Save the PDF document
        doc.save("IDCard.pdf");

        // Close the PDF document
        doc.close();
    }


  /*  public BufferedImage generateImage() {
        // create a BufferedImage object with the desired width and height
        BufferedImage image = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        // get the graphics context for the image
        Graphics2D g2d = image.createGraphics();

        // draw the ID card information on the image
        g2d.drawString(name, 10, 20);
        g2d.drawString(address, 10, 40);
        g2d.drawString(String.valueOf(idNumber), 10, 60);
        // draw the photo on the image
        BufferedImage photo = ImageIO.read(new File(photoFilename));
        g2d.drawImage(photo, 200, 10, null);

        // dispose of the graphics context
        g2d.dispose();

        return image;
    }*/


   /* public void saveImage(String filename) throws IOException {
        BufferedImage image = generateImage();
        ImageIO.write(image, "PNG", new File(filename));
    }*/


   /* public static void main(String[] args) {
        IDCard card = new IDCard();
        card.inputInformation();
        try {
            card.saveImage("idcard.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    static boolean barcodeGenerator(long IdNumber, String Name, int age) throws IOException, WriterException {
        String text = String.valueOf(IdNumber);

        int width  = 300;
        int height = 100;
        String imgFormat = "png";

        System.out.println(IdNumber);
        System.out.println(text.length());

        if(text.length() == 12){
            BitMatrix bitMatrix = new UPCAWriter().encode(text, BarcodeFormat.UPC_A, width, height);
            MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, new FileOutputStream(new    File(IdNumber+".png")));

            File f = new File(IdNumber+".png");

            if(f.exists() && !f.isDirectory()) {
                return  true;
            }

            return  false;
        }

        else {
            return  false;
        }


    }


    public static void main(String[] args) throws IOException, WriterException {

         long idNumber = generateRandom();



        // Get the ID card data from the user
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = sc.nextLine();

        System.out.println("Enter your age: ");
        int age = sc.nextInt();


        barcodeGenerator(idNumber,name,age);

        generateIDCard(idNumber, name, age, true );


    }
}
