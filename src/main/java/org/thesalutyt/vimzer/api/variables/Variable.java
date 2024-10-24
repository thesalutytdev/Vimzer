package org.thesalutyt.vimzer.api.variables;

public class Variable implements IVar {
    private final String name;
    private Object value;

    public Variable(String name, Object value) {
        this.name = name.replaceFirst("\"", "").replaceFirst("'", "");
        this.value = value;

        System.out.println(String.format("Added variable: %s: %s", name, value));
    }

    public void setValue(Object value) {
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
        return String.format("var: %s: %s", name, value);
    }
}
