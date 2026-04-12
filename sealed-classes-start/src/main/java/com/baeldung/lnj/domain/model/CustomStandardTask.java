package com.baeldung.lnj.domain.model;

public class CustomStandardTask extends StandardTask{
    @Override
    protected String getDetails() {
        return "custom";
    }
}
