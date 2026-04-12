package com.baeldung.lnj.domain.model;

public final class UrgentTask implements CategorizedTask{
    @Override
    public String getCategory() {
        return "urgent";
    }
}
