package org.thesalutyt.api.visual.screen.window.widget;

import org.thesalutyt.api.visual.screen.resource.IVisible;

public class IWidget implements IVisible {
    public String name;
    public int WIDTH;
    public int HEIGHT;

    public IWidget(String name, int WIDTH, int HEIGHT) {
        this.name = name;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    @Override
    public void close() {

    }

    @Override
    public String getName() {
        return name;
    }
}
