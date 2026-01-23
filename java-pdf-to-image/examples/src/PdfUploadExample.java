import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

/**
 * Demonstrates uploading a PDF file to Cloudinary.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="PdfUploadExample"
 * -Dexec.args="path/to/your.pdf"
 */
public class PdfUploadExample {

    public static void main(String[] args) {
        try {
            // Initialize Cloudinary from CLOUDINARY_URL environment variable
            Cloudinary cloudinary = new Cloudinary();
            System.out.println("[OK] Cloudinary initialized\n");

            // Get PDF path from command line or use default
            if (args.length == 0) {
                System.err.println("Usage: PdfUploadExample <path-to-pdf>");
                System.err.println(
                        "Example: mvn exec:java -Dexec.mainClass=\"PdfUploadExample\" -Dexec.args=\"sample.pdf\"");
                System.exit(1);
            }

            File pdfFile = new File(args[0]);
            if (!pdfFile.exists()) {
                System.err.println("Error: PDF file not found: " + args[0]);
                System.exit(1);
            }

            System.out.println("--- Uploading PDF to Cloudinary ---");
            System.out.println("File: " + pdfFile.getName());
            System.out.println("Size: " + (pdfFile.length() / 1024) + " KB\n");

            // Upload PDF as a "raw" resource (stores original PDF)
            Map uploadResult = cloudinary.uploader().upload(pdfFile, ObjectUtils.asMap(
                    "resource_type", "raw", // Raw = store as-is (not an image)
                    "public_id", "pdfs/my-document", // Custom ID for easy reference
                    "folder", "java-examples", // Organize in folders
                    "overwrite", true // Replace if already exists
            ));

            // Display upload results
            System.out.println("[OK] Upload successful!");
            System.out.println("Public ID:  " + uploadResult.get("public_id"));
            System.out.println("URL:        " + uploadResult.get("secure_url"));
            System.out.println("Format:     " + uploadResult.get("format"));
            System.out.println("Size:       " + uploadResult.get("bytes") + " bytes");

            // Upload PDF as "image" resource (enables page extraction)
            System.out.println("\n--- Uploading as Image Resource ---");
            System.out.println("(This allows Cloudinary to extract individual pages)\n");

            Map imageResult = cloudinary.uploader().upload(pdfFile, ObjectUtils.asMap(
                    "resource_type", "image", // Image = enable page extraction
                    "public_id", "pdf-pages/my-document",
                    "pages", true // Extract page metadata
            ));

            System.out.println("[OK] Image upload successful!");
            System.out.println("Public ID:  " + imageResult.get("public_id"));
            System.out.println("Pages:      " + imageResult.get("pages"));
            System.out.println("Dimensions: " + imageResult.get("width") + "x" + imageResult.get("height"));

            // Generate URLs for individual pages
            String publicId = (String) imageResult.get("public_id");
            System.out.println("\n--- Accessing Individual Pages ---");

            // Page 1 as JPG
            String page1 = cloudinary.url()
                    .resourceType("image")
                    .format("jpg")
                    .transformation(new Transformation().page(1))
                    .generate(publicId);
            System.out.println("Page 1 (JPG): " + page1);

            // Page 2 as PNG with width=400
            String page2 = cloudinary.url()
                    .resourceType("image")
                    .format("png")
                    .transformation(new Transformation()
                            .page(2)
                            .width(400))
                    .generate(publicId);
            System.out.println("Page 2 (PNG): " + page2);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}