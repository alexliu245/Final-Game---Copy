//itemTest.java
//alex liu

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.math.*;
public class itemTest extends JFrame implements ActionListener{
	DisplayPanel test;
	javax.swing.Timer myTimer;

	public itemTest() {
    	super ("test");
    	setSize(800,800);
    	setLayout(null);
    	
    	test = new DisplayPanel();
    	test.setSize(800,800);
    	test.setLocation(0,0);
    	add(test);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	
    	myTimer = new javax.swing.Timer(30,this);
    	myTimer.start();	
    }

    public void actionPerformed(ActionEvent evt){
    	test.update();
    }
    class DisplayPanel extends JPanel implements MouseListener, MouseMotionListener{
    	LootGenerator loot = new LootGenerator();
		HashMap<int[],Item> items = new HashMap<int[],Item>();
		int mx,my,counter;
		boolean click;
		
    	
		//-----------MouseListner---------------
		public void mouseClicked(MouseEvent e){};
		public void mouseEntered(MouseEvent e){};
		public void mouseExited(MouseEvent e){};
		public void mousePressed(MouseEvent e){click = true;}
		public void mouseReleased(MouseEvent e){click = false;}
		//-----------MotionListener-------------
    	public void mouseDragged(MouseEvent e){mx = e.getX();my = e.getY();}
    	public void mouseMoved(MouseEvent e){mx = e.getX();my = e.getY();}
    	
    	public DisplayPanel(){
    		super();
	    	setFocusable(true);
	    	grabFocus();
	    	addMouseMotionListener(this);
	    	addMouseListener(this);
	    	click=false;
	    	mx=0;
	    	my=0;
	    	counter = 0;
    	}
	
		public void update(){
			if (counter > 0){
				counter--;
			}
			if (click && counter == 0){
				System.out.println(Math.random()*1200-100);
				counter = 20;
	    		Item i = loot.generateItem(8);
	    		int[] tmp = {mx,my};
	    		items.put(tmp,i);
	    	}
		}
    	public void paintComponent(Graphics g){
    		g.setColor(Color.RED);
    		g.fillRect(0,0,800,800);
	    	Set<int[]> pos = items.keySet();
	    	for(int[] p : pos){
	    		g.drawImage(items.get(p).getImage(),p[0],p[1],this);
	    	}
	    	repaint();
	    }
	}
	public static void main (String [] args){
		itemTest frame=new itemTest();
	
	}
}