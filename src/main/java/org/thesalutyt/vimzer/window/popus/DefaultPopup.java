package org.thesalutyt.vimzer.window.popus;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.commmand.CommandLine;
import org.thesalutyt.vimzer.api.visual.screen.window.IWindow;
import org.thesalutyt.vimzer.api.visual.screen.window.WindowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultPopup extends IWindow implements Popup {
    private String message;

    public DefaultPopup(String message) {
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
        // Configurations.getCurrentActiveBrowserWindow().getCommandLine().requestFocus();
    }

    public static class MultiLine extends IWindow implements Popup {
        private ArrayList<String> messages = new ArrayList<>();

        public MultiLine(String[] messages) {
            super(new WindowSettings("Vimzer", 600, 30 + (20 * messages.length - 1), false, true));
            this.messages.addAll(Arrays.stream(messages).toList());

            this.setVisible(false);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(new BorderLayout(0, 0));

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

        public void addMessage(String message) {
            this.messages.add(message);
        }

        public void setMessages(ArrayList<String> messages) {
            this.messages = messages;
        }

        @Override
        public void setMessage(String message) {

        }

        @Override
        public void display() {
            JPanel panel = new JPanel();

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            this.messages.forEach(message ->
                panel.add(new JLabel(message))
            );


            this.add(panel);
            this.setVisible(true);
            this.requestFocus();
        }

        @Override
        public void stopShow() {
            this.setVisible(false);
            // Configurations.getCurrentActiveBrowserWindow().getCommandLine().requestFocus();
        }
    }
}
