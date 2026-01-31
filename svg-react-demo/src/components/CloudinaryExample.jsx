// src/components/CloudinaryExample.jsx

import { useState } from 'react'
import { AdvancedImage } from '@cloudinary/react'
import { Cloudinary } from '@cloudinary/url-gen'
import { scale, crop, fill } from '@cloudinary/url-gen/actions/resize'
import { autoGravity } from '@cloudinary/url-gen/qualifiers/gravity'
import { format } from '@cloudinary/url-gen/actions/delivery'

// Initialize Cloudinary instance
// Replace 'demo' with your cloud name
const cld = new Cloudinary({
    cloud: {
        cloudName: 'demo' // Using Cloudinary's demo account for this example
    }
})

function CloudinaryExample() {
    const [selectedSize, setSelectedSize] = useState(100)
    const [selectedFormat, setSelectedFormat] = useState('svg')

    // Sample image from Cloudinary
    // Replace with your own uploaded assets
    const svgAssets = [
        { id: 'sample', name: 'Sample Image' }
    ]

    // Create image instances with transformations
    const getTransformedImage = (publicId, size, imageFormat) => {
        const image = cld.image(publicId)

        // Apply transformations
        if (size !== 'original') {
            image.resize(scale().width(size))
        }

        // Convert format if requested
        if (imageFormat !== 'svg') {
            image.delivery(format(imageFormat))
        }

        return image
    }

    return (
        <div className="example-container">
            {/* Interactive controls */}
            <div style={{
                marginBottom: '2rem',
                padding: '1rem',
                background: '#f3f4f6',
                borderRadius: '0.5rem'
            }}>
                <h3 style={{ fontSize: '1rem', marginBottom: '1rem', color: '#374151' }}>
                    Transform SVGs Dynamically
                </h3>

                <div style={{
                    display: 'flex',
                    gap: '2rem',
                    flexWrap: 'wrap',
                    marginBottom: '1rem'
                }}>
                    <div>
                        <label style={{
                            display: 'block',
                            fontSize: '0.875rem',
                            marginBottom: '0.5rem',
                            color: '#374151',
                            fontWeight: '500'
                        }}>
                            Size:
                        </label>
                        <select
                            value={selectedSize}
                            onChange={(e) => setSelectedSize(e.target.value)}
                            style={{
                                padding: '0.5rem',
                                borderRadius: '0.375rem',
                                border: '1px solid #d1d5db',
                                fontSize: '0.875rem',
                                cursor: 'pointer'
                            }}
                        >
                            <option value="50">50px</option>
                            <option value="100">100px</option>
                            <option value="200">200px</option>
                            <option value="300">300px</option>
                            <option value="original">Original</option>
                        </select>
                    </div>

                    <div>
                        <label style={{
                            display: 'block',
                            fontSize: '0.875rem',
                            marginBottom: '0.5rem',
                            color: '#374151',
                            fontWeight: '500'
                        }}>
                            Format:
                        </label>
                        <select
                            value={selectedFormat}
                            onChange={(e) => setSelectedFormat(e.target.value)}
                            style={{
                                padding: '0.5rem',
                                borderRadius: '0.375rem',
                                border: '1px solid #d1d5db',
                                fontSize: '0.875rem',
                                cursor: 'pointer'
                            }}
                        >
                            <option value="svg">SVG</option>
                            <option value="png">PNG</option>
                            <option value="webp">WebP</option>
                            <option value="jpg">JPG</option>
                        </select>
                    </div>
                </div>

                <p style={{ fontSize: '0.75rem', color: '#6b7280', fontStyle: 'italic' }}>
                    ℹ️ Note: This demo uses Cloudinary's public demo account.
                    Replace with your own cloud name and assets in production.
                </p>
            </div>

            {/* Display transformed images */}
            <div style={{
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
                gap: '1.5rem',
                marginBottom: '1.5rem'
            }}>
                {svgAssets.map((asset) => {
                    const transformedImage = getTransformedImage(
                        asset.id,
                        selectedSize,
                        selectedFormat
                    )

                    return (
                        <div
                            key={asset.id}
                            style={{
                                padding: '1rem',
                                background: 'white',
                                borderRadius: '0.5rem',
                                border: '1px solid #e5e7eb',
                                textAlign: 'center'
                            }}
                        >
                            <div style={{
                                minHeight: '150px',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                marginBottom: '0.75rem',
                                background: '#f9fafb',
                                borderRadius: '0.375rem',
                                padding: '1rem'
                            }}>
                                <AdvancedImage
                                    cldImg={transformedImage}
                                    style={{ maxWidth: '100%', height: 'auto' }}
                                    alt={asset.name}
                                />
                            </div>
                            <p style={{
                                fontSize: '0.875rem',
                                color: '#374151',
                                fontWeight: '500',
                                marginBottom: '0.25rem'
                            }}>
                                {asset.name}
                            </p>
                            <p style={{
                                fontSize: '0.75rem',
                                color: '#6b7280',
                                fontFamily: 'monospace'
                            }}>
                                {selectedSize !== 'original' ? `${selectedSize}px` : 'Original'} · {selectedFormat.toUpperCase()}
                            </p>
                        </div>
                    )
                })}
            </div>

            {/* Example URLs */}
            <div style={{
                background: '#1f2937',
                color: '#f3f4f6',
                padding: '1rem',
                borderRadius: '0.5rem',
                fontSize: '0.75rem',
                fontFamily: 'monospace',
                overflowX: 'auto'
            }}>
                <p style={{ marginBottom: '0.5rem', color: '#9ca3af' }}>
                    Generated URL (click to open):
                </p>
                <a
                    href={getTransformedImage('sample', selectedSize, selectedFormat).toURL()}
                    target="_blank"
                    rel="noopener noreferrer"
                    style={{ color: '#60a5fa', textDecoration: 'underline' }}
                >
                    {getTransformedImage('sample', selectedSize, selectedFormat).toURL()}
                </a>
            </div>

            <div className="code-block" style={{ marginTop: '1rem' }}>
                <code>{`import { AdvancedImage } from '@cloudinary/react'
import { Cloudinary } from '@cloudinary/url-gen'
import { scale } from '@cloudinary/url-gen/actions/resize'
import { format } from '@cloudinary/url-gen/actions/delivery'

// Initialize Cloudinary
const cld = new Cloudinary({
  cloud: { cloudName: 'your-cloud-name' }
})

// Create and transform an image
const myImage = cld.image('your-svg-public-id')
  .resize(scale().width(200))
  .delivery(format('png'))

// Render in React
<AdvancedImage cldImg={myImage} />`}</code>
            </div>
        </div>
    )
}

export default CloudinaryExample
