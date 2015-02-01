import java.awt.*;
import javax.swing.*;

public class Sprite{
		
	Image att1_left,att1_right,att2_left,att2_right,idle_left,idle_right,walk_left,walk_right;
		
	public Sprite(String c){
		
		String dir = "Sprites/Characters/"+c+"/"+c;
			
		att1_left = new ImageIcon(dir+"_attack1_left.png").getImage();
		att1_right = new ImageIcon(dir+"_attack1_right.png").getImage();
		att2_left = new ImageIcon(dir+"_attack2_left.png").getImage();
		att2_right = new ImageIcon(dir+"_attack2_right.png").getImage();
		idle_left = new ImageIcon(dir+"_idle_left.png").getImage();
		idle_right = new ImageIcon(dir+"_idle_right.png").getImage();
		walk_left = new ImageIcon(dir+"_walk_left.png").getImage();
		walk_right = new ImageIcon(dir+"_walk_right.png").getImage();
			
	}
	
	public Image getAttLeft1(){
		return att1_left;
	}
		
	public Image getAttRight1(){
		return att1_right;
	}
		
	public Image getAttLeft2(){
		return att2_left;
	}
		
	public Image getAttRight2(){
		return att2_right;
	}
		
	public Image getIdleLeft(){
		return idle_left;
	}
		
	public Image getIdleRight(){
		return idle_right;
	}
		
	public Image getWalkLeft(){
		return walk_left;
	}
		
	public Image getWalkRight(){
		return walk_right;
	}
}