package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import View.ColorDialogView;

public class ColorDialogController implements ActionListener, ChangeListener {
    private ColorDialogView view;

    public ColorDialogController(Frame owner, Color initialColor) {
        view = new ColorDialogView(owner, initialColor);

        // Add action listeners
        view.setOkButtonListener(this);
        view.setCancelButtonListener(this);
        view.setSliderChangeListener(this);
        
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.ok) {
            // User clicked "Apply"
            Color selectedColor = view.getColor();
            // Now you can use the selectedColor as needed

            // Close the dialog
            view.dispose();
        } else if (e.getSource() == view.cancel) {
            // User clicked "Cancel"
            // Close the dialog without any action
            view.dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // Handle slider changes
        // Update the example color in the view
        Color newColor = view.getColor();
        view.updateColor(newColor);
    }
}