// src/components/InteractiveDemo.jsx

import { useState } from 'react'
import { StarIcon, HeartIcon, CheckIcon } from './icons'

function InteractiveDemo() {
    // State for star rating
    const [rating, setRating] = useState(3)
    const [hoverRating, setHoverRating] = useState(0)

    // State for like button
    const [liked, setLiked] = useState(false)

    // State for task list
    const [tasks, setTasks] = useState([
        { id: 1, text: 'Learn SVG basics', done: true },
        { id: 2, text: 'Use inline SVGs in React', done: true },
        { id: 3, text: 'Create reusable components', done: false },
    ])

    const toggleTask = (id) => {
        setTasks(tasks.map(task =>
            task.id === id ? { ...task, done: !task.done } : task
        ))
    }

    return (
        <div className="example-container">
            <p style={{ marginBottom: '1.5rem', color: '#6b7280' }}>
                SVGs in React become powerful when combined with state and event handlers.
                Here are some real-world interactive examples.
            </p>

            {/* Star Rating */}
            <div style={{ marginBottom: '2rem' }}>
                <h3 style={{ fontSize: '1rem', marginBottom: '0.75rem', color: '#374151' }}>
                    ⭐ Star Rating Component
                </h3>
                <div style={{ display: 'flex', gap: '0.25rem' }}>
                    {[1, 2, 3, 4, 5].map((star) => (
                        <StarIcon
                            key={star}
                            size={32}
                            color="#f59e0b"
                            filled={star <= (hoverRating || rating)}
                            onClick={() => setRating(star)}
                            onMouseEnter={() => setHoverRating(star)}
                            onMouseLeave={() => setHoverRating(0)}
                            style={{ cursor: 'pointer' }}
                        />
                    ))}
                </div>
                <p style={{ marginTop: '0.5rem', fontSize: '0.875rem', color: '#6b7280' }}>
                    You rated: {rating} out of 5 stars
                </p>
            </div>

            {/* Like Button */}
            <div style={{ marginBottom: '2rem' }}>
                <h3 style={{ fontSize: '1rem', marginBottom: '0.75rem', color: '#374151' }}>
                    ❤️ Like Button
                </h3>
                <button
                    onClick={() => setLiked(!liked)}
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.5rem',
                        padding: '0.5rem 1rem',
                        border: 'none',
                        borderRadius: '0.5rem',
                        background: liked ? '#ef4444' : '#6b7280',
                        color: 'white',
                        fontSize: '1rem',
                        cursor: 'pointer',
                        transition: 'background 0.2s ease',
                    }}
                >
                    <HeartIcon size={20} color="white" filled={liked} />
                    {liked ? 'Liked!' : 'Like'}
                </button>
            </div>

            {/* Task List */}
            <div>
                <h3 style={{ fontSize: '1rem', marginBottom: '0.75rem', color: '#374151' }}>
                    ✅ Task List
                </h3>
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {tasks.map((task) => (
                        <li
                            key={task.id}
                            onClick={() => toggleTask(task.id)}
                            style={{
                                display: 'flex',
                                alignItems: 'center',
                                gap: '0.75rem',
                                padding: '0.75rem',
                                marginBottom: '0.5rem',
                                background: task.done ? '#dcfce7' : '#f9fafb',
                                border: `1px solid ${task.done ? '#22c55e' : '#e5e7eb'}`,
                                borderRadius: '0.5rem',
                                cursor: 'pointer',
                                transition: 'all 0.2s ease',
                            }}
                        >
                            <CheckIcon
                                size={24}
                                color={task.done ? '#22c55e' : '#9ca3af'}
                                filled={task.done}
                            />
                            <span style={{
                                textDecoration: task.done ? 'line-through' : 'none',
                                color: task.done ? '#22c55e' : '#374151',
                            }}>
                                {task.text}
                            </span>
                        </li>
                    ))}
                </ul>
                <p style={{ marginTop: '0.5rem', fontSize: '0.875rem', color: '#6b7280' }}>
                    Completed: {tasks.filter(t => t.done).length} of {tasks.length}
                </p>
            </div>
        </div>
    )
}

export default InteractiveDemo