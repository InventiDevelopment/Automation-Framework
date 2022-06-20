package cz.inventi.qa.framework.core.objects.framework;

public class FrameworkAssertionException extends FrameworkException {

    public FrameworkAssertionException(String assertionName, Throwable err) {
        super("\n" + err.getClass() + " raised for assertion: '" + assertionName + "'\n" + err.getMessage());
    }

    public FrameworkAssertionException(String errorMessage, String assertionName) {
        super("\nException raised for assertion: '" + assertionName + "'\n" + errorMessage);
    }

    public FrameworkAssertionException(Throwable err) {
        super(err);
    }
}
