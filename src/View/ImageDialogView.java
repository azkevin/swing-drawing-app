package View;

import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageDialogView extends JDialog {
    private JButton chooseImageButton;
    private JTextField widthField;
    private JTextField heightField;
    private JButton ok;
    private JButton cancel;

    public ImageDialogView(Frame owner) {
        super(owner, "Choose Image", true);
        this.setResizable(false);

        chooseImageButton = new JButton("Choose Image");
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        ok = new JButton("Apply");
        cancel = new JButton("Cancel");

        ok.setPreferredSize(cancel.getPreferredSize());

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

    public JButton getChooseImageButton() {
        return chooseImageButton;
    }

    public JTextField getWidthField() {
        return widthField;
    }

    public JTextField getHeightField() {
        return heightField;
    }

    public JButton getOkButton() {
        return ok;
    }

    public JButton getCancelButton() {
        return cancel;
    }
}
