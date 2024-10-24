package org.thesalutyt.api.visual.screen.window.key;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyListen {
    public static HashMap<String, Runnable> pressEvents = new HashMap<>();
    public static HashMap<String, Runnable> releaseEvents = new HashMap<>();
    public static HashMap<String, Runnable> typedEvents = new HashMap<>();
    public static String comboFirst;
    public static void keyPressed(KeyEvent e) {
        for (String key : pressEvents.keySet()) {
            if (e.getKeyChar() == key.charAt(0)) {
                pressEvents.get(key).run();
            }
        }
    }
    public static void addPressEvent(String key, Runnable task) {
        pressEvents.put(key, task);
    }
    public static void removePressEvent(String key) {
        pressEvents.remove(key);
    }

    public static void keyReleased(KeyEvent e) {
        for (String key : releaseEvents.keySet()) {
            if (e.getKeyChar() == key.charAt(0)) {
                releaseEvents.get(key).run();
            }
        }
    }
    public static void addReleaseEvent(String key, Runnable task) {
        releaseEvents.put(key, task);
    }
    public static void removeReleaseEvent(String key) {
        releaseEvents.remove(key);
    }
    public static void keyTyped(KeyEvent e) {
        for (String key : typedEvents.keySet()) {
            if (e.getKeyChar() == key.charAt(0)) {
                typedEvents.get(key).run();
            } if (e.getKeyChar() == KeyEvent.VK_CONTROL) {
                typedEvents.get(key).run();
                comboFirst = "ctrl";
            } else if (e.getKeyChar() == KeyEvent.VK_ENTER && comboFirst != null) {
                typedEvents.get(comboFirst).run();
                comboFirst = null;
            }
        }
    }
    public static void addTypedEvent(String key, Runnable task) {
        typedEvents.put(key, task);
    }
    public static void removeTypedEvent(String key) {
        typedEvents.remove(key);
    }
}
