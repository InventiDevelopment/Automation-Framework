package cz.inventi.qa.framework.core.objects.framework;

public class FrameworkAssertionException extends FrameworkException {

    public FrameworkAssertionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public FrameworkAssertionException(String errorMessage) {
        super(errorMessage);
    }

    public FrameworkAssertionException(Throwable err) {
        super(err);
    }
}
