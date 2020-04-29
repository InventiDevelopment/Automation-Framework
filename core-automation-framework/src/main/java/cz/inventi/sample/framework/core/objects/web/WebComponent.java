package cz.inventi.sample.framework.core.objects.web;

public class WebComponent<T extends WebObject> extends WebObject {
    private WOProps props;

    public WebComponent(WOProps props) {
        super(props);
        this.props = props;
    }

    public T leaveComponent() {
        return getKlass();
    }

    public T getKlass() {
        return (T) props.getReturnKlass();
    }
}
