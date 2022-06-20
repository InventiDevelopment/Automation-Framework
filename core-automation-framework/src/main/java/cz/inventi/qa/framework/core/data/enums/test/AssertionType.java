package cz.inventi.qa.framework.core.data.enums.test;

public enum AssertionType {
    SOFT("soft assertion"),
    HARD("hard assertion");

    private final String name;

    AssertionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
