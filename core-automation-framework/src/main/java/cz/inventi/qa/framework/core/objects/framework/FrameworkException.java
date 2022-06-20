package cz.inventi.qa.framework.core.objects.framework;

import java.io.PrintWriter;
import java.io.StringWriter;

public class FrameworkException extends RuntimeException {

    public FrameworkException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public FrameworkException(String errorMessage) {
        super(errorMessage);
    }

    public FrameworkException(Throwable err) {
        super(err);
    }

    public String getStackTraceAsString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        printStackTrace(pw);
        return sw.toString();
    }
}
