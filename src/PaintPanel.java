import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PaintPanel extends JPanel
	{
		// the following avoids a "warning" with Java 1.5.0 complier (?)
		static final long serialVersionUID = 42L;

		Image image;
		Graphics2D graphics2D;
		private JLabel label;
		
		int x1, y1, x2, y2;
		
		private final Color white = new Color(255, 255, 255);
		private final Color black = new Color(0, 0, 0);
		private final Color red = new Color(255, 0, 0);
		private final Color blue = new Color(0, 0, 255);
		private final Stroke INK_STROKE_3 = new BasicStroke(3.0f);

		private Color currentColor;

		public PaintPanel()
		{
			// construct components
			// contentPane > outerPanel > inkPanel > (Components: clearButton, stroke)
			// configure components
			// add listeners
			// set components into the contentPane
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setPreferredSize(new Dimension(250, 250));
		}

		//Now for the constructors
		public PaintPanel(int f){
			setDoubleBuffered(false);
			this.setPreferredSize(new Dimension(800,600));
			currentColor = black;
			
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					x1 = e.getX();
					y1 = e.getY();
				}
			});
			//if the mouse is pressed it sets the oldX & oldY
			//coordinates as the mouses x & y coordinates
			addMouseMotionListener(new MouseMotionAdapter()
			{
				public void mouseDragged(MouseEvent e){
					x2 = e.getX();
					y2 = e.getY();
					
					if(graphics2D != null)
						graphics2D.setColor(currentColor);
						graphics2D.drawLine(x1, y1, x2, y2);
					repaint();
					x1 = x2;
					y1 = y2;
				}

			});
			//while the mouse is dragged it sets currentX & currentY as the mouses x and y
			//then it draws a line at the coordinates
			//it repaints it and sets oldX and oldY as currentX and currentY
		}

		public void paintComponent(Graphics g){
			if(image == null){
				image = createImage(getSize().width, getSize().height);
				graphics2D = (Graphics2D)image.getGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				clear();

			}
			g.drawImage(image, 0, 0, null);
		}
		//this is the painting bit
		//if it has nothing on it then
		//it creates an image the size of the window
		//sets the value of Graphics as the image
		//sets the rendering
		//runs the clear() method
		//then it draws the image


		public void clear(){
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			graphics2D.setPaint(Color.black);
			repaint();
		}
		//this is the clear
		//it sets the colors as white
		//then it fills the window with white
		//thin it sets the color back to black


		public void drawRect(int x, int y, int width, int height){
			
			
			
		}

		public void drawCirc(int x, int y, int r){
			
		}
		
		public void eraser(){
			currentColor = white;
		}
		public void setColor(Color c){
			currentColor = c;
		}


		
		public void drawImage(Image image){
			
			label = new JLabel("",new ImageIcon(image),JLabel.CENTER);
			this.add(label);
			
			this.revalidate();
			this.repaint();		
			
		}
		

	}