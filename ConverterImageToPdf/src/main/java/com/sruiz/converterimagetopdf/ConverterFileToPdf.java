package com.sruiz.converterimagetopdf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Susana Ruiz
 */
public class ConverterFileToPdf {

    public static final String DEST = "here\\your\\destination\\folder";// i.e "C:\\Scanned Documents\\2019\\";
    public static final String PDF_EXT = ".pdf";
    public static final String JPG_EXT = ".jpg";
    public static final String IMAGES_FOLDER = "\\images\\"; // Folder to move images in
    public static final String PARENTHESIS = "(";
    public static final String DOT = ".";
    

    public static void main(String[] args) {
        File folder = new File(DEST);
        File[] listOfFiles = folder.listFiles();
        Map<String, Set<File>> filteredFiles = new HashMap<>();

        for (File file : listOfFiles) {

            if (file.isFile()) {
                String name = file.getName();
                // Just get images
                if (name.endsWith(JPG_EXT)) {
                    // Getting the name of a file without the extension and/or the more than one file (1), (2),....
                    String shortName = name.contains(PARENTHESIS) ? name.substring(0, name.lastIndexOf(PARENTHESIS)).trim() : name.substring(0, name.lastIndexOf(DOT));
                    filteredFiles.computeIfAbsent(shortName, x -> new HashSet<>()).add(file);
                }
            }
        }
        try {
            createPDFFromImages(filteredFiles);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public static void createPDFFromImages(Map<String, Set<File>> filteredFiles) throws IOException {
        createPDFFromImages(filteredFiles, IMAGES_FOLDER);
    }

    public static void createPDFFromImages(Map<String, Set<File>> filteredFiles, String newFolder) throws IOException {

        for (Map.Entry listImages : filteredFiles.entrySet()) {
            //Initialize PDF writer
            PdfWriter writer = new PdfWriter(DEST + "\\" + listImages.getKey() + PDF_EXT);

            //Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            for (File file : (HashSet<File>) listImages.getValue()) {
                // Include images
                Image img = new Image(ImageDataFactory.create(file.getAbsolutePath()));
                document.add(img);
                // To get image files on images folder after PDF document has been created
                File newPath = new File (file.getParentFile(), newFolder);
                file.renameTo(new File(newPath, file.getName()));
            }
            document.close();
            System.out.println("Done!");
        }
    }
}
