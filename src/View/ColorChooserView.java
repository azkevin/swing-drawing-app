package View;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class ColorChooserView extends JPanel {
    private JButton[] colorButtons;

    private JButton primaryColor;
    private JButton custom2;
    private JButton custom3;
    private JButton newColor;
    private JButton secondaryColor;

    private JComboBox<String> option;
    private JRadioButton fill;

    private final String PRIMARY_COLOR = "Primary Color";
    private final String SECONDARY_COLOR = "Secondary Color";

    public ColorChooserView() {
        initializeColorChooser();
    }

    private void initializeColorChooser() {
        JToolBar panel = new JToolBar(JToolBar.HORIZONTAL);
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        panel.setFloatable(false);

        // Initialize color buttons
        initializeColorButtons();

        primaryColor = new JButton("");
        primaryColor.setPreferredSize(new Dimension(25, 25));
        primaryColor.setOpaque(true);
        primaryColor.setFocusPainted(false);
        primaryColor.setBackground(Color.black);

        custom2 = new JButton("");
        custom2.setPreferredSize(new Dimension(25, 25));
        custom2.setOpaque(true);
        custom2.setFocusPainted(false);
        custom2.setBackground(Color.white);

        custom3 = new JButton("");
        custom3.setPreferredSize(new Dimension(25, 25));
        custom3.setOpaque(true);
        custom3.setFocusPainted(false);
        custom3.setBackground(Color.white);

        secondaryColor = new JButton("");
        secondaryColor.setPreferredSize(new Dimension(25, 25));
        secondaryColor.setOpaque(true);
        secondaryColor.setFocusPainted(false);
        secondaryColor.setBackground(Color.white);

        newColor = new JButton("Edit Color");

        option = new JComboBox<String>();
        option.addItem(PRIMARY_COLOR);
        option.addItem(SECONDARY_COLOR);
        option.setSelectedItem(PRIMARY_COLOR);

        fill = new JRadioButton("Fill");
        fill.setSelected(false);

        // Add components to JPanel
        addColorButtons(panel);
        panel.add(custom2);
        panel.add(custom3);
        panel.add(newColor);
        panel.add(new JLabel("Primary color"));
        panel.add(primaryColor);
        panel.add(new JLabel("Secondary color"));
        panel.add(secondaryColor);
        panel.add(option);
        panel.add(fill);

        add(panel);
    }

    // Helper method to initialize color buttons
    private void initializeColorButtons() {
        colorButtons = new JButton[20];
        Border blackline = BorderFactory.createRaisedBevelBorder();
        Color[] colors = new Color[] {
            new Color(26, 188, 156), new Color(46, 204, 113), new Color(52, 152, 219),
            new Color(155, 89, 182), new Color(52, 73, 94), new Color(22, 160, 133),
            new Color(39, 174, 96), new Color(41, 128, 185), new Color(142, 68, 173),
            new Color(44, 62, 80), new Color(241, 196, 15), new Color(230, 126, 34),
            new Color(231, 76, 60), new Color(236, 240, 241), new Color(149, 165, 166),
            new Color(243, 156, 18), new Color(211, 84, 0), new Color(192, 57, 43),
            new Color(189, 195, 199), new Color(127, 140, 141)
        };

        for (int i = 0; i < colors.length; i++) {
            colorButtons[i] = new JButton("");
            colorButtons[i].setPreferredSize(new Dimension(25, 25));
            colorButtons[i].setOpaque(true);
            colorButtons[i].setFocusPainted(false);
            colorButtons[i].setBackground(colors[i]);
            colorButtons[i].setBorder(blackline);
        }
    }

    // Helper method to add color buttons
    private void addColorButtons(JToolBar panel) {
        for (JButton button : colorButtons) {
            panel.add(button);
        }
        panel.add(new JLabel("Most Recent"));
    }

    // Getter methods for components
    public JButton getPrimaryColorButton() {
        return primaryColor;
    }

    public JButton getSecondaryColorButton() {
        return secondaryColor;
    }

    public JButton[] getColorButtons() {
        return colorButtons;
    }

    public JButton getCustom2Button() {
        return custom2;
    }

    public JButton getCustom3Button() {
        return custom3;
    }

    public JButton getNewColorButton() {
        return newColor;
    }

    public JComboBox<String> getOptionComboBox() {
        return option;
    }

    public JRadioButton getFillRadioButton() {
        return fill;
    }
}
