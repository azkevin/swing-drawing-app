import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageDialog extends JDialog implements ActionListener {
    public static final int APPLY_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    int userResponse;

    JButton chooseImageButton;
    JTextField widthField;
    JTextField heightField;
    JButton ok;
    JButton cancel;

    File selectedFile;
    int width;
    int height;

    ImageDialog(Frame owner) {
        super(owner, "Choose Image", true);
        this.setResizable(false);

        chooseImageButton = new JButton("Choose Image");
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        ok = new JButton("Apply");
        cancel = new JButton("Cancel");

        ok.setPreferredSize(cancel.getPreferredSize());

        chooseImageButton.addActionListener(this);
        ok.addActionListener(this);
        cancel.addActionListener(this);

        JPanel p0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p0.add(new JLabel("Width:"));
        p0.add(widthField);
        p0.add(new JLabel("Height:"));
        p0.add(heightField);

        JPanel p1 = new JPanel();
        p1.add(chooseImageButton);

        JPanel p2 = new JPanel();
        p2.add(ok);
        p2.add(cancel);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.add(p0);
        p.add(p1);
        p.add(p2);

        this.setContentPane(p);
        this.pack();
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

    public int showCustomDialog(Frame f) {
        this.setLocationRelativeTo(f);
        this.setVisible(true);
        return userResponse;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == chooseImageButton) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
            }
        } else if (source == ok) {
            userResponse = APPLY_OPTION;
            width = parseDimension(widthField.getText());
            height = parseDimension(heightField.getText());
            this.setVisible(false);
        } else if (source == cancel) {
            userResponse = CANCEL_OPTION;
            this.setVisible(false);
        }
    }

    private int parseDimension(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // return -1 if parsing fails
        }
    }
}
