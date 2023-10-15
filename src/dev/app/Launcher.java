package dev.app;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               MainWindow main = new MainWindow();
               //TestWindow test = new TestWindow();
              //dev.joe.StatsWindow side = new dev.joe.StatsWindow();
               //dev.joe.GameObjectsWindow objects = new dev.joe.GameObjectsWindow();

                UndoRedoStack undoRedoStack = new UndoRedoStack();

            }
        });
    }
}
