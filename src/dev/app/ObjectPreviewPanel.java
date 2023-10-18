/*
 * Created by JFormDesigner on Wed Oct 18 20:21:34 IST 2023
 */

package dev.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import net.miginfocom.swing.*;

/**
 * @author josep
 */
public class ObjectPreviewPanel extends JPanel {
    public ObjectPreviewPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        lblPreview = new JLabel();
        PreviewPanel = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(64, 64));
        setPreferredSize(new Dimension(128, 128));
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));

        //---- lblPreview ----
        lblPreview.setText("Preview");
        add(lblPreview, "cell 1 0");

        //======== PreviewPanel ========
        {
            PreviewPanel.setBackground(new Color(0xccccff));
            PreviewPanel.setMinimumSize(new Dimension(128, 128));
            PreviewPanel.setBorder(new TitledBorder("text"));
            PreviewPanel.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        add(PreviewPanel, "cell 1 1 1 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel lblPreview;
    private JPanel PreviewPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
