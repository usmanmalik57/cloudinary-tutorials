<?php
declare(strict_types=1);

require_once __DIR__ . '/vendor/autoload.php';

use Cloudinary\Cloudinary;
use Dotenv\Dotenv;
use Cloudinary\Transformation\Resize;
use Cloudinary\Transformation\Effect;

// Load environment variables
$dotenv = Dotenv::createImmutable(__DIR__);
$dotenv->load();

if (!isset($_ENV['CLOUDINARY_URL'])) {
    die("CLOUDINARY_URL is not set in .env");
}

// Initialize Cloudinary
$cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);

// 1️⃣ Upload a video
$uploadResult = $cloudinary->uploadApi()->upload(
    __DIR__ . '/sample2.mp4',
    [
        'resource_type' => 'video',
        'folder' => 'simple-video-demo',
    ]
);

$publicId = $uploadResult['public_id'];

// Extract metadata from Cloudinary upload response
$resourceType = $uploadResult['resource_type'] ?? 'N/A';
$format       = $uploadResult['format'] ?? 'N/A';
$bytes        = $uploadResult['bytes'] ?? 'N/A';
$duration     = $uploadResult['duration'] ?? 'N/A';
$width        = $uploadResult['width'] ?? 'N/A';
$height       = $uploadResult['height'] ?? 'N/A';

// 2️⃣ Generate transformed video URL
$transformedUrl = (string) $cloudinary->video($publicId)
    ->deliveryType('upload')
    ->resize(
        Resize::fill()->height(250)
    )
    ->effect(Effect::grayscale())
    ->quality('auto')
    ->format('auto');
?>

<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Cloudinary PHP Video Metadata Demo</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      max-width: 800px;
      margin: 40px auto;
    }
    code {
      background: #f4f4f4;
      padding: 6px;
      border-radius: 6px;
      word-break: break-all;
      display: block;
      margin-bottom: 10px;
    }
    table {
      border-collapse: collapse;
      margin-top: 20px;
      width: 100%;
    }
    td, th {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }
    th {
      background: #f0f0f0;
    }
  </style>
</head>
<body>

<h2>Transformed Video Metadata</h2>

<p><strong>public_id</strong></p>
<code><?= htmlspecialchars($publicId) ?></code>

<p><strong>Transformed URL</strong></p>
<code><?= htmlspecialchars($transformedUrl) ?></code>

<h3>Upload Metadata (from Cloudinary)</h3>

<table>
  <tr><th>Property</th><th>Value</th></tr>
  <tr><td>Resource type</td><td><?= htmlspecialchars($resourceType) ?></td></tr>
  <tr><td>Format</td><td><?= htmlspecialchars($format) ?></td></tr>
  <tr><td>Bytes</td><td><?= number_format((int)$bytes) ?></td></tr>
  <tr><td>Duration (seconds)</td><td><?= htmlspecialchars((string)$duration) ?></td></tr>
  <tr><td>Original width</td><td><?= htmlspecialchars((string)$width) ?></td></tr>
  <tr><td>Original height</td><td><?= htmlspecialchars((string)$height) ?></td></tr>
</table>

</body>
</html>
