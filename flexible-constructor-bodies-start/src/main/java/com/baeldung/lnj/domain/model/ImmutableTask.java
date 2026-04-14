package com.baeldung.lnj.domain.model;

public record ImmutableTask(String title, int priority) {

    public ImmutableTask(String title) {
        int defaulPriority = title.contains("Urgent") ? 1 : 5;
        this(title, defaulPriority);
    }

    public ImmutableTask {
        // This already works from Java 16. However there is no explicit call to constructor inside the compact record
        // constructor which effectively represents the canonical constructor
        if (priority < 0 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 0 and 10");
        }
    }
}
