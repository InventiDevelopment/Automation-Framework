package cz.inventi.qa.framework.core.objects.web;

public class WebComponent<T extends WebObject> extends WebObject {

    public WebComponent(WOProps props) {
        super(props);
    }

    public T leaveComponent() {
        return getKlass();
    }

    @SuppressWarnings("unchecked")
    public T getKlass() {
        return (T) getProps().getReturnKlass();
    }
}
