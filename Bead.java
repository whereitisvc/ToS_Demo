import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
public class Bead
{
	int location;
	int property;
	int width = 70;
	int hight = 70;
	boolean delete = false;
	boolean pressed = false;
	Color color;
	public int raw1;
	public int raw2;
	public int col1;
	public int col2;
	 
	public Bead(){ location = 0; property = 0; }
	public Bead( int l,int p ){ location = l; property = p; }
	public void set_location(int l){ location = l; } 
	public void set_property(int p){ property = p; }
	public void set_delete(boolean d){ delete = d; }
	public void set_pressed(boolean p){ pressed = p; }
	public int get_location(){ return location; }
	public int get_property(){ return property; }
	public boolean get_delete(){ return delete; }
	public boolean get_pressed(){ return pressed; }
	public void draw( Graphics g )
	{ 
			if(property == 0)
				color = Color.BLUE;
			else if(property == 1)
				color = Color.RED;
			else if(property == 2)
				color = Color.GREEN;
			else if(property == 3)
				color = Color.YELLOW;
			else if(property == 4)
				color = Color.MAGENTA;
				
			if(delete)
			{	
				g.setColor(Color.WHITE);
				g.fillRect( 0+(location%6)*width , 300+(location/6)*hight ,width ,hight );
			}
			else
			{
				g.setColor(color);
				g.fillOval( 0+(location%6)*width , 300+(location/6)*hight ,width ,hight );
				g.setColor(Color.BLACK);
				g.drawString("("+location+")" ,0+(location%6)*width+30 ,300+(location/6)*hight+35);
				if(pressed)
				{	
					Color bcolor = color.darker();
					g.setColor(bcolor);g.fillOval( 0+(location%6)*width , 300+(location/6)*hight ,width ,hight ); 
					pressed = false;

				}
				else
				{
					g.setColor(color);g.drawOval( 0+(location%6)*width , 300+(location/6)*hight ,width ,hight ); 
				}
			}
			//System.out.printf("location %d ,property %d. Drawn.\n",location,property);
	}
}