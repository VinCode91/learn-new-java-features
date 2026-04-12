package com.baeldung.lnj.domain.model;

public sealed class FeatureTask implements CategorizedTask permits LargeFeatureTask {
    @Override
    public String getCategory() {
        return "feature";
    }
}
