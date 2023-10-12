import javax.swing.*;
import java.awt.*;

public class TestWindow {

    private JFrame frameStats;
    private int window_width = Settings.editorPanelWidth;
    private int window_height = Settings.editorPanelHeight;

    public TestWindow(){
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

        frameStats.add(new TestPanel());

        frameStats.pack();
    }
}
