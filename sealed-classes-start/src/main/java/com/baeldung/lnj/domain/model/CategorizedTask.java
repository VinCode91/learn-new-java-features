package com.baeldung.lnj.domain.model;

public sealed interface CategorizedTask permits FeatureTask, UrgentTask, StandardTask {
    String getCategory();
}
