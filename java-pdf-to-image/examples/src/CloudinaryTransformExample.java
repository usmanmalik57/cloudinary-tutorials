import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

/**
 * Demonstrates on-the-fly image transformations using Cloudinary.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="CloudinaryTransformExample"
 */
public class CloudinaryTransformExample {

    public static void main(String[] args) {
        try {
            Cloudinary cloudinary = new Cloudinary();
            System.out.println("[OK] Cloudinary initialized successfully");

            // The public ID of your uploaded PDF (from PdfUploadExample)
            String pdfPublicId = "pdf-pages/my-document";

            System.out.println("\n==================================================");
            System.out.println("      Cloudinary PDF Transformation Examples      ");
            System.out.println("==================================================");
            System.out.println("\nSource PDF: " + pdfPublicId);

            // 1. Basic Page Extraction
            System.out.println("\n--- 1. Basic Page Extraction ---");

            String page1Url = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation().page(1))
                    .generate(pdfPublicId);
            System.out.println("Page 1 (JPG): " + page1Url);

            String page2Url = cloudinary.url()
                    .resourceType("image")
                    .format("png")
                    .transformation(new Transformation().page(2))
                    .generate(pdfPublicId);
            System.out.println("Page 2 (PNG): " + page2Url);

            // 2. Resize and Scale Transformations
            System.out.println("\n--- 2. Resize and Scale ---");

            String scaled = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation()
                            .page(1)
                            .width(800)
                            .crop("scale"))
                    .generate(pdfPublicId);
            System.out.println("Width 800px (scaled): " + scaled);

            String thumbnail = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation()
                            .page(1)
                            .width(300)
                            .height(400)
                            .crop("fill")
                            .gravity("north"))
                    .generate(pdfPublicId);
            System.out.println("Thumbnail 300x400: " + thumbnail);

            // 3. Format Conversion
            System.out.println("\n--- 3. Format Conversion ---");

            String webpUrl = cloudinary.url()
                    .resourceType("image")
                    .format("webp")
                    .transformation(new Transformation()
                            .page(1)
                            .width(800))
                    .generate(pdfPublicId);
            System.out.println("WebP format: " + webpUrl);

            String autoFormat = cloudinary.url()
                    .resourceType("image")
                    .transformation(new Transformation()
                            .page(1)
                            .width(800)
                            .fetchFormat("auto"))
                    .generate(pdfPublicId);
            System.out.println("Auto format: " + autoFormat);

            // 4. Quality Optimization
            System.out.println("\n--- 4. Quality Optimization ---");

            String autoQuality = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation()
                            .page(1)
                            .width(800)
                            .quality("auto"))
                    .generate(pdfPublicId);
            System.out.println("Auto quality: " + autoQuality);

            // 5. Visual Enhancements
            System.out.println("\n--- 5. Visual Enhancements ---");

            String sharpened = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation()
                            .page(1)
                            .width(800)
                            .effect("sharpen:100"))
                    .generate(pdfPublicId);
            System.out.println("Sharpened: " + sharpened);

            String rounded = cloudinary.url()
                    .resourceType("image")
                    .format("png")
                    .transformation(new Transformation()
                            .page(1)
                            .width(600)
                            .radius(20))
                    .generate(pdfPublicId);
            System.out.println("Rounded corners: " + rounded);

            System.out.println("\n==================================================");
            System.out.println("All URLs are generated on-the-fly!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}