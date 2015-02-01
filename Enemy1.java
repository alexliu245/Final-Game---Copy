import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class Enemy{
	int hp, current_hp, def, move_speed, numAttacks, exp;
	int width, height, basic_range = 0;
	double x,y;	//for movement
	String name, currentAttack;
	int[] aggro = {0,0,0,0};
	Player target;
	boolean alreadyAttacking = false;
	MainFrame.TruePlayer human_player;
	Image[] sprites = new Image[2];
	ArrayList<Image> attackSprites = new ArrayList<Image>();
	ArrayList<String> attackTypes = new ArrayList<String>();
	
	public Enemy(String name, MainFrame.TruePlayer p){
		//since this is an external class TruePlayer has to be imported in
		human_player = p;
		this.name = name;
		importStats(name);
	}
	
	public Enemy clone(){
		return new Enemy(name,human_player);
	}
	
	public void importStats(String file){
		try{
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(String.format("Enemies/%s.txt",file))));
			
			name = inFile.nextLine();
			hp = Integer.parseInt(inFile.nextLine());
			def = Integer.parseInt(inFile.nextLine());
			move_speed = Integer.parseInt(inFile.nextLine());
			width = Integer.parseInt(inFile.nextLine());
			height = Integer.parseInt(inFile.nextLine());
			current_hp = hp;
			x = 0;
			y = 0;
			sprites[0] = new ImageIcon(inFile.nextLine()).getImage();
			sprites[1] = new ImageIcon(inFile.nextLine()).getImage();
			numAttacks = Integer.parseInt(inFile.nextLine());
			for(int i = 0; i < numAttacks; i++){
				String line = inFile.nextLine();
				if(!line.equals("x")){
					attackSprites.add(new ImageIcon(line).getImage());	
				}
			}
			for(int i = 0; i < numAttacks; i++){
				attackTypes.add(inFile.nextLine());
			}
			exp = Integer.parseInt(inFile.nextLine());
		}
		catch(IOException except){
			System.out.println("error in Enemy.importStats()");
		}
	}
	public String getName(){
		return name;
	}
	public Image getImage(){
		return sprites[0];
	}
	public int getHp(){
		return hp;
	}
	public int getCurrentHp(){
		return current_hp;
	}
	public void setCurrentHp(int hp){
		current_hp = hp;
	}
	public int getDef(){
		return def;
	}
	
	//AI targetting system
	public void setAggro(int[] a){
		aggro = a;
	}
	public void updateAggro(Player p, int dmg){
		ArrayList<Player> team = human_player.getTeam();
		int pos = team.indexOf(p);
		aggro[pos] += dmg;
	}
	public double dist(double x, double y, double x2, double y2){
		return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
	}
	public void updateTarget(){
		int posOfGreatest = 0, aggroNum = 0;
		boolean aggroActive = false;
		
		//updates aggro towards players - the aggro goes down by 25% each time
		for(int i = 0; i < 4; i++){
			aggro[i] = (int)(aggro[i]*0.75);
		}
		
		//checks and figures out who has the greatest aggro
		for(int i = 0; i < 4; i++){
			if(aggro[i] > aggroNum){
				aggroActive = true;
				aggroNum = aggro[i];
				posOfGreatest = i;
			}
		}
		ArrayList<Player> players = human_player.getTeam();
		int smallestDist = Integer.MAX_VALUE;
		
		//if one of the players has a greater aggro than the others, then the new
		//target becomes that player.
		if(aggroActive){
			target = players.get(posOfGreatest);
		}
		//otherwise, if theyre all somehow the same, then the target becomes the
		//person to the enemy.
		else{
			for(Player p:players){
				if(p!= null){
					if(dist(p.getX(),p.getY(),x,y) < smallestDist && p.getAlive()){
						smallestDist = (int)(dist(p.getX(),p.getY(),x,y));
						target = p;
					}
				}
			}
		}
	}
	
	//read attacks
	public void readAttacks(String s){
		/*this is the code used to read the 'attack types'.
		in order to use it, you give the method the 'attack type' and it's pos
		inside the ArrayList of 'attack types'.
		
		basically, it breaks down the attacks into shortened versions then calls
		them. The basic legend is this:
		all attacks look like this: __.#.#2 --> the __ represents the attack type,
		the # represents damage of attack, and the second # is the position of the
		projectile image in the ArrayList<Image> of projectiles, 4th part is projectile
		speed
		
		the __ are these:
		M - melee
		SF - single-fire projectile
		#WF - multi-way shot (think shotgun). # represents num of projectiles shot.
		#AWF - all-way shot. (full 360). # represents num of projectiles shot.
		*/
		String[] atk = s.split(".");
		int damage, numProj, pos;
		damage = Integer.parseInt(atk[1]);
		if(!atk[2].equals("")){
			pos = Integer.parseInt(atk[2]);
		}
		if(atk[0].equals("M")){
			basic_range = 30;
			if(checkAttackRange()){
				meleeAttack(damage);
			}	
		}
		else if(atk[0].equals("SF")){
			basic_range = 300;
			if(checkAttackRange()){
				pos = Integer.parseInt(atk[2]);
				singleShot(damage,attackSprites.get(pos),Integer.parseInt(atk[3]));
			}
		}
		else{
			if(atk[0].indexOf("AWF") == -1){
				basic_range = 400;
				if(checkAttackRange()){
					numProj = Integer.parseInt(atk[0].substring(0,atk[0].indexOf("W")));
					pos = Integer.parseInt(atk[2]);
					multiShot(numProj,damage,attackSprites.get(pos),Integer.parseInt(atk[3]));
				}
			}
			else{
				basic_range = 100000;
				if(checkAttackRange()){
					numProj = Integer.parseInt(atk[0].substring(0,atk[0].indexOf("A")));
					pos = Integer.parseInt(atk[2]);
					allWayShot(numProj,damage,attackSprites.get(pos),Integer.parseInt(atk[3]));
				}	
			}
		}
	}
	
	public boolean checkAttackRange(){
		if(target != null){
			if((int)(dist(target.getX(),target.getY(),x,y)) < basic_range){
				return true;
			}
		}
		return false;
	}

	//attacks
	public void attack(){
		if(!alreadyAttacking){
			if(attackTypes.size() > 0){
				currentAttack = attackTypes.get((int)(Math.random()*attackTypes.size()));
				alreadyAttacking = true;
			}
		}
		else{
			readAttacks(currentAttack);
		}
	}
	public void meleeAttack(int damage){
		target.takeDamage(damage);
		alreadyAttacking = false;
	}
	public void singleShot(int damage,Image pSprite, int projectileSpeed){
	}
	public void multiShot(int numProj, int damage,Image pSprite, int projectileSpeed){
	}
	public void allWayShot(int numProj, int damage,Image pSprite, int projectileSpeed){
	}
	
	//movement
	
	public int getMoveSpeed(){
		return move_speed;
	}
	public double getX(){
		return x-20;
	}
	public double getY(){
		return y-20;
	}
	public double getMidX(){
		return x;
	}
	public double getMidY(){
		return y;
	}
}