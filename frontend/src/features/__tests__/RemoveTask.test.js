import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import { useDispatch } from 'react-redux';
import { getAllTasks, deleteTask } from '../../store/tasksSlide';
import RemoveTask from '../RemoveTask';

jest.mock('react-redux', () => ({
    useDispatch: jest.fn(),
}));

jest.mock('../../store/tasksSlide', () => ({
    deleteTask: jest.fn(),
    getAllTasks: jest.fn(),
}));

describe('RemoveTask Component', () => {
    let dispatchMock;

    beforeEach(() => {
        dispatchMock = jest.fn();
        useDispatch.mockReturnValue(dispatchMock);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('dispatches deleteTask and getAllTasks actions when button is clicked', async () => {
        const taskId = 1;
        render(<RemoveTask id={taskId} />);

        const button = screen.getByRole('button');
        fireEvent.click(button);

        expect(dispatchMock).toHaveBeenCalledWith(deleteTask(taskId));
        expect(dispatchMock).toHaveBeenCalledWith(getAllTasks());
    });
});
