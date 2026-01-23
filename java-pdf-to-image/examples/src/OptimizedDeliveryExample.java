import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

/**
 * Demonstrates generating optimized delivery URLs for images.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="OptimizedDeliveryExample"
 */
public class OptimizedDeliveryExample {

    public static void main(String[] args) {
        try {
            Cloudinary cloudinary = new Cloudinary();
            String publicId = "pipeline-output/page_1";

            System.out.println("==================================================");
            System.out.println("      Optimized Image Delivery Examples          ");
            System.out.println("==================================================\n");

            // 1. Responsive URLs for different screen sizes
            System.out.println("--- Responsive Image URLs ---");
            int[] widths = { 320, 640, 1024 };
            for (int width : widths) {
                String url = cloudinary.url()
                        .transformation(new Transformation()
                                .width(width)
                                .crop("scale")
                                .quality("auto")
                                .fetchFormat("auto"))
                        .generate(publicId);
                System.out.println(width + "px: " + url);
            }

            // 2. Device Pixel Ratio (Retina support)
            System.out.println("\n--- Retina Display Support ---");
            String retina = cloudinary.url()
                    .transformation(new Transformation()
                            .width(400)
                            .dpr(2.0f)
                            .quality("auto"))
                    .generate(publicId);
            System.out.println("2x DPR: " + retina);

            // 3. Format optimization
            System.out.println("\n--- Format Optimization ---");
            String webOptimized = cloudinary.url()
                    .transformation(new Transformation()
                            .width(800)
                            .fetchFormat("auto")
                            .quality("auto"))
                    .generate(publicId);
            System.out.println("Auto format: " + webOptimized);

            System.out.println("\n==================================================");
            System.out.println("All URLs use Cloudinary's global CDN.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}