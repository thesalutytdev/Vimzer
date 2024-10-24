package org.thesalutyt.vimzer.api.features.commmand.argument;

public enum ArgumentType {
    STRING,
    NUMBER,
    BOOLEAN,
    ANY,
    NONE,
    VAR,
    ERROR;

    @Override
    public String toString() {
        return name();
    }
}
