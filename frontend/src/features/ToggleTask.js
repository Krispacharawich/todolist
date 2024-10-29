import React from 'react';

import { useDispatch } from 'react-redux'
import { getAllTasks, toggleTask } from '../store/tasksSlide'

const ToggleTask = ({ task }) => {
    const dispatch = useDispatch();
    const onToggle = (id) => {
        const toToggle = async () => {
            await dispatch(toggleTask(id));
            dispatch(getAllTasks());
        };
        toToggle();
    };
    return (
        <label style={{ display: 'flex', alignItems: 'center', marginRight: '1rem' }}>
            <input
                type="checkbox"
                checked={task.completed}
                onChange={() => onToggle(task.id)}
                style={{
                    marginRight: '0.5rem',
                    cursor: 'pointer',
                }}
            />
            <span style={{ fontSize: '0.9rem', color: '#666' }}>Done</span>
        </label>
    );
};
export default ToggleTask;