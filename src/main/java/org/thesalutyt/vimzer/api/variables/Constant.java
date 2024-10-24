package org.thesalutyt.vimzer.api.variables;

public class Constant implements IVar {
    private final String name;
    private final Object value;

    public Constant(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("const: %s: %s", name, value);
    }
}
