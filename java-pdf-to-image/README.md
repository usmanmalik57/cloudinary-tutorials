# Java PDF to Image Converter

Convert PDF documents to images using Apache PDFBox and Cloudinary.

## Prerequisites

- Java JDK 11+
- Maven 3.6+
- Cloudinary account (for cloud examples) - [Sign up free](https://cloudinary.com/users/register/free)

## Setup

1. **Install dependencies:**
   ```bash
   cd examples
   mvn install
   ```

2. **Set Cloudinary credentials** (for cloud examples):
   ```bash
   export CLOUDINARY_URL=cloudinary://api_key:api_secret@cloud_name
   ```

3. **Add a sample PDF** to the `pdf/` folder

## Running Examples

### 1. Local PDF to Image (No Cloudinary Required)

Converts PDF pages to PNG and JPEG images locally.

```bash
mvn exec:java -Dexec.mainClass="PdfToImageConverter" -Dexec.args="pdf/sample.pdf"
```

Output: Images saved to `output/` folder

---

### 2. Upload PDF to Cloudinary

Uploads PDF and generates URLs for individual pages.

```bash
mvn exec:java -Dexec.mainClass="PdfUploadExample" -Dexec.args="pdf/sample.pdf"
```

Requires: `CLOUDINARY_URL` environment variable

---

### 3. Cloudinary Transformations

Demonstrates on-the-fly image transformations (resize, format conversion, quality optimization).

```bash
mvn exec:java -Dexec.mainClass="CloudinaryTransformExample"
```

**Note:** Update the `pdfPublicId` in the code to match your uploaded PDF's public ID.

---

### 4. Conversion Pipeline

Complete workflow: render PDF → upload to Cloudinary → generate optimized URLs.

```bash
mvn exec:java -Dexec.mainClass="PdfConversionPipeline" -Dexec.args="pdf/sample.pdf"
```

Requires: `CLOUDINARY_URL` environment variable

---

### 5. Optimized Delivery

Generates responsive image URLs for different devices and screen sizes.

```bash
mvn exec:java -Dexec.mainClass="OptimizedDeliveryExample"
```


**"Must supply api_key"**  
Set the `CLOUDINARY_URL` environment variable.

**"PDF file not found"**  
Ensure your PDF file exists in the `pdf/` directory.


