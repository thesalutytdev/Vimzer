package org.thesalutyt.vimzer.api.features.commmand;

import org.thesalutyt.vimzer.back.history.command.CommandHistory;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;
import org.thesalutyt.vimzer.api.visual.screen.window.IWindow;
import org.thesalutyt.vimzer.api.visual.screen.window.WindowSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CommandLine extends IWindow {
    public final JTextField commandLine = new JTextField();
    private final CommandHistory history = new CommandHistory();
    private JFrame pinned;

    public CommandLine() {
        super(new WindowSettings("Vimzer Commmand Line", 600, 30, false, true));

        this.add(commandLine, BorderLayout.CENTER);

        this.setVisible(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void display() {
        setVisible(true);

        commandLine.requestFocus();
        commandLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commandLine.getText();

                Feedback feedback = CommandParser.parse(command);

                if (!feedback.isSuccess() || feedback.isNecessary()) {
                    new DefaultPopup(feedback.getMessage()).display();
                }

                commandLine.setText("");

                history.add(command);
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!history.history.isEmpty()) {
                        commandLine.setText(history.getPrevious());
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (!history.history.isEmpty()) {
                        commandLine.setText(history.getNext());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        commandLine.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!history.history.isEmpty()) {
                        commandLine.setText(history.getPrevious());
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (!history.history.isEmpty()) {
                        commandLine.setText(history.getNext());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                commandLine.requestFocus();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }

    public void stopShow() {
        setVisible(false);
    }

    public JTextField getCommandLine() {
        return commandLine;
    }

    public void pin(JFrame frame) {
        pinned = frame;
    }

    public CommandHistory getHistory() {
        return history;
    }

    public JFrame getPinned() {
        return pinned;
    }
}
