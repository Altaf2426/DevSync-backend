package com.altaf.DevSync.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Chats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "workSpace_id")
    private WorkSpace workSpace;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
