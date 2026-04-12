package com.baeldung.lnj.domain.model;

// permits is inferred for this interface
public sealed interface TaskResult {
    boolean hasError();

    final class Success implements TaskResult {
        @Override
        public boolean hasError() {
            return false;
        }
    }

    final class Failure implements TaskResult {
        @Override
        public boolean hasError() {
            return true;
        }
    }
}
