import React from 'react'
import { useDispatch } from 'react-redux'
import { addTask, getAllTasks } from '../store/tasksSlide'

const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '2rem',
    borderRadius: '12px',
    backgroundColor: '#ffffff',
    boxShadow: '0 6px 12px rgba(0, 0, 0, 0.1)',
    width: '100%',
    maxWidth: '400px',
    margin: '1rem auto',
};

const h1Style = {
    fontSize: '1.5rem',
    color: '#333',
    marginBottom: '1.5rem'
};

const inputStyle = {
    padding: '0.75rem 1rem',
    fontSize: '1rem',
    border: '1px solid #e0e0e0',
    borderRadius: '8px',
    outline: 'none',
    marginBottom: '1rem',
    width: '100%',
    boxSizing: 'border-box',
    transition: 'border-color 0.3s',
};

const buttonSubmitStyle = {
    padding: '0.75rem',
    fontSize: '1rem',
    backgroundColor: '#4a90e2',
    color: '#ffffff',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
    width: '100%',
    fontWeight: 'bold',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    transition: 'background-color 0.3s, transform 0.1s',
};

const AddTask = () => {
    const dispatch = useDispatch();
    const addTaskFn = (e) => {
        e.preventDefault();
        console.log(e.target);
        const add = async () => {
            await dispatch(addTask(e.target[0].value));
            dispatch(getAllTasks());
        };
        add();
    };

    return (
        <div style={containerStyle}>
            <h1 style={h1Style}>Add Task</h1>
            <form name="addForm" onSubmit={addTaskFn} style={{ width: '100%' }}>
                <input
                    name="add"
                    placeholder="Enter task..."
                    style={inputStyle}
                    onFocus={(e) => (e.target.style.borderColor = '#4a90e2')}
                    onBlur={(e) => (e.target.style.borderColor = '#e0e0e0')}
                />
                <button
                    type="submit"
                    style={buttonSubmitStyle}
                    onMouseEnter={(e) => {
                        e.target.style.backgroundColor = '#357ABD';
                        e.target.style.transform = 'scale(1.02)';
                    }}
                    onMouseLeave={(e) => {
                        e.target.style.backgroundColor = '#4a90e2';
                        e.target.style.transform = 'scale(1)';
                    }}
                >
                    Add
                </button>
            </form>
        </div>
    )
};
export default AddTask;