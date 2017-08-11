import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Graphics;

public class BeadPanel extends JPanel implements  MouseListener, MouseMotionListener 
{
		Bead table[];
		private ArrayList<Combo> combolist;
		int locA,locB;
		int combo = 0;
		int index = 0;
		boolean first = true;
		boolean stop = false;
		boolean clear = false;
		boolean drag = false;
		
		public BeadPanel()
		{
			combolist = new ArrayList<Combo>();
			table = new Bead[30];
		
			Random rand = new Random();
			
			 for(int i=0;i<30;i++)
				table[i] = new Bead( i,rand.nextInt(5) );
			 eliminate();
			 
			 while(!clear)
			 {
				drop_create();
				eliminate();
			 }
			 
			this.addMouseMotionListener(this);
			this.addMouseListener(this);		
			
		}
		
		public void	mouseClicked(MouseEvent e){}
		public void	mouseEntered(MouseEvent e){}
		public void	mouseExited(MouseEvent e){}
		public void	mousePressed(MouseEvent e){}
		public void	mouseReleased(MouseEvent e)
		{
			first = true; repaint();  //for ABswitch
			if(drag){ 
				eliminate(); 
				//try { Thread.currentThread().sleep(1000); }
				//catch(InterruptedException ex) {;}
				drop_create(); drag = false; } //for drag&release
		}
		public void mouseDragged( MouseEvent e )
		{
			/**************************************** for ABswitch *****************************************/
			if(first)
              {  locA = into_location(e.getX(),e.getY()); first = false; }
			locB = into_location(e.getX(),e.getY());
			table[locA].set_pressed(true);
			ABswitch();
			repaint();
			locA = into_location(e.getX(),e.getY());
			
			/**************************************** for drag&release *****************************************/
			drag = true;
		}
		public void mouseMoved( MouseEvent e )
		{
				//System.out.printf("\r mouse:( %3d , %3d ) ,location: %3d",e.getX(),e.getY(),into_location(e.getX(),e.getY()));
		}
		
		
		public int into_location( int x,int y )
		{
			int width = 70;
			int hight = 70;
			return (y-300)/hight*6 + (x/width)%6;
		}
		public void ABswitch()
		{
			int temp = table[locA].get_property();
			table[locA].set_property( table[locB].get_property() ); 
			table[locB].set_property( temp ); 
		}

		public void eliminate()
		{
			/**************************************** eliminate parameter *****************************************/
			int rmax,lmin,upmin,dmax;
			int raw1 = 0 ,raw2 = 0;
			int col1 = 0 ,col2 = 0;
		
			for(int i=0;i<30;i++)
			{
				rmax = i+(5-i%6);
				lmin = i-i%6;
				upmin = i-6*(i/6);
				dmax = i+6*(4-i/6);
				raw1 = 0 ;raw2 = 0;
				col1 = 0 ;col2 = 0;
				
				for(int x=i;x<rmax;x++)
					if( table[x].get_property() == table[x+1].get_property() )
						raw1++;
					else break;
				for(int x=i;x>lmin;x--)
					if( table[x].get_property() == table[x-1].get_property() )
						raw2++;
					else break;
						
				for(int x=i;x<dmax;x=x+6)
					if( table[x].get_property() == table[x+6].get_property() )
						col1++;	
					else break;
				for(int x=i;x>upmin;x=x-6)
					if( table[x].get_property() == table[x-6].get_property() )
					    col2++;
					else break;
				table[i].raw1 = raw1;   table[i].raw2 = raw2;
				table[i].col1 = col1;   table[i].col2 = col2;
			}
			
			/**************************************** 1. group for counts combos --> 3 for a base *****************************************/
			
			for(int i=0;i<30;i++)
			{
				if( !table[i].get_delete() )
				{
					raw1 = table[i].raw1;   raw2 = table[i].raw2;
					col1 = table[i].col1;   col2 = table[i].col2;
					
					if( (raw1+raw2) > 1 )
					{
						Combo tempc;
						int tempc_i=0;
						int buttom,top,length;
						
						if(raw2>0) buttom = i-raw2;  
						else buttom = i;
						
						top = buttom + raw1 + raw2 + 1;  length = top-buttom; tempc = new Combo(length);
						for(int j=buttom;j<top;j++ )
						{	table[j].set_delete(true);  tempc.setdata(tempc_i,j);  tempc_i++;  }
						combolist.add( tempc ); index++;
						//System.out.printf("<%d> ",index-1);
						//combolist.get( index-1 ).showdata();
					}
					if( (col1+col2) > 1 )
					{
						Combo tempc;
						int tempc_i=0;
						int buttom,top,length;
						
						if(col2>0) buttom = i-col2*6;  
						else buttom = i;
						
						top = buttom + (col1 + col2 +1 )*6;  length = (top-buttom)/6; tempc = new Combo(length);
						for(int j=buttom;j<top;j=j+6 )
						{	table[j].set_delete(true);  tempc.setdata(tempc_i,j);  tempc_i++;  }
						combolist.add( tempc ); index++;
						//System.out.printf("<%d> ",index-1);
						//combolist.get( index-1 ).showdata();
					}

				}
			}
			
			/**************************************** 2. group for counts combos --> final group *****************************************/
			
			boolean once = true,near = false;
			
			for(int i=0;i<index;i++)
			{
				if(near)
				{ near = false; i=0; } //System.out.printf("\n* i=%d ,index=%d\n",i,index);
				Combo temp = combolist.get(i);
				for(int p=i+1;p<index;p++)
				{
				   Combo temp1 = combolist.get(p);
				
				   for(int j=0;j<temp.length;j++)
				   {
						int data = temp.getdata(j);
						for(int k=0;k<temp1.length;k++)
						{
							int data1 = temp1.getdata(k);
							if( table[data].get_property() == table[data1].get_property() )
							{
									int dis;
									dis = Math.abs( data%6-data1%6 ) + Math.abs( data/6-data1/6 );
									if(dis<2){ near = true; break; }
							}
						}
					
						if(near)
						{
							
							Combo temp2 = new Combo( temp.length+temp1.length );
							for(int l=0;l<temp.length;l++)temp2.setdata(0+l, temp.getdata(l));
							for(int l=0;l<temp1.length;l++)temp2.setdata(temp.length+l, temp1.getdata(l));	
							/*
							System.out.println();
							temp.showdata();
							temp1.showdata();
							temp2.showdata(); */
							
							combolist.remove(i); index--;
							combolist.remove(p-1); index--;
							combolist.add(0,temp2); index++;
							break;
						}
						if(near) break;
					}
					if(near)break;
					
				}
			}
			/**************************************** show data condition. *****************************************/
			System.out.println();
			for(int i=0;i<30;i++)
			{
				if( (i%6)==0 )System.out.println();
				//System.out.printf(" %d",temp[i]);
				if(table[i].get_delete()) System.out.printf(" *");
				else System.out.printf(" 0");
			}
			System.out.println();
			for(int q=0;q<index;q++)
			{	System.out.printf("<%d> ",q);combolist.get(q).showdata(); }  
			
			combo = index;
			System.out.printf("combo = %d",combo);
			if(combo==0) clear=true; 
			else clear=false;
			
			//stop = true;
			repaint();
			
		}
		
		public void drop_create()
		{
				
				for(int i=29;i>=0;i--)
				{
					int idown = 24+i%6;
					if( !table[i].get_delete() )
					{
						while( !table[idown].get_delete() && idown != i ){ idown = idown-6; }
						
						if( table[idown].get_delete() )
						{
							table[idown].set_property( table[i].get_property() );
							table[idown].set_delete(false);
							table[i].set_delete(true);
						}
					}
				}
				
				Random rand = new Random();
				for(int i=0;i<30;i++)
				    if( table[i].get_delete() ) table[i] = new Bead( i,rand.nextInt(5) );
					
				combolist.clear(); index = 0;
				
				repaint();
		}
		public void paintComponent(Graphics g)
		{
			
			g.setColor(Color.WHITE);
			g.fillRect(0,300,440,400);
			for(int i=0;i<30;i++)
					table[i].draw(g);
			/*
			if(stop)
			{ 
				System.out.printf("\nstop 1.\n");
				try { Thread.sleep(1000); }
				catch(InterruptedException ex) {;} 
				stop = false;
				System.out.printf("\nstop 2.\n");
			}*/
			

		}
}