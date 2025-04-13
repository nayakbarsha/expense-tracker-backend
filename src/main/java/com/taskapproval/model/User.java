package com.taskapproval.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.taskapproval.model.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private Set<Task> createdTasks = new HashSet<>();
    
    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL)
    private Set<Approval> approvals = new HashSet<>();
    
    // Constructor for essential fields
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
