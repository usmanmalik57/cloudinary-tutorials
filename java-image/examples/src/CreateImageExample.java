import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Demonstrates creating images in memory with different properties.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="CreateImageExample"
 */
public class CreateImageExample {

    public static void main(String[] args) {
        try {
            File outputDir = new File("output");
            outputDir.mkdirs();

            System.out.println("==================================================");
            System.out.println("         Create Image Examples                    ");
            System.out.println("==================================================\n");

            // Example 1: Create transparent image
            System.out.println("--- Transparent Image ---");
            BufferedImage transparent = createTransparentImage();
            ImageIO.write(transparent, "PNG", new File("output/transparent.png"));
            System.out.println("Saved: output/transparent.png");
            System.out.println("Type: TYPE_INT_ARGB (supports transparency)");

            // Example 2: Create gradient image
            System.out.println("\n--- Gradient Image ---");
            BufferedImage gradient = createGradientImage();
            ImageIO.write(gradient, "PNG", new File("output/gradient.png"));
            System.out.println("Saved: output/gradient.png");

            // Example 3: Create grayscale image
            System.out.println("\n--- Grayscale Image ---");
            BufferedImage grayscale = createGrayscaleImage();
            ImageIO.write(grayscale, "PNG", new File("output/grayscale.png"));
            System.out.println("Saved: output/grayscale.png");
            System.out.println("Type: TYPE_BYTE_GRAY (8-bit grayscale)");

            System.out.println("\n[OK] All examples complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create an image with transparent overlapping shapes.
     */
    public static BufferedImage createTransparentImage() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw semi-transparent circle
        g2d.setColor(new Color(59, 130, 246, 180)); // Blue, 70% opacity
        g2d.fillOval(50, 50, 200, 200);

        // Draw overlapping semi-transparent rectangle
        g2d.setColor(new Color(239, 68, 68, 150)); // Red, 60% opacity
        g2d.fillRect(150, 150, 200, 200);

        g2d.dispose();
        return image;
    }

    /**
     * Create a gradient background with centered text.
     */
    public static BufferedImage createGradientImage() {
        int width = 600;
        int height = 300;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Create gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(139, 92, 246), // Purple
                width, 0, new Color(59, 130, 246) // Blue
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Add centered text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 42));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "Java Images";
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = (height + fm.getAscent()) / 2 - 5;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
        return image;
    }

    /**
     * Create a grayscale gradient image.
     */
    public static BufferedImage createGrayscaleImage() {
        int width = 400;
        int height = 300;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();

        // Draw vertical gradient from black to white
        for (int x = 0; x < width; x++) {
            int grayLevel = (int) (255.0 * x / width);
            g2d.setColor(new Color(grayLevel, grayLevel, grayLevel));
            g2d.drawLine(x, 0, x, height);
        }

        g2d.dispose();
        return image;
    }
}