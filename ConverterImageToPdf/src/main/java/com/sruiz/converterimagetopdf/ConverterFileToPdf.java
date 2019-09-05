package com.sruiz.converterimagetopdf;

import java.util.*;
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
                if (name.contains(JPG_EXT)) {
                    // Getting the name of a file without the extension and/or the more than one file (1), (2),....
                    String short_name = short_name = name.contains(PARENTHESIS) ? (String) name.substring(0, name.lastIndexOf(PARENTHESIS)).trim() : (String) name.substring(0, name.lastIndexOf(DOT));
                    Set<File> subListImages = new HashSet<>();
                    if (filteredFiles.containsKey(short_name)) {
                        subListImages = filteredFiles.get(short_name);
                    }
                    subListImages.add(file);
                    filteredFiles.put(short_name, subListImages);
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

    public static void createPDFFromImages(Map<String, Set<File>> filteredFiles, String new_folder) throws IOException {

        for (Map.Entry listImages : filteredFiles.entrySet()) {
            //Initialize PDF writer
            PdfWriter writer = new PdfWriter(DEST + listImages.getKey() + PDF_EXT);

            //Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            for (File file : (HashSet<File>) listImages.getValue()) {
                // Include images
                Image img = new Image(ImageDataFactory.create(file.getAbsolutePath()));
                document.add(img);
                // To get image files on images folder after PDF document has been created
                String new_path = pathComponent(file.getAbsolutePath()) + new_folder;
                file.renameTo(new File(new_path + file.getName()));
                System.out.println(new_path + file.getName());
            }
            document.close();
            System.out.println("Done!");
        }
    }

    /**
     * Remove file information from a filename returning only its path component
     *
     * @param filename The filename
     * @return The path information
     *
     */
    public static String pathComponent(String filename) {
        int i = filename.lastIndexOf(File.separator);
        return (i > -1) ? filename.substring(0, i) : filename;
    }
}
