import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Converts PDF pages to images locally using Apache PDFBox.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="PdfToImageConverter"
 * -Dexec.args="path/to/your.pdf"
 */
public class PdfToImageConverter {

    private static final int DEFAULT_DPI = 150;

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: PdfToImageConverter <path-to-pdf> [dpi]");
                System.err.println(
                        "Example: mvn exec:java -Dexec.mainClass=\"PdfToImageConverter\" -Dexec.args=\"pdf/sample.pdf 150\"");
                System.exit(1);
            }

            File pdfFile = new File(args[0]);
            if (!pdfFile.exists()) {
                System.err.println("Error: PDF file not found: " + args[0]);
                System.exit(1);
            }

            int dpi = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_DPI;
            File outputDir = new File("output");
            outputDir.mkdirs();

            System.out.println("==================================================");
            System.out.println("        PDF to Image Converter (PDFBox)           ");
            System.out.println("==================================================\n");
            System.out.println("Input PDF:  " + pdfFile.getName());
            System.out.println("Output DPI: " + dpi);
            System.out.println("Output Dir: " + outputDir.getAbsolutePath() + "\n");

            // Example 1: Convert all pages to PNG
            System.out.println("--- Converting to PNG ---");
            convertToPng(pdfFile, dpi, outputDir);

            // Example 2: Convert all pages to JPEG with quality control
            System.out.println("\n--- Converting to JPEG (75% quality) ---");
            convertToJpeg(pdfFile, dpi, outputDir, 0.75f);

            // Example 3: Render a single page as BufferedImage
            System.out.println("\n--- Rendering Single Page as BufferedImage ---");
            BufferedImage firstPage = renderSinglePage(pdfFile, 0, dpi);
            System.out.println("Page 1 rendered: " + firstPage.getWidth() + "x" + firstPage.getHeight() + " pixels");

            System.out.println("\n[OK] All conversions complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts all PDF pages to PNG images.
     */
    public static void convertToPng(File pdfFile, int dpi, File outputDir) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            String baseName = pdfFile.getName().replaceFirst("[.][^.]+$", "");

            for (int i = 0; i < pageCount; i++) {
                // Render page at specified DPI as RGB image
                BufferedImage image = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);

                // Save as PNG
                String fileName = String.format("%s_page_%03d.png", baseName, i + 1);
                File outputFile = new File(outputDir, fileName);
                ImageIO.write(image, "PNG", outputFile);

                System.out.println("  Page " + (i + 1) + ": " + fileName +
                        " (" + (outputFile.length() / 1024) + " KB)");
            }
        }
    }

    /**
     * Converts all PDF pages to JPEG with quality control.
     */
    public static void convertToJpeg(File pdfFile, int dpi, File outputDir, float quality)
            throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            String baseName = pdfFile.getName().replaceFirst("[.][^.]+$", "");

            // Get JPEG writer and configure quality
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(quality); // 0.0 to 1.0

            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);

                String fileName = String.format("%s_page_%03d.jpg", baseName, i + 1);
                File outputFile = new File(outputDir, fileName);

                // Write with quality settings
                try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                    writer.setOutput(ios);
                    writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);
                }

                System.out.println("  Page " + (i + 1) + ": " + fileName +
                        " (" + (outputFile.length() / 1024) + " KB)");
            }

            writer.dispose();
        }
    }

    /**
     * Renders a single page from PDF as a BufferedImage.
     * Useful when you need to process the image in memory.
     */
    public static BufferedImage renderSinglePage(File pdfFile, int pageIndex, int dpi)
            throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            if (pageIndex < 0 || pageIndex >= document.getNumberOfPages()) {
                throw new IllegalArgumentException(
                        "Page index " + pageIndex + " out of range (0-" +
                                (document.getNumberOfPages() - 1) + ")");
            }

            PDFRenderer renderer = new PDFRenderer(document);
            return renderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);
        }
    }
}