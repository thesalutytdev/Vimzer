package org.thesalutyt.vimzer.api.visual.screen.window.widget;

import javax.swing.*;

public class ITextArea extends IWidget {
    public JTextArea textArea;

    public ITextArea(String defaultText, int WIDTH, int HEIGHT) {
        super(defaultText, WIDTH, HEIGHT);
        this.textArea = new JTextArea(defaultText);
    }

    public void setDefaultText(String text) {
        this.textArea.setText(text);
    }

    public String getText() {
        return this.textArea.getText();
    }
}
