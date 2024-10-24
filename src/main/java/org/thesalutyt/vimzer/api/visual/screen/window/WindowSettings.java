package org.thesalutyt.api.visual.screen.window;

import javax.swing.*;

public class WindowSettings {
    public String TITLE;
    public int WIDTH;
    public int HEIGHT;
    public boolean RESIZABLE = true;
    public boolean visible = true;
    public ImageIcon icon;

    public WindowSettings(String TITLE, int WIDTH, int HEIGHT, boolean RESIZABLE, boolean visible, String icon_path) {
        this.TITLE = TITLE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.RESIZABLE = RESIZABLE;
        this.visible = visible;
        this.icon = new ImageIcon(icon_path);
    }

    public WindowSettings(String TITLE, int WIDTH, int HEIGHT, boolean RESIZABLE, boolean visible) {
        this.TITLE = TITLE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.RESIZABLE = RESIZABLE;
        this.visible = visible;
    }

    public WindowSettings(String TITLE, int WIDTH, int HEIGHT) {
        this.TITLE = TITLE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public WindowSettings(String TITLE) {
        this.TITLE = TITLE;
        this.WIDTH = 500;
        this.HEIGHT = 500;
    }

    public WindowSettings() {
        this.TITLE = "Test";
        this.WIDTH = 500;
        this.HEIGHT = 500;
    }
}
