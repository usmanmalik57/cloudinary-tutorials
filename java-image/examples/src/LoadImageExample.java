import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Demonstrates loading images from different sources.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="LoadImageExample"
 */
public class LoadImageExample {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("           Load Image Examples                    ");
        System.out.println("==================================================\n");

        // Example 1: Load from local file
        System.out.println("--- Loading from File ---");
        loadFromFile("images/meadow-7196549_640.jpg");

        // Example 2: Load from URL
        System.out.println("\n--- Loading from URL ---");
        loadFromUrl("https://cdn.pixabay.com/photo/2018/08/14/13/23/ocean-3605547_640.jpg");

        // Example 3: Load from classpath
        System.out.println("\n--- Loading from Classpath ---");
        loadFromClasspath("/sample.jpg");

        System.out.println("\n[OK] All load examples complete!");
    }

    /**
     * Load an image from the local file system.
     */
    public static BufferedImage loadFromFile(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);

            if (image != null) {
                System.out.println("Loaded: " + filePath);
                System.out.println("Dimensions: " + image.getWidth() + "x" + image.getHeight());
                System.out.println("Type: " + getImageTypeName(image.getType()));
            } else {
                System.out.println("Failed: Unsupported format");
            }

            return image;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Load an image from a URL.
     */
    public static BufferedImage loadFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);

            if (image != null) {
                System.out.println("Loaded from URL");
                System.out.println("Dimensions: " + image.getWidth() + "x" + image.getHeight());
            } else {
                System.out.println("Failed: Unsupported format");
            }

            return image;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Load an image from classpath resources.
     */
    public static BufferedImage loadFromClasspath(String resourcePath) {
        try {
            InputStream stream = LoadImageExample.class.getResourceAsStream(resourcePath);

            if (stream == null) {
                System.out.println("Resource not found: " + resourcePath);
                return null;
            }

            BufferedImage image = ImageIO.read(stream);
            stream.close();

            if (image != null) {
                System.out.println("Loaded from classpath: " + resourcePath);
                System.out.println("Dimensions: " + image.getWidth() + "x" + image.getHeight());
            }

            return image;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get human-readable name for BufferedImage type.
     */
    private static String getImageTypeName(int type) {
        switch (type) {
            case BufferedImage.TYPE_INT_RGB:
                return "TYPE_INT_RGB";
            case BufferedImage.TYPE_INT_ARGB:
                return "TYPE_INT_ARGB";
            case BufferedImage.TYPE_BYTE_GRAY:
                return "TYPE_BYTE_GRAY";
            default:
                return "TYPE_CUSTOM (" + type + ")";
        }
    }
}