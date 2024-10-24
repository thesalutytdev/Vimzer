package org.thesalutyt.api.visual.screen.window;

import org.thesalutyt.api.visual.screen.resource.IVisible;
import org.thesalutyt.api.visual.screen.window.key.KeyListen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;

public class IWindow extends JFrame implements IVisible {
    @Serial
    private static final long serialVersionUID = -5570653778104813836L;

    public IWindow(WindowSettings settings) {
        super(settings.TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(settings.WIDTH, settings.HEIGHT);
        this.setVisible(settings.visible);
        this.setResizable(settings.RESIZABLE);
        if (settings.icon != null) {
            this.setIconImage(settings.icon.getImage());
        }
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                KeyListen.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                KeyListen.keyReleased(e);
            }
        });
    }

    @Override
    public void close() {
        this.setVisible(false);
        System.exit(0);
    }
}
