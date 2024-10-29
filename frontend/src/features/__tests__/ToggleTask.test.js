import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import { useDispatch } from 'react-redux';
import { toggleTask, getAllTasks } from '../../store/tasksSlide';
import ToggleTask from '../ToggleTask';


jest.mock('react-redux', () => ({
    useDispatch: jest.fn(),
}));

jest.mock('../../store/tasksSlide', () => ({
    toggleTask: jest.fn(),
    getAllTasks: jest.fn(),
}));

describe('ToggleTask Component', () => {
    let dispatchMock;

    beforeEach(() => {
        dispatchMock = jest.fn();
        useDispatch.mockReturnValue(dispatchMock);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('renders the component with checkbox unchecked when task is incomplete', () => {
        const task = { id: 1, completed: false };
        render(<ToggleTask task={task} />);

        const checkbox = screen.getByRole('checkbox');
        expect(checkbox).toBeInTheDocument();
        expect(checkbox).not.toBeChecked();
    });

    it('renders the component with checkbox checked when task is complete', () => {
        const task = { id: 1, completed: true };
        render(<ToggleTask task={task} />);

        const checkbox = screen.getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });

    it('dispatches toggleTask and getAllTasks when checkbox is toggled', async () => {
        const task = { id: 1, completed: false };
        render(<ToggleTask task={task} />);

        const checkbox = screen.getByRole('checkbox');
        fireEvent.click(checkbox);

        expect(dispatchMock).toHaveBeenCalledWith(toggleTask(task.id));
        expect(dispatchMock).toHaveBeenCalledWith(getAllTasks());
    });
});
