import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorDialog extends JDialog implements ActionListener, ChangeListener {
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;

	public static final int APPLY_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	int userResponse;

	final String[] SZ = { "10", "14", "18", "22", "26", "32", "38", "48" };

	JTextField example;
	JButton ok;
	JButton cancel;

	Color currentColor;
	JSlider red;
	JSlider green;
	JSlider blue;

	ColorDialog(Frame owner, Color c) {
		super(owner, "Customize Color", true);
		this.setResizable(false);
		//this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontList = ge.getAvailableFontFamilyNames();

		example = new JTextField("");
		example.setHorizontalAlignment(SwingConstants.CENTER);
		example.setFont(new Font("sanserif", Font.PLAIN, 28));
		example.setEditable(false);

		ok = new JButton("Apply");
		cancel = new JButton("Cancel");
		ok.setPreferredSize(cancel.getPreferredSize());

		currentColor = c;

		red = new JSlider(0, 255, currentColor.getRed());
		blue = new JSlider(0, 255, currentColor.getBlue());
		green = new JSlider(0, 255, currentColor.getGreen());

		// -------------
		// add listeners
		// -------------

		ok.addActionListener(this);
		cancel.addActionListener(this);

		red.addChangeListener(this);
		blue.addChangeListener(this);
		green.addChangeListener(this);

		// -----------------
		// layout components
		// -----------------

		JPanel p0 = new JPanel();
		p0.add(red);
		p0.setBorder(new TitledBorder(new EtchedBorder(), "Red"));

		JPanel p1 = new JPanel();
		p1.add(green);
		p1.setBorder(new TitledBorder(new EtchedBorder(), "Green"));

		JPanel p2 = new JPanel(); // use FlowLayout
		p2.add(blue);
		p2.setBorder(new TitledBorder(new EtchedBorder(), "Blue"));
		p2.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p3.add(example);
		p3.setPreferredSize(new Dimension(250, 60));
		p3.setMaximumSize(new Dimension(250, 60));
		p3.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel p4 = new JPanel();
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

		// tweek sizes of panels to make the dialog look nice

		Dimension d1 = p3.getPreferredSize();

		this.setContentPane(p);
		this.pack();
	}

	// -------------------------------
	// implement ActionListener method
	// -------------------------------

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == ok) {
			userResponse = APPLY_OPTION;
			this.setVisible(false);
		}

		else if (source == cancel) {
			userResponse = CANCEL_OPTION;
			this.setVisible(false);
		}

	}

	public void updateColor() {
		currentColor = new Color(red.getValue(), green.getValue(), blue.getValue());
		example.setBackground(currentColor);
	}

	public Color getColor() {
		return example.getBackground();
	}

	public int showCustomDialog(Frame f, Color c) {
		this.setLocationRelativeTo(f);

		// set the font combobox to the current font family name

		// currentColor = c;

		red.setValue(c.getRed());
		green.setValue(c.getGreen());
		blue.setValue(c.getBlue());
		updateColor();
		// show the dialog

		this.setVisible(true);

		// When the user closes the dialog by clicking "Apply" or "Cancel",
		// return a pre-defined integer indicating which button was
		// pressed.

		return userResponse;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		updateColor();
	}
}