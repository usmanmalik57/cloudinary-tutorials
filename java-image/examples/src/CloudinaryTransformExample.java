import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

/**
 * Demonstrates generating transformation URLs for Cloudinary images.
 * 
 * Usage:
 * export CLOUDINARY_URL=cloudinary://api_key:api_secret@cloud_name
 * mvn exec:java -Dexec.mainClass="CloudinaryTransformExample"
 */
public class CloudinaryTransformExample {

    public static void main(String[] args) {
        try {
            Cloudinary cloudinary = new Cloudinary();
            System.out.println("[OK] Cloudinary initialized\n");

            // Public ID of previously uploaded image
            String publicId = "java-demo/java-examples/processed-image";

            System.out.println("==================================================");
            System.out.println("      Cloudinary Transformation Examples          ");
            System.out.println("==================================================\n");
            System.out.println("Source: " + publicId + "\n");

            // 1. Generate thumbnail
            System.out.println("--- Thumbnail (150x150 fill crop) ---");
            String thumbnail = cloudinary.url()
                    .transformation(new Transformation()
                            .width(150).height(150)
                            .crop("fill")
                            .gravity("face"))
                    .generate(publicId);
            System.out.println(thumbnail);

            // 2. Mobile-optimized version
            System.out.println("\n--- Mobile Optimized (320px width) ---");
            String mobile = cloudinary.url()
                    .transformation(new Transformation()
                            .width(320)
                            .crop("scale")
                            .fetchFormat("auto")
                            .quality("auto"))
                    .generate(publicId);
            System.out.println(mobile);

            // 3. WebP format
            System.out.println("\n--- WebP Format (800px width) ---");
            String webp = cloudinary.url()
                    .transformation(new Transformation()
                            .width(800)
                            .fetchFormat("webp")
                            .quality("auto"))
                    .generate(publicId);
            System.out.println(webp);

            // 4. With effects
            System.out.println("\n--- With Improvement and Sharpen ---");
            String enhanced = cloudinary.url()
                    .transformation(new Transformation()
                            .width(600)
                            .effect("improve")
                            .effect("sharpen"))
                    .generate(publicId);
            System.out.println(enhanced);

            // 5. Responsive images (multiple sizes)
            System.out.println("\n--- Responsive Image Set ---");
            int[] widths = { 320, 640, 1024 };
            for (int width : widths) {
                String responsive = cloudinary.url()
                        .transformation(new Transformation()
                                .width(width)
                                .crop("scale")
                                .quality("auto")
                                .fetchFormat("auto"))
                        .generate(publicId);
                System.out.println(width + "px: " + responsive);
            }

            System.out.println("\n==================================================");
            System.out.println("All URLs generated on-the-fly!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}