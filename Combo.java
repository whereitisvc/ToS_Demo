import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Graphics;

public class Combo
{
	public int length;
	int data[];
	
	public Combo(){ length = 3; data = new int[length]; }
	public Combo( int l )
	{
		length = l;
		data = new int[length];
	}
	
	public void setdata( int i , int d ){  data[i] = d;  }
	public void showdata()
	{
		System.out.printf("length : %d ,data = ",length);
		for(int i=0;i<length;i++) System.out.printf("%2d ",data[i]);
		System.out.println();
	}
	public int getdata(int i)
	{	return data[i];  }
	
}
