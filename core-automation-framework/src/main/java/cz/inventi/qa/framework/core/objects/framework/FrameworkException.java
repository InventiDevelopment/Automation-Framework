package cz.inventi.qa.framework.core.objects.framework;

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
}
