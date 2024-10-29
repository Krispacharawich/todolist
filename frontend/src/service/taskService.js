
const BASE_URL = "http://localhost:8080/api/tasks";

export const getAllTasksService = async () => {
    const response = await fetch(`${BASE_URL}/`, {
        method: 'GET'
    });
    return await response.json();
};

export const addTaskService = async (task) => {
    const response = await fetch(`${BASE_URL}/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(task),
    });
    return await response.json();
};

export const deleteTaskService = async (id) => {
    const response = await fetch(`${BASE_URL}/${id}`, {
        method: 'DELETE'
    });
    return await response.json();
}
export const toggleTaskService = async (id) => {
    const response = await fetch(`${BASE_URL}/${id}/toggle`, {
        method: 'POST',
    });

    return await response.json();
};

export const moveTaskUpService = async (moveTask) => {
    const response = await fetch(`${BASE_URL}/priority/move-up`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(moveTask),
    });
    return await response.json();
}

export const moveTaskDownService = async (moveTask) => {
    const response = await fetch(`${BASE_URL}/priority/move-down`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(moveTask),
    });
    return await response.json();
}

