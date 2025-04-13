package com.taskapproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.taskapproval.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatorId(Long creatorId);

    @Query("SELECT t FROM Task t JOIN t.approvals a WHERE a.approver.id = :approverId")
    List<Task> findTasksAwaitingApproval(@Param("approverId") Long approverId);
}
