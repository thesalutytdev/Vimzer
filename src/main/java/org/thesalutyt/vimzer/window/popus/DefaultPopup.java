package org.thesalutyt.vimzer.window;

import org.thesalutyt.vimzer.api.visual.screen.window.IWindow;
import org.thesalutyt.vimzer.api.visual.screen.window.WindowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Popup extends IWindow {
    private String message;

    public Popup(String message) {
        super(new WindowSettings("Vimzer", 600, 30, false, true));
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.message = message;

        this.add(new JLabel(message), BorderLayout.CENTER);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    stopShow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void display() {
        this.setVisible(true);
        this.requestFocus();
    }

    public void stopShow() {
        this.setVisible(false);
    }
}
