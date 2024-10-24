package org.thesalutyt.vimzer.window;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefDevToolsClient;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.cef.handler.CefKeyboardHandlerAdapter;
import org.thesalutyt.vimzer.Configurations;
import org.thesalutyt.vimzer.api.features.commmand.CommandLine;
import org.thesalutyt.vimzer.api.features.lock.Password;
import org.thesalutyt.vimzer.api.variables.VariableHandler;
import org.thesalutyt.vimzer.api.visual.screen.window.IWindow;
import org.thesalutyt.vimzer.api.visual.screen.window.WindowSettings;
import org.thesalutyt.vimzer.back.history.browser.History;
import org.thesalutyt.vimzer.back.history.browser.HistoryPage;
import org.thesalutyt.vimzer.window.popus.DefaultPopup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow extends IWindow {
    private final JTextField address_;
    private final CefApp cefApp_;
    private final CefClient client_;
    private final CefBrowser browser_;
    private final Component browerUI_;
    private boolean browserFocus_ = true;
    private String startURL = Configurations.getDefaultPage();
    private static History history = new History();
    private static int shiftClicks = 0;
    private static CommandLine commandLine = new CommandLine();
    private MainWindow self = this;

    public MainWindow(boolean useOSR, boolean isTransparent, String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        super(new WindowSettings("Vimzer", 800, 600));

        Configurations.setCurrentActiveBrowserWindow(self);

        if (Configurations.isFirstRun()) {
            Configurations.showHelp();
            Configurations.setFirstRun(false);
        }

        VariableHandler.load();
        commandLine.pin(this);

        CefAppBuilder builder = new CefAppBuilder();
        builder.getCefSettings().windowless_rendering_enabled = useOSR;
        // USE builder.setAppHandler INSTEAD OF CefApp.addAppHandler!
        // Fixes compatibility issues with MacOSX
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }
        });

        if (args.length > 0) {
            builder.addJcefArgs(args);
        }

        // (1) The entry point to JCEF is always the class CefApp. There is only one
        //     instance per application and therefore you have to call the method
        //     "getInstance()" instead of a CTOR.
        //
        //     CefApp is responsible for the global CEF context. It loads all
        //     required native libraries, initializes CEF accordingly, starts a
        //     background task to handle CEF's message loop and takes care of
        //     shutting down CEF after disposing it.
        //
        //     WHEN WORKING WITH MAVEN: Use the builder.build() method to
        //     build the CefApp on first run and fetch the instance on all consecutive
        //     runs. This method is thread-safe and will always return a valid app
        //     instance.
        cefApp_ = builder.build();

        // (2) JCEF can handle one to many browser instances simultaneous. These
        //     browser instances are logically grouped together by an instance of
        //     the class CefClient. In your application you can create one to many
        //     instances of CefClient with one to many CefBrowser instances per
        //     client. To get an instance of CefClient you have to use the method
        //     "createClient()" of your CefApp instance. Calling an CTOR of
        //     CefClient is not supported.
        //
        //     CefClient is a connector to all possible events which come from the
        //     CefBrowser instances. Those events could be simple things like the
        //     change of the browser title or more complex ones like context menu
        //     events. By assigning handlers to CefClient you can control the
        //     behavior of the browser. See tests.detailed.MainFrame for an example
        //     of how to use these handlers.
        client_ = cefApp_.createClient();

        // (3) Create a simple message router to receive messages from CEF.
        CefMessageRouter msgRouter = CefMessageRouter.create();
        client_.addMessageRouter(msgRouter);

        // (4) One CefBrowser instance is responsible to control what you'll see on
        //     the UI component of the instance. It can be displayed off-screen
        //     rendered or windowed rendered. To get an instance of CefBrowser you
        //     have to call the method "createBrowser()" of your CefClient
        //     instances.
        //
        //     CefBrowser has methods like "goBack()", "goForward()", "loadURL()",
        //     and many more which are used to control the behavior of the displayed
        //     content. The UI is held within a UI-Compontent which can be accessed
        //     by calling the method "getUIComponent()" on the instance of CefBrowser.
        //     The UI component is inherited from a java.awt.Component and therefore
        //     it can be embedded into any AWT UI.
        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browerUI_ = browser_.getUIComponent();

        // (5) For this minimal browser, we need only a text field to enter an URL
        //     we want to navigate to and a CefBrowser window to display the content
        //     of the URL. To respond to the input of the user, we're registering an
        //     anonymous ActionListener. This listener is performed each time the
        //     user presses the "ENTER" key within the address field.
        //     If this happens, the entered value is passed to the CefBrowser
        //     instance to be loaded as URL.

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                    shiftClicks++;
                    System.out.println("shift click handler >> window");

                    if (shiftClicks == 3) {
                        shiftClicks = 0;

                        commandLine.display();
                    }

                }

                if (e.getKeyCode() == KeyEvent.VK_F6) {
                    address_.requestFocus();
                }

                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    browser_.loadURL(browser_.getURL());
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        address_ = new JTextField(startURL, 100);
        address_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.loadURL(address_.getText());
                history.add(new HistoryPage(address_.getText(), browser_.getURL()));
            }
        });

        browerUI_.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 4) {
                    browser_.getDevTools().goForward();
                } else if (e.getButton() == 3) {
                    browser_.getDevTools().goBack();
                    CefDevToolsClient devToolsClient = browser_.getDevTools().getDevToolsClient();

                }
            }
        });

        client_.addKeyboardHandler(new CefKeyboardHandlerAdapter() {
            @Override
            public boolean onKeyEvent(CefBrowser browser, CefKeyEvent event) {
                if (event.native_key_code == KeyEvent.VK_SHIFT){
                    shiftClicks++;
                    System.out.println("shift click handler >> client_");

                    if (shiftClicks == 3) {
                        shiftClicks = 0;

                        commandLine.display();
                    }

                }

                if (event.native_key_code == KeyEvent.VK_F6) {
                    address_.requestFocus();
                }

                if (event.native_key_code == KeyEvent.VK_F5) {
                    browser_.loadURL(browser_.getURL());
                }

                return true;
            }
        });

        browerUI_.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                    shiftClicks++;
                    System.out.println("shift click handler >> browerUI_");

                    if (shiftClicks == 3) {
                        shiftClicks = 0;

                        commandLine.display();
                    }

                }

                if (e.getKeyCode() == KeyEvent.VK_F6) {
                    address_.requestFocus();
                }

                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    browser_.loadURL(browser_.getURL());
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        address_.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                    shiftClicks++;
                    System.out.println("shift click handler >> address");

                    if (shiftClicks == 3) {
                        shiftClicks = 0;

                        commandLine.display();
                    }

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        // Update the address field when the browser URL changes.
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                address_.setText(url);
            }
        });

        // Clear focus from the browser when the address field gains focus.
        address_.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                address_.requestFocus();
            }
        });

        // Clear focus from the address field when the browser gains focus.
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus_) return;
                browserFocus_ = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus_ = false;
            }
        });



        // (6) All UI components are assigned to the default content pane of this
        //     JFrame and afterwards the frame is made visible to the user.
        getContentPane().add(address_, BorderLayout.NORTH);
        getContentPane().add(browerUI_, BorderLayout.CENTER);
        pack();
        setSize(800, 600);
        setVisible(true);

        // (7) To take care of shutting down CEF accordingly, it's important to call
        //     the method "dispose()" of the CefApp instance if the Java
        //     application will be closed. Otherwise you'll get asserts from CEF.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Configurations.save();
                VariableHandler.dump();
                CefApp.getInstance().dispose();
                dispose();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                Configurations.setCurrentActiveBrowserWindow(self);
            }
        });

    }

    public static void main(String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        if (Configurations.isPasswordSet()) {
            new Password.PasswordInput()
                    .onRightPass(() -> {
                        try {
                            MainWindow mainWindow = new MainWindow(false, false, args);
                        } catch (UnsupportedPlatformException | CefInitializationException | IOException |
                                 InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .display();
        } else {
            MainWindow mainWindow = new MainWindow(false, false, args);
        }

    }

    public void loadURL(String url) {
        browser_.loadURL(url);
        history.add(new HistoryPage(address_.getText(), url));
//        new Thread(
//            () -> {
//                try {
//                    wait(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        ).start();
    }

    public String getURL() {
        return browser_.getURL();
    }

    public History getHistory() {
        return history;
    }

    public MainWindow getSelf() {
        return self;
    }

    public CefBrowser getBrowser() {
        return browser_;
    }

    public CefClient getClient() {
        return client_;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public void reload() {
        browser_.reload();
    }
}
