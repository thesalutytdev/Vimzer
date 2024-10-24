package org.thesalutyt.vimzer.api.features.commmand;

import org.thesalutyt.vimzer.window.popus.DefaultPopup;

public enum Feedback {
    SUCCESS("Command executed successfully.", true),
    FAIL("Command failed with error: %ERROR_MESSAGE% (%ERROR_CODE%).", false),
    MESSAGE("%MESSAGE%", true),;

    private String message;
    private final boolean success;
    private boolean necessary = false;

    Feedback(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    public void raise() {
        new DefaultPopup(getMessage()).display();
    }
}
