package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plan")
public class Plan {

    @NotBlank(message = "Не определено название цели")
    @Column(name="name")
    private String name;
    @NotBlank(message = "Не определенно описание цели")
    @Column
    private String description;

    @Column
    @NotNull(message = "Не определён срок до которого нужно выполнить поставленую цель")
    private LocalDateTime deadline;
    @Column
    private boolean completed = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Min(message = "Не определена важность задачи", value = 1)
    private int priority;

    @Column
    @NotNull(message = "Не определена категория цели")
    private PlanCategory planCategory;

    @Column
    private LocalDateTime dateTimeEndPlan;
    @Column
    private Frequency frequency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
