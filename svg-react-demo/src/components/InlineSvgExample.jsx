// src/components/InlineSvgExample.jsx

function InlineSvgExample() {
    return (
        <div className="example-container">
            <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
                Inline SVGs are embedded directly in your JSX code. This gives you full
                control over styling and makes each element accessible via CSS and JavaScript.
            </p>

            <div className="icon-row">
                {/* Clock Icon - demonstrates stroke-based SVG */}
                <div className="icon-label">
                    <svg
                        width="48"
                        height="48"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    >
                        <circle cx="12" cy="12" r="10" />
                        <polyline points="12 6 12 12 16 14" />
                    </svg>
                    <span>Clock</span>
                </div>

                {/* Mail Icon - demonstrates path element */}
                <div className="icon-label">
                    <svg
                        width="48"
                        height="48"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    >
                        <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" />
                        <polyline points="22,6 12,13 2,6" />
                    </svg>
                    <span>Mail</span>
                </div>

                {/* Bell Icon - demonstrates multiple paths */}
                <div className="icon-label">
                    <svg
                        width="48"
                        height="48"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                    >
                        <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
                        <path d="M13.73 21a2 2 0 0 1-3.46 0" />
                    </svg>
                    <span>Bell</span>
                </div>

                {/* Success Icon - demonstrates fill color */}
                <div className="icon-label">
                    <svg
                        width="48"
                        height="48"
                        viewBox="0 0 24 24"
                        fill="#22c55e"
                        stroke="#22c55e"
                        strokeWidth="2"
                    >
                        <circle cx="12" cy="12" r="10" fill="none" />
                        <polyline points="9 12 11 14 15 10" fill="none" />
                    </svg>
                    <span>Success</span>
                </div>
            </div>

            <div className="code-block">
                <code>{`<svg 
  width="48" 
  height="48" 
  viewBox="0 0 24 24" 
  fill="none" 
  stroke="currentColor"    {/* Uses parent's text color */}
  strokeWidth="2"          {/* Note: camelCase in JSX */}
>
  <circle cx="12" cy="12" r="10" />
</svg>`}</code>
            </div>
        </div>
    )
}

export default InlineSvgExample