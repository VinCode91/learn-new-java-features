package com.baeldung.lnj.domain.model;

import java.time.LocalDate;

public class WebTask extends Task{

    private String url;


    public WebTask(String code, String name, String description, String rawUrl) {
        String sanitizedUrl = rawUrl.strip();
        if (!sanitizedUrl.startsWith("http")) {
            sanitizedUrl = "https://" + sanitizedUrl;
        }
        String safeName = (name == null) ? "Untitled Web Task" : name;

        super(code, safeName, description, LocalDate.now());
        this.url = sanitizedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
