package org.thesalutyt.vimzer.api.features.commmand;

public enum CommandExecutionFeedback {
    SUCCESS("Command executed successfully.", true),
    FAIL("Command failed with error: %ERROR_MESSAGE% (%ERROR_CODE%).", false);

    private String message;
    private final boolean success;

    CommandExecutionFeedback(String message, boolean success) {
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
}
