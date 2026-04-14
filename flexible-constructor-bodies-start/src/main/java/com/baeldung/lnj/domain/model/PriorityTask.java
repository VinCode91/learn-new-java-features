package com.baeldung.lnj.domain.model;

import java.time.LocalDate;

public class PriorityTask extends Task{

    private int priority;

    public PriorityTask(String code, String name, String description, LocalDate dueDate, int priority) {
        if (priority < 0 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 0 and 10");
        }
        super(code, name, description, dueDate);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
