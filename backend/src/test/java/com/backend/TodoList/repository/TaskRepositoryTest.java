package com.backend.TodoList.repository;

import com.backend.TodoList.repository.entity.TaskEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();

        TaskEntity task1 = new TaskEntity();
        task1.setId(1);
        task1.setTitle("Task 1");
        task1.setPriority(1);
        task1.setCompleted(false);
        TaskEntity task2 = new TaskEntity();
        task1.setId(2);
        task2.setTitle("Task 2");
        task2.setPriority(2);
        task2.setCompleted(false);
        TaskEntity task3 = new TaskEntity();
        task1.setId(3);
        task3.setTitle("Task 3");
        task3.setPriority(3);
        task3.setCompleted(false);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        entityManager.flush();
    }

    @Test
    public void testFindAllByOrderByPriorityAsc() {
        List<TaskEntity> tasks = taskRepository.findAllByOrderByPriorityAsc();
        assertThat(tasks).hasSize(3);
        assertThat(tasks.get(0).getPriority()).isEqualTo(1);
        assertThat(tasks.get(1).getPriority()).isEqualTo(2);
        assertThat(tasks.get(2).getPriority()).isEqualTo(3);
    }

    @Test
    public void testInsertTask() {
        taskRepository.insertTask("Task 4");
        List<TaskEntity> tasks = taskRepository.findAllByOrderByPriorityAsc();

        assertThat(tasks).hasSize(4);
        assertThat(tasks.get(3).getTitle()).isEqualTo("Task 4");
        assertThat(tasks.get(3).getPriority()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void testUpdateOtherTasksPriorityForMoveUp() {
        taskRepository.updateOtherTasksPriorityForMoveUp(3, 1);
        entityManager.flush();
        entityManager.clear();
        List<TaskEntity> tasks = taskRepository.findAllByOrderByPriorityAsc();

        assertThat(tasks).hasSize(3);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Task 1");
        assertThat(tasks.get(0).getPriority()).isEqualTo(2);
        assertThat(tasks.get(1).getTitle()).isEqualTo("Task 2");
        assertThat(tasks.get(1).getPriority()).isEqualTo(3);
        assertThat(tasks.get(2).getTitle()).isEqualTo("Task 3");
        assertThat(tasks.get(2).getPriority()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void testUpdateOtherTasksPriorityForMoveDown() {
        taskRepository.updateOtherTasksPriorityForMoveDown(1, 2);
        entityManager.flush();
        entityManager.clear();
        List<TaskEntity> tasks = taskRepository.findAllByOrderByPriorityAsc();
        assertThat(tasks.get(1).getPriority()).isEqualTo(1); // Task 1's priority should have been decremented
    }

    @Test
    @Transactional
    public void testUpdateOtherTasksPriorityForRemove() {
        taskRepository.updateOtherTasksPriorityForRemove(1);
        entityManager.flush();
        entityManager.clear();
        List<TaskEntity> tasks = taskRepository.findAllByOrderByPriorityAsc();
        assertThat(tasks).hasSize(3);
        assertThat(tasks.get(1).getPriority()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testUpdateTaskPriority() {
        TaskEntity task = taskRepository.findFirstByOrderByIdDesc();
        taskRepository.updateTaskPriority(task.getId(), 1);
        entityManager.flush();
        entityManager.clear();
        TaskEntity updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(updatedTask.getPriority()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testToggleCompleted() {
        TaskEntity task = taskRepository.findFirstByOrderByIdDesc();
        taskRepository.toggleCompleted(task.getId());
        entityManager.flush();
        entityManager.clear();

        TaskEntity updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(updatedTask.getCompleted()).isTrue();
    }

    @Test
    public void testFindFirstByOrderByIdDesc() {
        TaskEntity task = taskRepository.findFirstByOrderByIdDesc();
        assertThat(task.getTitle()).isEqualTo("Task 3");
    }
}
