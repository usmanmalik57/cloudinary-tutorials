<?php
require 'vendor/autoload.php';

use FFMpeg\FFMpeg;
use FFMpeg\Format\Video\WebM;

$ffmpeg = FFMpeg::create();
$video = $ffmpeg->open('sample.mp4');

$video->save(new WebM(), 'output.webm');

echo "Video converted to WebM format.\n";
