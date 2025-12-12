<?php
require 'vendor/autoload.php';

use FFMpeg\FFMpeg;
use FFMpeg\Coordinate\TimeCode;
use FFMpeg\Coordinate\Dimension;
use FFMpeg\Format\Video\X264;

if (!isset($_FILES['video']) || $_FILES['video']['error'] !== UPLOAD_ERR_OK) {
    exit('No file uploaded or error in upload.');
}

if (!is_dir('uploads')) mkdir('uploads');
if (!is_dir('outputs')) mkdir('outputs');

// Save first video
$uploadedFile = $_FILES['video']['tmp_name'];
$originalName = basename($_FILES['video']['name']);
$fileHash = md5_file($uploadedFile);
$targetPath = "uploads/{$fileHash}_{$originalName}";

if (!file_exists($targetPath)) {
    move_uploaded_file($uploadedFile, $targetPath);
}

$ffmpeg = FFMpeg::create();
$video = $ffmpeg->open($targetPath);

$action = $_POST['action'] ?? '';
$outputFile = '';

switch ($action) {
    case 'resize':
        $outputFile = 'outputs/resized_' . basename($targetPath);
        $video->filters()->resize(new Dimension(640, 480))->synchronize();
        $video->save(new X264(), $outputFile);
        echo "Video resized and saved to $outputFile";
        break;

    case 'trim':
        $outputFile = 'outputs/trimmed_' . basename($targetPath);
        $clip = $video->clip(TimeCode::fromSeconds(0), TimeCode::fromSeconds(5));
        $clip->save(new X264(), $outputFile);
        echo "Video trimmed and saved to $outputFile";
        break;

    case 'thumbnail':
        $outputFile = 'outputs/thumbnail_' . pathinfo($targetPath, PATHINFO_FILENAME) . '.jpg';
        $frame = $video->frame(TimeCode::fromSeconds(2));
        $frame->save($outputFile);
        echo "Thumbnail saved to $outputFile";
        break;

    case 'join':
        if (!isset($_FILES['video2']) || $_FILES['video2']['error'] !== UPLOAD_ERR_OK) {
            exit('Second video not uploaded for joining.');
        }

        $uploadedFile2 = $_FILES['video2']['tmp_name'];
        $originalName2 = basename($_FILES['video2']['name']);
        $fileHash2 = md5_file($uploadedFile2);
        $targetPath2 = "uploads/{$fileHash2}_{$originalName2}";

        if (!file_exists($targetPath2)) {
            move_uploaded_file($uploadedFile2, $targetPath2);
        }

        $absolutePath1 = realpath($targetPath);
        $absolutePath2 = realpath($targetPath2);
        $outputFile = 'outputs/joined_' . time() . '.mp4';

        $video1 = $ffmpeg->open($absolutePath1);
        $video1->concat([$absolutePath1, $absolutePath2])
               ->saveFromSameCodecs($outputFile, true);

        echo "Videos joined and saved to $outputFile";
        break;

    default:
        echo "Unknown action.";
}
