import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import javax.sound.sampled.AudioSystem;

public class Player {
	BuffReader b_reader = new BuffReader();
	Accessory ring1,ring2;
	Armor secondary, main;
	Weapon weap;
	int def,hp,armor,bonus_hp,current_hp,max_mana,current_mana,mana_regen,damage, level, basic_range, bonus_mana, exp, exp_to_level, move_speed;
	int def_level_up, hp_level_up, dmg_level_up, count, direction, bonus_dmg;
	double x,y;
	Rectangle rect;
	double atk_spd, crit_chance, evasion, crit_dmg, bonus_atk_spd, bonus_crit_chance, bonus_evasion, bonus_crit_dmg, dist, angle; 
	String class_type, weight_type, attack_type;
	boolean moving, alive, attacking, hasTarget;
	Image sprite;
	Enemy target;
	Sprite sprites;
	Image pSprite = new ImageIcon("game sprites/enemies/projectiles/Blade.png").getImage();
	AudioClip selectAud;
	MainFrame.TruePlayer human_player;
	
    public Player(String name,Sprite s, MainFrame.TruePlayer human_player){
    	this.human_player = human_player;
		count = 0;
		sprites = s;
		target = null;
		
		loadStats(name);
		calcExpToLevel();
    }
    
    public void loadStats(String file){
    	try{
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(String.format("Players/%s.txt",file))));
			level = Integer.parseInt(inFile.nextLine());
			exp = Integer.parseInt(inFile.nextLine());
			hp = Integer.parseInt(inFile.nextLine());
			def = Integer.parseInt(inFile.nextLine());
			max_mana = Integer.parseInt(inFile.nextLine());
			mana_regen = Integer.parseInt(inFile.nextLine());
			damage = Integer.parseInt(inFile.nextLine());
			atk_spd = Double.parseDouble(inFile.nextLine());
			crit_chance = Double.parseDouble(inFile.nextLine());
			evasion = Double.parseDouble(inFile.nextLine());
			crit_dmg = Double.parseDouble(inFile.nextLine());
			class_type = inFile.nextLine();
			weight_type = inFile.nextLine();
			attack_type = inFile.nextLine();
			hp_level_up = Integer.parseInt(inFile.nextLine());
			def_level_up = Integer.parseInt(inFile.nextLine());
			dmg_level_up = Integer.parseInt(inFile.nextLine());
			basic_range = Integer.parseInt(inFile.nextLine());
			move_speed = Integer.parseInt(inFile.nextLine());
			selectAud = Applet.newAudioClip(getClass().getResource("Audio/"+file+"_select.wav"));
			
			bonus_mana = 0;
			bonus_hp = 0;
			bonus_dmg = 0;
			bonus_crit_dmg = 0;
	    	bonus_atk_spd = 0;
	    	bonus_crit_chance = 0;
	    	bonus_evasion = 0;
	    	main = null;
	    	secondary = null;
	    	weap = null;
	    	ring1 = null;
	    	ring2 = null;
	    	
	    	hasTarget = false;
	    	alive = true;
		}
		catch(IOException except){
			System.out.println("Error in Player.loadStats()");
		}
    }
    
    public void saveStats(){
		try{
			PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter(String.format("Players/%s.txt",class_type))));
			outFile.println(Integer.toString(level));
			outFile.println(Integer.toString(exp));
			outFile.println(Integer.toString(hp));
			outFile.println(Integer.toString(def));
			outFile.println(Integer.toString(max_mana));
			outFile.println(Integer.toString(mana_regen));
			outFile.println(Integer.toString(damage));
			outFile.println(Double.toString(atk_spd));
			outFile.println(Double.toString(crit_chance));
			outFile.println(Double.toString(evasion));
			outFile.println(Double.toString(crit_dmg));
			outFile.println(class_type);
			outFile.println(weight_type);
			outFile.println(attack_type);
			outFile.println(Integer.toString(hp_level_up));
			outFile.println(Integer.toString(def_level_up));
			outFile.println(Integer.toString(dmg_level_up));
			outFile.println(Integer.toString(basic_range));
			outFile.println(Integer.toString(move_speed));
			
			outFile.close();
		}
		catch(IOException except){
			System.out.println("Error in Player.saveStats()");
		}
	}
	
    public void printStats(){
    	System.out.println("hp "+hp+String.format("[%d]",bonus_hp));
    	System.out.println("def "+def+String.format("[%d]",armor));
    	System.out.println("dmg "+damage+String.format("[%d]",getWeaponDmg()));
    	System.out.println("critchance "+crit_chance+String.format("[%.3f]",bonus_crit_chance));
    	System.out.println("critdmg "+crit_dmg+String.format("[%.3f]",bonus_crit_dmg));
    	System.out.println("atkspd "+atk_spd+String.format("[%.3f]",bonus_atk_spd));
    	System.out.println("evasion "+evasion+String.format("[%.3f]",bonus_evasion));
    }
    //Partial Getter Functions
    public String getName(){
    	return class_type;
    }
    
    public int getRange(){
    	return basic_range;
    }
    
    public String getWeight(){
    	return weight_type;
    }
    public String getAttackType(){
    	return attack_type;
    }
    public void changeBonusHp(int bh){
    	bonus_hp += bh;
    }
    public void changeBonusMana(int m){
    	bonus_mana = m;
    }
    public void changeArmor(int al){
    	armor += al;
    }
    public void changeBonusAtkSpd(double as){
    	bonus_atk_spd += as;
    }
    public void changeBonusEvasion(double e){
    	bonus_evasion += e;
    }
    public void changeBonusCritChance(double cc){
    	bonus_crit_chance += cc;
    }
    public void changeBonusCritDmg(double cd){
    	bonus_crit_dmg += cd;
    }
    public int getHp(){
    	return hp;
    }
    public int getMana(){
    	return max_mana;
    }
    public int getDef(){
    	return def;
    }
    public int getDamage(){
    	return damage;
    }
    public double getAtkSpd(){
    	return atk_spd;
    }
    public double getCritChance(){
    	return crit_chance;
    }
    public double getEvasion(){
    	return evasion;
    }
    public double getCritDmg(){
    	return crit_dmg;
    }
    public int getBonusHp(){
    	return bonus_hp;
    }
    public int getBonusMana(){
    	return bonus_mana;
    }
    public void changeBonusDmg(int dmg){
    	bonus_dmg += dmg;
    }
    public int getWeaponDmg(){
    	if(weap != null){
    		return weap.getDamage();
    	}
    	return 0;
    }
    public int getArmor(){
    	int total = 0;
    	if(main != null){
    		total += main.getArmorLevel();
    	}
    	if(secondary != null){
    		total += secondary.getArmorLevel();
    	}
    	return total;
    }
    public double getBonusCritChance(){
    	return bonus_crit_chance;
    }
    public double getBonusCritDmg(){
    	return bonus_crit_dmg;
    }
    public double getBonusEvasion(){
    	return bonus_evasion;
    }
    public double getBonusAtkSpd(){
    	return bonus_atk_spd;
    }
    public int getMoveSpeed(){
    	return move_speed;
    }
    
    //True Getter Methods
    public int getCurrentHp(){
    	return current_hp;
    }
    public void setCurrentHp(int h){
    	current_hp = h;
    }
    public int getTrueMaxHp(){
    	return hp+getBonusHp();
    }
    public int getCurrentMana(){
    	return current_mana;
    }
    public void setCurrentMana(int m){
    	current_mana = m;
    }
    public int getTrueMaxMana(){
    	return max_mana+getBonusMana();
    }
    public double getTrueCritChance(){
    	return crit_chance+getBonusCritChance();
    }
    public double getTrueCritDmg(){
    	return crit_dmg+getBonusCritDmg();
    }
    public double getTrueEvasion(){
    	return evasion+getBonusEvasion();
    }
    public double getAttackRange(){
    	return basic_range;
    }
    public int getManaRegen(){
    	return mana_regen;
    }
    public int getTrueDamage(){
     	return damage+getWeaponDmg()+bonus_dmg;
    }
    public int getTrueDef(){
     	return def+getArmor();
    }
    public double getTrueAtkSpd(){
     	return atk_spd+getBonusAtkSpd();
    }
    
    //Equipment Methods
    public Armor getMain(){
    	return main;
    }
    public Armor getSecondary(){
    	return secondary;
    }
    public Weapon getWeap(){
    	return weap;
    }
    public Accessory getRing1(){
    	return ring1;
    }
    public Accessory getRing2(){
    	return ring2;
    }
    public boolean equip(Item i, int ringChoice){
    	if(i.getType().equals("Weapon")){
    		Weapon temp = (Weapon)i;
    		if(temp.getClassType().equals(class_type)){
    			b_reader.equipItem(this,temp);
    			if(weap == null){
    				weap = temp;
    				return true;
    			}
    			else{
    				unequip(weap,0);
    				weap = temp;
    				return true;
    			}
    		}
    		else{
    			return false;
    		}
    	}
    	else if(i.getType().equals("Armor")){
			Armor temp = (Armor)i;
    		if(temp.getClassType().equals(weight_type)){
    			b_reader.equipItem(this,temp);
    			if(temp.getPart().equals("secondary")){
    				if(secondary == null){
    					secondary = temp;
    					return true;
    				}
    				else{
    					unequip(secondary,0);
    					secondary = temp;
    					return true;
    				}
    			}
    			else{
    				if(main == null){
    					main = temp;
    					return true;
    				}
    				else{
    					unequip(main,0);
    					main = temp;
    					return true;
    				}
    			}
    		}
    		else{
    			return false;
    		}
    	}
    	else{
    		Accessory temp = (Accessory)i;
    		b_reader.equipItem(this,temp);
    		if(ringChoice == 1){
	    		if(ring1 == null){
	    			ring1 = temp;
	    			return true;
	    		}
	    		else{
	    			unequip(ring1,1);
	    			ring1 = temp;
	    			return true;
	    		}
    		}
    		else if(ringChoice == 2){
    			if(ring2 == null){
	    			ring2 = temp;
	    			return true;
	    		}
	    		else{
	    			unequip(ring2,2);
	    			ring2 = temp;
	    			return true;
	    		}
    		}
    	}
    	return false;
    }
    public void unequip(Item i, int ringChoice){
    	if(i != null){
    		if(i.getType().equals("Weapon")){
	    		Weapon temp = (Weapon)i;
	    		if(weap == temp){
	    			b_reader.unequipItem(this,temp);
	    			weap = null;
	    		}
	    	}
	    	else if(i.getType().equals("Armor")){
				Armor temp = (Armor)i;
	    		if(temp.getPart().equals("secondary")){
	    			if(secondary == temp){
	    				b_reader.unequipItem(this,temp);
	    				secondary = null;
	    			}
	    		}
	    		else if (temp.getPart().equals("main")){
	    			if(main == temp){
	    				b_reader.unequipItem(this,temp);
	    				main = null;	
	    			}
	    		}
	    	}
	    	else{
	    		Accessory temp = (Accessory)i;
	    		if(ringChoice == 1){
	    			if(ring1 == temp){
		    			b_reader.unequipItem(this,temp);
		    			ring1 = null;
		    		}
	    		}
	    		else if(ringChoice == 2){
	    			if(ring2 == temp){
		    			b_reader.unequipItem(this,temp);
		    			ring2 = null;
		    		}
	    		}	
	    	}
    	}
    }
	
	//Battle Methods
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
	public Rectangle getRect(){
		return rect;
	}
	public boolean getMoving(){
		return moving;
	}
	public void updateRect(){
		rect = new Rectangle((int)x-20,(int)y-20,40,40);
	}
	public void setX(double x){
		this.x=x;
	}
	public void setY(double y){
		this.y=y;
	}
	public void setMoving(boolean flag){
		if (flag==false){
			dist=0;
		}
		moving = flag;
	}
	public void setPos(double x, double y){
		this.x = x;
		this.y = y;
		rect = new Rectangle((int)x-20,(int)y-20,40,40);
	}
	
	public void setDirection(int mx,int my){
		if (mx > x){
			direction = 10;
		}
		else if(mx < x){         
			direction = 20;
		}
		else{
			direction = 0;
		}
		
		if (my > y){ 
			direction += 1;
		}
		else if (my < y){ 
			direction += 2;
		}
		if(direction == 0){
			dist = 0;
		}			
	}
		
	public void setAngle(int mx, int my){
		if(direction!=0){
			angle = Math.atan((my-y)/(mx-x));
		
			if (direction == 21){
				angle = angle - Math.PI;
			}
			else if (direction == 22){
				angle = Math.PI + angle;
			}
		}
	}
	
	public void setMove(int mx, int my){
		dist = Math.pow(Math.pow(mx-x,2) + Math.pow(my-y,2),.5)/move_speed;
		moving = true;
		setDirection(mx,my);
		setAngle(mx,my);
	}
	
	public void move(){
		if(dist >= 1 && moving){
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
			dist -= 1;
		}
		else{
			moving = false;
		}
	}
	
	public void setSprite(){
		Image oldSprite = sprite;
		
		if (direction!=0){
			if (moving==false){
				if (direction/10 < 2){
					if (attacking && hasTarget){
						if (oldSprite==sprites.getAttRight2() && (count/getTrueAtkSpd())%3==0){
							sprite = sprites.getAttRight1();
							doDamage();
						}
						else if ((count/getTrueAtkSpd())%5==0){
							sprite = sprites.getAttRight2();
						}
					}
					else{
						sprite = sprites.getIdleRight();
					}
				}else{
					if (attacking && hasTarget){
						if (oldSprite==sprites.getAttLeft2() && (count/getTrueAtkSpd())%3==0){
							sprite = sprites.getAttLeft1();
							doDamage();
						}
						else if ((count/getTrueAtkSpd())%5==0){
							sprite = sprites.getAttLeft2();
						}
					}
					else{
						sprite = sprites.getIdleLeft();
					}
				}
			}else{
				if (count%3==0){
					if (direction/10 < 2){
						if (oldSprite == sprites.getWalkRight()){
							sprite = sprites.getIdleRight();
						}
						else{
							sprite = sprites.getWalkRight();	
						} 
					}
					else{
						if (oldSprite == sprites.getWalkLeft()){
							sprite = sprites.getIdleLeft();
						}
						else{
							sprite = sprites.getWalkLeft();	
						}
					}
				}
			}
		}else{
			sprite = sprites.getIdleRight();
		}
		count++;
		if (count == 99){
			count = 0;
		}
	}
	
	public Image getSprite(){
		if (sprite!=null){
			return sprite;
		}else{
			return null;
		}
	}
	
	public void resetDD(){
		dist = 0;
		direction = 0;
		angle = 0;
	}
	
	public void doDamage(){
		if(attack_type.equals("melee")){
			target.takeDamage(damage,this);
		}
		else{
			human_player.getPlayerProjectiles().add(new Projectile(x,y, (int)target.getMidX(),(int)target.getMidY(),10,damage,pSprite,this,false));
		}
	}
	
	public Rectangle projectedMoveRect(){
		return new Rectangle((int)(x + move_speed*Math.cos(angle)-20),(int)(y + move_speed*Math.sin(angle)-20),40,40);
	} 
	
	//Stat Methods
	public int getLevel(){
    	return level;
    }
    public int getExp(){
    	if(level == 20){	//resets exp everytime to ensure that player doesnt over level
    		exp = 0;
    	}
    	return exp;
    }
    public boolean setExp(int e){
    	exp = e;
    	if(exp >= exp_to_level){
    		exp = exp - exp_to_level;
    		calcExpToLevel();
    		levelUp();
    		return true;
    	}
    	return false;
    }
    public int getExpToLevel(){
    	return exp_to_level;
    }
    public void calcExpToLevel(){
    	int[] increments = {18,28,38,48,58,68,78,88,98,108,118,128,138,148,158,168,178,188,198};
    	//note that all increments are in 1000s because of *100
    	exp_to_level = increments[level-1] * 100;
    }
	public boolean getAlive(){
    	return alive;
    }
    public void updateAlive(){
    	if(current_hp <= 0){
    		alive = false;
    	}
    }
	public void levelUp(){
		hp += hp_level_up;
		def += def_level_up;
		damage += dmg_level_up;
		level += 1;
	}
	public void recoverMana(){
		current_mana += mana_regen;
		if(current_mana > getTrueMaxMana()){
			current_mana = getTrueMaxMana();
		}
	}
	public void recoverMana(int m){
		current_mana += m;
		if(current_mana > getTrueMaxMana()){
			current_mana = getTrueMaxMana();
		}
	}
	public void resetPlayerStats(){
		current_mana = getTrueMaxMana();
		current_hp = getTrueMaxHp();
		alive = true;
	}
	public void heal(int h){
		if(current_hp + h > getTrueMaxHp()){
			current_hp = getTrueMaxHp();
		}
		else{
			current_hp += h;
		}
	}
	public int calcDamage(int dmg, int def){
		return (dmg/def)*15 - (def/100);
	}
	public void takeDamage(int dmg){
		if(Math.random() > getTrueEvasion()){
			if(calcDamage(dmg,getTrueDef()) > 0){
				current_hp -= calcDamage(dmg,getTrueDef());
				human_player.getHitEfx().add(new HitEffect(getX()-5,getY()-5,"blood",15));
				human_player.getHitNums().add(new HitNumber(getMidX(),getY(),Integer.toString(calcDamage(dmg,getTrueDef())),50));
			}
			else{
				human_player.getHitNums().add(new HitNumber(getMidX(),getY(),"Blocked",50));
			}
		}
		else{
			human_player.getHitEfx().add(new HitEffect(getMidX(),getY(),"Dodged",15));
		}
		
		if(current_hp <= 0){
			alive = false;
		}
	}
	public AudioClip getSelectAudio(){
		return selectAud;
	}
	
	public Enemy getTarget(){
		return target;
	}
	
	public void setTarget(Enemy e){
		target = e;
		hasTarget = true;
	}
	
	public void setAttacking(Boolean b){
		attacking = b;
	}
	
	public void checkAttacking(Enemy e){
		if (checkAttackRange(e)){
			attacking = true;
		}else{
			attacking = false;
		}
	}
	
	public boolean checkAttackRange(Enemy e){
		int d = (int)Math.sqrt(Math.pow((x-e.getMidX()),2) + Math.pow((y-e.getMidY()),2));
		
		if (d<(basic_range + (int)Math.sqrt(2*(Math.pow(e.width/2,2))))){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasTarget(){
		return hasTarget;
	}
	
	public void setHasTarget(boolean flag){
		hasTarget = flag;
	}
	
	public double dist(double x, double y, double x2, double y2){
		return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
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
	
	//this is the basic format - you dont need to really add anything unless one of the moves require
	//something special (most probably wont);
	static class Knight extends Player{
		boolean AQActive,AWActive,AEActive,ARActive;
		//note that not necessarily all of these booleans will be used. This is just
		//to ensure that all the classes are similar.
		int defBonusQ, defBonusR;
		long counterAR;
		long[] timers = {-60000,-60000,-60000,-60000};
		MainFrame.TruePlayer human_player;
		
		public Knight(MainFrame.TruePlayer p){
			super("Knight",new Sprite("Knight"),p);
			human_player = p;
			AQActive = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			counterAR = 0;
			defBonusQ = 0;
			defBonusR = 0;
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}

		public void updateGeneral(){
			//call this each time the timer goes down, since the attack
			//lasts for 4 seconds.
			if(ARActive){
				for(Enemy e:human_player.getEnemiesOnScreen()){
					if(dist(getMidX(),getMidY(),e.getMidX(),e.getMidY()) < 100){
						e.takeDamage((int)(getTrueDamage()*1.5),this);
					}
				}
				if(System.currentTimeMillis() - timers[3] >= 4000){
					ARActive = false;
					changeArmor(-defBonusR);
					timers[3] = System.currentTimeMillis();
				}
			}
		}
		public void updateMana(){
			//call updateMana() once every second
			if(AQActive){
				if(getCurrentMana() >= 8){
					setCurrentMana(getCurrentMana() - 8);
				}
				else if(getCurrentMana() < 8){
					AQ(0,0);
					//by forcefully calling AQ() again while it's still
					//active will automatically cause it to switch off.
				}
			}
			recoverMana();
		}
		public void AQ(int mx,int my){
			if(!AQActive && System.currentTimeMillis() - timers[0] >= 3000){
				defBonusQ += (int)(getTrueDef()*0.75);
				changeArmor(defBonusQ);
				AQActive = true;
				timers[3] = System.currentTimeMillis();
			}
			else if(AQActive){
				changeArmor(-defBonusQ);
				AQActive = false;				
			}
		}
		public void AW(int mx,int my){
			if(getCurrentMana() >= 20 && System.currentTimeMillis() - timers[1] >= 15000){
				if(target != null){
					target.takeDamage((int)(getTrueDamage()*2.5),this);
				}
				setCurrentMana(getCurrentMana()-20);
				timers[1] = System.currentTimeMillis();
			}
		}
		public void AE(int mx,int my){
			if(getCurrentMana() >= 25 && System.currentTimeMillis() - timers[2] >= 20000){
				int[] temp = {-60000,-60000,-60000,-60000};
				for(int i = 0; i < 4; i++){
					if(human_player.getTeam().get(i) == this){
						temp[i] = 500;
					}
				}
				for(Enemy e:human_player.getEnemiesOnScreen()){
					e.setAggro(temp);
				}
				setCurrentMana(getCurrentMana()-25);
				timers[2] = System.currentTimeMillis();
			}
		}
		public void AR(int mx,int my){
			if(getCurrentMana() >= 60 && System.currentTimeMillis() - timers[3] >= 50000){
				ARActive = true;
				defBonusR = (int)(getTrueDef()*0.4);
				changeArmor(defBonusR);
				setCurrentMana(getCurrentMana()-60);
				timers[3] = System.currentTimeMillis();
			}
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = -60000;
			}
		}
	}
	
	static class Archer extends Player{
		boolean AQActive,AWActive,AEActive,ARActive;
		MainFrame.TruePlayer human_player;
		Image projectileQ = new ImageIcon("game sprites/enemies/projectiles/Black Arrow.png").getImage();
		Image projectileE = new ImageIcon("game sprites/enemies/projectiles/White Bolt.png").getImage();
		long[] timers = {-60000,-60000,-60000,-60000};
		int atkspdBonusW, counterAR;
		Point targetR;
		
		public Archer(MainFrame.TruePlayer p){
			super("Archer",new Sprite("Archer"),p);
			human_player = p;
			AQActive = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			atkspdBonusW = 0;
			counterAR = 0;
			targetR = new Point(0,0);
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}
		public void updateGeneral(){
			if(ARActive){
				for(Enemy e:human_player.getEnemiesOnScreen()){
					if(dist((double)targetR.getX(),(double)targetR.getY(),e.getMidX(),e.getMidY()) < 125){
						e.takeDamage((int)(getTrueDamage()*2),this);
					}
				}
				if(System.currentTimeMillis() - timers[3] >= 7000){
					ARActive = false;
				}
			}
		}
		public void updateMana(){
			if(AWActive){
				if(getCurrentMana() >= 9){
					setCurrentMana(getCurrentMana() - 9);
				}
				else if(getCurrentMana() < 9){
					AW(0,0);
				}
			}
			recoverMana();
		}
		public void AQ(int mx, int my){
			if(getCurrentMana() >= 15 && System.currentTimeMillis() - timers[0] >= 15000){
				double angle = getAngle((double)mx,(double)my);
				double dmg = (double)getTrueDamage()*2.5;
				human_player.getPlayerProjectiles().add(new Projectile(getX(),getY(),angle-Math.PI/9,10,dmg,projectileQ,this,false));
				human_player.getPlayerProjectiles().add(new Projectile(getX(),getY(),angle,10,dmg,projectileQ,this,false));
				human_player.getPlayerProjectiles().add(new Projectile(getX(),getY(),angle+Math.PI/9,10,dmg,projectileQ,this,false));
				setCurrentMana(getCurrentMana()-15);
				timers[0] = System.currentTimeMillis();
			}
		}
		public void AW(int mx, int my){
			if(!AWActive && System.currentTimeMillis() - timers[1] >= 3000){
				atkspdBonusW += (int)(getTrueAtkSpd());
				changeBonusAtkSpd(atkspdBonusW);
				AWActive = true;
				timers[1] = System.currentTimeMillis();
			}
			else if(AWActive){
				changeBonusAtkSpd(-atkspdBonusW);
				AWActive = false;				
			}
		}
		public void AE(int mx, int my){
			if(getCurrentMana() >= 15 && System.currentTimeMillis() - timers[2] >= 15000){
				double angle = getAngle((double)mx,(double)my);
				double dmg = (double)getTrueDamage()*3;
				human_player.getPlayerProjectiles().add(new Projectile(getX(),getY(),angle-Math.PI/9,10,dmg,projectileQ,this,true));
				setCurrentMana(getCurrentMana()-15);
				timers[2] = System.currentTimeMillis();
			}
		}
		public void AR(int mx, int my){
			if(getCurrentMana() >= 80 && System.currentTimeMillis() - timers[3] >= 45000){
				ARActive = true;
				targetR = new Point(mx,my);
				setCurrentMana(getCurrentMana()-80);
				timers[3] = System.currentTimeMillis();
			}
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = -60000;
			}
		}
	}
	
	static class Wizard extends Player{
		boolean AQActive,AWActive,AEActive,ARActive;
		MainFrame.TruePlayer human_player;
		Image projectileQ = new ImageIcon("game sprites/enemies/projectiles/Blue Mystic Shot.png").getImage();
		long[] timers = {-60000,-60000,-60000,-60000};
		int counterAR;
		
		public Wizard(MainFrame.TruePlayer p){
			super("Wizard",new Sprite("Wizard"),p);
			human_player = p;
			AQActive = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			counterAR = 0;
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}
		public void updateGeneral(){
			if(ARActive){
				for(Enemy e:human_player.getEnemiesOnScreen()){
					if(counterAR%1000 == 0){
						e.takeTrueDamage((int)(getTrueDamage()*1.5),this);
					}
				}
				//this attack lasts for 5 seconds
				counterAR --;
				if(counterAR <= 0){
					ARActive = false;
				}
			}
		}
		public void updateMana(){
			if(AEActive){
				recoverMana(20);
			}
			recoverMana();
		}
		public void AQ(int mx, int my){
			if(getCurrentMana() >= 20 && timers[0] == 0){
				if(target!=null){
					double angle = getAngle((double)x,(double)y);
					double dmg = (double)getTrueDamage()*2.5;
					human_player.getPlayerProjectiles().add(new Projectile(getX(),getY(),angle,10,dmg,projectileQ,this,false));
					setCurrentMana(getCurrentMana()-20);
				}
			}
		}
		public void AW(int mx, int my){
			if(getCurrentMana() >= 20 && timers[1] == 0){
				if(target!=null){
					int dmg = (int)((double)getTrueDamage()*1.75);
					target.takeTrueDamage(dmg,this);
					if(target.getCurrentHp() <= 0){
						for(Enemy e:human_player.getEnemiesOnScreen()){
							if(dist(target.getX(),target.getY(),e.getX(),e.getY()) < 30){
								e.takeTrueDamage((int)(getTrueDamage()*2.5),this);
							}		
						}
					}
					setCurrentMana(getCurrentMana()-20);
				}
			}
		}
		public void AE(int mx, int my){
			if(!AEActive && timers[2] == 0){
				ARActive = true;
			}
			else if(AEActive){
				AEActive = false;
			}
		}
		public void AR(int mx, int my){
			if(getCurrentMana() >= 100 && timers[3] == 0){
				counterAR = 5000;
				ARActive = true;
				setCurrentMana(getCurrentMana()-100);
			}
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = 0;
			}
		}
	}
	
	static class Priest extends Player{
		boolean AQActive,AQActive2,AWActive,AEActive,ARActive;
		MainFrame.TruePlayer human_player;
		long[] timers = {-60000,-60000,-60000,-60000};
		Player friendly_target, old_target;
		int defBonusQ, counterAQ, counterAR;
		int[] defBonusR = {-60000,-60000,-60000,-60000};
		double[] atkspdBonusR = {-60000,-60000,-60000,-60000};
		
		public Priest(MainFrame.TruePlayer p){
			super("Priest",new Sprite("Priest"),p);
			human_player = p;
			AQActive = false;
			AQActive2 = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			defBonusQ = 0;
			counterAQ = 0;
			counterAR = 0;
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAQActive2(){
			return AQActive2;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}
		public void updateFriendlyTarget(Player p){
			friendly_target = p;
		}
		public void resetFriendlyTarget(){
			friendly_target = null;
		}
		public void updateGeneral(){
			if(AQActive){
				counterAQ--;
				if(counterAQ == 0){
					AEActive = false;
					old_target.changeArmor(-defBonusQ);
				}
			}			//Over here the queue for the heal/buffs is done
			if(friendly_target != null){
				if(AQActive2){
					int healHp = (int)((double)friendly_target.getTrueMaxHp()*0.4);
					friendly_target.heal(healHp);
					defBonusQ = (int)((double)friendly_target.getTrueDef()*0.25);
					old_target = friendly_target;
					old_target.changeArmor(defBonusQ);
					counterAQ = 3000;
					AQActive = true;
					AQActive2 = false;
				}
				if(AWActive){
					int recoverManaAmount = (int)((double)friendly_target.getTrueMaxMana()*0.5);
					friendly_target.recoverMana(recoverManaAmount);
					AWActive = false;
				}
				if(AEActive){
					if(friendly_target.getName().equals("Knight")){
						Knight knight = (Knight)friendly_target;
						knight.resetCooldowns();
					}
					else if(friendly_target.getName().equals("Archer")){
						Archer archer = (Archer)friendly_target;
						archer.resetCooldowns();
					}
					else if(friendly_target.getName().equals("Wizard")){
						Wizard wizard = (Wizard)friendly_target;
						wizard.resetCooldowns();
					}
					else if(friendly_target.getName().equals("Priest")){
						Priest priest = (Priest)friendly_target;
						priest.resetCooldowns();
					}
					else if(friendly_target.getName().equals("Paladin")){
						Paladin paladin = (Paladin)friendly_target;
						paladin.resetCooldowns();
					}
					else if(friendly_target.getName().equals("Assassin")){
						Assassin assassin = (Assassin)friendly_target;
						assassin.resetCooldowns();
					}
				}
				resetFriendlyTarget();
			}
		}
		public void updateMana(){
			recoverMana();
		}
		public void AQ(int mx, int my){
			if(getCurrentMana() >= 35 && timers[0] == 0){
				AQActive2 = true;
				setCurrentMana(getCurrentMana()-35);
			}
		}
		public void AW(int mx, int my){
			if(timers[1] == 0){
				AWActive = true;
			}
		}
		public void AE(int mx, int my){
			if(getCurrentMana() >= 35 && timers[2] == 0){
				AEActive = true;
				setCurrentMana(getCurrentMana()-35);
			}
		}
		public void AR(int mx, int my){
			if(getCurrentMana() > 125 && timers[3] == 0){
				for(int i = 0; i < 4; i++){
					if(human_player.getTeam().get(i) != null){
						int healHp = (int)((double)human_player.getTeam().get(i).getTrueMaxHp()*0.5);
						human_player.getTeam().get(i).heal(healHp);
						int defBonusTmp = (int)((double)human_player.getTeam().get(i).getTrueMaxHp()*0.25);
						defBonusR[i] = defBonusTmp;
						double atkspdBonusTmp = human_player.getTeam().get(i).getTrueAtkSpd()*0.25;
						atkspdBonusR[i] = atkspdBonusTmp;
						counterAR = 10000;
						ARActive = true;
					}
				}
				setCurrentMana(getCurrentMana()-125);
			}
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = 0;
			}
		}
	}
	
	static class Paladin extends Player{
		boolean AQActive,AWActive,AEActive,ARActive;
		MainFrame.TruePlayer human_player;
		long[] timers = {-60000,-60000,-60000,-60000};
		int damageCounter, defBonusW, atkBonusW, counterAQ,counterAW,counterAE,counterAR;
		
		public Paladin(MainFrame.TruePlayer p){
			super("Paladin",new Sprite("Paladin"),p);
			human_player = p;
			AQActive = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			damageCounter = 0;
			defBonusW = 0;
			atkBonusW = 0;
			counterAQ = 0;
			counterAW = 0;
			counterAE = 0;
			counterAR = 0;
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}
		public void updateGeneral(){
			if(AQActive){
				counterAQ--;
				if(counterAQ%1000 == 0){
					int dmg = (int)((double)getTrueDamage()*0.25);
					target.takeDamage(dmg,this);
				}
				if(counterAE == 0){
					AQActive = false;
				}
			}
			if(AWActive){
				counterAW--;
				if(counterAW == 0){
					changeArmor(-defBonusW);
					changeBonusDmg(-atkBonusW);
					AWActive = false;
				}
			}
			if(AEActive){
				if(counterAE%1000 == 0){
					for(Enemy e: human_player.getEnemiesOnScreen()){
						if(dist(getMidX(),getMidY(),e.getMidX(),e.getMidY()) < 175){
							int dmg = (int)((double)getTrueDamage()*0.3);
							e.takeDamage(dmg,this);
						}
					}
					for(Player p: human_player.getTeam()){
						if(p != null){
							if(dist(getMidX(),getMidY(),p.getMidX(),p.getMidY()) < 175){
								int healHp = (int)((double)p.getTrueMaxHp()*0.02);
								p.heal(healHp);
							}
						}
					}
				}
			}
			if(ARActive){
				counterAR--;
				if(counterAR == 0){
					for(Enemy e: human_player.getEnemiesOnScreen()){
						e.takeDamage(damageCounter*3,this);
					}
					ARActive = false;
				}
			}
		}
		public void updateMana(){
			if(AEActive){
				if(getCurrentMana() >= 15){
					setCurrentMana(getCurrentMana() - 15);
				}
				else if(getCurrentMana() < 15){
					AE(0,0);
				}
			}
			recoverMana();
		}
		public void AQ(int mx, int my){
			if(getCurrentMana() > 20 && timers[0] == 0){
				if(target != null){
					int dmg = (int)((double)getTrueDamage()*1.5);
					AQActive = true;
					counterAQ = 3000;
					target.takeDamage(dmg,this);
					setCurrentMana(getCurrentMana()-20);
				}
			}
		}
		public void AW(int mx, int my){
			if(getCurrentMana() >= 20 && timers[1] == 0){
				if(!AWActive){
					AWActive = true;
					defBonusW = (int)((double)getTrueDef()*0.5);
					atkBonusW = (int)((double)getTrueDamage()*0.25);
					changeArmor(defBonusW);
					changeBonusDmg(atkBonusW);
					counterAW = 10000;
					setCurrentMana(getCurrentMana()-20);
				}
			}
		}
		public void AE(int mx, int my){
			if(getCurrentMana() >= 15 && timers[2] == 0){
				if(!AEActive){
					AEActive = true;
				}
				else if(AEActive){
					AEActive = false;
					counterAE = 0;
				}
			}
		}
		public void AR(int mx, int my){
			if(getCurrentMana() >= 80 && timers[3] == 0){
				ARActive = true;
				counterAR = 10000;
				damageCounter = 0;
				setCurrentMana(getCurrentMana()-80);
			}
		}
		public void addDamage(int dmg){
			damageCounter += dmg;
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = 0;
			}
		}
	}
	
	static class Assassin extends Player{
		boolean AQActive,AWActive,AEActive,ARActive;
		MainFrame.TruePlayer human_player;
		long[] timers = {-60000,-60000,-60000,-60000};
		int atkBonusE, atkspdBonusR, critdmgBonusR, critchanceBonusR, counterAR;
		
		public Assassin(MainFrame.TruePlayer p){
			super("Assassin",new Sprite("Assassin"),p);
			human_player = p;
			AQActive = false;
			AWActive = false;
			AEActive = false;
			ARActive = false;
			atkBonusE = 0;
			atkspdBonusR = 0;
			critdmgBonusR = 0;
			critchanceBonusR = 0;
			counterAR = 0;
		}
		public boolean getAQActive(){
			return AQActive;
		}
		public boolean getAWActive(){
			return AWActive;
		}
		public boolean getAEActive(){
			return AEActive;
		}
		public boolean getARActive(){
			return ARActive;
		}
		public void updateGeneral(){
			if(ARActive){
				counterAR--;
				if(counterAR == 0){
					ARActive = false;
					changeBonusAtkSpd(-atkspdBonusR);
					changeBonusCritDmg(-critdmgBonusR);
					changeBonusCritChance(-critchanceBonusR);
				}
			}
		}
		public void updateMana(){
			if(AEActive){
				if(getCurrentMana() >= 7){
					setCurrentMana(getCurrentMana()-7);
				}
				else{
					AE(0,0);
				}
			}
			recoverMana();
		}
		public void AQ(int mx, int my){
			if(getCurrentMana() >= 30 && timers[0] == 0){
				if(target!=null){
					if(dist(target.getMidX(),target.getMidY(),getMidX(),getMidY()) < 100){
						target.takeTrueDamage((int)(getTrueDamage()*2.5),this);
					}
				}
				setCurrentMana(getCurrentMana()-30);
			}
		}
		public void AW(int mx, int my){
			if(getCurrentMana() >= 30 && timers[1] == 0){
				if(target!=null){
					if(target.getX()-x > 0){
						setX(target.getX()-40);
						setY(target.getY());
					}
					else{
						setX(target.getX()+40);
						setY(target.getY());
					}
					int dmg = (int)(getTrueDamage()*(getTrueCritDmg()*1.5));
					target.takeDamage(dmg,this);
				}
				setCurrentMana(getCurrentMana()-30);
			}	
		}
		public void AE(int mx, int my){
			if(!AEActive){
				atkBonusE = (int)((double)getTrueDamage()*0.45);
				changeBonusDmg(atkBonusE);
				AEActive = true;
			}
			else if(AEActive){
				AEActive = false;
				changeBonusDmg(-atkBonusE);
			}
		}
		public void AR(int mx, int my){
			if(getCurrentMana() >= 80 && timers[3] == 0){
				ARActive = true;
				double atkspdBonusR = getTrueAtkSpd()*0.75;
				double critdmgBonusR = getTrueCritDmg()*0.5;
				double critchanceBonusR = getTrueCritChance()*100;
				changeBonusAtkSpd(atkspdBonusR);
				changeBonusCritDmg(critdmgBonusR);
				changeBonusCritChance(critchanceBonusR);
				counterAR = 5000;
				setCurrentMana(getCurrentMana()-80);
			}
		}
		public void resetCooldowns(){
			//for priest
			for(int i = 0; i < 4; i++){
				timers[i] = 0;
			}
		}
	}
}