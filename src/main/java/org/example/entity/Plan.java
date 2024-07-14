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
    @Column()
    private String description;

    @Column()
    private LocalDateTime deadline;
    @Column()
    private boolean completed = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column()
    private int rating;

    @Column()
    private PlanCategory planCategory;
}
