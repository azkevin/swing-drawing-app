// import java.awt.Color;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import javax.swing.JButton;
// import javax.swing.JComboBox;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JRadioButton;
// import javax.swing.JToolBar;
// import javax.swing.BorderFactory;
// import java.awt.FlowLayout;

// import View.ColorChooserView;
// import View.DrawFrame;

// public class ColorChooserController implements ActionListener {
//     private ColorChooserView view;
//     private DrawFrame frame;

//     private final Color[] colors = {
//         new Color(26, 188, 156),   // turquoiseC
//         new Color(46, 204, 113),   // emeraldC
//         new Color(52, 152, 219),   // peter_riverC
//         new Color(155, 89, 182),   // amethystC
//         new Color(52, 73, 94),   // wet_asphaltC
//         new Color(22, 160, 133),   // green_seaC
//         new Color(39, 174, 96),   // nephritisC
//         new Color(41, 128, 185),   // belize_holeC
//         new Color(142, 68, 173),   // wisteriaC
//         new Color(44, 62, 80),   // midnight_blueC
//         new Color(241, 196, 15),   // sun_flowerC
//         new Color(230, 126, 34),   // carrotC
//         new Color(231, 76, 60),   // alizarinC
//         new Color(236, 240, 241),   // cloudsC
//         new Color(149, 165, 166),   // concreteC
//         new Color(243, 156, 18),   // orangeC
//         new Color(211, 84, 0),   // pumpkinC
//         new Color(192, 57, 43),   // pomegranateC
//         new Color(189, 195, 199),   // silverC
//         new Color(127, 140, 141)   // asbestosC
//     };

//     public ColorChooserController(ColorChooserView view, DrawFrame frame) {
//         this.view = view;
//         this.frame = frame;
//         attachListeners();
//     }

//     private void attachListeners() {
//         view.fill.addActionListener(this);
//         view.custom2.addActionListener(this);
//         view.custom3.addActionListener(this);
//         view.primaryColor.addActionListener(this);
//         view.secondaryColor.addActionListener(this);
//         view.newColor.addActionListener(this);
//         view.option.addActionListener(this);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == view.fill) {
//             JRadioButton b = (JRadioButton) e.getSource();
//             frame.getInkPanel().setTransparency(!b.isSelected());
//         } else {
//             JButton b = (JButton) e.getSource();
//             if (b == view.custom2) {
//                 swapCustomColors();
//             } else if (b == view.custom3) {
//                 swapCustomColors3();
//             } else if (b == view.secondaryColor) {
//                 swapSecondaryColor();
//             } else if (b == view.newColor) {
//                 openColorDialog();
//             } else {
//                 swapColors(b);
//             }
//             updateColors();
//         }
//     }

//     private void swapCustomColors() {
//         Color switchColor = view.custom2.getBackground();
//         if (view.option.getSelectedItem().equals("Primary Color")) {
//             view.custom2.setBackground(view.view.primaryColor.getBackground());
//             view.view.primaryColor.setBackground(switchColor);
//         } else {
//             view.custom2.setBackground(view.view.secondaryColor.getBackground());
//             view.view.secondaryColor.setBackground(switchColor);
//         }
//     }

//     private void swapCustomColors3() {
//         if (view.option.getSelectedItem().equals("Primary Color")) {
//             Color oldCustom1 = view.view.primaryColor.getBackground();
//             view.view.primaryColor.setBackground(view.custom3.getBackground());
//             view.custom3.setBackground(view.view.custom2.getBackground());
//             view.view.custom2.setBackground(oldCustom1);
//         } else {
//             Color oldCustom1 = view.view.secondaryColor.getBackground();
//             view.view.secondaryColor.setBackground(view.custom3.getBackground());
//             view.custom3.setBackground(view.view.custom2.getBackground());
//             view.view.custom2.setBackground(oldCustom1);
//         }
//     }

//     private void swapSecondaryColor() {
//         Color primaryColor = view.view.primaryColor.getBackground();
//         view.view.secondaryColor.setBackground(primaryColor);
//         frame.getInkPanel().setFillColor(primaryColor);
//     }

//     private void openColorDialog() {
//         int i;
//         if (view.option.getSelectedItem().equals("Primary Color")) {
//             i = view.cd.showCustomDialog(frame, view.view.primaryColor.getBackground());
//         } else {
//             i = view.cd.showCustomDialog(frame, view.view.secondaryColor.getBackground());
//         }

//         if (i == ColorDialog.APPLY_OPTION) {
//             if (view.option.getSelectedItem().equals("Primary Color")) {
//                 view.custom3.setBackground(view.view.custom2.getBackground());
//                 view.view.custom2.setBackground(view.view.primaryColor.getBackground());
//                 view.view.primaryColor.setBackground(view.cd.currentColor);
//             } else {
//                 view.custom3.setBackground(view.view.custom2.getBackground());
//                 view.view.custom2.setBackground(view.view.secondaryColor.getBackground());
//                 view.view.secondaryColor.setBackground(view.cd.currentColor);
//             }
//         }
//     }

//     private void swapColors(JButton button) {
//         if (view.option.getSelectedItem().equals("Primary Color")) {
//             view.custom3.setBackground(view.view.custom2.getBackground());
//             view.view.custom2.setBackground(view.view.primaryColor.getBackground());
//             view.view.primaryColor.setBackground(button.getBackground());
//         } else {
//             view.custom3.setBackground(view.view.custom2.getBackground());
//             view.view.custom2.setBackground(view.view.secondaryColor.getBackground());
//             view.view.secondaryColor.setBackground(button.getBackground());
//         }
//     }

//     private void updateColors() {
//         frame.getInkPanel().setColor(view.view.primaryColor.getBackground());
//         frame.getInkPanel().setFillColor(view.view.secondaryColor.getBackground());
//     }
// }


package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import View.ColorChooserView;
import View.DrawFrame;

public class ColorChooserController implements ActionListener {
    private ColorChooserView view;
    private DrawFrame frame;

    public ColorChooserController(ColorChooserView view, DrawFrame frame) {
        this.view = view;
        this.frame = frame;

        // Add action listeners to view components
        view.getPrimaryColorButton().addActionListener(this);
        view.getSecondaryColorButton().addActionListener(this);
        view.getCustom2Button().addActionListener(this);
        view.getCustom3Button().addActionListener(this);
        view.getNewColorButton().addActionListener(this);
        view.getOptionComboBox().addActionListener(this);
        view.getFillRadioButton().addActionListener(this);

        // Add action listeners to color buttons
        for (JButton button : view.getColorButtons()) {
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getFillRadioButton()) {
            handleFillCheckbox();
        } else if (source == view.getNewColorButton()) {
            handleNewColorButton();
        } else {
            handleColorButtonActions(source);
        }
    }

    private void handleFillCheckbox() {
        JRadioButton fillCheckbox = view.getFillRadioButton();
        frame.getInkPanel().setTransparency(!fillCheckbox.isSelected());
    }

    private void handleNewColorButton() {
        Color initialColor = view.getOptionComboBox().getSelectedItem().equals("Primary Color") ? 
                             view.getPrimaryColorButton().getBackground() :
                             view.getSecondaryColorButton().getBackground();
        ColorDialogController colorDialogController = new ColorDialogController(frame, initialColor);
        if(colorDialogController.userResponse == 0){
            JButton custom2Button = view.getCustom2Button();
            JButton custom3Button = view.getCustom3Button();
            if (view.getOptionComboBox().getSelectedItem().equals("Primary Color")){
                JButton primaryColorButton = view.getPrimaryColorButton();
                custom3Button.setBackground(custom2Button.getBackground());
                custom2Button.setBackground(primaryColorButton.getBackground());
                primaryColorButton.setBackground(colorDialogController.selectedColor);
                frame.getInkPanel().setColor(primaryColorButton.getBackground());
            }
            else{
                JButton secondaryColorButton = view.getSecondaryColorButton();
                custom3Button.setBackground(custom2Button.getBackground());
                custom2Button.setBackground(secondaryColorButton.getBackground());
                secondaryColorButton.setBackground(colorDialogController.selectedColor);
                frame.getInkPanel().setFillColor(secondaryColorButton.getBackground());
            }
        }
    }
    

    private void handleColorButtonActions(Object source) {
        JButton button = (JButton) source;
        if (button == view.getCustom2Button() || button == view.getCustom3Button()) {
            handleCustomColorButton(button);
        } else if (button == view.getPrimaryColorButton() || button == view.getSecondaryColorButton()) {
            handlePrimarySecondaryColorButton(button);
        } else {
            handleRegularColorButton(button);
        }
    }

    private void handleCustomColorButton(JButton button) {
        JButton custom2Button = view.getCustom2Button();
        JButton custom3Button = view.getCustom3Button();
        JButton primaryColorButton = view.getPrimaryColorButton();
        JButton secondaryColorButton = view.getSecondaryColorButton();

        Color tempColor = button.getBackground();
        if (view.getOptionComboBox().getSelectedItem().equals("Primary Color")) {
            button.setBackground(primaryColorButton.getBackground());
            primaryColorButton.setBackground(tempColor);
        } else {
            button.setBackground(secondaryColorButton.getBackground());
            secondaryColorButton.setBackground(tempColor);
        }
        custom3Button.setBackground(custom2Button.getBackground());
        custom2Button.setBackground(tempColor);

        frame.getInkPanel().setColor(primaryColorButton.getBackground());
        frame.getInkPanel().setFillColor(secondaryColorButton.getBackground());
    }

    private void handlePrimarySecondaryColorButton(JButton button) {
        JButton primaryColorButton = view.getPrimaryColorButton();
        JButton secondaryColorButton = view.getSecondaryColorButton();

        if (button == view.getSecondaryColorButton()) {
            secondaryColorButton.setBackground(primaryColorButton.getBackground());
            frame.getInkPanel().setFillColor(secondaryColorButton.getBackground());
        }
    }

    private void handleRegularColorButton(JButton button) {
        JButton primaryColorButton = view.getPrimaryColorButton();
        JButton secondaryColorButton = view.getSecondaryColorButton();

        if (view.getOptionComboBox().getSelectedItem().equals("Primary Color")) {
            view.getCustom3Button().setBackground(view.getCustom2Button().getBackground());
            view.getCustom2Button().setBackground(primaryColorButton.getBackground());
            primaryColorButton.setBackground(button.getBackground());
        } else {
            view.getCustom3Button().setBackground(view.getCustom2Button().getBackground());
            view.getCustom2Button().setBackground(secondaryColorButton.getBackground());
            secondaryColorButton.setBackground(button.getBackground());
        }

        frame.getInkPanel().setColor(primaryColorButton.getBackground());
        frame.getInkPanel().setFillColor(secondaryColorButton.getBackground());
    }
}
