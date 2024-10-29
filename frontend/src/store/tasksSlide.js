import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { addTaskService, getAllTasksService, toggleTaskService, deleteTaskService, moveTaskDownService, moveTaskUpService } from '../service/taskService'

export const addTask = createAsyncThunk('task/add', async (title) => {
    const newTask = { title };
    const response = await addTaskService(newTask);
    return response;
});

export const getAllTasks = createAsyncThunk('task/getAllTasks', async () => {
    const response = await getAllTasksService();
    return response;
});

export const toggleTask = createAsyncThunk('task/toggle', async (id) => {
    const response = await toggleTaskService(id);
    return response;
});

export const deleteTask = createAsyncThunk('task/delete', async (id) => {
    const response = await deleteTaskService(id);
    return response;
});

export const moveTaskUp = createAsyncThunk('task/moveup', async (moveTask) => {
    const response = await moveTaskUpService(moveTask);
    return response;
})
export const moveTaskDown = createAsyncThunk('task/movedown', async (moveTask) => {
    const response = await moveTaskDownService(moveTask);
    return response;
})

export const tasksSlide = createSlice({
    name: 'task',
    initialState: {
        items: []
    },
    extraReducers: (builder) => {
        builder
            .addCase(getAllTasks.fulfilled, (state, action) => {
                state.items = [...action.payload.taskList];
            })
    },
});
export default tasksSlide.reducer