import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.*;


public class MainFrame extends JFrame implements ActionListener{
	MenuPanel menu;
	GamePanel game; 
	BattlePanel battle;
	
	ShopPanel shop;
	InvenPanel inven;
	TeamPanel team;
	SkillsPanel skills;
	
	String currentScreen;
	
	javax.swing.Timer myTimer;

	public MainFrame(){
		super("Dawn of Legends");
		
		setSize(960,755);
		setLayout(null);
		
		menu = new MenuPanel();
		menu.setSize(960,755);
		menu.setLocation(0,0);
		add(menu);

		game = new GamePanel();
		game.setSize(960,755);
		game.setLocation(0,0);
		add(game);
		
		battle = new BattlePanel();
		battle.setSize(960,755);
		battle.setLocation(0,0);
		add(battle);
		
		shop = new ShopPanel();
		shop.setSize(960,755);
		shop.setLocation(0,0);
		add(shop);
		
		inven = new InvenPanel();
		inven.setSize(960,755);
		inven.setLocation(0,0);
		add(inven);
		
		team = new TeamPanel();
		team.setSize(960,755);
		team.setLocation(0,0);
		add(team);
		
		skills = new SkillsPanel();
		skills.setSize(960,755);
		skills.setLocation(0,0);
		add(skills);
		
		currentScreen = "menu";
		setVisible(true);
		
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		myTimer = new javax.swing.Timer(30,this);
		myTimer.start();
		//setResizable(false);
	}
	
	public void actionPerformed(ActionEvent evt){
		
		if (currentScreen.equals("menu")){
			menu.update();
		}else if (currentScreen.equals("game")){
			game.update();
		}else if (currentScreen.equals("battle")){
			battle.update();
		}else if (currentScreen.equals("shop")){
			shop.update();
		}else if (currentScreen.equals("inven")){
			inven.update();
		}else if (currentScreen.equals("team")){
			team.update();
		}else if (currentScreen.equals("skills")){
			skills.update();
		}
	}
	
	public void changeScreen(String s){
		
		if (currentScreen.equals("menu")){
			menu.setVisible(false);
		}
		else if (currentScreen.equals("game")){
			game.setVisible(false);
		}
		else if (currentScreen.equals("battle")){
			battle.setVisible(false);
		}
		else if (currentScreen.equals("shop")){
			shop.setVisible(false);
		}
		else if (currentScreen.equals("inven")){
			inven.setVisible(false);
		}
		else if (currentScreen.equals("team")){
			team.setVisible(false);
		}
		else if (currentScreen.equals("skills")){
			skills.setVisible(false);
		}
		
		if (s.equals("menu")){
			menu.setVisible(true);
		}
		else if (s.equals("game")){
			game.setVisible(true);
		}
		else if (s.equals("battle")){
			battle.setVisible(true);
		}
		else if (s.equals("shop")){
			shop.setVisible(true);
		}
		else if (s.equals("inven")){
			inven.setVisible(true);
		}
		else if (s.equals("team")){
			team.setVisible(true);
		}
		else if (s.equals("skills")){
			skills.setVisible(true);
		}
		
		currentScreen = s;
	} 
	
	public static void main(String[]args){
		new MainFrame();
	}

	class MenuPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		Boolean click;
		
		Image menuScreen;
		AButton startButton;
		
		public MenuPanel(){
			super();
			
			menuScreen = new ImageIcon("Sprites/MenuPanel/menuScreen.png").getImage();
			
			startButton = new AButton(380,385,200,50,"Sprites/MenuPanel/startButtonOff.png","Sprites/MenuPanel/startButtonOn.png");
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click = false;
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(menuScreen,0,0,this);
			
			Point point = new Point(mx,my);
			
			startButton.resetImage();
			
			if (startButton.collide(point,click)){				
				setVisible(false);
				changeScreen("game");
			}		
			
			g.drawImage(startButton.getImage(),380,385,this);	
				
			repaint();
		}
		
		public void update(){
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}	
	
	class GamePanel extends JPanel implements MouseListener,MouseMotionListener{	
		int mx,my;
		Boolean click;
		
		Image mapScreen;
		
		AButton shopButton,invenButton,teamButton,skillsButton,exitButton;
		
		Rectangle battleRect1;
	
		public GamePanel(){
			super();
			
			mapScreen = new ImageIcon("Sprites/GamePanel/mapScreen.png").getImage();
					
			shopButton = new AButton(0,670,192,50,"Sprites/GamePanel/shopButtonOff.png","Sprites/GamePanel/shopButtonOn.png");
			invenButton = new AButton(192,670,192,50,"Sprites/GamePanel/invenButtonOff.png","Sprites/GamePanel/invenButtonOn.png");
			teamButton = new AButton(384,670,192,50,"Sprites/GamePanel/teamButtonOff.png","Sprites/GamePanel/teamButtonOn.png");
			skillsButton = new AButton(576,670,192,50,"Sprites/GamePanel/skillsButtonOff.png","Sprites/GamePanel/skillsButtonOn.png");
			exitButton = new AButton(768,670,192,50,"Sprites/GamePanel/exitButtonOff.png","Sprites/GamePanel/exitButtonOn.png");
			
			battleRect1 = new Rectangle(100,515,50,50);
			
			click=false;
				
			addMouseListener(this);
			addMouseMotionListener(this);
			
			setVisible(false);
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(mapScreen,0,0,this);
			
			Point point = new Point(mx,my);
			
			g.setColor(Color.RED);
			g.fillOval(100,515,50,50);
			
			if (battleRect1.contains(point)){
				if (click){
					changeScreen("battle");
				}
			}
			
			exitButton.resetImage();
			shopButton.resetImage();
			invenButton.resetImage();
			teamButton.resetImage();
			skillsButton.resetImage();
			
			if (exitButton.collide(point,click)){
					setVisible(false);
					changeScreen("menu");
			}
			else if (shopButton.collide(point,click)){
					setVisible(false);
					changeScreen("shop");
			}
			else if (invenButton.collide(point,click)){
					setVisible(false);
					changeScreen("inven");
			}
			else if (teamButton.collide(point,click)){
					setVisible(false);
					changeScreen("team");
			}
			else if (skillsButton.collide(point,click)){
					setVisible(false);
					changeScreen("skills");
			}
			
			g.drawImage(shopButton.getImage(),0,670,this);
			g.drawImage(invenButton.getImage(),192,670,this);
			g.drawImage(teamButton.getImage(),384,670,this);
			g.drawImage(skillsButton.getImage(),576,670,this);
			g.drawImage(exitButton.getImage(),768,670,this);
				
			repaint();
		}
		
		public void update(){
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class ShopPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
		
		public ShopPanel(){
			super();
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0,0,getWidth(),getHeight());
		}
		
		public void update(){
			repaint();
		}
	
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;	}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class InvenPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
	
		public InvenPanel(){
			super();
	
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0,0,getWidth(),getHeight());
		}
		
		public void update(){
			repaint();
		}
			
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class TeamPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
		
		public TeamPanel(){
			super();
	
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0,0,getWidth(),getHeight());
		}
		
		public void update(){
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class SkillsPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
		
		public SkillsPanel(){
			super();
	
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
	
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0,0,getWidth(),getHeight());
		}
		
		public void update(){
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class BattlePanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
		Enemy dude;
		Player archer,assassin,knight,paladin,priest,wizard;
		Player player,player1,player2,player3,player4,oldPlayer;
		Player [] playersSelected;
		Enemy [] enemiesSelected;
		
		public BattlePanel(){
			super();
			
			archer = new Player(new Sprite("archer"));
			assassin = new Player(new Sprite("assassin"));
			knight = new Player(new Sprite("knight"));
			paladin = new Player(new Sprite("paladin"));
			priest = new Player(new Sprite("priest"));
			wizard = new Player(new Sprite("wizard"));
			
			dude = new Enemy(new Sprite("wizard"));
			dude.setLocation(100,100);
				
			player1 = knight;
			player2 = archer;
			player3 = wizard;
			player4 = priest;
			
			player1.setLocation(480,330);
			player2.setLocation(450,380);
			player3.setLocation(510,380);
			player4.setLocation(480,430);
			
			playersSelected = new  Player[] {player1,player2,player3,player4};
			enemiesSelected = new Enemy[] {dude};
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click = false;
		}
		
		public void paintComponent(Graphics g){	
			g.setColor(Color.WHITE);
			g.fillRect(0,0,getWidth(),getHeight());
			
			g.drawImage(player1.getSprite(),(int)player1.getX(),(int)player1.getY(),this);
			g.drawImage(player2.getSprite(),(int)player2.getX(),(int)player2.getY(),this);
			g.drawImage(player3.getSprite(),(int)player3.getX(),(int)player3.getY(),this);
			g.drawImage(player4.getSprite(),(int)player4.getX(),(int)player4.getY(),this);	
			
			g.drawImage(dude.getSprite(),(int)dude.getX(),(int)dude.getY(),this);
						
			g.setColor(Color.RED);
			g.drawRect((int)dude.getX(),(int)dude.getY(),40,40);
			g.drawRect((int)player1.getX(),(int)player1.getY(),40,40);
			g.drawRect((int)player2.getX(),(int)player2.getY(),40,40);
			g.drawRect((int)player3.getX(),(int)player3.getY(),40,40);
			g.drawRect((int)player4.getX(),(int)player4.getY(),40,40);
			
			
		
		}
		
		public void update(){
			for(Player p : playersSelected){
				if (p!=null){
					if(checkCollision(p) == false){
						p.move();
					}
					p.setSprite();
				}
			}
			for(Enemy e: enemiesSelected){
				if(e != null){
					if(checkCollision(e) == false){
						e.move();
					}
					e.setSprite();
				}
			}
			repaint();
		}
		
		public void checkMove(){
			Point point = new Point(mx,my);
			oldPlayer = player;
			
			if(player1.getRect().contains(point)){
				if(Math.pow(Math.pow(mx-player1.getRealX(),2) + Math.pow(my-player1.getRealY(),2),.5) <=20){
					player=player1;
				}
			}
			else if (player2.getRect().contains(point)){
				if(Math.pow(Math.pow(mx-player2.getRealX(),2) + Math.pow(my-player2.getRealY(),2),.5) <=20){
					player=player2;
				}
			}
			else if (player3.getRect().contains(point)){
				if(Math.pow(Math.pow(mx-player3.getRealX(),2) + Math.pow(my-player3.getRealY(),2),.5) <=20){
					player=player3;
				}
			}
			else if (player4.getRect().contains(point)){
				if(Math.pow(Math.pow(mx-player4.getRealX(),2) + Math.pow(my-player4.getRealY(),2),.5) <=20){
					player=player4;
				}
			}
			
			if (mx>0 && my>0 && player != null && player == oldPlayer){
				player.setMove(mx,my);
			}
		}
		
		public boolean checkCollision(Player p){
			for(Enemy e: enemiesSelected){
				if(p.projectedMoveRect().intersects(e.getRect())){
						return true;
				}
			}
			
			for(Player pl : playersSelected){
				if (p!=pl){
					if(p.projectedMoveRect().intersects(pl.getRect())){
						return true;
					}
				}
			}
			return false;
		}
		
		public boolean checkCollision(Enemy e){
			for(Enemy en: enemiesSelected){
				if(e!=en){
					if(e.projectedMoveRect().intersects(en.getRect())){
						return true;
					}	
				}
			}
			
			for(Player p : playersSelected){
				if(e.projectedMoveRect().intersects(p.getRect())){
					return true;
				}
			}
			return false;
		}
		
		public void collision(Player p,Enemy e){
			if (p.getRect().intersects(e.getRect())){
				
				if (p.getMoving()){
					p.setX(p.getRealX() - p.getSpeed()*Math.cos(p.getAngle()));
					p.setY(p.getRealY() - p.getSpeed()*Math.sin(p.getAngle()));
					p.setMoving(false);
					p.updateRect();	
				}
				
				if (e.getMoving()){
					e.setX(e.getRealX() - e.getSpeed()*Math.cos(e.getAngle()));
					e.setY(e.getRealY() - e.getSpeed()*Math.sin(e.getAngle()));
					e.setMoving(false);
					e.updateRect();		
				}
											
			}
			
		}
		
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){
			click=true;
			checkMove();
		}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class Player{
		double x,y, dist,angle;
		int direction,speed,count;
		Rectangle rect;
		boolean moving;
		
		Image sprite;
		Sprite sprites;
		
		public Player(Sprite s){
			sprites = s;
			
			speed = 5;
			direction = 0;
			angle = 0;
			count = 0;
			
			moving = false;
			
		}
		
		public double getX(){
			return x-20;
		}
		
		public double getY(){
			return y-20;
		}
		
		public double getRealX(){
			return x;
		}
		
		public double getRealY(){
			return y;
		}
		
		public Rectangle getRect(){
			return rect;
		}
		
		public boolean getMoving(){
			return moving;
		}
		
		public Image getSprite(){
			if (sprite!=null){
				return sprite;
			}else{
				return null;
			}
		}
		
		public void setSprite(){
			Image oldSprite = sprite;
			
			if (direction!=0){
				if (moving==false){
					if (direction/10 < 2){
						sprite = sprites.getIdleRight();
					}else{
						sprite = sprites.getIdleLeft();
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
		}
		
		public void setMoving(boolean flag){
			moving = flag;
		}
		
		public int getSpeed(){
			return speed;
		}
		
		public double getAngle(){
			return angle;
		}
		
		public void setX(double x){
			this.x=x;
		}
		
		public void setY(double y){
			this.y=y;
		}
		
		public void setLocation(double x, double y){
			this.x=x;
			this.y=y;
			updateRect();
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
			dist = Math.pow(Math.pow(mx-x,2) + Math.pow(my-y,2),.5)/speed;
			moving = true;
			setDirection(mx,my);
			setAngle(mx,my);
		}
		
		public void move(){
			if(dist > 1 && moving){
				setX(x + speed*Math.cos(angle));
				setY(y + speed*Math.sin(angle));
				
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
		
		public void updateRect(){
			rect = new Rectangle((int)x-20,(int)y-20,40,40);
		}
		
		public Rectangle projectedMoveRect(){
			return new Rectangle((int)(x + speed*Math.cos(angle)-20),(int)(y + speed*Math.sin(angle)-20),40,40);
		} 
	}
	
	class Enemy{
		double x,y, dist,angle;
		int direction,speed,count;
		Rectangle rect;
		boolean moving;
		
		Image sprite;
		Sprite sprites;
		
		public Enemy(Sprite s){
			sprites = s;
			
			speed = 2;
			direction = 0;
			angle = 0;
			count = 0;
			
			moving = false;
			
		}
		
		public double getX(){
			return x-20;
		}
		
		public double getY(){
			return y-20;
		}
		
		public double getDistance(){
			return dist;
		}
		
		public double getRealX(){
			return x;
		}
		
		public double getRealY(){
			return y;
		}
		
		public Rectangle getRect(){
			return rect;
		}
		
		public boolean getMoving(){
			return moving;
		}
		
		public Image getSprite(){
			if (sprite!=null){
				return sprite;
			}else{
				return null;
			}
		}
		
		public void setSprite(){
			Image oldSprite = sprite;
			
			if (moving==false){
				if (angle < .5*Math.PI || angle > 1.5 *Math.PI){
					sprite = sprites.getIdleRight();
				}
				else{
					sprite = sprites.getIdleLeft();
				}
			}
			else{
				if (count%3==0){
					if (angle < .5*Math.PI || angle > 1.5 *Math.PI){
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
			count++;
		}
		
		public void setMoving(boolean flag){
			moving = flag;
		}
		
		public int getSpeed(){
			return speed;
		}
		
		public double getAngle(){
			return angle;
		}
		
		public void setX(double x){
			this.x=x;
		}
		
		public void setY(double y){
			this.y=y;
		}
		
		public void setLocation(double x, double y){
			this.x=x;
			this.y=y;
			updateRect();
		}
		
		public void setAngle(){
			angle = 2*Math.PI*Math.random();
		}
		
		public void setMove(){
			dist = (int)(Math.random()*10);
			moving = true;
			setAngle();
			
			
		}
							
		public void move(){
			if(dist > 1 && moving){
				setX(x + speed*Math.cos(angle));
				setY(y + speed*Math.sin(angle));
				
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
				setMove();
			}
		}
		
		public void updateRect(){
			rect = new Rectangle((int)x-20,(int)y-20,40,40);
		}
		
		public Rectangle projectedMoveRect(){
			return new Rectangle((int)(x + speed*Math.cos(angle) -20),(int)(y + speed*Math.sin(angle)-20),40,40);
		} 
	}

}

