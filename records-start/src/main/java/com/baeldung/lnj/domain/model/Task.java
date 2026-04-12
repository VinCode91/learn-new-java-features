package com.baeldung.lnj.domain.model;

import java.io.Serializable;

public record Task(String code, String name) implements Serializable {

    /**
     * When we declare a record, the Java compiler automatically generates several members for us:
     *
     * private final fields for each property – code, name
     * A “canonical constructor” that takes all components as arguments – Task(String code, String name)
     * Public accessor methods for each component – code(), name()
     * Correct implementations of equals(), hashCode(), and toString() based on all the components in the record header
     */

    public static final String DEFAULT_NAME = "DEFAULT";


    /**
     * Compact constructor to run validation or normalization code before canonical constructor is called
     */
    public Task {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code can't be blank");
        }
        if (name == null || name.isBlank()) {
            name = DEFAULT_NAME; // Normalization
        }
    }

    /**
     * Overloaded constructors must always delegate to the canonical one
     */
    public Task(String code) {
        this(code, "Default Name");
    }

    public static Task fromCampaign(Campaign campaign) {
        return new Task(campaign.getCode(), campaign.getName());
    }

    public String asLogEntry() {
        return "Task[" + code + "]";
    }
}
