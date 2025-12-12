<?php
require_once __DIR__ . '/vendor/autoload.php';

use Cloudinary\Cloudinary;
use Dotenv\Dotenv;
use Cloudinary\Transformation\Resize;

// Load environment variables
$dotenv = Dotenv::createImmutable(__DIR__);
$dotenv->load();

$cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);

// Videos to upload and optimize
$videoPaths = [
    'sample.mp4',
    'sample2.mp4',
];

foreach ($videoPaths as $filePath) {
    if (!file_exists($filePath)) {
        echo "File not found: $filePath\n";
        continue;
    }

    $publicId = pathinfo($filePath, PATHINFO_FILENAME);

    echo "Uploading {$filePath} as {$publicId}...\n";

    // Upload video
    $uploadResult = $cloudinary->uploadApi()->upload(
        $filePath,
        [
            'resource_type' => 'video',
            'public_id' => $publicId,
        ]
    );

    // Generate optimized delivery URL
    $optimizedUrl = (string) $cloudinary->video($publicId)
        ->resize(
            Resize::scale()->width(512)
        )
        ->quality(30)
        ->format('auto');

    echo "Optimized video URL:\n{$optimizedUrl}\n\n";
}

echo "All uploads completed.\n";
