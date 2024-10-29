import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import { useDispatch } from 'react-redux';
import { addTask, getAllTasks } from '../../store/tasksSlide';
import AddTask from '../AddTask';


jest.mock('react-redux', () => ({
    useDispatch: jest.fn(),
}));

jest.mock('../../store/tasksSlide', () => ({
    addTask: jest.fn(),
    getAllTasks: jest.fn(),
}));

describe('AddTask Component', () => {
    let dispatchMock;

    beforeEach(() => {
        dispatchMock = jest.fn();
        useDispatch.mockReturnValue(dispatchMock);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('renders the form with input and submit button', () => {
        render(<AddTask />);
        const input = screen.getByPlaceholderText('Enter task...');
        expect(input).toBeInTheDocument();

        const button = screen.getByRole('button', { name: /add/i });
        expect(button).toBeInTheDocument();
    });

    it('dispatches addTask and getAllTasks actions when form is submitted', async () => {
        render(<AddTask />);

        const input = screen.getByPlaceholderText('Enter task...');
        const button = screen.getByRole('button', { name: /add/i });
        const form = screen.getByRole('form');
        fireEvent.submit(form, { target: [{ value: 'Task1' }] });
        fireEvent.click(button);

        expect(dispatchMock).toHaveBeenCalledWith(addTask('Task1'));
        expect(dispatchMock).toHaveBeenCalledWith(getAllTasks());
    });
});
