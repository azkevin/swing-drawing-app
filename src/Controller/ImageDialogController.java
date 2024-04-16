package Controller;

import View.ImageDialogView;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageDialogController implements ActionListener {
    public static final int APPLY_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private ImageDialogView view;
    private int userResponse;
    private File selectedFile;
    private int width;
    private int height;

    public ImageDialogController(Frame owner) {
        this.view = new ImageDialogView(owner);
        this.view.getChooseImageButton().addActionListener(this);
        this.view.getOkButton().addActionListener(this);
        this.view.getCancelButton().addActionListener(this);
    }

    public int showCustomDialog(Frame f) {
        this.view.setLocationRelativeTo(f);
        this.view.setVisible(true);
        return userResponse;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == view.getChooseImageButton()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "svg"));
            int returnVal = fileChooser.showOpenDialog(view);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
            }
        } else if (source == view.getOkButton()) {
            userResponse = APPLY_OPTION;
            width = parseDimension(view.getWidthField().getText());
            height = parseDimension(view.getHeightField().getText());
            view.setVisible(false);
        } else if (source == view.getCancelButton()) {
            userResponse = CANCEL_OPTION;
            view.setVisible(false);
        }
    }

    private int parseDimension(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // return 0 if parsing fails
        }
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public int getWidthValue() {
        return width;
    }

    public int getHeightValue() {
        return height;
    }
}
