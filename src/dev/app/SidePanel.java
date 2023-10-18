/*
 * Created by JFormDesigner on Wed Oct 18 20:28:45 IST 2023
 */

package dev.app;

import javax.swing.*;
import java.awt.*;

/**
 * @author josep
 */
public class SidePanel extends JPanel {
    public SidePanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        additionalProperties1 = new AdditionalProperties();

        //======== this ========
        setPreferredSize(new Dimension(Settings.sidePanelWidth, Settings.sidePanelHeight));
        setLayout(new GridLayout(2, 1, 5, 5));
        add(additionalProperties1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private AdditionalProperties additionalProperties1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
