// src/components/ImgTagExample.jsx

// Import SVG files from the assets folder
// Vite will handle these imports and provide the correct URL
import starIcon from '../assets/star.svg'
import heartIcon from '../assets/heart.svg'

function ImgTagExample() {
    return (
        <div className="example-container">
            <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
                Using the <code>&lt;img&gt;</code> tag is the simplest approach. You can import
                SVG files or reference them from the public folder. However, you cannot style
                the SVG internals with CSS.
            </p>

            <h3 style={{ fontSize: '1rem', marginBottom: '0.5rem', color: '#374151' }}>
                Method A: Import from assets folder
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <img
                        src={starIcon}
                        alt="Star icon"
                        width="48"
                        height="48"
                    />
                    <span>Star</span>
                </div>

                <div className="icon-label">
                    <img
                        src={heartIcon}
                        alt="Heart icon"
                        width="48"
                        height="48"
                    />
                    <span>Heart</span>
                </div>
            </div>

            <h3 style={{ fontSize: '1rem', margin: '1rem 0 0.5rem', color: '#374151' }}>
                Method B: Reference from public folder
            </h3>
            <div className="icon-row">
                <div className="icon-label">
                    <img
                        src="/icons/check.svg"
                        alt="Check icon"
                        width="48"
                        height="48"
                    />
                    <span>Check</span>
                </div>
            </div>

            <div className="code-block">
                <code>{`// Method A: Import from assets (bundled with your code)
import starIcon from '../assets/star.svg'
<img src={starIcon} alt="Star" width="48" height="48" />

// Method B: From public folder (served as static file)
<img src="/icons/check.svg" alt="Check" width="48" height="48" />`}</code>
            </div>
        </div>
    )
}

export default ImgTagExample