import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

/**
 * Demonstrates uploading images to Cloudinary.
 * 
 * Usage:
 * export CLOUDINARY_URL=cloudinary://api_key:api_secret@cloud_name
 * mvn exec:java -Dexec.mainClass="CloudinaryUploadExample"
 */
public class CloudinaryUploadExample {

    public static void main(String[] args) {
        try {
            // Initialize Cloudinary from environment variable
            Cloudinary cloudinary = new Cloudinary();
            System.out.println("[OK] Cloudinary initialized\n");

            System.out.println("==================================================");
            System.out.println("       Cloudinary Upload Examples                 ");
            System.out.println("==================================================\n");

            // Create a sample image
            System.out.println("--- Creating Sample Image ---");
            BufferedImage image = createSampleImage();
            File tempFile = new File("output/cloudinary-sample.png");
            tempFile.getParentFile().mkdirs();
            ImageIO.write(image, "PNG", tempFile);
            System.out.println("Created: " + tempFile.getName() + " (" +
                    (tempFile.length() / 1024) + " KB)\n");

            // Upload to Cloudinary
            System.out.println("--- Uploading to Cloudinary ---");
            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                    "public_id", "java-examples/processed-image",
                    "folder", "java-demo",
                    "overwrite", true));

            System.out.println("[OK] Upload successful!");
            System.out.println("Public ID:  " + uploadResult.get("public_id"));
            System.out.println("URL:        " + uploadResult.get("secure_url"));
            System.out.println("Format:     " + uploadResult.get("format"));
            System.out.println("Size:       " + uploadResult.get("bytes") + " bytes");

            // Upload from URL
            System.out.println("\n--- Uploading from URL ---");
            Map urlResult = cloudinary.uploader().upload(
                    "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png",
                    ObjectUtils.asMap(
                            "folder", "java-demo",
                            "public_id", "external-upload"));

            System.out.println("[OK] URL upload successful!");
            System.out.println("Public ID:  " + urlResult.get("public_id"));
            System.out.println("URL:        " + urlResult.get("secure_url"));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create a sample image for upload demonstration.
     */
    public static BufferedImage createSampleImage() {
        BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(59, 130, 246),
                600, 400, new Color(139, 92, 246));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 600, 400);

        // Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 48));
        g2d.drawString("Cloudinary Upload", 100, 220);

        g2d.dispose();
        return image;
    }
}