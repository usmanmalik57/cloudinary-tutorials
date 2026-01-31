// src/App.jsx

import InlineSvgExample from './components/InlineSvgExample'
import ImgTagExample from './components/ImgTagExample'
import IconGallery from './components/IconGallery'
import SvgrExample from './components/SvgrExample'
import InteractiveDemo from './components/InteractiveDemo'
import CloudinaryExample from './components/CloudinaryExample'

function App() {
  return (
    <div className="app">
      {/* Header */}
      <header className="app-header">
        <h1>🎨 SVG in React Demo</h1>
        <p>Explore different methods for using SVGs in your React applications</p>
      </header>

      {/* Method 1: Inline SVG */}
      <section className="section">
        <h2>Inline SVG</h2>
        <InlineSvgExample />
      </section>

      {/* Method 2: img Tag */}
      <section className="section">
        <h2>SVG with img Tag</h2>
        <ImgTagExample />
      </section>

      {/* Method 3: Reusable Components */}
      <section className="section">
        <h2>Reusable SVG Components</h2>
        <IconGallery />
      </section>

      {/* Method 4: SVGR Import */}
      <section className="section">
        <h2>SVGR Import</h2>
        <SvgrExample />
      </section>

      {/* Cloudinary SVG Management */}
      <section className="section">
        <h2>☁️ Manage SVGs at Scale with Cloudinary</h2>
        <CloudinaryExample />
      </section>

      {/* Interactive Demo */}
      <section className="section">
        <h2>🚀 Interactive Demo</h2>
        <InteractiveDemo />
      </section>

      {/* Footer */}
      <footer style={{ textAlign: 'center', padding: '2rem', color: '#6b7280' }}>
        <p>Built with React + Vite</p>
      </footer>
    </div>
  )
}

export default App