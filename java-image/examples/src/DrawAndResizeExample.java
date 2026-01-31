import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Demonstrates drawing on images and resizing techniques.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="DrawAndResizeExample"
 */
public class DrawAndResizeExample {

    public static void main(String[] args) {
        try {
            File outputDir = new File("output");
            outputDir.mkdirs();

            System.out.println("==================================================");
            System.out.println("        Draw and Resize Examples                  ");
            System.out.println("==================================================\n");

            // Example 1: Draw on a new image
            System.out.println("--- Drawing Example ---");
            BufferedImage drawn = drawExample();
            ImageIO.write(drawn, "PNG", new File("output/drawn.png"));
            System.out.println("Saved: output/drawn.png");

            // Example 2: Resize using Graphics2D (recommended)
            System.out.println("\n--- Resize with Graphics2D ---");
            BufferedImage resized1 = resizeWithGraphics2D(drawn, 400, 300);
            ImageIO.write(resized1, "PNG", new File("output/resized-graphics2d.png"));
            System.out.println("Saved: output/resized-graphics2d.png (" +
                    resized1.getWidth() + "x" + resized1.getHeight() + ")");

            // Example 3: Resize using AffineTransform
            System.out.println("\n--- Resize with AffineTransform ---");
            BufferedImage resized2 = resizeWithAffineTransform(drawn, 400, 300);
            ImageIO.write(resized2, "PNG", new File("output/resized-transform.png"));
            System.out.println("Saved: output/resized-transform.png (" +
                    resized2.getWidth() + "x" + resized2.getHeight() + ")");

            System.out.println("\n[OK] All examples complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create an image with shapes, gradients, and text.
     */
    public static BufferedImage drawExample() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Enable high-quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fill background with gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(59, 130, 246), // Blue
                800, 600, new Color(139, 92, 246) // Purple
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Draw semi-transparent shapes
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillOval(100, 100, 200, 200);
        g2d.fillRect(500, 300, 200, 200);

        // Draw text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 48));
        g2d.drawString("Java Images", 250, 320);

        g2d.dispose();
        return image;
    }

    /**
     * Resize using Graphics2D (recommended for quality and speed).
     */
    public static BufferedImage resizeWithGraphics2D(BufferedImage original,
            int targetWidth,
            int targetHeight) {
        BufferedImage resized = new BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resized.createGraphics();

        // Set high-quality rendering hints
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resized;
    }

    /**
     * Resize using AffineTransform (best for combined transformations).
     */
    public static BufferedImage resizeWithAffineTransform(BufferedImage original,
            int targetWidth,
            int targetHeight) {
        BufferedImage resized = new BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        double scaleX = (double) targetWidth / original.getWidth();
        double scaleY = (double) targetHeight / original.getHeight();

        AffineTransform transform = AffineTransform.getScaleInstance(scaleX, scaleY);
        g2d.drawImage(original, transform, null);
        g2d.dispose();

        return resized;
    }
}