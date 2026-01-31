// src/components/IconGallery.jsx

import { StarIcon, HeartIcon, CheckIcon } from './icons'

function IconGallery() {
    return (
        <div className="example-container">
            <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
                Reusable icon components accept props for size, color, and variants.
                This approach provides consistency across your app and makes icons
                easy to update globally.
            </p>

            {/* Default size (24px) */}
            <h3 style={{ fontSize: '1rem', marginBottom: '0.5rem', color: '#374151' }}>
                Default Size (24px)
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <StarIcon />
                    <span>Star</span>
                </div>
                <div className="icon-label">
                    <HeartIcon />
                    <span>Heart</span>
                </div>
                <div className="icon-label">
                    <CheckIcon />
                    <span>Check</span>
                </div>
            </div>

            {/* Custom sizes */}
            <h3 style={{ fontSize: '1rem', margin: '1rem 0 0.5rem', color: '#374151' }}>
                Custom Sizes
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <StarIcon size={16} />
                    <span>16px</span>
                </div>
                <div className="icon-label">
                    <StarIcon size={24} />
                    <span>24px</span>
                </div>
                <div className="icon-label">
                    <StarIcon size={32} />
                    <span>32px</span>
                </div>
                <div className="icon-label">
                    <StarIcon size={48} />
                    <span>48px</span>
                </div>
            </div>

            {/* Custom colors */}
            <h3 style={{ fontSize: '1rem', margin: '1rem 0 0.5rem', color: '#374151' }}>
                Custom Colors
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <HeartIcon size={32} color="#ef4444" />
                    <span>Red</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#f59e0b" />
                    <span>Orange</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#22c55e" />
                    <span>Green</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#3b82f6" />
                    <span>Blue</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#8b5cf6" />
                    <span>Purple</span>
                </div>
            </div>

            {/* Filled variants */}
            <h3 style={{ fontSize: '1rem', margin: '1rem 0 0.5rem', color: '#374151' }}>
                Outline vs Filled
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <StarIcon size={32} color="#f59e0b" />
                    <span>Outline</span>
                </div>
                <div className="icon-label">
                    <StarIcon size={32} color="#f59e0b" filled />
                    <span>Filled</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#ef4444" />
                    <span>Outline</span>
                </div>
                <div className="icon-label">
                    <HeartIcon size={32} color="#ef4444" filled />
                    <span>Filled</span>
                </div>
            </div>

            {/* Interactive with className */}
            <h3 style={{ fontSize: '1rem', margin: '1rem 0 0.5rem', color: '#374151' }}>
                Interactive (hover me!)
            </h3>
            <div className="icon-row">
                <StarIcon size={32} className="icon-interactive" />
                <HeartIcon size={32} className="icon-interactive" />
                <CheckIcon size={32} className="icon-interactive" />
            </div>

            <div className="code-block">
                <code>{`import { StarIcon, HeartIcon } from './icons'

// Default usage
<StarIcon />

// Custom size and color
<StarIcon size={32} color="#f59e0b" />

// Filled variant
<HeartIcon size={32} color="#ef4444" filled />

// With CSS class for hover effects
<StarIcon className="icon-interactive" />`}</code>
            </div>
        </div>
    )
}

export default IconGallery
