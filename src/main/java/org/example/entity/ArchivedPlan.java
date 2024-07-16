package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "archivedPlan")
public class ArchivedPlan {

    @Column(name="name")
    private String name;

    @Column
    private String description;

    @Column
    private LocalDateTime deadline;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int rating;

    @Column
    private PlanCategory planCategory;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
