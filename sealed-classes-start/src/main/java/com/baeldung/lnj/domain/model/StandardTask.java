package com.baeldung.lnj.domain.model;

public abstract non-sealed class StandardTask implements CategorizedTask {
    @Override
    public String getCategory() {
        return "standard_" + getDetails();
    }

    protected abstract String getDetails();
}
