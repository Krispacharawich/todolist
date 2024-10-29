import React from 'react';
import PropTypes from 'prop-types';
import { useDrag, useDrop } from 'react-dnd';
import { useSelector } from 'react-redux'
import RemoveTask from '../features/RemoveTask';
import ToggleTask from '../features/ToggleTask';

const ItemType = 'TASK';

const TaskItem = ({ task, index, onDelete, moveItem }) => {
    const ref = React.useRef(null);
    const taskList = useSelector((state) => state.tasksState.items);
    
    const [{ isDragging }, drag] = useDrag({
        type: ItemType,
        item: () => {
            return { index }
        },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        end: (item, monitor) => {
            if (monitor.didDrop()) {
                moveItem(index, monitor.getDropResult().dropIndex, taskList);
            }
        }
    });
    const opacity = isDragging ? 0 : 1
    const [{ handlerId, isOver }, drop] = useDrop({
        accept: ItemType,
        collect(monitor) {
            return {
                handlerId: monitor.getHandlerId(),
                isOver: monitor.isOver(),
            }
        },
        hover(item, monitor) {
            if (!ref.current) {
                return
            }
            const dragIndex = item.index
            const hoverIndex = index
            if (dragIndex === hoverIndex) {
                return
            }
            // Determine rectangle on screen
            const hoverBoundingRect = ref.current?.getBoundingClientRect()
            // Get vertical middle
            const hoverMiddleY =
                (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2
            // Determine mouse position
            const clientOffset = monitor.getClientOffset()
            // Get pixels to the top
            const hoverClientY = clientOffset.y - hoverBoundingRect.top
            // Only perform the move when the mouse has crossed half of the items height
            // When dragging downwards, only move when the cursor is below 50%
            // When dragging upwards, only move when the cursor is above 50%
            // Dragging downwards
            if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
                return
            }
            // Dragging upwards
            if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
                return
            }
            // Time to actually perform the action
            // moveItem(dragIndex, hoverIndex)
            // Note: we're mutating the monitor item here!
            // Generally it's better to avoid mutations,
            // but it's good here for the sake of performance
            // to avoid expensive index searches.
            item.index = hoverIndex
        },
        drop(item) {
            return { dropIndex: item.index }
        }
    });

    drag(drop(ref));
    
    return (
        <div
            ref={ref}
            style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '1rem',
                borderRadius: '12px',
                cursor: 'pointer',
                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
                marginBottom: '1rem',
                opacity,
                backgroundColor: isOver ? '#a7f9ec' : '#ffffff',
            }}
            data-handler-id={handlerId}
        >
            <li style={{
                textDecoration: task.completed ? 'line-through' : 'none',
                fontSize: '1rem',
                color: task.completed ? 'red' : '#333',
                listStyleType: 'none',
                flex: 1,
                marginRight: '1rem',
            }}>
                <b>{task.title}</b>
            </li>
            <ToggleTask task={task} />
            <RemoveTask id={task.id} onDelete={onDelete} />
        </div>
    )
};

TaskItem.propTypes = {
    task: PropTypes.shape({
        title: PropTypes.string,
        completed: PropTypes.bool
    }).isRequired
}

export default TaskItem;