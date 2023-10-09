import javax.swing.*;
import java.awt.*;

public class ColorPalette extends JPanel {

    private int width = Helper.colorPanelWidth;
    private int height = Helper.colorPanelHeight;
    private JButton[] colorButton;
    private Color selectedColor;

    public ColorPalette(){
        this.setLayout(new GridLayout(2,10));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.setVisible(true);
        colorButton = new JButton[20];

        draw();

    }

    public void draw(){
        int[][] rgb = {
                {0, 0, 0},
                {127, 127, 127},
                {136, 0, 21},
                {40, 40, 40},
                {255, 127, 39},
                {255, 242, 0},
                {34, 177, 76},
                {0, 162, 232},
                {63, 72, 204},
                {163, 73, 164},
                {255, 255, 255},
                {195, 195, 195},
                {185, 122, 87},
                {255, 174, 201},
                {255, 201, 14},
                {239, 228, 176},
                {181, 230, 29},
                {153, 217, 234},
                {112, 146, 190},
                {200, 191, 231}
        };

        for(int i = 0; i < colorButton.length; i++){

            int r = rgb[i][0];
            int g = rgb[i][1];
            int b = rgb[i][2];

            Color c = new Color(r, g, b);
            JButton cb = new JButton();
            cb.setBackground(c);
            cb.addActionListener(s -> selectColor(cb.getBackground()));
            //colorButton[i] = cb;
            this.add(cb);
        }
    }

    public ColorPalette getColorPalletPanel(){
        return this;
    }

    public Color selectColor(Color t){
        Helper.currentColor = t;
        return t;
    }

}
