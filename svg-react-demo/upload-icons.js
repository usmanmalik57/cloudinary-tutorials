import { v2 as cloudinary } from 'cloudinary'
import dotenv from 'dotenv'

dotenv.config()

cloudinary.config({
    cloud_name: process.env.CLOUDINARY_CLOUD_NAME,
    api_key: process.env.CLOUDINARY_API_KEY,
    api_secret: process.env.CLOUDINARY_API_SECRET
})

async function uploadIcon(filePath, publicId) {
    const result = await cloudinary.uploader.upload(filePath, {
        public_id: publicId,
        folder: 'icons',
        resource_type: 'image',
        overwrite: true,
        invalidate: true
    })
    console.log(`✅ ${publicId}:`, result.secure_url)
    return result
}

// Upload icons
async function uploadAll() {
    try {
        await uploadIcon('./src/assets/star.svg', 'star')
        await uploadIcon('./src/assets/heart.svg', 'heart')
        console.log('\n✨ All icons uploaded successfully!')
    } catch (error) {
        console.error('❌ Upload failed:', error.message)
    }
}

uploadAll()
