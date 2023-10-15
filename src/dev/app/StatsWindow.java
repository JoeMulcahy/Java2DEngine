package dev.app;

import javax.swing.*;
import java.awt.*;

// Eventually this will be a separate window which will display various stats and program states using dev.joe.StatsPanel
//

public class StatsWindow {
    private JFrame frameStats;
    private int window_width = Settings.statsPanelWidth;
    private int window_height = Settings.statsPanelHeight;

    public StatsWindow(){
        initialise();
    }

    private void initialise(){
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();

        frameStats = new JFrame();
        frameStats.setLayout(new FlowLayout());
        frameStats.setTitle("Stats");
        frameStats.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameStats.setPreferredSize(new Dimension(window_width, window_height));
        //frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frameStats.setVisible(true);
        frameStats.setLocationRelativeTo(null);
        frameStats.setResizable(true);

        frameStats.add(new StatsPanel());



        frameStats.pack();
    }
}
