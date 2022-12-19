package org.plexobject.demo.services.exceptions;

import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationException extends RuntimeException {
    public final Set<String> errors = new HashSet<>();

    public void checkArgument(boolean cond, String message) {
        if (!cond) {
            errors.add(message);
        }
    }
    public void checkNullEmpty(String val, String message) {
        if (val == null || val.trim().length() == 0) {
            errors.add(message);
        }
    }
    public void checkNullEmpty(List<?> val, String message) {
        if (val == null || val.size() == 0) {
            errors.add(message);
        }
    }
    public void checkNull(Object val, String message) {
        if (val == null) {
            errors.add(message);
        }
    }
    public void checkThrow() {
        if (!errors.isEmpty()) {
           throw this;
        }
    }

    public void checkNullZero(MonetaryAmount val , String message) {
        if (val == null || val.isZero()) {
            errors.add(message);
        }
    }
    public void checkNullZero(int val , String message) {
        if (val == 0) {
            errors.add(message);
        }
    }
}
