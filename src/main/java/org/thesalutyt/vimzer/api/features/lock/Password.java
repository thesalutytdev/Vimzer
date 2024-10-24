package org.thesalutyt.vimzer.api.features.lock;

import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.visual.screen.window.IWindow;
import org.thesalutyt.vimzer.api.visual.screen.window.WindowSettings;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;
import org.thesalutyt.vimzer.window.popus.Popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Password {
    private static String password;
    public static boolean inputRight = false;
    private static final File file = new File(Configurations.getBrowserDir() + File.separator + ".cache");

    static {
        load();
    }

    public static void load() {
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                password = scanner.nextLine();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Password.password = Encoder.encodePassword(password);
        Configurations.setPasswordSet(true);
        save();
    }

    public static class PasswordInput extends IWindow implements Popup {
        private final JPasswordField passwordInput = new JPasswordField();
        private int tries = 0;
        private static final int maxTries = 3;
        private boolean lock = false;
        private Runnable onRightPass;

        public PasswordInput() {
            super(new WindowSettings("Vimzer Password Input", 600, 30, false, true));

            this.add(passwordInput, BorderLayout.CENTER);

            this.passwordInput.addActionListener(e -> {
                if (tries > maxTries) {
                    new DefaultPopup("Wrong password." + (maxTries - tries)).display();
                    lock = true;

                    System.exit(0);
                }

                if (!password.isEmpty()) {
                    System.out.println(Encoder.encodePassword(String.valueOf(passwordInput.getPassword())));
                    System.out.println(password);

                    if (!Encoder.verifyPassword(password, String.valueOf(passwordInput.getPassword()))) {
                        new DefaultPopup("Wrong password. Tries left: " + (maxTries - tries)).display();
                        tries++;
                    } else {
                        setPassword(password);
                        inputRight = true;
                        stopShow();

                        if (onRightPass != null) {
                            onRightPass.run();
                        }
                    }
                }
            });

            this.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (lock) {
                            System.exit(0);
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            this.setVisible(true);
        }

        public PasswordInput onRightPass(Runnable onRightPass) {
            this.onRightPass = onRightPass;
            return this;
        }

        @Override
        public void setMessage(String message) {

        }

        @Override
        public void display() {
            this.setVisible(true);
            this.requestFocus();
        }

        @Override
        public void stopShow() {
            this.setVisible(false);
        }
    }
}
