import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class Shape {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	private Color color;
	private Color fillColor;
	private BasicStroke stroke;
	private String message;
	
	public boolean transparent;
	
	private int shape;
	private Font font;
	
	public int group = 0;
	
	
	public Shape(int x1, int y1,int x2, int y2, Color color,BasicStroke stroke, int shape,Color fill,boolean transparent){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.stroke = stroke;
		this.shape = shape;
		this.group = 0;
		this.fillColor = fill;
		this.transparent = transparent;
	}
	
	public Shape(int x1, int y1, int fontSize, Font font, Color color, BasicStroke stroke, int shape, String message){
		this.x1 = x1;
		this.y1 = y1;
		this.y2 = 0;
		this.font = font;
		this.x2 = fontSize;
		this.color = color;
		this.stroke = stroke;
		this.shape = shape;
		this.group = 0;
		this.message = message;
	}
	
	public Shape(int x1, int y1,int x2, int y2, Color color,BasicStroke stroke, int shape,int group){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.stroke = stroke;
		this.shape = shape;
		this.group = group;
		
	}
	public int getShape(){
		return this.shape;
	}
	public String getMessage() {
		return this.message;
	}
	public Font getFont() {
		return this.font;
	}
	public int getx1(){
		return this.x1;
	}
	public int getx2(){
		return this.x2;
	}
	public int gety1(){
		return this.y1;
	}
	public int gety2(){
		return this.y2;
	}
	public Color getColor(){
		return this.color;
	}
	public Color getfillColor(){
		return this.fillColor;
	}
	public BasicStroke getStroke(){
		return this.stroke;
	}
	public boolean getTransparency(){
		return this.transparent;
	}
	
	public int getGroup(){
		return this.group;
	}
}
