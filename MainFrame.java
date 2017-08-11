import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener
{
	JPanel monsterp,lifep;
	BeadPanel beanp;
	JButton button1,button2;
	
	public MainFrame()
	{
		super("Painter");
		int width = 440;
		this.setSize(width,700);
		
		monsterp = new JPanel();
		lifep = new JPanel();
		beanp = new BeadPanel();
		button1 = new JButton("Eliminate");
		button1.addActionListener(this);
		button2 = new JButton("Drop & Create");
		button2.addActionListener(this);
		
		monsterp.setSize(width,250);
		//lifep.setSize(width,50);
		button1.setSize(width/2,50);
		button2.setSize(width/2,50);
		beanp.setSize(width,400);
		monsterp.setBackground(Color.BLACK);
		//lifep.setBackground(Color.RED);
		beanp.setBackground(Color.WHITE);
		
		monsterp.setLocation(0,0);
		//lifep.setLocation(0,250);
		button1.setLocation(0,250);
		button2.setLocation(width/2,250);
		beanp.setLocation(0,300);
		
		this.add(monsterp);
		//this.add(lifep);
		this.add(button1);
		this.add(button2);
		this.add(beanp);
		
	}
	public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button1)
		{	
			beanp.eliminate();	
			//try { Thread.sleep(1000); }
				//catch(InterruptedException ex) {;} 
			beanp.drop_create();
		}
		if(e.getSource()==button2)
		{
			beanp.drop_create();
		}
	}
}