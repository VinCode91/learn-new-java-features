package com.baeldung.lnj.domain.model;

public final class LargeFeatureTask extends FeatureTask{
    @Override
    public String getCategory() {
        return "large-" + super.getCategory();
    }
}
