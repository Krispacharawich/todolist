package com.backend.TodoList.repository;

import com.backend.TodoList.repository.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findAllByOrderByPriorityAsc();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO task (title, priority, completed, created_at, updated_at) VALUES (?1, (SELECT COALESCE(MAX(priority), 0) + 1 FROM task)" +
            ", false, NOW(), NOW())", nativeQuery = true)
    void insertTask(String title);
    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.priority = t.priority +1 WHERE t.priority >= ?2 and t.priority < ?1", nativeQuery = true)
    void updateOtherTasksPriorityForMoveUp(Integer fromPriority, Integer toPriority);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.priority = t.priority -1 WHERE t.priority > ?1 and t.priority <= ?2", nativeQuery = true)
    void updateOtherTasksPriorityForMoveDown(Integer fromPriority, Integer toPriority);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.priority = t.priority -1 WHERE t.id > ?1", nativeQuery = true)
    void updateOtherTasksPriorityForRemove(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.priority = ?2 WHERE t.id = ?1", nativeQuery = true)
    void updateTaskPriority(Integer id, Integer priority);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task t SET t.completed = NOT t.completed WHERE t.id = ?1", nativeQuery = true)
    void toggleCompleted(Integer id);

    TaskEntity findFirstByOrderByIdDesc();
}
