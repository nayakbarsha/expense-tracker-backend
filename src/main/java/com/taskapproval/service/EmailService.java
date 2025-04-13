package com.taskapproval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    public void sendApprovalRequestEmail(String to, String creatorName, String taskTitle, Long taskId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Task Approval Request");
        message.setText(String.format(
            "Hello,\n\n" +
            "%s has created a new task titled '%s' that requires your approval.\n\n" +
            "Please log in to review and approve/reject this task.\n\n" +
            "Task ID: %d\n\n" +
            "Thanks,\nTask Approval System",
            creatorName, taskTitle, taskId
        ));
        
        mailSender.send(message);
    }
    
    public void sendApprovalActionNotification(String to, String approverName, String taskTitle, boolean isApproved, Long taskId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Task Approval Update");
        message.setText(String.format(
            "Hello,\n\n" +
            "%s has %s your task titled '%s'.\n\n" +
            "Please log in to view the details.\n\n" +
            "Task ID: %d\n\n" +
            "Thanks,\nTask Approval System",
            approverName, 
            isApproved ? "approved" : "rejected", 
            taskTitle, 
            taskId
        ));
        
        mailSender.send(message);
    }
    
    public void sendTaskCompletedNotification(String to, String taskTitle, Long taskId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Task Fully Approved");
        message.setText(String.format(
            "Hello,\n\n" +
            "The task titled '%s' has been fully approved by all required approvers.\n\n" +
            "Please log in to view the details.\n\n" +
            "Task ID: %d\n\n" +
            "Thanks,\nTask Approval System",
            taskTitle, 
            taskId
        ));
        
        mailSender.send(message);
    }
    
    public void sendCommentNotification(String to, String commenterName, String taskTitle, String commentContent, Long taskId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Comment on Task");
        message.setText(String.format(
            "Hello,\n\n" +
            "%s has commented on task titled '%s':\n\n" +
            "\"%s\"\n\n" +
            "Please log in to view the full details.\n\n" +
            "Task ID: %d\n\n" +
            "Thanks,\nTask Approval System",
            commenterName, 
            taskTitle,
            commentContent,
            taskId
        ));
        
        mailSender.send(message);
    }
}
