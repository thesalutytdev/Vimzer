package org.thesalutyt.api.visual.screen.window.widget;

import javax.swing.*;

public class IButton extends IWidget {
    public String name;
    public JButton button;

    public IButton(String name) {
        super(name, 10, 10);
        this.name = name;
        this.button = new JButton(name);
    }

    public void click() {
        this.button.doClick();
    }

    public void addClickAction(Runnable task) {
        this.button.addActionListener(e -> {
            task.run();
        });
    }
}
