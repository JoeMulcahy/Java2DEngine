package dev.app;

import javax.swing.*;
import java.awt.*;

public class NewProjectDialogBox {

    private int width = 300;
    private int height = 200;
    private JFrame frame;
    private JLabel lblProjectName;
    private JLabel lblCanvasWidth;
    private JLabel lblCanvasHeight;
    private JLabel lblCanvasColor;
    private Color canvasColor;
    public static NewProjectDialogBox Instance;

    private JButton btnOK;
    private JButton btnCancel;

    private JTextField jtfProjectName;
    private JTextField jtfCanvasWidth;
    private JTextField jtfCanvasHeight;
    private ColorPalette cpCanvasColor;

    public NewProjectDialogBox(){
        Instance = this;
        initialise();
    }

    private void initialise(){
        frame = new JFrame("New Project");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setResizable(false);

        frame.setLayout(new GridLayout(5, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        lblProjectName = new JLabel("Project Name");
        lblCanvasWidth = new JLabel("Canvas width");
        lblCanvasHeight = new JLabel("Canvas height");
        lblCanvasColor = new JLabel("Canvas color");

        jtfProjectName = new JTextField();
        jtfCanvasWidth = new JTextField("512");
        jtfCanvasHeight = new JTextField("512");
        cpCanvasColor = new ColorPalette();

        btnCancel = new JButton("Cancel");
        btnOK = new JButton("OK");



        btnOK.addActionListener(s -> initiateProject(jtfProjectName.getText(), jtfCanvasWidth.getText(),
                jtfCanvasHeight.getText(), cpCanvasColor.getBackground()));
        btnCancel.addActionListener(s -> close());


        frame.add(lblProjectName);
        frame.add(jtfProjectName);

        frame.add(lblCanvasWidth);
        frame.add(jtfCanvasWidth);

        frame.add(lblCanvasHeight);
        frame.add(jtfCanvasHeight);

        frame.add(lblCanvasColor);
        frame.add(cpCanvasColor);

        frame.add(btnCancel);
        frame.add(btnOK);

        frame.pack();
    }

    private void initiateProject(String name, String width, String height, Color c){
        System.out.println(name + " " +
                width + " " +
                height + " " +
                c + "\n");

        MainWindow.Instance.closeWindow();

        new MainWindow();

        Settings.projectName = name;
        Settings.editorPanelWidth = Integer.parseInt(width);
        Settings.editorPanelHeight= Integer.parseInt(height);
        Settings.editorBackgroundColor = c;

        System.out.println(Settings.projectName + " " +
                Settings.editorPanelWidth  + " " +
                Settings.editorPanelHeight + " " +
                Settings.editorBackgroundColor + "\n");

        frame.setVisible(false);
        frame.dispose();

    }

    private void close(){
        frame.setVisible(false);
        frame.dispose();
    }
}
