package com.altaf.DevSync.Model;

import jakarta.persistence.*;

@Entity
public class WorkSpaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkSpace workSpace;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Role role;
}
