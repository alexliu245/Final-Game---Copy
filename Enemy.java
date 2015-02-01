import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class Enemy{
	int hp, current_hp, def, move_speed, numAttacks, exp,direction;
	int width, height, basic_range = 0,count;
	double x,y,angle;	//for movement
	String name, currentAttack;
	int[] aggro = {0,0,0,0};
	Player target;
	boolean alreadyAttacking = false,moving,waiting;
	MainFrame.TruePlayer human_player;
	Image[] sprites = new Image[2];
	ArrayList<Image> attackSprites = new ArrayList<Image>();
	ArrayList<String> attackTypes = new ArrayList<String>();
	Rectangle rect;
	Image sprite;
	
	
	public Enemy(String name, MainFrame.TruePlayer p){
		//since this is an external class TruePlayer has to be imported in
		human_player = p;
		this.name = name;
		importStats(name);
		sprite = null;
		moving = false;
		updateRect();
		waiting = false;
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
			x = 100;
			y = 100;
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
	
	public void takeDamage(int damage, Player p){
		if(damage - getDef()>0){
			current_hp -= damage - getDef();
		}
		updateAggro(p,damage);
	}
	
	//AI targetting system
	public void setAggro(int[] a){
		aggro = a;
	}
	
	public void updateAggro(Player p, int dmg){
  		ArrayList<Player> team = human_player.getTeam();
  		int pos = team.indexOf(p);
  		aggro[pos] += dmg;
  		
  		ArrayList<Player> players = human_player.getTeam();
  		if(!p.getAlive()){
   			pos = players.indexOf(p);
  			aggro[pos] = 0;
 		}
	}
	
	public double dist(double x, double y, double x2, double y2){
		return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
	}
	public void updateTarget(){
		int posOfGreatest, aggroNum = 0;
		boolean aggroActive = false;
		ArrayList<Player> players = human_player.getTeam();
		
		//updates aggro towards players - the aggro goes down by 25% each time
		for(int i = 0; i < 4; i++){
			aggro[i] = (int)(aggro[i]*0.75);
		}
		
		//checks and figures out who has the greatest aggro
		for(int i = 0; i < 4; i++){
			if(aggro[i] > aggroNum){	
				if(players.get(i).getAlive()){
					aggroActive = true;
					posOfGreatest = i;
					aggroNum = aggro[i];
					target = players.get(posOfGreatest); //if one of the players has a greater aggro than the others, then the new target becomes that player
				}
			}
		}
		
		int smallestDist = Integer.MAX_VALUE;
		
		
		
		//otherwise, if theyre all somehow the same, then the target becomes the
		//person to the enemy.
		if(!aggroActive){
			for(Player p:players){
				if(p!=null){
					if(p.getAlive()){
						if(dist(p.getX(),p.getY(),x,y) < smallestDist){
							smallestDist = (int)(dist(p.getX(),p.getY(),x,y));
							target = p;
						}
					}
				}
			}
		}
	}
	
	//read attacks
	public void readAttacks(String s){
		System.out.println(s);
		System.out.println(target.getName());
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
		String[] atk = s.split("\\.");
		int damage, numProj, pos;
		damage = Integer.parseInt(atk[1]);
		if(!atk[2].equals("")){
			pos = Integer.parseInt(atk[2]);
		}
		if(atk[0].equals("M")){
			basic_range = 100;
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
			if((int)(dist(target.getMidX(),target.getMidY(),x,y)) <= basic_range){
				return true;
			}
		}
		return false;
	}
	
	//attacks
	public void attack(){
		if (!waiting){
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
		else{
			count-=1;
			if (count == 0){
				waiting = false;
			}
		}
	}
	public void meleeAttack(int damage){
		target.takeDamage(damage);
		alreadyAttacking = false;
		waiting = true;
		count = 25;
	}
	public void singleShot(int damage,Image pSprite, int projectileSpeed){
		human_player.getEnemyProjectiles().add(new Projectile(x,y,(int)target.getX(),(int)target.getY(),projectileSpeed,damage,pSprite));
		alreadyAttacking = false;
		waiting = true;
		count = 50;
	}
	public void multiShot(int numProj, int damage,Image pSprite, int projectileSpeed){
		double angle = getAngle(target.getX(),target.getY());
		for(int i=0;i<numProj;i++){
			human_player.getEnemyProjectiles().add(new Projectile(x,y, 1/3*angle+i*Math.PI/numProj,projectileSpeed,damage,pSprite));
		}
		alreadyAttacking = false;
		waiting = true;
		count = 50;		
	}
	public void allWayShot(int numProj, int damage,Image pSprite, int projectileSpeed){
		double angle = getAngle(target.getX(),target.getY());
		for(int i=0;i<numProj;i++){
			human_player.getEnemyProjectiles().add(new Projectile(x,y, 2*i*Math.PI/numProj,projectileSpeed,damage,pSprite));
		}
		alreadyAttacking = false;
		waiting = true;
		count = 50;
	}
	public double getAngle(double mx,double my){
		double a;
		int d;
		if (mx > x){
			d = 10;
		}
		else if(mx < x){         
			d = 20;
		}
		else{
			d = 0;
		}
		
		if (my > y){ 
			d += 1;
		}
		else if (my < y){ 
			d += 2;
		}			
		if(d>=10){
			a = Math.atan((my-y)/(mx-x));
		
			if (d == 21){
				a = a - Math.PI;
			}
			else if (d == 22){
				a = Math.PI + a;
			}
			else if (d == 20){
				a+= Math.PI;
			}
		}
		else{
			a = 0;
		}
		return a;
	}
	
	//movement
	
	public int getMoveSpeed(){
		return move_speed;
	}
	public double getX(){
		return x-width/20;
	}
	public double getY(){
		return y-height/20;
	}
	public double getMidX(){
		return x;
	}
	public double getMidY(){
		return y;
	}
	public void updateRect(){
		rect = new Rectangle((int)x-width/20,(int)y-height/20,width,height);
	}
	public Rectangle projectedMoveRect(){
		return new Rectangle((int)(x + move_speed*Math.cos(angle)-width/20),(int)(y + move_speed*Math.sin(angle)-height/20),width,height);
	} 
	
	public Rectangle getRect(){
		return rect;
	}
	public void move(){
		if (target.getX() > x){
			direction = 10;
		}
		else if(target.getX() < x){         
			direction = 20;
		}
		else{
			direction = 0;
		}
		
		if (target.getY() > y){ 
			direction += 1;
		}
		else if (target.getY() < y){ 
			direction += 2;
		}			
		if(direction!=0){
			angle = Math.atan((target.getY()-y)/(target.getX()-x));
		
			if (direction == 21){
				angle = angle - Math.PI;
			}
			else if (direction == 22){
				angle = Math.PI + angle;
			}
		}
		else{
			angle = 0;
		}
		setX(x + move_speed*Math.cos(angle));
		setY(y + move_speed*Math.sin(angle));
		
		if (x>932){
			x = 932;
		}
		else if (x<20){
			x = 20;
		}
		else if (y<20){
			y = 20;
		}
		else if (y>700){
			y = 700;
		}
		updateRect();
	}
	
	public void setSprite(){	
		if (direction!=0){
		
			if (direction/10 < 2){
				sprite = sprites[1];
			}else{
				sprite = sprites[0];
			}
		
		}else{
			sprite = sprites[1];
		}
	}
	
	public Image getSprite(){
		if (sprite!=null){
			return sprite;
		}else{
			return null;
		}
	}
	
	public void setX(double x){
		this.x = x;
	}
	public void setY(double x){
		this.y = x;
	}
	public void reset(){
		x=100;
		y=100;
		alreadyAttacking = false;
	}
}