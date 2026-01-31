import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

/**
 * Demonstrates image editing operations and filters.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="EditAndFiltersExample"
 */
public class EditAndFiltersExample {

    public static void main(String[] args) {
        try {
            File outputDir = new File("output");
            outputDir.mkdirs();

            System.out.println("==================================================");
            System.out.println("       Edit and Filter Examples                   ");
            System.out.println("==================================================\n");

            // Create a sample image
            BufferedImage original = createSampleImage();
            ImageIO.write(original, "PNG", new File("output/edit-original.png"));
            System.out.println("Created original: 800x600");

            // Example 1: Crop
            System.out.println("\n--- Cropping ---");
            BufferedImage cropped = cropImage(original, 200, 150, 400, 300);
            ImageIO.write(cropped, "PNG", new File("output/edit-cropped.png"));
            System.out.println("Saved: output/edit-cropped.png (400x300)");

            // Example 2: Rotate
            System.out.println("\n--- Rotating ---");
            BufferedImage rotated = rotateImage(original, 15);
            ImageIO.write(rotated, "PNG", new File("output/edit-rotated.png"));
            System.out.println("Saved: output/edit-rotated.png (rotated 15°)");

            // Example 3: Adjust opacity
            System.out.println("\n--- Adjusting Opacity ---");
            BufferedImage transparent = adjustOpacity(original, 0.5f);
            ImageIO.write(transparent, "PNG", new File("output/edit-transparent.png"));
            System.out.println("Saved: output/edit-transparent.png (50% opacity)");

            // Example 4: Blur filter
            System.out.println("\n--- Applying Blur ---");
            BufferedImage blurred = applyBlur(original);
            ImageIO.write(blurred, "PNG", new File("output/filter-blur.png"));
            System.out.println("Saved: output/filter-blur.png");

            // Example 5: Sharpen filter
            System.out.println("\n--- Applying Sharpen ---");
            BufferedImage sharpened = applySharpen(original);
            ImageIO.write(sharpened, "PNG", new File("output/filter-sharpen.png"));
            System.out.println("Saved: output/filter-sharpen.png");

            // Example 6: Edge detection
            System.out.println("\n--- Edge Detection ---");
            BufferedImage edges = applyEdgeDetection(original);
            ImageIO.write(edges, "PNG", new File("output/filter-edges.png"));
            System.out.println("Saved: output/filter-edges.png");

            System.out.println("\n[OK] All examples complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create sample image for testing.
     */
    public static BufferedImage createSampleImage() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(59, 130, 246),
                800, 600, new Color(139, 92, 246));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);

        // Shapes
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.fillOval(150, 100, 200, 200);
        g2d.fillRect(450, 300, 200, 200);

        // Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 48));
        g2d.drawString("Test Image", 280, 320);

        g2d.dispose();
        return image;
    }

    /**
     * Crop a region from an image.
     */
    public static BufferedImage cropImage(BufferedImage source, int x, int y,
            int width, int height) {
        // Validate bounds
        int cropWidth = Math.min(width, source.getWidth() - x);
        int cropHeight = Math.min(height, source.getHeight() - y);

        // getSubimage returns a view - create independent copy
        BufferedImage cropped = source.getSubimage(x, y, cropWidth, cropHeight);
        BufferedImage result = new BufferedImage(cropWidth, cropHeight, source.getType());
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(cropped, 0, 0, null);
        g2d.dispose();

        return result;
    }

    /**
     * Rotate an image by specified angle.
     */
    public static BufferedImage rotateImage(BufferedImage source, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double sin = Math.abs(Math.sin(angleRadians));
        double cos = Math.abs(Math.cos(angleRadians));

        int originalWidth = source.getWidth();
        int originalHeight = source.getHeight();

        // Calculate new dimensions
        int newWidth = (int) Math.floor(originalWidth * cos + originalHeight * sin);
        int newHeight = (int) Math.floor(originalHeight * cos + originalWidth * sin);

        BufferedImage result = new BufferedImage(newWidth, newHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Translate to center, rotate, then draw
        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - originalWidth) / 2.0,
                (newHeight - originalHeight) / 2.0);
        transform.rotate(angleRadians, originalWidth / 2.0, originalHeight / 2.0);

        g2d.drawImage(source, transform, null);
        g2d.dispose();

        return result;
    }

    /**
     * Adjust image opacity.
     */
    public static BufferedImage adjustOpacity(BufferedImage source, float alpha) {
        BufferedImage result = new BufferedImage(
                source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();

        return result;
    }

    /**
     * Apply blur filter using convolution.
     */
    public static BufferedImage applyBlur(BufferedImage source) {
        float[] kernel = new float[25];
        for (int i = 0; i < 25; i++) {
            kernel[i] = 1.0f / 25.0f;
        }

        Kernel blurKernel = new Kernel(5, 5, kernel);
        ConvolveOp op = new ConvolveOp(blurKernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(source, null);
    }

    /**
     * Apply sharpen filter.
     */
    public static BufferedImage applySharpen(BufferedImage source) {
        float[] kernel = {
                0, -1, 0,
                -1, 5, -1,
                0, -1, 0
        };

        Kernel sharpenKernel = new Kernel(3, 3, kernel);
        ConvolveOp op = new ConvolveOp(sharpenKernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(source, null);
    }

    /**
     * Apply edge detection filter.
     */
    public static BufferedImage applyEdgeDetection(BufferedImage source) {
        float[] kernel = {
                -1, -1, -1,
                -1, 8, -1,
                -1, -1, -1
        };

        Kernel edgeKernel = new Kernel(3, 3, kernel);
        ConvolveOp op = new ConvolveOp(edgeKernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(source, null);
    }
}