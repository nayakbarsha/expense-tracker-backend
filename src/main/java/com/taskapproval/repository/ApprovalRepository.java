package com.taskapproval.repository;

import com.taskapproval.model.Approval;
import com.taskapproval.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByTaskId(Long taskId);

    List<Approval> findByApproverId(Long approverId);

    Optional<Approval> findByTaskIdAndApproverId(Long taskId, Long approverId);

    @Repository
    interface CommentRepository extends JpaRepository<Comment, Long> {
        List<Comment> findByTaskIdOrderByCreatedAtDesc(Long taskId);
    }
}
