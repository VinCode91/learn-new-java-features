package com.baeldung.lnj.domain.model;

public interface TaskValidator {
    Task getTask();

    default boolean isValidCode() {
        return isValidCode(getTask().getCode());
    }
    default boolean isDescriptionValid() {
        return isDescriptionNotNullOrEmpty();
    }

    private boolean isDescriptionNotNullOrEmpty() {
        String description = getTask().getDescription();
        return !(description == null || description.trim().isEmpty());
    }

    private static boolean isValidCode(String str) {
        return str != null && str.startsWith("a");
    }
}
