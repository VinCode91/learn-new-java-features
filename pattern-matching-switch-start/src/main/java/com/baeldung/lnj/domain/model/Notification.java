package com.baeldung.lnj.domain.model;

public sealed interface Notification {

    // record are final by design => no need to add the keyword
    record EmailNotification(String recipient, String subject) implements Notification {
    }

    record SmsNotification(String phoneNumber) implements Notification {
    }
}
