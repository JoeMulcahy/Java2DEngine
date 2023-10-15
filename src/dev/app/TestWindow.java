package dev.app;

import javax.swing.*;
import java.awt.*;

public class TestWindow {

    private JFrame frame;
    private int window_width = Settings.inspectorWidth;
    private int window_height = Settings.inspectorHeight;

    //private InspectorPanel inspectorPanel;

    public TestWindow(){
        initialise();
    }

    private void initialise(){

        //inspectorPanel = new InspectorPanel();

        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setTitle("test window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(window_width, window_height));
        //frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        //frame.add(inspectorPanel);

        frame.pack();
    }
}
