package org.thesalutyt.api.visual.screen.window.widget;

import javax.swing.*;
import java.awt.*;

public class IWidgetPanel extends JPanel {
    public JPanel panel = new JPanel();

    public void addButton(IButton widget, String layout) {
        panel.add(widget.button, layout);
    }

    public void addLabel(ILabel widget, String layout) {
        panel.add(widget.label, layout);
    }

    public void addTextArea(ITextArea widget, String layout) {
        panel.add(widget.textArea, layout);
    }

    public void removeLabel(ILabel widget) {
        panel.remove(widget.label);
    }

    public void setLayout(FlowLayout layout) {
        panel.setLayout(layout);
    }
}
