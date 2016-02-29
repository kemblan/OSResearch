/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import javax.swing.JTextArea;

/**
 *
 * @author mateusz
 */
public class UILogger {

    private static UILogger instance = null;

    private JTextArea logBox = null;

    public static UILogger getInstance() {
        if (instance == null) {
            instance = new UILogger();
        }
        return instance;
    }

    private UILogger() {
    }

    public void initialize(JTextArea logTextArea) {
        this.logBox = logTextArea;
    }

    public void log(String text) {
        this.logBox.append(text);
    }
}
