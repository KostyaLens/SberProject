package org.example.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Plan {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private boolean completed;
    private int id;
    private int rating;
}
