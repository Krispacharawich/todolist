import { useEffect, useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import TaskItem from './TaskItem';
import { getAllTasks, moveTaskUp, moveTaskDown } from '../store/tasksSlide'

const TaskList = () => {
    const taskList = useSelector((state) => state.tasksState.items);
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(getAllTasks());
    }, [dispatch]);

    const reloadMoveTask = async (fnc) => {
        await dispatch(fnc);
        dispatch(getAllTasks());
    };

    const moveItem = useCallback((fromIndex, toIndex, tasks) => {
        const moveTaskObj = {
            id: tasks[fromIndex].id,
            fromPriority: tasks[fromIndex].priority,
            toPriority: tasks[toIndex].priority,
        };

        if (fromIndex < toIndex) {
            reloadMoveTask(moveTaskDown(moveTaskObj));
        } else {
            reloadMoveTask(moveTaskUp(moveTaskObj));
        }

    }, []);
    return (
        <ul>
            {taskList.map((task, index) => (
                <TaskItem key={task.id} index={index} task={task} moveItem={moveItem} />
            ))}
        </ul>
    );
};

export default TaskList;