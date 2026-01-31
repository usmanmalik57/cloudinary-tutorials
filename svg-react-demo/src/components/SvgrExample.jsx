// src/components/SvgrExample.jsx

// Import SVG files as React components using ?react query
import StarIcon from '../assets/star.svg?react'
import HeartIcon from '../assets/heart.svg?react'

function SvgrExample() {
    return (
        <div className="example-container">
            <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
                With SVGR, you can import SVG files directly as React components.
                Add <code>?react</code> to the import path to enable this feature.
            </p>

            <div className="icon-row">
                {/* Default usage */}
                <div className="icon-label">
                    <StarIcon width={48} height={48} />
                    <span>Default</span>
                </div>

                {/* With custom fill color */}
                <div className="icon-label">
                    <StarIcon width={48} height={48} fill="#f59e0b" stroke="#f59e0b" />
                    <span>Colored</span>
                </div>

                {/* Heart icon */}
                <div className="icon-label">
                    <HeartIcon width={48} height={48} />
                    <span>Heart</span>
                </div>

                {/* Heart with color */}
                <div className="icon-label">
                    <HeartIcon width={48} height={48} fill="#ef4444" stroke="#ef4444" />
                    <span>Red Heart</span>
                </div>
            </div>

            <div className="code-block">
                <code>{`// vite.config.js - Add the SVGR plugin
import svgr from 'vite-plugin-svgr'
export default defineConfig({
  plugins: [react(), svgr()],
})

// In your component - use ?react query
import StarIcon from '../assets/star.svg?react'

<StarIcon width={48} height={48} />
<StarIcon width={48} height={48} fill="#f59e0b" />`}</code>
            </div>
        </div>
    )
}

export default SvgrExample