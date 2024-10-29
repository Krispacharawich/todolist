import { getAllTasksService, addTaskService, deleteTaskService, toggleTaskService, moveTaskUpService, moveTaskDownService } from '../taskService';

const BASE_URL = "http://localhost:8080/api/tasks";

beforeEach(() => {
    global.fetch = jest.fn();
});

afterEach(() => {
    jest.clearAllMocks();
});

describe('Task Service API', () => {
    it('should fetch all tasks with getAllTasksService', async () => {
        const mockResponse = [{ id: 1, title: 'Task 1', priority: 1, completed: false, createdAt: "2024-10-28", updatedAt: "2024-10-28" }];
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await getAllTasksService();

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/`, { method: 'GET' });
        expect(result).toEqual(mockResponse);
    });

    it('should add a task with addTaskService', async () => {
        const newTask = { title: 'New Task' };
        const mockResponse = { success: true, id: 2 };
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await addTaskService(newTask);

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newTask),
        });
        expect(result).toEqual(mockResponse);
    });

    it('should delete a task with deleteTaskService', async () => {
        const taskId = 1;
        const mockResponse = { success: true };
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await deleteTaskService(taskId);

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/${taskId}`, { method: 'DELETE' });
        expect(result).toEqual(mockResponse);
    });

    it('should toggle a task with toggleTaskService', async () => {
        const taskId = 1;
        const mockResponse = { success: true };
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await toggleTaskService(taskId);

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/${taskId}/toggle`, { method: 'POST' });
        expect(result).toEqual(mockResponse);
    });

    it('should move a task up with moveTaskUpService', async () => {
        const moveTask = { id: 1, priority: 'high' };
        const mockResponse = { success: true };
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await moveTaskUpService(moveTask);

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/priority/move-up`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(moveTask),
        });
        expect(result).toEqual(mockResponse);
    });

    it('should move a task down with moveTaskDownService', async () => {
        const moveTask = { id: 2, priority: 'low' };
        const mockResponse = { success: true };
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockResponse,
        });

        const result = await moveTaskDownService(moveTask);

        expect(fetch).toHaveBeenCalledWith(`${BASE_URL}/priority/move-down`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(moveTask),
        });
        expect(result).toEqual(mockResponse);
    });
});
