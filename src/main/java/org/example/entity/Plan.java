package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.PlanCategory;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plan")
public class Plan {

    @NotBlank(message = "Не задано название цели")
    @Column(name="name")
    private String name;
    @NotBlank(message = "Отсуствует описание")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime deadline;
    @Column(nullable = false)
    private boolean completed = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private PlanCategory planCategory;
}
