import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PaintPanel extends JPanel
	{
		private final int PENCIL_TOOL = 0;
		private final int LINE_TOOL = 1;
		private final int RECTANGLE_TOOL = 2;
		private final int CIRCLE_TOOL = 3;
		private final int SELECT_TOOL = 4;
		private final int TEXT_TOOL = 5;
		
		private BasicStroke stroke = new BasicStroke((float) 0.1);
		BufferedImage canvas;
		Graphics2D graphics2D;
		private int activeTool = 0;
		private JLabel label;
		
		int x1, y1, x2, y2;
		

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
			this.setPreferredSize(new Dimension(800,600));
			
			
			currentColor = Color.BLACK;
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					x1 = e.getX();
					y1 = e.getY();
				}
				
				public void mouseReleased(MouseEvent e) {
					if (activeTool == LINE_TOOL) {
						
						graphics2D.drawLine(x1, y1, x2, y2);
						System.out.println("0");
					}
					else if (activeTool == RECTANGLE_TOOL) {
					
						if (x1 < x2 && y1 < y2) {
							graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
						}
						else if (x2 < x1 && y1 < y2) {
							graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
						}
						else if (x1 < x2 && y2 < y1) {
							graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
						}
						else if (x2 < x1 && y2 < y1) {
							graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
						}
						
					}
					else if (activeTool == CIRCLE_TOOL) {
						if (x1 < x2 && y1 < y2) {
							graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
						}
						else if (x2 < x1 && y1 < y2) {
							graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
						}
						else if (x1 < x2 && y2 < y1) {
							graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
						}
						else if (x2 < x1 && y2 < y1) {
							graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
						}
					}
					else if (activeTool == SELECT_TOOL){
						
					}
					else if (activeTool == TEXT_TOOL){
						
						
					}
					repaint();
				}
			});
			//if the mouse is pressed it sets the oldX & oldY
			//coordinates as the mouses x & y coordinates
			addMouseMotionListener(new MouseMotionListener()
			{
				public void mouseDragged(MouseEvent e){
					x2 = e.getX();
					y2 = e.getY();
					if (activeTool == PENCIL_TOOL) {
						graphics2D.drawLine(x1, y1, x2, y2);
						repaint();
						x1 = x2;
						y1 = y2;
					}
					
				}
				public void mouseMoved(MouseEvent e) {
					
				}

			});
			//while the mouse is dragged it sets currentX & currentY as the mouses x and y
			//then it draws a line at the coordinates
			//it repaints it and sets oldX and oldY as currentX and currentY
		}
		

		public void paintComponent(Graphics g){
			if(canvas == null){
				canvas = new BufferedImage(800, 600,BufferedImage.TYPE_INT_ARGB);
				graphics2D = canvas.createGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				clear();
			}
			g.drawImage(canvas, 0, 0, null);
		}
		
		public void setTool(int tool) {
			this.activeTool = tool;
		}
		
		public void setImage(BufferedImage image) {
			graphics2D.dispose();
			canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			graphics2D = canvas.createGraphics();
			graphics2D.drawImage(image, 0, 0, null);
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		public void clear(){
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			repaint();
			graphics2D.setColor(currentColor);
		}
		
		public void eraser(){ // find a transparent color later
			graphics2D.setColor(Color.WHITE);
		}
		public void setColor(Color c){
			currentColor = c;
			graphics2D.setColor(c);
		}
		public void setThickness(float f){
			stroke = new BasicStroke(f);
			graphics2D.setStroke(stroke);
		}
//		public void thicker(){
//			System.out.println(stroke.getLineWidth());
//			stroke = new BasicStroke(stroke.getLineWidth() + 2f);
//			graphics2D.setStroke(stroke);
//		}
//		public void thinner(){
//			if (stroke.getLineWidth() > 2.1f){
//				System.out.println(stroke.getLineWidth());
//				BigDecimal bd = new BigDecimal(Float.toString(stroke.getLineWidth()-2f));
//				bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
//				stroke = new BasicStroke(bd.floatValue());
//				graphics2D.setStroke(stroke); 
//			}
//			
//		}
		
	}