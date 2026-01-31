import javax.imageio.*;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

/**
 * Demonstrates saving images in different formats with quality control.
 * 
 * Usage:
 *   mvn exec:java -Dexec.mainClass="SaveImageExample"
 */
public class SaveImageExample {

    public static void main(String[] args) {
        try {
            File outputDir = new File("output");
            outputDir.mkdirs();

            System.out.println("==================================================");
            System.out.println("          Save Image Examples                     ");
            System.out.println("==================================================\n");

            // Create a sample image
            BufferedImage image = createSampleImage();

            // Save as PNG (lossless)
            System.out.println("--- Saving as PNG ---");
            File pngFile = new File("output/saved.png");
            ImageIO.write(image, "PNG", pngFile);
            System.out.println("Saved: " + pngFile.getName() + 
                " (" + (pngFile.length() / 1024) + " KB)");

            // Save as JPEG with different quality levels
            System.out.println("\n--- Saving as JPEG (various quality) ---");
            saveJpegWithQuality(image, new File("output/quality-95.jpg"), 0.95f);
            saveJpegWithQuality(image, new File("output/quality-75.jpg"), 0.75f);
            saveJpegWithQuality(image, new File("output/quality-50.jpg"), 0.50f);

            // Save as GIF
            System.out.println("\n--- Saving as GIF ---");
            File gifFile = new File("output/saved.gif");
            ImageIO.write(image, "GIF", gifFile);
            System.out.println("Saved: " + gifFile.getName() + 
                " (" + (gifFile.length() / 1024) + " KB)");

            System.out.println("\n[OK] All save examples complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create a sample image for demonstration.
     */
    public static BufferedImage createSampleImage() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(59, 130, 246),
            800, 600, new Color(139, 92, 246)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 64));
        g2d.drawString("Quality Test", 180, 320);

        g2d.dispose();
        return image;
    }

    /**
     * Save JPEG with custom quality setting.
     * 
     * @param image   Image to save
     * @param file    Output file
     * @param quality Quality from 0.0 (lowest) to 1.0 (highest)
     */
    public static void saveJpegWithQuality(BufferedImage image, File file, float quality)
            throws Exception {

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writer available");
        }

        ImageWriter writer = writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(file);
        writer.setOutput(ios);

        JPEGImageWriteParam param = new JPEGImageWriteParam(null);
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        writer.write(null, new IIOImage(image, null, null), param);

        ios.close();
        writer.dispose();

        int qualityPercent = (int) (quality * 100);
        System.out.println("Quality " + qualityPercent + "%: " + file.getName() + 
            " (" + (file.length() / 1024) + " KB)");
    }
}