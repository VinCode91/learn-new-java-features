package com.baeldung.lnj.domain.model;

import java.time.LocalDate;
import java.util.logging.Logger;

public class AuditedTask extends Task{

    private static final Logger logger = Logger.getLogger(AuditedTask.class.getName());

    public AuditedTask(String code, String name) {
        logger.info("Attempting to create task with name: " + name);
        super(code, name, "Audited Task", LocalDate.now());
    }
}
