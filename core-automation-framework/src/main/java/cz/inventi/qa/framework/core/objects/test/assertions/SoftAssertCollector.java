package cz.inventi.qa.framework.core.objects.test.assertions;

import cz.inventi.qa.framework.core.objects.framework.FrameworkAssertionException;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

import java.util.ArrayList;
import java.util.List;

public class SoftAssertCollector {
    private final List<FrameworkAssertionException> exceptions;

    public SoftAssertCollector() {
        this.exceptions = new ArrayList<>();
    }

    public void printExceptions() {
        if (exceptions.size() > 0) {
            FrameworkException e = new FrameworkException(
                    "Suppressed exceptions (" + exceptions.size() + ") occurred during runtime:\n"
                    + getExceptionsGrouped()
            );
            e.setStackTrace(new StackTraceElement[]{});
            throw e;
        }
    }

    private String getExceptionsGrouped() {
        StringBuilder exceptionsGrouped = new StringBuilder();
        for (int count = 1; count <= exceptions.size(); count++) {
            exceptionsGrouped
                    .append("\nSuppressed exception #")
                    .append(count)
                    .append(":\n")
                    .append(exceptions.get(count - 1).getStackTraceAsString());
        }
        return exceptionsGrouped.toString();
    }

    public void addException(FrameworkAssertionException e) {
        exceptions.add(e);
    }
}