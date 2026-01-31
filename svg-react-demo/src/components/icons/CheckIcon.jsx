// src/components/icons/CheckIcon.jsx

function CheckIcon({
    size = 24,
    color = 'currentColor',
    filled = false,
    className = '',
    ...props
}) {
    // Filled version shows a circle with checkmark
    if (filled) {
        return (
            <svg
                width={size}
                height={size}
                viewBox="0 0 24 24"
                fill={color}
                className={className}
                role="img"
                aria-label="Checked"
                {...props}
            >
                <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" />
            </svg>
        )
    }

    // Outline version shows just the checkmark
    return (
        <svg
            width={size}
            height={size}
            viewBox="0 0 24 24"
            fill="none"
            stroke={color}
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            className={className}
            role="img"
            aria-label="Check icon"
            {...props}
        >
            <polyline points="20 6 9 17 4 12" />
        </svg>
    )
}

export default CheckIcon