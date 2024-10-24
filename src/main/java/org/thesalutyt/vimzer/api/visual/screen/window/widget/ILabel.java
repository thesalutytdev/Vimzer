package org.thesalutyt.api.visual.screen.window.widget;

import javax.swing.*;

public class ILabel extends IWidget {
    protected JLabel label;

    public ILabel(String text, int WIDTH, int HEIGHT) {
        super(text, WIDTH, HEIGHT);
        this.label = new JLabel(text);
    }

    public void setText(String text) {
        this.label.setText(text);
    }
}
