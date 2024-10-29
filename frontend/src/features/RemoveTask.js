import React from 'react';
import { FaTrash } from 'react-icons/fa';
import { useDispatch } from 'react-redux'
import { getAllTasks, deleteTask } from '../store/tasksSlide'

const RemoveTask = ({ id }) => {
    const dispatch = useDispatch();
    const onDeleteFn = () => {
        const toDelete = async () => {
            await dispatch(deleteTask(id));
            dispatch(getAllTasks());
        };
        toDelete();
    };
    return (
        <button
            onClick={onDeleteFn}
            style={{
                background: 'none',
                border: 'none',
                cursor: 'pointer',
                padding: '0.5rem',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                borderRadius: '50%',
                transition: 'background-color 0.3s',
            }}
            onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = '#f0f0f0')}
            onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = 'transparent')}
        >
            <FaTrash size={16} color="#888" />
        </button>
    )
}

export default RemoveTask;