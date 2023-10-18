/*
 * Created by JFormDesigner on Wed Oct 18 19:54:38 IST 2023
 */

package dev.app;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * @author josep
 */
public class AdditionalProperties extends JPanel {
    public AdditionalProperties() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        lblAttributes = new JLabel();
        lblSelectedObject = new JLabel();
        lblObjectType = new JLabel();
        cmbObjectType = new JComboBox();
        lblCollision = new JLabel();
        checkBoxCollision = new JCheckBox();
        lblInput = new JLabel();
        lblUp = new JLabel();
        tfUp = new JTextField();
        lblDown = new JLabel();
        tfDown = new JTextField();
        lblLeft = new JLabel();
        tfLeft = new JTextField();
        lblRight = new JLabel();
        tfRight = new JTextField();
        lblButton1 = new JLabel();
        tfButton1 = new JTextField();
        lblButton2 = new JLabel();
        tfButton2 = new JTextField();

        //======== this ========
        setMinimumSize(new Dimension(128, 512));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setPreferredSize(new Dimension(512, 512));
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- lblAttributes ----
        lblAttributes.setText("Attributes");
        add(lblAttributes, "cell 3 0");

        //---- lblSelectedObject ----
        lblSelectedObject.setText("text");
        add(lblSelectedObject, "cell 1 1 2 1");

        //---- lblObjectType ----
        lblObjectType.setText("ObjectType");
        add(lblObjectType, "cell 1 3");
        add(cmbObjectType, "cell 3 3");

        //---- lblCollision ----
        lblCollision.setText("Collision");
        add(lblCollision, "cell 1 4");
        add(checkBoxCollision, "cell 3 4");

        //---- lblInput ----
        lblInput.setText("Input");
        add(lblInput, "cell 1 5");

        //---- lblUp ----
        lblUp.setText("Up");
        add(lblUp, "cell 3 5");
        add(tfUp, "cell 4 5");

        //---- lblDown ----
        lblDown.setText("Down");
        add(lblDown, "cell 3 6");
        add(tfDown, "cell 4 6");

        //---- lblLeft ----
        lblLeft.setText("Left");
        add(lblLeft, "cell 3 7");
        add(tfLeft, "cell 4 7");

        //---- lblRight ----
        lblRight.setText("Right");
        add(lblRight, "cell 3 8");
        add(tfRight, "cell 4 8");

        //---- lblButton1 ----
        lblButton1.setText("Button 1");
        add(lblButton1, "cell 3 9");
        add(tfButton1, "cell 4 9");

        //---- lblButton2 ----
        lblButton2.setText("Button2");
        add(lblButton2, "cell 3 10");
        add(tfButton2, "cell 4 10");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel lblAttributes;
    private JLabel lblSelectedObject;
    private JLabel lblObjectType;
    private JComboBox cmbObjectType;
    private JLabel lblCollision;
    private JCheckBox checkBoxCollision;
    private JLabel lblInput;
    private JLabel lblUp;
    private JTextField tfUp;
    private JLabel lblDown;
    private JTextField tfDown;
    private JLabel lblLeft;
    private JTextField tfLeft;
    private JLabel lblRight;
    private JTextField tfRight;
    private JLabel lblButton1;
    private JTextField tfButton1;
    private JLabel lblButton2;
    private JTextField tfButton2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
