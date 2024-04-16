package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;

public class ColorDialogView extends JDialog {
    public JButton ok;
    public JButton cancel;
    private JSlider red;
    private JSlider green;
    private JSlider blue;
    private JTextField example;

    // Constants representing user responses
    public static final int APPLY_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    public ColorDialogView(Frame owner, Color initialColor) {
        super(owner, "Customize Color", true);
        initComponents(initialColor);
    }

    private void initComponents(Color initialColor) {
        JPanel p0 = new JPanel();
        red = new JSlider(0, 255, initialColor.getRed());
        p0.add(red);
        p0.setBorder(new TitledBorder(new EtchedBorder(), "Red"));


        JPanel p1 = new JPanel();
        green = new JSlider(0, 255, initialColor.getGreen());
        p1.add(green);
        p1.setBorder(new TitledBorder(new EtchedBorder(), "Green"));

        JPanel p2 = new JPanel();
        blue = new JSlider(0, 255, initialColor.getBlue());
        p2.add(blue);
        p2.setBorder(new TitledBorder(new EtchedBorder(), "Blue"));
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel p3 = new JPanel();
        example = new JTextField();
        example.setHorizontalAlignment(SwingConstants.CENTER);
        example.setEditable(false);
        example.setBackground(initialColor);
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.add(example);
		p3.setPreferredSize(new Dimension(250, 60));
		p3.setMaximumSize(new Dimension(250, 60));
		p3.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel p4 = new JPanel();
        ok = new JButton("Apply");
        cancel = new JButton("Cancel");
        p4.add(ok);
        p4.add(cancel);
        p4.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(p0);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p4);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setContentPane(p);
        pack();
        setLocationRelativeTo(null);
    }

    public void setOkButtonListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

    public void setCancelButtonListener(ActionListener listener) {
        cancel.addActionListener(listener);
    }

    public void setSliderChangeListener(ChangeListener listener) {
        red.addChangeListener(listener);
        green.addChangeListener(listener);
        blue.addChangeListener(listener);
    }

    public void updateColor(Color newColor) {
        example.setBackground(newColor);
    }

    // public int getUserResponse() {
    //     // Return APPLY_OPTION or CANCEL_OPTION based on button clicked
    //     if (lastActionPerformed == ok) {
    //         return APPLY_OPTION;
    //     } else if (lastActionPerformed == cancel) {
    //         return CANCEL_OPTION;
    //     } else {
    //         // Return a default value if neither "Apply" nor "Cancel" button was clicked
    //         return CANCEL_OPTION;
    //     }
    // }

    public Color getColor() {
        return new Color(red.getValue(), green.getValue(), blue.getValue());
    }
}
