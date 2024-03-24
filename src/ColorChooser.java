import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class ColorChooser extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2358101254605896432L;
	// private JButton turquoise;
	// private JButton emerald;
	// private JButton peter_river;
	// private JButton amethyst;
	// private JButton wet_asphalt;
	// private JButton green_sea;
	// private JButton nephritis;
	// private JButton belize_hole;
	// private JButton wisteria;
	// private JButton midnight_blue;
	// private JButton sun_flower;
	// private JButton carrot;
	// private JButton alizarin;
	// private JButton clouds;
	// private JButton concrete;
	// private JButton orange;
	// private JButton pumpkin;
	// private JButton pomegranate;
	// private JButton silver;
	// private JButton asbestos;
	private JButton[] colorButtons;

	private JButton primaryColor;
	private JButton custom2;
	private JButton custom3;
	private JButton newColor;
	private JButton secondaryColor;

	// private Color turquoiseC = new Color(26, 188, 156);
	// private Color emeraldC = new Color(46, 204, 113);
	// private Color peter_riverC = new Color(52, 152, 219);
	// private Color amethystC = new Color(155, 89, 182);
	// private Color wet_asphaltC = new Color(52, 73, 94);
	// private Color green_seaC = new Color(22, 160, 133);
	// private Color nephritisC = new Color(39, 174, 96);
	// private Color belize_holeC = new Color(41, 128, 185);
	// private Color wisteriaC = new Color(142, 68, 173);
	// private Color midnight_blueC = new Color(44, 62, 80);
	// private Color sun_flowerC = new Color(241, 196, 15);
	// private Color carrotC = new Color(230, 126, 34);
	// private Color alizarinC = new Color(231, 76, 60);
	// private Color cloudsC = new Color(236, 240, 241);
	// private Color concreteC = new Color(149, 165, 166);
	// private Color orangeC = new Color(243, 156, 18);
	// private Color pumpkinC = new Color(211, 84, 0);
	// private Color pomegranateC = new Color(192, 57, 43);
	// private Color silverC = new Color(189, 195, 199);
	// private Color asbestosC = new Color(127, 140, 141);
	private Color[] colors;

	private JToolBar cc;

	private DrawFrame frame;

	private JComboBox<String> option;
	private JRadioButton fill;

	ColorDialog cd;

	private final String PRIMARY_COLOR = "Primary Color";
	private final String SECONDARY_COLOR = "Secondary Color";

	public ColorChooser(DrawFrame frame) {
		cc = new JToolBar(JToolBar.HORIZONTAL);
		// cc.setLayout(new GridLayout(5,5));
		cc.setLayout(new FlowLayout());
		cc.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		cc.setFloatable(false);
		this.frame = frame;
		this.initializeColorChooser(cc);

		cd = new ColorDialog(frame, primaryColor.getBackground());

		primaryColor.addActionListener(this);
		custom2.addActionListener(this);
		custom3.addActionListener(this);
		secondaryColor.addActionListener(this);
		newColor.addActionListener(this);
		fill.addActionListener(this);
	}

	private void initializeColorChooser(JToolBar panel) {
		colors = new Color[] {
				new Color(26, 188, 156), // turquoiseC
				new Color(46, 204, 113), // emeraldC
				new Color(52, 152, 219), // peter_riverC
				new Color(155, 89, 182), // amethystC
				new Color(52, 73, 94), // wet_asphaltC
				new Color(22, 160, 133), // green_seaC
				new Color(39, 174, 96), // nephritisC
				new Color(41, 128, 185), // belize_holeC
				new Color(142, 68, 173), // wisteriaC
				new Color(44, 62, 80), // midnight_blueC
				new Color(241, 196, 15), // sun_flowerC
				new Color(230, 126, 34), // carrotC
				new Color(231, 76, 60), // alizarinC
				new Color(236, 240, 241), // cloudsC
				new Color(149, 165, 166), // concreteC
				new Color(243, 156, 18), // orangeC
				new Color(211, 84, 0), // pumpkinC
				new Color(192, 57, 43), // pomegranateC
				new Color(189, 195, 199), // silverC
				new Color(127, 140, 141) // asbestosC
		};

		colorButtons = new JButton[colors.length];
		Border blackline = BorderFactory.createRaisedBevelBorder();
		for (int i = 0; i < colors.length; i++) {
			colorButtons[i] = new JButton("");
			colorButtons[i].setPreferredSize(new Dimension(25, 25));
			colorButtons[i].setOpaque(true);
			// colorButtons[i].setBorderPainted(false);
			colorButtons[i].setFocusPainted(false);
			colorButtons[i].setBackground(colors[i]);
			colorButtons[i].addActionListener(this);
			colorButtons[i].setBorder(blackline);
		}

		primaryColor = new JButton("");
		primaryColor.setPreferredSize(new Dimension(25, 25));
		primaryColor.setOpaque(true);
		// primaryColor.setBorderPainted(false);
		primaryColor.setFocusPainted(false);
		primaryColor.setBackground(Color.black);
		primaryColor.addActionListener(this);
		primaryColor.setBorder(blackline);

		custom2 = new JButton("");
		custom2.setPreferredSize(new Dimension(25, 25));
		custom2.setOpaque(true);
		// custom2.setBorderPainted(false);
		custom2.setFocusPainted(false);
		custom2.setBackground(Color.white);
		custom2.addActionListener(this);
		custom2.setBorder(blackline);

		custom3 = new JButton("");
		custom3.setPreferredSize(new Dimension(25, 25));
		custom3.setOpaque(true);
		// custom3.setBorderPainted(false);
		custom3.setFocusPainted(false);
		custom3.setBackground(Color.white);
		custom3.addActionListener(this);
		custom3.setBorder(blackline);

		secondaryColor = new JButton("");
		secondaryColor.setPreferredSize(new Dimension(25, 25));
		secondaryColor.setOpaque(true);
		// secondaryColor.setBorderPainted(false);
		secondaryColor.setFocusPainted(false);
		secondaryColor.setBackground(Color.white);
		secondaryColor.addActionListener(this);
		secondaryColor.setBorder(blackline);

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
	}

	// Helper method to add color buttons
	private void addColorButtons(JToolBar panel) {
		for (JButton button : colorButtons) {
			panel.add(button);
		}
		panel.add(new JLabel("Most Recent"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == fill) {
			JRadioButton b = (JRadioButton) e.getSource();
			frame.getInkPanel().setTransparency(!b.isSelected());
		} else {

			JButton b = (JButton) e.getSource();

			if (b == custom2) {
				Color switchs = custom2.getBackground();
				if (option.getSelectedItem() == PRIMARY_COLOR) {

					custom2.setBackground(primaryColor.getBackground());
					primaryColor.setBackground(switchs);
				} else {
					custom2.setBackground(secondaryColor.getBackground());
					secondaryColor.setBackground(switchs);
				}

			} else if (b == custom3) {
				if (option.getSelectedItem() == PRIMARY_COLOR) {
					Color oldCustom1 = primaryColor.getBackground();
					primaryColor.setBackground(custom3.getBackground());
					custom3.setBackground(custom2.getBackground());
					custom2.setBackground(oldCustom1);
				} else {
					Color oldCustom1 = secondaryColor.getBackground();
					secondaryColor.setBackground(custom3.getBackground());
					custom3.setBackground(custom2.getBackground());
					custom2.setBackground(oldCustom1);
				}

			} else if (b == primaryColor) {
			} else if (b == secondaryColor) {
				secondaryColor.setBackground(primaryColor.getBackground());
				frame.getInkPanel().setFillColor(secondaryColor.getBackground());
			} else if (b == newColor) {
				int i;
				if (option.getSelectedItem() == PRIMARY_COLOR) {
					i = cd.showCustomDialog(frame, primaryColor.getBackground());
				} else {
					i = cd.showCustomDialog(frame, secondaryColor.getBackground());
				}

				if (i == ColorDialog.APPLY_OPTION) {
					if (option.getSelectedItem() == PRIMARY_COLOR) {
						custom3.setBackground(custom2.getBackground());
						custom2.setBackground(primaryColor.getBackground());
						primaryColor.setBackground(cd.currentColor);
					} else {
						custom3.setBackground(custom2.getBackground());
						custom2.setBackground(secondaryColor.getBackground());
						secondaryColor.setBackground(cd.currentColor);
					}
					System.out.println(cd.currentColor.getRed() + " " + cd.currentColor.getGreen() + " "
							+ cd.currentColor.getBlue());
				}
			}

			else {
				if (option.getSelectedItem() == PRIMARY_COLOR) {
					custom3.setBackground(custom2.getBackground());
					custom2.setBackground(primaryColor.getBackground());
					primaryColor.setBackground(b.getBackground());
				} else {
					custom3.setBackground(custom2.getBackground());
					custom2.setBackground(secondaryColor.getBackground());
					secondaryColor.setBackground(b.getBackground());

				}

			}

			frame.getInkPanel().setColor(primaryColor.getBackground());
			frame.getInkPanel().setFillColor(secondaryColor.getBackground());
		}
	}

	public JToolBar getToolBar() {
		return this.cc;
	}

}
