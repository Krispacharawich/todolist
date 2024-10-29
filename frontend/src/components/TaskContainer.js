import AddTask from '../features/AddTask';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import TaskList from './TaskList';

const taskContainerStyle = {
    paddingTop: '80px',
}
const TaskContainer = () =>
(
    <div style={taskContainerStyle}>
        <AddTask />
        <DndProvider backend={HTML5Backend}>
            <TaskList />
        </DndProvider>
    </div>
);

export default TaskContainer;