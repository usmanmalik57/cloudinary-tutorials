// src/components/icons/StarIcon.jsx

/**
 * StarIcon - A reusable star icon component
 * 
 * @param {number} size - Width and height in pixels (default: 24)
 * @param {string} color - Stroke/fill color (default: 'currentColor')
 * @param {boolean} filled - Whether the star is filled (default: false)
 * @param {string} className - Additional CSS classes
 */
function StarIcon({
    size = 24,
    color = 'currentColor',
    filled = false,
    className = '',
    ...props  // Spread remaining props (onClick, style, etc.)
}) {
    return (
        <svg
            width={size}
            height={size}
            viewBox="0 0 24 24"
            fill={filled ? color : 'none'}
            stroke={color}
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            className={className}
            role="img"
            aria-label="Star icon"
            {...props}
        >
            <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
        </svg>
    )
}

export default StarIcon