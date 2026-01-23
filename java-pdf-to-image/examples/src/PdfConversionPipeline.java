import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Complete pipeline: render PDF locally -> upload to Cloudinary -> generate
 * URLs.
 * 
 * Usage:
 * mvn exec:java -Dexec.mainClass="PdfConversionPipeline"
 * -Dexec.args="path/to/your.pdf"
 */
public class PdfConversionPipeline {

    private final Cloudinary cloudinary;
    private final int dpi;

    public PdfConversionPipeline() {
        this.cloudinary = new Cloudinary();
        this.dpi = 150;
    }

    /**
     * Processes a single PDF page: render -> upload -> generate URL
     */
    public String processPage(File pdfFile, int pageIndex, String publicId) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            // Step 1: Render page to image
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);

            // Step 2: Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Step 3: Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(
                    imageBytes,
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "overwrite", true,
                            "resource_type", "image"));

            // Step 4: Generate optimized URL
            String optimizedUrl = cloudinary.url()
                    .transformation(new Transformation()
                            .quality("auto")
                            .fetchFormat("auto"))
                    .generate(publicId);

            return optimizedUrl;
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: PdfConversionPipeline <path-to-pdf>");
                System.exit(1);
            }

            File pdfFile = new File(args[0]);
            if (!pdfFile.exists()) {
                System.err.println("Error: PDF file not found: " + args[0]);
                System.exit(1);
            }

            PdfConversionPipeline pipeline = new PdfConversionPipeline();

            System.out.println("==================================================");
            System.out.println("         PDF Conversion Pipeline                  ");
            System.out.println("==================================================\n");
            System.out.println("Processing: " + pdfFile.getName() + "\n");

            try (PDDocument document = Loader.loadPDF(pdfFile)) {
                int pageCount = document.getNumberOfPages();

                for (int i = 0; i < pageCount; i++) {
                    String publicId = "pipeline-output/page_" + (i + 1);
                    String url = pipeline.processPage(pdfFile, i, publicId);

                    System.out.println("Page " + (i + 1) + " -> " + url);
                }
            }

            System.out.println("\n[OK] Pipeline complete!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}