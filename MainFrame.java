//MainFrame.java
//Alex Liu, Alex Popovic, Ahmed Hassan

/*The main code body for out game, Dawn of Legends. Keeps track of everything, and contains all the other classes as well.
the MainFrame consists of a set of JPanels which the player interacts with, including:
MenuPanel - main menu that shows upon startup
GamePanel - screen which shows the map and allows player to switch between different panels
BattlePanel - main part of the game. This is where the battles between the player and the enemy AI
VictoryExpPanel - panel where the player gets exp distribution if they win the battle
VictoryPanel - panel which gives the player gets item drops and money if they win the battle 
ShopPanel - panel where the player can purchase or sell weapons
InventoryPanel - panel where the player can equip or unequip different items on their heroes
TeamPanel - panel where the player can check on their heroes' current stats or change their team lineup
SkillsPanel - panel where the player can check what the special skill set of their heroes are

Comments will go more in depth for each panel later.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.*;
import java.io.*;


public class MainFrame extends JFrame implements ActionListener{
	//The main class. All the panels are stored here, and the switching between panels and updating the panels
	//is done here.
	MenuPanel menu;
	GamePanel game; 
	BattlePanel battle;	
	ShopPanel shop;
	InvenPanel inven;
	TeamPanel team;
	SkillsPanel skills;
	VictoryPanel victory;
	VictoryExpPanel victoryExp;
	
	Font calFont12,calFont20,calFont30;	
	public TruePlayer human_player = new TruePlayer();	//TruePlayer class - stores info that is required throughout various
	//panels, such as player money, player progress, etc. Internal class of MainFrame.
	
	String currentScreen;	//tracks what the screen the player is currently on
	
	javax.swing.Timer myTimer;

	public MainFrame(){
		//basic constructor
		super("Dawn of Legends");
		
		setSize(960,755);
		setLayout(null);
		
		//importing the fonts for use throughout the game
		try{
			calFont12 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/calibri.ttf"))).deriveFont(0,12);
			calFont20 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/calibri.ttf"))).deriveFont(0,20);
			calFont30 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts/calibri.ttf"))).deriveFont(0,30);
		}
		catch(Exception except){
			System.out.println("Error in MainFrame.importFonts");
		}
		
		//adding the various panels
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
		
		victory = new VictoryPanel();
		victory.setSize(960,755);
		victory.setLocation(0,0);
		add(victory);
		
		victoryExp = new VictoryExpPanel();
		victoryExp.setSize(960,755);
		victoryExp.setLocation(0,0);
		add(victoryExp);
		
		currentScreen = "menu";	//sets the first screen to the Main Menu
		setVisible(true);
		
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		myTimer = new javax.swing.Timer(30,this);
		startTimer();
	}
	
	public void actionPerformed(ActionEvent evt){
		//whenever the timer does a tick, the panels are updated depending on what
		//the currentScreen is. That way, only the panel the player is looking at
		//is updated.
		
		if (currentScreen.equals("menu")){
			menu.update();
		}
		else if (currentScreen.equals("game")){
			game.update();
		}
		else if (currentScreen.equals("battle")){
			battle.update();
		}
		else if (currentScreen.equals("victory")){
			victory.update();
		}
		else if (currentScreen.equals("victoryExp")){
			victoryExp.update();
		}
		else if (currentScreen.equals("shop")){
			shop.update();
		}
		else if (currentScreen.equals("inven")){
			inven.update();
		}
		else if (currentScreen.equals("team")){
			team.update();
		}
		else if (currentScreen.equals("skills")){
			skills.update();
		}
	}
	
	public void changeScreen(String s){
		//allows the other panels to switch between the panels by calling the changeScreen function.
		//sets all other panels to invisible and sets the target panel to visible, then calls any other things
		//that might be required, like resetting the shop inventory for example.
		
		if (currentScreen.equals("menu")){
			menu.setVisible(false);
		}
		else if (currentScreen.equals("game")){
			game.setVisible(false);
		}
		else if (currentScreen.equals("battle")){
			battle.setVisible(false);	
		}
		else if (currentScreen.equals("victory")){
			victory.setVisible(false);	
		}
		else if (currentScreen.equals("victoryExp")){
			victoryExp.setVisible(false);	
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
			battle.reset();	
		}
		else if (s.equals("victoryExp")){
			//victoryExp panel is reset inside battle panel.
			victoryExp.setVisible(true);	
		}
		else if (s.equals("victory")){
			//victory panel items are reset inside battle panel.
			victory.setInventory();
			victory.setVisible(true);	
		}
		else if (s.equals("shop")){
			shop.setInventory();
			shop.resetShop(84,(int)(Math.random()*8+1));
			shop.setVisible(true);
		}
		else if (s.equals("inven")){
			inven.setInventory();
			inven.setPlayer();
			inven.setVisible(true);
		}
		else if (s.equals("team")){
			team.setVisible(true);
		}
		else if (s.equals("skills")){
			skills.setVisible(true);
		}
		
		currentScreen = s;	//sets the current 
	}
	
	public void startTimer(){
		myTimer.start();
	}
	
	public void stopTimer(){
		myTimer.stop();
	}
	
	public static void main(String[]args){
		new MainFrame();
	}

	class MenuPanel extends JPanel implements MouseListener,MouseMotionListener{
		//MenuPanel. This is where the Main Menu is. Doesn't really do much - it just contains buttons
		//that lead the player to another screen. The entire panel is fairly basic - just get images,
		//create buttons, draw images, check if player clicked, go to other panels, etc. Nothing to
		//really say here.
		
		int mx,my;
		Boolean click;
		
		AButton startButton;	//AButtons(short for Alex Liu Buttons), they help to streamline the button process.
		Image menuScreen;
		
		public MenuPanel(){
			super();
			
			startButton = new AButton(380,385,200,50,"Sprites/MenuPanel/startButtonOff.png","Sprites/MenuPanel/startButtonOn.png");
			menuScreen = new ImageIcon("Sprites/MenuPanel/menuScreen.png").getImage();

			addMouseListener(this);
			addMouseMotionListener(this);
			
			click = false;
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(menuScreen,0,0,this);
			Point point = new Point(mx,my);
			
			startButton.resetImage();
			//How AButtons work: all AButtons have a clicked and notclicked image. collide() determines which image
			//is sent when AButton.getImage() is called, and also returns true or false for collision. In order to draw
			//AButtons, first the image must be reset (resetImage()), then collision is checked (collide()), and finally
			//the image is drawn.
			
			if (startButton.collide(point,click)){	
				//changes screens if player clicks a button.			
				setVisible(false);
				changeScreen("game");
			}
			g.drawImage(startButton.getImage(),380,385,this);		
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
		//GamePanel. This is the main map screen, and it allows the player to choose what level to play
		//or choose which menu they want to visit.
		
		int mx,my;
		Boolean click;
		
		Image mapScreen;
		AButton shopButton,invenButton,teamButton,skillsButton,exitButton,battleRect1;
	
		public GamePanel(){
			//basic constructor.
			super();
			
			mapScreen = new ImageIcon("Sprites/GamePanel/mapScreen.png").getImage();
			
			shopButton = new AButton(0,670,192,50,"Sprites/GamePanel/shopButtonOff.png","Sprites/GamePanel/shopButtonOn.png");
			invenButton = new AButton(192,670,192,50,"Sprites/GamePanel/invenButtonOff.png","Sprites/GamePanel/invenButtonOn.png");
			teamButton = new AButton(384,670,192,50,"Sprites/GamePanel/teamButtonOff.png","Sprites/GamePanel/teamButtonOn.png");
			skillsButton = new AButton(576,670,192,50,"Sprites/GamePanel/skillsButtonOff.png","Sprites/GamePanel/skillsButtonOn.png");
			exitButton = new AButton(768,670,192,50,"Sprites/GamePanel/exitButtonOff.png","Sprites/GamePanel/exitButtonOn.png");

			battleRect1 = new AButton(100,515,50,50,null,null);
			
			click=false;
				
			addMouseListener(this);
			addMouseMotionListener(this);
			
			setVisible(false);
		}

		public void paintComponent(Graphics g){
			g.drawImage(mapScreen,0,0,this);
			Point p = new Point(mx,my);
			
			g.setColor(Color.RED);
			g.fillOval(100,515,50,50);

			battleRect1.resetImage();
			exitButton.resetImage();
			shopButton.resetImage();
			invenButton.resetImage();
			teamButton.resetImage();
			skillsButton.resetImage();
			
			//AButtons also work for circular buttons! :P
			
			battleRect1.collide(p,click);
			exitButton.collide(p,click);
			shopButton.collide(p,click);
			invenButton.collide(p,click);
			teamButton.collide(p,click);
			skillsButton.collide(p,click);
			
			g.drawImage(shopButton.getImage(),shopButton.getX(),shopButton.getY(),this);
			g.drawImage(invenButton.getImage(),invenButton.getX(),invenButton.getY(),this);
			g.drawImage(teamButton.getImage(),teamButton.getX(),teamButton.getY(),this);
			g.drawImage(skillsButton.getImage(),skillsButton.getX(),teamButton.getY(),this);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			repaint();
		}
		
		public void update(){
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			
			if (battleRect1.collideCircle(p,click)){
				setVisible(false);
				changeScreen("battle");
			}
			
			if (exitButton.collide(p,click)){
				human_player.saveAll();
				setVisible(false);
				changeScreen("menu");
			}
			else if (shopButton.collide(p,click)){
				setVisible(false);
				changeScreen("shop");
			}
			else if (invenButton.collide(p,click)){
				setVisible(false);
				changeScreen("inven");
			}
			else if (teamButton.collide(p,click)){
				setVisible(false);
				changeScreen("team");
			}
			else if (skillsButton.collide(p,click)){
				setVisible(false);
				changeScreen("skills");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class BattlePanel extends JPanel implements MouseListener,MouseMotionListener{
		//BattlePanel. This is where the magic happens. All of the 'real' game is here, and this is where the player
		//fights the enemies. The BattlePanel handles the enemy spawning, enemy and player movements, and allows
		//both the players and enemies to fight each other, sometimes using special moves, along with a UI (user interface).
		//There's a lot here, so more detail will be explained when required.
		
		int mx,my;
		boolean click;
		AButton exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
		ArrayList<Player> playersSelected;	//tracks the current set of heroes the player has chosen
		ArrayList<Image> uiimages = new ArrayList<Image>(), clearuiimages = new ArrayList<Image>();
		Player player, oldPlayer;	//tracks what hero the player is currently using
		ArrayList<HitNumber> hitnums = new ArrayList<HitNumber>();
		//holds the 'hitnums' which appear above a creature's head when it takes damage.
		ArrayList<HitEffect> hitefx = new ArrayList<HitEffect>();
		//like hitnums, holds the 'hit efx' which draw images when something is hit.
		
		//!!! NEW ITEM NEW ITEM !!!
		ArrayList<Battle> battles = new ArrayList<Battle>();
		//!!!
		
		public BattlePanel(){
			super();
			
			//UI CODE
			//loads in the images.
			loadUIImages();
			//
			
			//!!! NEW ITEMS NEW ITEMS !!!
			loadBattles();
			//!!!
			
			addMouseListener(this);
			addMouseMotionListener(this);
			click = false;
			setVisible(false);

		}
		
		//!!! NEW ITEMS NEW ITEMS !!!
		public void loadBattles(){
			try{
				Scanner inFile = new Scanner(new BufferedReader(new FileReader("Players/battles.txt")));
				for(int i = 0; i < 20; i++){
					String skip = inFile.nextLine();
					int area = Integer.parseInt(inFile.nextLine());
					int waves = Integer.parseInt(inFile.nextLine());
					int maxEnemies = Integer.parseInt(inFile.nextLine());
					ArrayList<Integer> enemySpawns = new ArrayList<Integer>();
					int tmp = Integer.parseInt(inFile.nextLine());
					for(int j = 0; j<tmp; j++){
						enemySpawns.add(Integer.parseInt(inFile.nextLine()));
					}
					ArrayList<Integer> enemyPerRound = new ArrayList<Integer>();
					for(int j = 0; j<waves; j++){
						enemyPerRound.add(Integer.parseInt(inFile.nextLine()));
					}
					int money = Integer.parseInt(inFile.nextLine());
					int[] itemsWon = {0,0,0,0,0,0,0,0,0,0,0,0};
					for(int j = 0; j<12; j++){
						itemsWon[j] = Integer.parseInt(inFile.nextLine());
					}
					battles.add(new Battle(area,waves,maxEnemies,enemySpawns,enemyPerRound,money,itemsWon));
				}
			}
			catch(IOException except){
				System.out.println("Error in BattlePanel.loadBattles()");
			}
		}
		//!!!
		
		//UI CODE
		public void loadUIImages(){
			uiimages.add(new ImageIcon("Backgrounds/UI_knight.png").getImage());
			uiimages.add(new ImageIcon("Backgrounds/UI_archer.png").getImage());
			uiimages.add(new ImageIcon("Backgrounds/UI_wizard.png").getImage());
			uiimages.add(new ImageIcon("Backgrounds/UI_priest.png").getImage());
			uiimages.add(new ImageIcon("Backgrounds/UI_paladin.png").getImage());
			uiimages.add(new ImageIcon("Backgrounds/UI_assassin.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_knight_clear.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_archer_clear.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_wizard_clear.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_priest_clear.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_paladin_clear.png").getImage());
			clearuiimages.add(new ImageIcon("Backgrounds/UI_assassin_clear.png").getImage());
		}
		public void drawUIStats(Graphics g){
			//draws all the UI stats - in this case, strings for the UI such as health, or the hitnums
			//which show damage above an object's head.
			g.setFont(calFont12);	
			g.setColor(new Color(246,190,58));
			
			//This is all just drawing strings in different places. Takes in the stats from the hero
			//the player is currently using and displays them to the player.
			String tmpHp = Integer.toString(player.getCurrentHp())+"/"+Integer.toString(player.getTrueMaxHp());
			String tmpMana = Integer.toString(player.getCurrentMana())+"/"+Integer.toString(player.getTrueMaxMana());
			g.drawString(tmpHp,355,678);
			g.drawString(tmpMana,355,703);
			
			g.setColor(Color.BLACK);
			g.drawString(String.format("Level: %d",player.getLevel()),138,600);
			g.drawString(String.format("Def: %d",player.getTrueDef()),138,615);
			g.drawString(String.format("Atk Spd: %.2f",player.getTrueAtkSpd()),138,630);
			g.drawString(String.format("Crit Chance: %.2f",player.getTrueCritChance()),138,645);
			g.drawString(String.format("Crit Damage: %.2f",player.getTrueCritDmg()),138,660);
			g.drawString(String.format("Evasion: %.2f",player.getTrueEvasion()),138,675);
			
			g.drawString("Q",276,598);
			g.drawString("W",339,598);
			g.drawString("E",404,598);
			g.drawString("R",467,598);
			
			//Drawing Hit Numbers
			//draws hitnumbers.
			for(HitNumber n:hitnums){
				g.setColor(n.getColor());
				g.drawString(n.getString(),(int)(n.getX()),(int)(n.getY()));
			}
			
			//Drawing Hit Efx
			//draws hit effects.
			for(HitEffect e:hitefx){
				g.drawImage(e.getImage(),(int)(e.getX()),(int)(e.getY()),this);
			}
			
		}
		public void drawUIBase(Graphics g){
			//draws the 'base' for the UI, in other words, the boxes and status bars (hp and mana).
			
			Rectangle r1 = new Rectangle(0,370,141,196);	//rects for the UI
			Rectangle r2 = new Rectangle(0,527,517,193);
			boolean drawn = false;	//tracks whether the UI has been drawn yet to ensure that overlapping doesnt occur,
			//since it would ruin the purpose of alpha
			Point pp;	//tracks where the players are
			for(Player p:playersSelected){
				//goes through the player's team. If one of the players are inside the area where the UI should be, then
				//a transparent UI is displayed. The UI is different depending on what current hero is equipped.
				
				if(p != null && p.getAlive()){
					pp = new Point((int)(p.getX()),(int)(p.getY()));
					if(r1.contains(pp) || r2.contains(pp)){
						if(drawn == false){
							if(player.getName().equals("Knight")){
								g.drawImage(clearuiimages.get(0),0,0,this);
							}
							else if(player.getName().equals("Archer")){
								g.drawImage(clearuiimages.get(1),0,0,this);
							}
							else if(player.getName().equals("Wizard")){
								g.drawImage(clearuiimages.get(2),0,0,this);
							}
							else if(player.getName().equals("Priest")){
								g.drawImage(clearuiimages.get(3),0,0,this);
							}
							else if(player.getName().equals("Paladin")){
								g.drawImage(clearuiimages.get(4),0,0,this);
							}
							else if(player.getName().equals("Assassin")){
								g.drawImage(clearuiimages.get(5),0,0,this);
							}
						}
						//drawing health bars
						//takes in the player health as a percentage,then draws the rectangle.
						g.setColor(new Color(255,100,0,122));
						int hpRectWidth = (int)(239*(double)player.getCurrentHp()/(double)player.getTrueMaxHp());
						g.fillRect(254,668,hpRectWidth,13);
						
						g.setColor(new Color(0,100,255,122));
						int manaRectWidth = (int)(239*(double)player.getCurrentMana()/(double)player.getTrueMaxMana());
						g.fillRect(254,693,manaRectWidth,13);
						
						for(int i = 0; i < 4; i++){
							//for each of the players, side bars are displayed containing HP and etc. to allow the
							//player to easily track the condition of their entire team.
							if(playersSelected.get(i) != null && playersSelected.get(i).getAlive()){
								Player play = playersSelected.get(i);
								hpRectWidth = (int)(100*(double)play.getCurrentHp()/(double)play.getTrueMaxHp());			
								manaRectWidth = (int)(100*(double)play.getCurrentMana()/(double)play.getTrueMaxMana());
								g.setColor(new Color(255,100,0,122));
								g.fillRect(32,395+(47*i),hpRectWidth,5);
								g.setColor(new Color(0,100,255,122));
								g.fillRect(32,403+(47*i),hpRectWidth,5);
							}
						}
						
						drawn = true;
					}
				}
			}
			if(!drawn){
				//basically the same thing, just this time it's opaque instead of clear.
				if(player.getName().equals("Knight")){
					g.drawImage(uiimages.get(0),0,0,this);
				}
				else if(player.getName().equals("Archer")){
					g.drawImage(uiimages.get(1),0,0,this);
				}
				else if(player.getName().equals("Wizard")){
					g.drawImage(uiimages.get(2),0,0,this);
				}
				else if(player.getName().equals("Priest")){
					g.drawImage(uiimages.get(3),0,0,this);
				}
				else if(player.getName().equals("Paladin")){
					g.drawImage(uiimages.get(4),0,0,this);
				}
				else if(player.getName().equals("Assassin")){
					g.drawImage(uiimages.get(5),0,0,this);
				}
				
				g.setColor(new Color(255,100,0,200));
				int hpRectWidth = (int)(239*(double)player.getCurrentHp()/(double)player.getTrueMaxHp());
				g.fillRect(254,668,hpRectWidth,13);
				
				g.setColor(new Color(0,100,255,200));
				int manaRectWidth = (int)(239*(double)player.getCurrentMana()/(double)player.getTrueMaxMana());
				g.fillRect(254,693,manaRectWidth,13);
				
				for(int i = 0; i < 4; i++){
					if(playersSelected.get(i) != null && playersSelected.get(i).getAlive()){
						Player play = playersSelected.get(i);
						hpRectWidth = (int)(100*(double)play.getCurrentHp()/(double)play.getTrueMaxHp());			
						manaRectWidth = (int)(100*(double)play.getCurrentMana()/(double)play.getTrueMaxMana());
						g.setColor(new Color(255,100,0,200));
						g.fillRect(32,395+(47*i),hpRectWidth,5);
						g.setColor(new Color(0,100,255,200));
						g.fillRect(32,403+(47*i),hpRectWidth,5);
					}
				}
				drawn = true;	
			}
			
		}
		
		public void paintComponent(Graphics g){	
			//draws the graphics. For organization purposes, we decided to split up all the graphics into seperate
			//portions and then created seperate functions for each.
			Point p = new Point(mx,my);
			g.setColor(Color.WHITE);
			g.fillRect(0,0,960,720);
			exitButton.resetImage();
			exitButton.collide(p,click);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			drawPlayers(g);
			drawUIBase(g);
			drawUIStats(g);
		}
		
		public void drawPlayers(Graphics g){
			//Draws the players onto the screen.
			//-------------------------
			g.drawImage(human_player.baseEnemies1().get(15).getImage(),100,100,this);
			g.drawImage(human_player.baseEnemies2().get(11).getImage(),200,100,this);
			g.drawImage(human_player.baseEnemies3().get(11).getImage(),300,100,this);
			g.drawImage(human_player.baseEnemies2().get(10).getImage(),400,100,this);
			g.drawImage(human_player.baseEnemies3().get(10).getImage(),500,100,this);
			//-------------------------
			
			for(Player p : playersSelected){
				if (p!=null && p.getAlive()){
					g.drawImage(p.getSprite(),(int)p.getX(),(int)p.getY(),this);
				}
			}
		}
		
		public void update(){
			//updates everything on the screen. Updates enemies, projectiles, players, etc.
			for(Player p : playersSelected){
				if (p!=null){
					p.updateAlive();
					if(p.getAlive()){
						if(checkCollision(p) == false){
							p.move();
						}
						p.setSprite();
						//
						//
						//
						if(Math.random() < 0.05){
							int temptemp = p.getTrueDef() + (int)(Math.random()*25-12);
							p.takeDamage(temptemp);
							if(Math.random() > p.getEvasion()){
								if(temptemp - p.getTrueDef() <= 0){
									hitnums.add(new HitNumber(p.getMidX(),p.getY(),"Blocked",50));
								}
								else{
									hitefx.add(new HitEffect(p.getX()-5,p.getY()-5,"slash",15));
									hitnums.add(new HitNumber(p.getMidX(),p.getY(),Integer.toString(temptemp-p.getTrueDef()),50));
								}
							}
							else{
								hitnums.add(new HitNumber(p.getMidX(),p.getY(),"Dodged",50));
							}
							
						}	
					}
					else{
						boolean teamStillAlive = false;
						for(Player pl: playersSelected){
							if(pl != null){
								if(pl.getAlive()){
									teamStillAlive = true;
									player = pl;
								}
							}
						}
						if(!teamStillAlive){
							setVisible(false);
							changeScreen("game");
						}
					}
				}
			}
			
			//Hit Numbers & Hit Effects
			//updates the hitnumbers.
			ArrayList<HitNumber> removeListHitNums = new ArrayList<HitNumber>();	//tracks which hitnumbers are finished.
			for(HitNumber n:hitnums){
				//if the hitnumber has hit the end of it's lifespan, it is removed. Otherwise, it is added to 
				//toDrawHitNums to be drawn later.
				if(n.update()){
					removeListHitNums.add(n);
				}
			} 
			for(HitNumber n:removeListHitNums){
				//the hitnums are removed from the main list.
				hitnums.remove(n);
			}
			
			//exact same logic as hitnums, just for hit efx.
			ArrayList<HitEffect> removeListHitEfx = new ArrayList<HitEffect>();
			for(HitEffect e:hitefx){
				if(e.update()){
					removeListHitEfx.add(e);
				}
			} 
			for(HitEffect e:removeListHitEfx){
				hitefx.remove(e);
			}
			//
			
			repaint();
		}
		
		public void checkMove(){
			//checks if the move the player wants to do is legal. In this case, it means checking whether
			//or not the player wants to move their currently selected hero to an empty space.
			//if the space is not empty, then the hero will not move.
			Point point = new Point(mx,my);
			oldPlayer = player;
			
			for(Player p : playersSelected){
				//if the player happens to click on another hero's rect, then the new hero that is
				//being controlled is the hero that was clicked.
				if (p!=null){
					if(p.getRect().contains(point)){
						player = p;
					}
				}
			}
			
			if (mx>0 && my>0 && player != null && player == oldPlayer && player.getAlive()){
				player.setMove(mx,my);	//sets the player's destination.
			}
		}
		
		public boolean checkCollision(Player p){
			/*
			for(Enemy e: enemiesSelected){
				if(p.projectedMoveRect().intersects(e.getRect())){
						return true;
				}
			}
			*/
			
			for(Player pl : playersSelected){
				if (p!=pl && pl!=null){
					if(p.projectedMoveRect().intersects(pl.getRect())){
						return true;
					}
				}
			}
			return false;
		}
		/*
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
		*/
		
		public void reset(){
			//resets the battle state.
			int[] pos = {45,90,135,180};	//positions are set back to before
			int counter = 0;	//counter is put back to 0
			playersSelected = human_player.getTeam();	//team is reset
			for(Player p : playersSelected){
				//all heroes are put back to their start positions
				if (p!= null){
					p.setX(400 + pos[counter]);
					p.setY(360);
					p.updateRect();
					p.resetPlayerStats();	//the heroes all have their stats reset(ie hp, mana, alive status)
				}
				counter++;
			}
			player = playersSelected.get(0);	//player is reset to the first one
			oldPlayer = null;
		}
		
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			if(exitButton.collide(p,click)){
				setVisible(false);
				int[] expEarnings = {1000,2000,3000,0};
				victoryExp.resetExpEarnings(expEarnings);
				int[] wonItems = {0,0,0,0,0,0,0,0,6,6,6,6};
				victory.resetDrop(wonItems,(int)(Math.random()*2000));
				changeScreen("victoryExp");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){checkMove();}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class VictoryExpPanel extends JPanel implements MouseListener,MouseMotionListener{
		int mx,my;
		boolean click;
		Image background = new ImageIcon("backgrounds/victoryexpscreen.png").getImage(), level_up = new ImageIcon("backgrounds/level_up.png").getImage();
		Image[] player_sprites = {null,null,null,null,null,null};
		AButton exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
		ArrayList<Player> team = new ArrayList<Player>();
		ArrayList<HitEffect> levelupefx = new ArrayList<HitEffect>();	//repurposing of hitefx to draw level up effect
		int[] expEarnings, expGivenAlready = {0,0,0,0};
		Rectangle[] rects = {null,null,null,null};
		
		public VictoryExpPanel(){
			super();
			
			loadSprites();
			loadRects();
			team = human_player.getTeam();
			
			addMouseListener(this);
			addMouseMotionListener(this);
			click = false;
			setVisible(false);
		} 
		public void loadRects(){
			rects[0] = new Rectangle(40,250,400,200);
			rects[1] = new Rectangle(40,530,400,200);
			rects[2] = new Rectangle(440,250,400,200);
			rects[3] = new Rectangle(440,530,400,200);
		}
		public void loadSprites(){
			//loads in the sprites of the different heroes.
			try{
				player_sprites[0] = new ImageIcon("game sprites/characters/knight/knight_idle_right.png").getImage();
				player_sprites[1] = new ImageIcon("game sprites/characters/archer/archer_idle_right.png").getImage();
				player_sprites[2] = new ImageIcon("game sprites/characters/wizard/wizard_idle_right.png").getImage();
				player_sprites[3] = new ImageIcon("game sprites/characters/priest/priest_idle_right.png").getImage();
				player_sprites[4] = new ImageIcon("game sprites/characters/paladin/paladin_idle_right.png").getImage();
				player_sprites[5] = new ImageIcon("game sprites/characters/assassin/assassin_idle_right.png").getImage();
			}
			catch(Exception except){
				System.out.println("Error occurred in TeamPanel.loadSprites");
			}
		}
		public void resetExpEarnings(int[] ee){
			expEarnings = ee;
			expGivenAlready[0] = 0;
			expGivenAlready[1] = 0;
			expGivenAlready[2] = 0;
			expGivenAlready[3] = 0;
		}
		
		public void drawSprites(Graphics g){
			for(int i = 0; i < 4; i++){
				if(team.get(i) != null){
					int x = (int)(rects[i].getX()+10);
					int y = (int)(rects[i].getY()+10);
					if(team.get(i).getName().equals("Knight")){
						g.drawImage(player_sprites[0],x,y,this);
					}
					else if(team.get(i).getName().equals("Archer")){
						g.drawImage(player_sprites[1],x,y,this);
					}
					else if(team.get(i).getName().equals("Wizard")){
						g.drawImage(player_sprites[2],x,y,this);
					}
					else if(team.get(i).getName().equals("Priest")){
						g.drawImage(player_sprites[3],x,y,this);
					}
					else if(team.get(i).getName().equals("Paladin")){
						g.drawImage(player_sprites[4],x,y,this);
					}
					else if(team.get(i).getName().equals("Assassin")){
						g.drawImage(player_sprites[5],x,y,this);
					}
					g.setColor(Color.BLACK);
					g.fillRect((int)(rects[i].getX()+175),(int)(rects[i].getY()+75),150,15);
					g.setColor(new Color(255,183,100,190));
					int expWidth = (int)(150.0*((double)(team.get(i).getExp())/(double)(team.get(i).getExpToLevel())));
					g.fillRect((int)(rects[i].getX()+175),(int)(rects[i].getY()+75),expWidth,15);
				}
			}
			for(HitEffect e:levelupefx){
				g.drawImage(e.getImage(),(int)(e.getX()),(int)(e.getY()),this);
			}
		}
		public void drawUI(Graphics g){
			g.setColor(new Color(255,183,100));
			setFont(calFont20);
			
			for(int i = 0; i < 4; i++){
				if(team.get(i) != null){
					String expString = Integer.toString(team.get(i).getExp())+" / "+Integer.toString(team.get(i).getExpToLevel());
					g.drawString("Level "+Integer.toString(team.get(i).getLevel()),(int)(rects[i].getX()+175),(int)(rects[i].getY())+45);
					g.drawString(expString,(int)(rects[i].getX()+175),(int)(rects[i].getY())+70);
				}
			}
			
		}
		public void paintComponent(Graphics g){
			Point p = new Point(mx,my);
			g.drawImage(background,0,0,this);
			
			exitButton.resetImage();
			exitButton.collide(p,click);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			drawSprites(g);
			drawUI(g);
		}
		
		public void update(){
			for(int i = 0; i < 4; i++){
				if(team.get(i) != null){
					if(expEarnings[i] > 0){
						if(expEarnings[i] > 1000){
							expGivenAlready[i] += 25;
							expEarnings[i] -= 25;
							if(team.get(i).setExp(team.get(i).getExp() + 25)){
								levelupefx.add(new HitEffect((int)(rects[i].getX()),(int)(rects[i].getY()),"level",75));
							}
						}
						else if(expEarnings[i] > 100){
							expGivenAlready[i] += 10;
							expEarnings[i] -= 10;
							if(team.get(i).setExp(team.get(i).getExp() + 10)){
								levelupefx.add(new HitEffect((int)(rects[i].getX()),(int)(rects[i].getY()),"level",75));
							}
						}
						else{
							expGivenAlready[i] += 1;
							expEarnings[i] -= 1;
							if(team.get(i).setExp(team.get(i).getExp() + 1)){
								levelupefx.add(new HitEffect((int)(rects[i].getX()),(int)(rects[i].getY()),"level",75));
							}
						}
					}
				}
			}
			ArrayList<HitEffect> removeLevelupefx = new ArrayList<HitEffect>();
			for(HitEffect e:levelupefx){
				if(e.update()){
					removeLevelupefx.add(e);
				}
			} 
			for(HitEffect e:removeLevelupefx){
				levelupefx.remove(e);
			}
			repaint();
		}
		
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			
			if(exitButton.collide(p,click)){
				//exits the VictoryPanel and back to GamePanel. Sets the player's inventory and money to the new ones.
				for(int i = 0; i < 4; i++){
					if(team.get(i) != null){
						team.get(i).setExp(team.get(i).getExp() + expEarnings[i]);
						expEarnings[i] = 0;
					}
				}
				human_player.setTeam(team);
				setVisible(false);
				changeScreen("victory");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;	}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	class VictoryPanel extends JPanel implements MouseListener,MouseMotionListener{
		//panel displayed after a victory in battle. This one shows the player their post-battle random generated loot,
		//and also gives the player money.
		int mx,my;
		boolean click;
		Image background = new ImageIcon("backgrounds/victoryscreen.png").getImage();
		AButton exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
		AButton swapButton,selectedSlot1,selectedSlot2;	//selectedSlot# tracks the inventory slots that the player is currently using.
		ArrayList<AButton> inventory = new ArrayList<AButton>(), dropInventory = new ArrayList<AButton>();
		//tracks the player's inventory BUTTONS as well as the loot drops' inventory BUTTONS.
		LootGenerator loot = new LootGenerator();	//lootGenerator object. This creates random loot based on desired
		//level of rarity.
		Item[][] inv = new Item[7][12], dropInv = new Item[2][12];
		//tracks the player's actual ITEMS and the loot drops' ITEMS.
		Item item1 = null, item2 = null;
		//tracks which item is currently selected in either inventory.
		int money, money_found = 0;	//tracks money.
		
		public VictoryPanel(){
			super();
			
			swapButton = new AButton(40,335,120,50,"Sprites/Buttons/swapButtonOff.png","Sprites/Buttons/swapButtonOn.png");
			
			//immediately, all the different AButtons are added for the inventories.
			for(int y = 400; y < 680; y += 40){
				for(int x = 40; x < 520; x += 40){
					inventory.add(new AButton(x,y,40,40,null,null));
					//AButtons even work with null images! :D
				}
			}
			for(int y = 232; y < 312; y += 40){
				for(int x = 40; x < 520; x += 40){
					dropInventory.add(new AButton(x,y,40,40,null,null));
				}
			}
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		public void resetDrop(int[] r, int m){
			//resets the items being dropped. r is an array that contains the # of items
			//for each rarity of item. m is the money gained.
			int totalItems = 0;
			for(int i = 0; i < 12; i++){
				for(int j = 0; j < r[i]; j++){
					if(totalItems < 25){
						int x = totalItems%12;
						int y = totalItems/12;
						dropInv[y][x] = loot.generateItem(i+1);
						totalItems++;
					}
				}
			}
			money_found = m;
		}
		public void setInventory(){
			//sets the VictoryPanel up. Gets the player's current information.
			money = human_player.getMoney();
			inv = human_player.getInventory();
		}
		public void drawSlots(Graphics g, Point p){
			//draws the slots. These drawing types of code occur in almost every single panel from here on out,
			//so the explanation here will be very in depth and the explanation in the other panels will
			//be fairly sparse. The only difference is maybe the numebr of slots or the positions.
			
			//the different buttons are drawn.
			swapButton.resetImage();
			exitButton.resetImage();
			swapButton.collide(p,click);
			exitButton.collide(p,click);
			g.drawImage(swapButton.getImage(),swapButton.getX(),swapButton.getY(),this);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			//if the player is currently hovering over an inventory slot, the inventory slot will
			//have a red transparent rect drawn over it to give the player instant feedback.
			g.setColor(new Color(255,0,0,125));
			for(AButton button:inventory){
				if(button.getRect().contains(p)){	
					g.fillRect(button.getX(),button.getY(),40,40);
				}
			}
			for(AButton button:dropInventory){
				if(button.getRect().contains(p)){	
					g.fillRect(button.getX(),button.getY(),40,40);
				}
			}
			//if the player has already selected different slots, then a yellow transparent rect
			//is drawn on top.
			if(selectedSlot1 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot1.getX(),selectedSlot1.getY(),40,40);
			}
			if(selectedSlot2 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot2.getX(),selectedSlot2.getY(),40,40);
			}
		}
		
		public void drawItems(Graphics g, Point p){
			//like the drawSlots, this code appears quite often, so indepth here, sparse later.
			
			//goes through each item in the loot drop's inventory and draws it on screen.
			for(int y = 40; y < 120; y += 40){
				for(int x = 40; x < 520; x += 40){
					if(dropInv[(y-40)/40][(x-40)/40] != null){
						g.drawImage(dropInv[(y-40)/40][(x-40)/40].getImage(),x,y+192,this);
					}
				}
			}
			//goes through each item in the player's inventory and draws it.
			for(int y = 40; y < 320; y += 40){
				for(int x = 40; x < 520; x += 40){
					if(inv[(y-40)/40][(x-40)/40] != null){
						g.drawImage(inv[(y-40)/40][(x-40)/40].getImage(),x,y+360,this);
					}
				}
			}
			//if the player has already selected slots, then the panel will check to see if the player
			//has actually selected items at those positions. Since the buttons are stored in an arraylist
			//and the items are stored in an Item[][], mods and division is used to determine the positions
			//of items in the Item[][] given ArrayList position.
			if(selectedSlot1 != null){
				int x = inventory.indexOf(selectedSlot1)%12;
				int y = inventory.indexOf(selectedSlot1)/12;
				if(inv[y][x] != null){
					g.drawImage(inv[y][x].getImage(),735,631,this);
					//draws the image of the currently selected item in the info box.
				}
				
			}
			//same thing as the code before it.
			if(selectedSlot2 != null){
				int x = dropInventory.indexOf(selectedSlot2)%12;
				int y = dropInventory.indexOf(selectedSlot2)/12;
				if(dropInv[y][x] != null){
					g.drawImage(dropInv[y][x].getImage(),735,397,this);
				}
			}
		}
		public int calcWidth(String s){
			//code used to calculate the approximate widths of strings using Calibri 20pt font.
			//Approximate pixel dimensions for calibri 20pt:
			int[] widths = {12,11,11,12,9,10,13,11,5,6,11,9,16,13,13,11,15,12,10,11,13,13,19,11,12,10,9,11,9,10,10,7,9,10,5,5,9,4,15,10,10,11,10,8,9,7,10,11,16,10,11,10,4};
			int height = 14;
			String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'";
			
			int total = 0;
			
			for(int i = 0; i < s.length(); i++){
				//System.out.println(letters.indexOf(s.charAt(i))+" "+widths[letters.indexOf(s.charAt(i))-1]);
				total += widths[letters.indexOf(s.charAt(i))];
			}
			return total;
		}
		public void drawLongString(String s, int x, int y, int width, Graphics g){
			//Method for drawing longer strings inside boxes
			//width refers to how many letters can fit within the space provided. I use w's as
			//a standard form to measure that.
			
			//note this only works for calFont20, as the dimension changes are designed for
			//calibri fonts. It might work with other fonts accidentally though.
			String tmp = "";
			int yShift = 0, lineCount = 0, lineWidth = 0;
			ArrayList<String> words = new ArrayList<String>();
			
			for(int i = 0; i < s.length(); i++){
				//splits the string apart and puts them in an ArrayList.
				if(s.charAt(i) == ' ' ){
					words.add(tmp);
					tmp = "";
				}
				else if(i == s.length()-1){
					tmp += s.charAt(i);
					words.add(tmp);
					tmp = "";
				}
				else{
					tmp += s.charAt(i);
				}
			}
			for(String w:words){
				//Goes through the entire string word by word. If the width of the the current word
				//is too long to be printed on the space given, then the word will shift down.
				if(lineCount+w.length() > width){
					yShift++;
					lineCount = 0;
					lineWidth = 0;
					g.drawString(w,x,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
				else{
					g.drawString(w,x+lineWidth,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
			}
		}
		public void drawText(Graphics g){
			//draws the text in the inventory system. Again, used similarly in the majority of the panels.
			setFont(calFont20);
						
			g.setColor(new Color(246,190,58));
			
			//tells the player how much money they have and how much they have earned.
			g.drawString(String.format("Current Money: %d G",money),320,340);
			g.drawString(String.format("Money Found: %d G",money_found),320,380);
			
			//figures out which items are currently selected (if at all)
			if(selectedSlot1 != null){
				int x = inventory.indexOf(selectedSlot1)%12;
				int y = inventory.indexOf(selectedSlot1)/12;
				item1 = inv[y][x];
			}
			if(selectedSlot2 != null){
				int x = dropInventory.indexOf(selectedSlot2)%12;
				int y = dropInventory.indexOf(selectedSlot2)/12;
				item2 = dropInv[y][x];
			}
			
			String[] efx;
			
			//if the items are selected, then information on the items is displayed. (This is why drawLongString is used, as item
			//names can get extremely long.
			if(item1 != null){
				drawLongString(item1.getName(),620,490,25,g);
				g.drawString(String.format("Sells for: %d G",(int)(item1.getValue()*0.75)),610,227);
				if(item1.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				else if(item1.getType().equals("Armor")){
					Armor tmpItem = (Armor)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				else if(item1.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item1;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				
			}
			if(item2 != null){
				drawLongString(item2.getName(),620,259,25,g);
				g.drawString(String.format("Buy for: %d G",item2.getValue()),760,227);
				if(item2.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,324);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
				else if(item2.getType().equals("Armor")){
					Armor tmpItem = (Armor)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType(),620,324);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel())+"; R"+Integer.toString(tmpItem.getRarity()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
				else if(item2.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item2;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),620,324);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
			}
		}
		
		public void paintComponent(Graphics g){
			Point p = new Point(mx,my);
			g.drawImage(background,0,0,this);
			
			drawSlots(g,p);
			drawItems(g,p);
			drawText(g);
		}
		
		public void update(){
			repaint();
		}
	
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			
			for(AButton button:inventory){
				if(button.collide(p,click)){
					selectedSlot1 = button;
				}
			}
			for(AButton button:dropInventory){
				if(button.collide(p,click)){
					selectedSlot2 = button;
				}
			}
			
			if(swapButton.collide(p,click)){
				//if the swap button is clicked, the panel will check to see if both a destination slot
				//in the player inventory is selected and a slot in the loot drop inventory is selected.
				//then, it will switch the 2 items (if there are any).
				if(selectedSlot1 != null && selectedSlot2 != null){
					int x1 = inventory.indexOf(selectedSlot1)%12;
					int y1 = inventory.indexOf(selectedSlot1)/12;
					int x2 = dropInventory.indexOf(selectedSlot2)%12;
					int y2 = dropInventory.indexOf(selectedSlot2)/12;
					Item i = inv[y1][x1];
					inv[y1][x1] = dropInv[y2][x2];
					dropInv[y2][x2] = i;	
				}
			}
			
			if(exitButton.collide(p,click)){
				//exits the VictoryPanel and back to GamePanel. Sets the player's inventory and money to the new ones.
				human_player.setInventory(inv);
				human_player.setMoney(money + money_found);
				setVisible(false);
				changeScreen("game");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;	}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	class ShopPanel extends JPanel implements MouseListener,MouseMotionListener{
		//ShopPanel. Allows the player to purchase and sell their items, or just check on what the item does. Works almost
		//the exact same as VictoryPanel (actually, VictoryPanel was made by copy-pasting this one and then edited) so ShopPanel
		//shouldnt really need much explanation. If something still isnt clear, then refer to the fucntion or variable of the same
		//name in VictoryPanel.
		
		int mx,my;
		boolean click;
		Image background = new ImageIcon("backgrounds/shop.png").getImage();
		AButton exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
		AButton buyButton,sellButton, selectedSlot1,selectedSlot2;
		ArrayList<AButton> inventory = new ArrayList<AButton>(), shopInventory = new ArrayList<AButton>();
		LootGenerator loot = new LootGenerator();
		Item[][] inv = new Item[7][12], shopInv = new Item[7][12];
		int money;
		Item item1 = null, item2 = null;
		
		public ShopPanel(){
			super();
			
			buyButton = new AButton(40,335,120,50,"Sprites/Buttons/buyButtonOff.png","Sprites/Buttons/buyButtonOn.png");
			sellButton = new AButton(180,335,120,50,"Sprites/Buttons/sellButtonOff.png","Sprites/Buttons/sellButtonOn.png");
			
			for(int y = 400; y < 680; y += 40){
				for(int x = 40; x < 520; x += 40){
					inventory.add(new AButton(x,y,40,40,null,null));
				}
			}
			for(int y = 40; y < 320; y += 40){
				for(int x = 40; x < 520; x += 40){
					shopInventory.add(new AButton(x,y,40,40,null,null));
				}
			}
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		
		public void setInventory(){
			inv = human_player.getInventory();
			money = human_player.getMoney();
		}
		public void resetShop(int i, int r){
			//resets the shop's supply. In this case, the shop has a 'skewing' system which allows
			//items that are either 1 level of rarity higher or lower than the one desired, which
			//allows for greater variety in items.
			int skew = 0;
			if(i < 85){
				int tmp = 0;
				while(tmp < i){
					int x = tmp%12;
					int y = tmp/12;
					if(r == 1){
						skew = (int)(Math.random()*3-1);
					}
					else if(r == 8){
						skew = (int)(Math.random()*3-2);
					}
					else{
						skew = (int)(Math.random()*4-2);
					}
					shopInv[y][x] = loot.generateItem(r+skew);
					tmp++;
				}
			}
		}
		
		public void drawSlots(Graphics g, Point p){
			//exact same as VictoryPanel.drawSlots()
			
			g.setColor(new Color(255,0,0,125));
			
			buyButton.resetImage();
			sellButton.resetImage();
			exitButton.resetImage();
			buyButton.collide(p,click);
			sellButton.collide(p,click);
			exitButton.collide(p,click);
			g.drawImage(buyButton.getImage(),buyButton.getX(),buyButton.getY(),this);
			g.drawImage(sellButton.getImage(),sellButton.getX(),sellButton.getY(),this);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			for(AButton button:inventory){
				if(button.getRect().contains(p)){	
					g.fillRect(button.getX(),button.getY(),40,40);
				}
			}
			for(AButton button:shopInventory){
				if(button.getRect().contains(p)){	
					g.fillRect(button.getX(),button.getY(),40,40);
				}
			}
			if(selectedSlot1 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot1.getX(),selectedSlot1.getY(),40,40);
			}
			if(selectedSlot2 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot2.getX(),selectedSlot2.getY(),40,40);
			}
		}
		public void drawItems(Graphics g, Point p){
			//exact same as VictoryPanel.drawItems(), just slightly more items.
			
			for(int y = 40; y < 320; y += 40){
				for(int x = 40; x < 520; x += 40){
					if(shopInv[(y-40)/40][(x-40)/40] != null){
						g.drawImage(shopInv[(y-40)/40][(x-40)/40].getImage(),x,y,this);
					}
					if(inv[(y-40)/40][(x-40)/40] != null){
						g.drawImage(inv[(y-40)/40][(x-40)/40].getImage(),x,y+360,this);
					}
				}
			}
			if(selectedSlot1 != null){
				int x = inventory.indexOf(selectedSlot1)%12;
				int y = inventory.indexOf(selectedSlot1)/12;
				if(inv[y][x] != null){
					g.drawImage(inv[y][x].getImage(),735,631,this);
				}
				
			}
			if(selectedSlot2 != null){
				int x = shopInventory.indexOf(selectedSlot2)%12;
				int y = shopInventory.indexOf(selectedSlot2)/12;
				if(shopInv[y][x] != null){
					g.drawImage(shopInv[y][x].getImage(),735,397,this);
				}
			}
		}
		
		public int calcWidth(String s){
			//same as VictoryPanel.calcWidth().
			
			//Approximate pixel dimensions for calibri 20pt:
			int[] widths = {12,11,11,12,9,10,13,11,5,6,11,9,16,13,13,11,15,12,10,11,13,13,19,11,12,10,9,11,9,10,10,7,9,10,5,5,9,4,15,10,10,11,10,8,9,7,10,11,16,10,11,10,4};
			int height = 14;
			String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'";
			
			int total = 0;
			
			for(int i = 0; i < s.length(); i++){
				//System.out.println(letters.indexOf(s.charAt(i))+" "+widths[letters.indexOf(s.charAt(i))-1]);
				total += widths[letters.indexOf(s.charAt(i))];
				
			}
			
			return total;
		}
		public void drawLongString(String s, int x, int y, int width, Graphics g){
			//same as VictoryPanel.drawLongString().
			
			String tmp = "";
			int yShift = 0, lineCount = 0, lineWidth = 0;
			ArrayList<String> words = new ArrayList<String>();
			
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == ' ' ){
					words.add(tmp);
					tmp = "";
				}
				else if(i == s.length()-1){
					tmp += s.charAt(i);
					words.add(tmp);
					tmp = "";
				}
				else{
					tmp += s.charAt(i);
				}
			}
			for(String w:words){
				if(lineCount+w.length() > width){
					yShift++;
					lineCount = 0;
					lineWidth = 0;
					g.drawString(w,x,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
				else{
					g.drawString(w,x+lineWidth,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
			}
		}
		public void drawText(Graphics g){
			//ALMOST the same as VictoryPanel.drawText(). Only difference here is that 
			//it also draws how much the items are worth. That however is just straightforward
			//drawing of strings, so no explanation is required. Note that items are only worth
			//75% of their real value when sold.
			
			setFont(calFont20);
						
			g.setColor(new Color(246,190,58));
			g.drawString(String.format("Money: %d G",money),320,370);
			
			if(selectedSlot1 != null){
				int x = inventory.indexOf(selectedSlot1)%12;
				int y = inventory.indexOf(selectedSlot1)/12;
				item1 = inv[y][x];
			}
			if(selectedSlot2 != null){
				int x = shopInventory.indexOf(selectedSlot2)%12;
				int y = shopInventory.indexOf(selectedSlot2)/12;
				item2 = shopInv[y][x];
			}
			
			String[] efx;
			
			if(item1 != null){
				drawLongString(item1.getName(),620,490,25,g);
				g.drawString(String.format("Sells for: %d G",(int)(item1.getValue()*0.75)),610,227);
				if(item1.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				else if(item1.getType().equals("Armor")){
					Armor tmpItem = (Armor)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				else if(item1.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item1;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),620,555);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),620,573);
					g.drawString(tmpItem.getEffects()[0],620,591);
					g.drawString(tmpItem.getEffects()[1],620,609);
				}
				
			}
			else{	
				g.drawString("Sells for:",610,227);
			}
			if(item2 != null){
				drawLongString(item2.getName(),620,259,25,g);
				g.drawString(String.format("Buy for: %d G",item2.getValue()),760,227);
				if(item2.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),620,324);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
				else if(item2.getType().equals("Armor")){
					Armor tmpItem = (Armor)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType(),620,324);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel())+"; R"+Integer.toString(tmpItem.getRarity()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
				else if(item2.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item2;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),620,324);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),620,342);
					g.drawString(tmpItem.getEffects()[0],620,360);
					g.drawString(tmpItem.getEffects()[1],620,378);
				}
			}
			else{	
				g.drawString("Buy for:",760,227);
			}
		}
		
		public void paintComponent(Graphics g){
			Point p = new Point(mx,my);
			g.drawImage(background,0,0,this);
			
			drawSlots(g,p);
			drawItems(g,p);
			drawText(g);
		}
		
		public void update(){
			repaint();
		}
	
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			
			for(AButton button:inventory){
				if(button.collide(p,click)){
					selectedSlot1 = button;
				}
			}
			for(AButton button:shopInventory){
				if(button.collide(p,click)){
					selectedSlot2 = button;
				}
			}
			
			if(sellButton.collide(p,click)){
				//if an item is sold, the player will recieve 75% of the item's value and then the
				//item will permanently disappear.
				if(selectedSlot1 != null){
					int x = inventory.indexOf(selectedSlot1)%12;
					int y = inventory.indexOf(selectedSlot1)/12;
					if(inv[y][x] != null){
						money += (int)(inv[y][x].getValue()*0.75);
						inv[y][x] = null;
					}	
				}
			}
			if(buyButton.collide(p,click)){
				//in order for an item to be purchased, a slot in both the store and the player inventories
				//must be selected (destination slot). If the destination slot is empty, then the player pays
				//how much money the item is worth and then the item is placed in that slot, and the store loses
				//that item.
				if(selectedSlot1 != null && selectedSlot2 != null){
					int x1 = inventory.indexOf(selectedSlot1)%12;
					int y1 = inventory.indexOf(selectedSlot1)/12;
					int x2 = shopInventory.indexOf(selectedSlot2)%12;
					int y2 = shopInventory.indexOf(selectedSlot2)/12;
					if(inv[y1][x1] == null && shopInv[y2][x2] != null){
						if(money > shopInv[y2][x2].getValue()){
							inv[y1][x1] = shopInv[y2][x2];
							money -= shopInv[y2][x2].getValue();
							shopInv[y2][x2] = null;
						}
					}
				}
			}
			
			if(exitButton.collide(p,click)){
				//changes back to GamePanel. Sets the player inventories and money to the new ones.
				human_player.setMoney(money);
				human_player.setInventory(inv);
				setVisible(false);
				changeScreen("game");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){	click=true;	}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class InvenPanel extends JPanel implements MouseListener,MouseMotionListener{
		//InvenPanel. Lets the player browse their item collection as well as equip the items onto their different heroes.
		//Again, it's similar to ShopPanel and VictoryPanel in many ways, so these comments will be quick.
		
		int mx,my, curPlayer;	//curPlayer - tracks current player being displayed on screen.
		boolean click;
		Item[][] inv1;
		ArrayList<AButton> slots = new ArrayList<AButton>(), eSlots = new ArrayList<AButton>(), playerButtons = new ArrayList<AButton>();
		//PlayerButtons allows the player to switch between the different heroes on their team, allowing them to equip items onto those
		//different heroes.
		ArrayList<Item> equippedItems = new ArrayList<Item>();
		//tracks what items are equipped on the hero currently being displayed.
		ArrayList<Player> players;
		//tracks the current team of the player.
		Image[] backgrounds = new Image[6];
		AButton selectedSlot1 = null, selectedSlot2 = null, selectedPlayerSlot = null, exitButton,equipButton,unequipButton;
		//same as before, but now there's a selectedPlayerSlot which tracks what player is currently selected.
		
		public InvenPanel(){
			super();
			
			exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
			eSlots.add(new AButton(454,116,40,40,null,null)); //ring1
			eSlots.add(new AButton(454,171,40,40,null,null)); //ring2
			eSlots.add(new AButton(401,224,40,40,null,null)); //weap
			eSlots.add(new AButton(324,224,40,40,null,null)); //main armor
			eSlots.add(new AButton(249,224,40,40,null,null)); //secondary armor
			playerButtons.add(new AButton(78,88,120,50,null,null));
			playerButtons.add(new AButton(78,148,120,50,null,null));
			playerButtons.add(new AButton(78,206,120,50,null,null));
			playerButtons.add(new AButton(78,268,120,50,null,null));
			equipButton = new AButton(576,90,120,50,"Sprites/Buttons/equipButtonOff.png","Sprites/Buttons/equipButtonOn.png");
			unequipButton = new AButton(720,90,120,50,"Sprites/Buttons/unequipButtonOff.png","Sprites/Buttons/unequipButtonOn.png");
			
			players = human_player.getTeam();	//gets the current team from the player.
			for(int i = 0; i < 5; i++){
				equippedItems.add(null);
			}
			curPlayer = 0;	//sets the current player to the first one
			selectedPlayerSlot = playerButtons.get(curPlayer);	//resets the player button to the first player
			setPlayer();	//sets the player.
			
			loadImages();
	
			addMouseListener(this);
			addMouseMotionListener(this);
			
			for(int y = 400; y < 680; y += 40){
				for(int x = 40; x < 520; x += 40){
					slots.add(new AButton(x,y,40,40,null,null));
				}
			}
			
			click=false;
			setVisible(false);
		}
		
		public void loadImages(){
			//loads in the diff. background images.
			try{
				backgrounds[0] = new ImageIcon("backgrounds/inventory_knight.png").getImage();
				backgrounds[1] = new ImageIcon("backgrounds/inventory_archer.png").getImage();
				backgrounds[2] = new ImageIcon("backgrounds/inventory_wizard.png").getImage();
				backgrounds[3] = new ImageIcon("backgrounds/inventory_priest.png").getImage();
				backgrounds[4] = new ImageIcon("backgrounds/inventory_paladin.png").getImage();
				backgrounds[5] = new ImageIcon("backgrounds/inventory_assassin.png").getImage();
			}
			catch(Exception except){
				System.out.println("Error in SkillsPanel.loadBackgrounds");
			}
		}
		public void setInventory(){
			//sets the inventory.
			inv1 = human_player.getInventory();
		}
		public int calcWidth(String s){
			//Refer to VictoryPanel.calcWidth().
			//Approximate pixel dimensions for calibri 20pt:
			int[] widths = {12,11,11,12,9,10,13,11,5,6,11,9,16,13,13,11,15,12,10,11,13,13,19,11,12,10,9,11,9,10,10,7,9,10,5,5,9,4,15,10,10,11,10,8,9,7,10,11,16,10,11,10,4};
			int height = 14;
			String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'";
			
			int total = 0;
			
			for(int i = 0; i < s.length(); i++){
				total += widths[letters.indexOf(s.charAt(i))];
				
			}
			
			return total;
		}
		public void drawLongString(String s, int x, int y, int width, Graphics g){
			//Refer to VictoryPanel.drawLongString().
			
			String tmp = "";
			int yShift = 0, lineCount = 0, lineWidth = 0;
			ArrayList<String> words = new ArrayList<String>();
			
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == ' ' ){
					words.add(tmp);
					tmp = "";
				}
				else if(i == s.length()-1){
					tmp += s.charAt(i);
					words.add(tmp);
					tmp = "";
				}
				else{
					tmp += s.charAt(i);
				}
			}
			for(String w:words){
				if(lineCount+w.length() > width){
					yShift++;
					lineCount = 0;
					lineWidth = 0;
					g.drawString(w,x,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
				else{
					g.drawString(w,x+lineWidth,y+(yShift*17));
					lineCount += w.length();
					lineWidth += calcWidth(w)+6;
				}
			}
		}
		public void drawText(Graphics g, Point p){
			//Refer to VictoryPanel.drawText().
			Item item1 = null,item2 = null;
			g.setColor(new Color(246,190,58));
			g.setFont(calFont20);
			
			if(selectedSlot1 != null){
				int x = slots.indexOf(selectedSlot1)%12;
				int y = slots.indexOf(selectedSlot1)/12;
				if(inv1[y][x] != null){
					item1 = inv1[y][x];
				}
			}
			if(selectedSlot2 != null){
				item2 = equippedItems.get(eSlots.indexOf(selectedSlot2));
			}
			
			String[] efx;
			
			if(item1 != null){
				drawLongString(item1.getName(),587,430,25,g);	
				g.drawString(String.format("Value: %d G",(int)(item1.getValue()*0.75)),587,567);
				if(item1.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),587,495);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),587,513);
					g.drawString(tmpItem.getEffects()[0],587,531);
					g.drawString(tmpItem.getEffects()[1],587,549);
				}
				else if(item1.getType().equals("Armor")){
					Armor tmpItem = (Armor)item1;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),587,495);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel()),587,513);
					g.drawString(tmpItem.getEffects()[0],587,531);
					g.drawString(tmpItem.getEffects()[1],587,549);
				}
				else if(item1.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item1;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),587,495);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),587,513);
					g.drawString(tmpItem.getEffects()[0],587,531);
					g.drawString(tmpItem.getEffects()[1],587,549);
				}	
			}
			if(item2 != null){
				drawLongString(item2.getName(),587,175,25,g);
				g.drawString(String.format("Value: %d G",item2.getValue()),587,330);
				if(item2.getType().equals("Weapon")){
					Weapon tmpItem = (Weapon)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),587,258);
					g.drawString("Damage: "+Integer.toString(tmpItem.getDamage()),587,276);
					g.drawString(tmpItem.getEffects()[0],587,294);
					g.drawString(tmpItem.getEffects()[1],587,312);
				}
				else if(item2.getType().equals("Armor")){
					Armor tmpItem = (Armor)item2;
					g.drawString(tmpItem.getType()+"; "+tmpItem.getClassType()+"; R"+Integer.toString(tmpItem.getRarity()),587,258);
					g.drawString("Armor: "+Integer.toString(tmpItem.getArmorLevel()),587,276);
					g.drawString(tmpItem.getEffects()[0],587,294);
					g.drawString(tmpItem.getEffects()[1],587,312);
				}
				else if(item2.getType().equals("Accessory")){
					Accessory tmpItem = (Accessory)item2;
					g.drawString(tmpItem.getType()+"; R"+Integer.toString(tmpItem.getRarity()),587,258);
					g.drawString("Effect Multiplier: "+Double.toString(tmpItem.getMultiplier()),587,276);
					g.drawString(tmpItem.getEffects()[0],587,294);
					g.drawString(tmpItem.getEffects()[1],587,312);
				}
			}
		}
		
		public void drawSlots(Graphics g, Point p){
			//Refer to ShopPanel.drawSlots().
			//Only difference here is that a playerSelectedButton is also highlighted.
			
			equipButton.resetImage();
			unequipButton.resetImage();
			exitButton.resetImage();
			
			equipButton.collide(p,click);
			unequipButton.collide(p,click);
			exitButton.collide(p,click);
			
			g.drawImage(equipButton.getImage(),equipButton.getX(),equipButton.getY(),this);
			g.drawImage(unequipButton.getImage(),unequipButton.getX(),unequipButton.getY(),this);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			if(selectedSlot1 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot1.getX(),selectedSlot1.getY(),40,40);
			}
			if(selectedSlot2 != null){
				g.setColor(new Color(255,183,100,125));
				g.fillRect(selectedSlot2.getX(),selectedSlot2.getY(),40,40);
			}
			if(selectedPlayerSlot != null){
				g.setColor(Color.YELLOW);
				g.drawRect(selectedPlayerSlot.getX(),selectedPlayerSlot.getY(),120,50);
			}
			
			for(AButton tmp:eSlots){
				if(tmp.getRect().contains(p)){
					g.setColor(new Color(255,0,0,125));
					g.fillRect(tmp.getX(),tmp.getY(),40,40);
				}
			}
			for(AButton tmp:slots){
				if(tmp.getRect().contains(p)){
					g.setColor(new Color(255,0,0,125));
					g.fillRect(tmp.getX(),tmp.getY(),40,40);
				}
			}
			for(AButton tmp:playerButtons){
				if(tmp.getRect().contains(p)){
					g.setColor(new Color(255,0,0,125));
					g.fillRect(tmp.getX(),tmp.getY(),120,50);
				}
			}
		}
		public void drawItems(Graphics g, Point p){
			//Refer to ShopPanel.drawItems().
			
			for(int y = 400; y < 680; y += 40){
				for(int x = 40; x < 520; x += 40){
					if(inv1[(y-400)/40][(x-40)/40] != null){
						g.drawImage(inv1[(y-400)/40][(x-40)/40].getImage(),x,y,this);
					}
				}
			}
			for(Item i:equippedItems){
				if(i != null){
					Rectangle tmp = eSlots.get(equippedItems.indexOf(i)).getRect();
					g.drawImage(i.getImage(),(int)tmp.getX(),(int)tmp.getY(),this);
				}
			}
			if(selectedSlot1 != null){
				int x = slots.indexOf(selectedSlot1)%12;
				int y = slots.indexOf(selectedSlot1)/12;
				if(inv1[y][x] != null){
					g.drawImage(inv1[y][x].getImage(),697,571,this);
				}
			}
			if(selectedSlot2 != null){
				if(equippedItems.get(eSlots.indexOf(selectedSlot2)) != null){
					g.drawImage(equippedItems.get(eSlots.indexOf(selectedSlot2)).getImage(),697,316,this);
				}
				
			}
		}
		
		public void paintComponent(Graphics g){
			//almost the same. In this case, a different background is drawn depending on
			//which hero is currently selected.
			
			if(players.get(curPlayer).getName().equals("Knight")){
				g.drawImage(backgrounds[0],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Archer")){
				g.drawImage(backgrounds[1],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Wizard")){
				g.drawImage(backgrounds[2],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Priest")){
				g.drawImage(backgrounds[3],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Paladin")){
				g.drawImage(backgrounds[4],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Assassin")){
				g.drawImage(backgrounds[5],0,0,this);
			}
			Point p = new Point(mx,my);
			
			
			drawSlots(g,p);
			drawItems(g,p);
			drawText(g,p);
		}
		
		public void update(){
			repaint();
		}
		
		public void setPlayer(){
			//sets the 'equipped items' to the ones of the current hero being displayed.
			Player tmp = players.get(curPlayer);
			//equippedItems order:
			//0 - ring1, 1 - ring2, 2 - weap, 3 - main, 4 - secondary
			equippedItems.set(0,tmp.getRing1());
			equippedItems.set(1,tmp.getRing2());
			equippedItems.set(2,tmp.getWeap());
			equippedItems.set(3,tmp.getMain());
			equippedItems.set(4,tmp.getSecondary());
		}
		
		public boolean checkPlayer(int i){
			//checks if the player slot being selected
			//actually contains a player so that there 
			//are no null exception errors.
			if(players.get(i) != null){
				return true;
			}
			return false;
		}
			
		public void mouseClicked(MouseEvent e){
			click = true;
			Point p = new Point(mx,my);
			
			if(equipButton.collide(p,click)){
				if(selectedSlot1 != null && selectedSlot2 != null){
					//if the equip button is to work, several things must occur. First, the item slot selected
					//must actually contain an item. Second, a slot in the hero's equipped items must be selected.
					//Third, the hero must be of the right type to equip the item (which is checked in Player).
					//Finally, the slot itself must be the right one. Due to a... 'glitch', when it comes to 
					//armor and weapons, the right slot doesnt actually have to be selected, but for rings,
					//since there are 2 ring slots, the right ring slot must actually be selected.
					
					//If there is already an item equipped, then the 2 items are switched. 
					int x = slots.indexOf(selectedSlot1)%12;
					int y = slots.indexOf(selectedSlot1)/12;
					if(inv1[y][x] != null){
						
						//due to the fact that it automatically selects the item's place and replaces it,
						//if the wrong slot is selected and an item is put it can delete the previous item
						//in the right slot. Therefore, a roundabout sol'n has to be taken.
						
						if(inv1[y][x].getType().equals("Armor")){
							Armor i = (Armor)inv1[y][x];
							if(i.getPart().equals("main")){
								Item it = equippedItems.get(3);
								if(players.get(curPlayer).equip(i,0)){
									inv1[y][x] = it;
								}
							}
							else if(i.getPart().equals("secondary")){
								Item it = equippedItems.get(4);
								if(players.get(curPlayer).equip(i,0)){
									inv1[y][x] = it;
								}
							}
						}
						else if(inv1[y][x].getType().equals("Weapon")){
							Weapon i = (Weapon)inv1[y][x];
							Item it = equippedItems.get(2);
							if(players.get(curPlayer).equip(i,0)){
								inv1[y][x] = it;
							}
						}
						else if(inv1[y][x].getType().equals("Accessory")){
							Accessory i = (Accessory)inv1[y][x];
							if(players.get(curPlayer).equip(i,eSlots.indexOf(selectedSlot2)+1)){
								inv1[y][x] = equippedItems.get(eSlots.indexOf(selectedSlot2));
							}
						}
					}
				}
				//sets the player again to get the new inventory.
				setPlayer();
			}
			if(unequipButton.collide(p,click)){
				//much more straightforward than equip, all this does it remove the item from the selected
				//equipped slot and puts it into into the selected inventory slot if it's empty.
				if(selectedSlot1 != null && selectedSlot2 != null){
					int x = slots.indexOf(selectedSlot1)%12;
					int y = slots.indexOf(selectedSlot1)/12;
					if(inv1[y][x] == null && equippedItems.get(eSlots.indexOf(selectedSlot2)) != null){
						inv1[y][x] = equippedItems.get(eSlots.indexOf(selectedSlot2));
						players.get(curPlayer).unequip(equippedItems.get(eSlots.indexOf(selectedSlot2)),eSlots.indexOf(selectedSlot2)+1);
					}
				}
				//sets the player again to get the new inventory.
				setPlayer();
			}
			for(AButton tmp:eSlots){
				if(tmp.collide(p,click)){
					selectedSlot2 = tmp;
				}
			}
			for(AButton tmp:slots){
				if(tmp.collide(p,click)){
					selectedSlot1 = tmp;
				}
			}
			for(AButton tmp:playerButtons){
				//allows the player to switch between the different heroes on their team.
				if(tmp.collide(p,click)){
					if(checkPlayer(playerButtons.indexOf(tmp))){
						curPlayer = playerButtons.indexOf(tmp);
						selectedPlayerSlot = tmp;
						setPlayer();//sets the new inventory.
					}
				}
			}
			
			if(exitButton.collide(p,click)){
				//goes from InvenPanel to GamePanel.
				//sets the new inventories and resets all the selected positions.
				human_player.setInventory(inv1);
				curPlayer = 0;
				selectedPlayerSlot = playerButtons.get(0);
				selectedSlot1 = null;
				selectedSlot2 = null;
				setVisible(false);
				changeScreen("game");
			}
		}
		
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class TeamPanel extends JPanel implements MouseListener,MouseMotionListener{
		//Team Panel. Displays the stats of the different heroes and also allows the player
		//to change the team order.
		int mx,my;
		boolean click;
		ArrayList<AButton> playerButtons = new ArrayList<AButton>(), selectButtons = new ArrayList<AButton>();
		//buttons that track the heroes being selected - playerButtons track the player's actual team which
		//selectButtons track all the players.
		AButton selectedPlayerSlot, selectedSelectSlot;
		AButton exitButton,setButton, clearButton;
		Image[] player_sprites = new Image[6];
		int curPlayer;	//tracks currently selected player in the team.
		ArrayList<Player> players, allPlayers;	//players is the 4-man team, and allPlayers is the entire group of heroes(6)
		Image background = new ImageIcon("backgrounds/team.png").getImage();
		
		public TeamPanel(){
			super();
			exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
			setButton = new AButton(360,425,120,50,"Sprites/Buttons/setButtonOff.png","Sprites/Buttons/setButtonOn.png");
			clearButton = new AButton(480,425,120,50,"Sprites/Buttons/clearButtonOff.png","Sprites/Buttons/clearButtonOn.png");
			selectButtons.add(new AButton(40,558,120,120,null,null));
			selectButtons.add(new AButton(190,558,120,120,null,null));
			selectButtons.add(new AButton(331,558,120,120,null,null));
			selectButtons.add(new AButton(474,558,120,120,null,null));
			selectButtons.add(new AButton(615,558,120,120,null,null));
			selectButtons.add(new AButton(758,558,120,120,null,null));
			
			playerButtons.add(new AButton(120,71,120,120,null,null));
			playerButtons.add(new AButton(120,361,120,120,null,null));
			playerButtons.add(new AButton(678,71,120,120,null,null));
			playerButtons.add(new AButton(678,361,120,120,null,null));
			
			//sets the teams and resets the positions.
			players = human_player.getTeam();
			allPlayers = human_player.getAllPlayers();
			
			curPlayer = 0;
			
			selectedPlayerSlot = playerButtons.get(curPlayer);
			selectedSelectSlot = selectButtons.get(0);
			
			loadSprites();
			
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
		}
		
		public void loadSprites(){
			//loads in the sprites of the different heroes.
			try{
				player_sprites[0] = new ImageIcon("game sprites/characters/knight/knight_idle_right.png").getImage();
				player_sprites[1] = new ImageIcon("game sprites/characters/archer/archer_idle_right.png").getImage();
				player_sprites[2] = new ImageIcon("game sprites/characters/wizard/wizard_idle_right.png").getImage();
				player_sprites[3] = new ImageIcon("game sprites/characters/priest/priest_idle_right.png").getImage();
				player_sprites[4] = new ImageIcon("game sprites/characters/paladin/paladin_idle_right.png").getImage();
				player_sprites[5] = new ImageIcon("game sprites/characters/assassin/assassin_idle_right.png").getImage();
			}
			catch(Exception except){
				System.out.println("Error occurred in TeamPanel.loadSprites");
			}
		}
		
		public void drawSlots(Graphics g){
			//draws in the slots. Although fairly different from the other panels, it's only because the positions 
			//are significantly different. Otherwise, it's the same logic as ShopPanel or InvenPanel.drawSlots().
			Point p = new Point(mx,my);
			
			exitButton.resetImage();
			setButton.resetImage();
			clearButton.resetImage();
			exitButton.collide(p,click);
			setButton.collide(p,click);
			clearButton.collide(p,click);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			g.drawImage(setButton.getImage(),setButton.getX(),setButton.getY(),this);
			g.drawImage(clearButton.getImage(),clearButton.getX(),clearButton.getY(),this);
			
			g.setColor(new Color(255,0,0,125));
			for(AButton button:playerButtons){
				if(button.getRect().contains(p)){
					g.fillRect(button.getX(),button.getY(),120,120);
				}
			}
			for(AButton button:selectButtons){
				if(button.getRect().contains(p)){
					g.fillRect(button.getX(),button.getY(),120,120);
				}
			}
			
			g.setColor(new Color(255,183,100,125));
			g.fillRect(selectedPlayerSlot.getX(),selectedPlayerSlot.getY(),120,120);
			g.fillRect(selectedSelectSlot.getX(),selectedSelectSlot.getY(),120,120);
		}
		
		public void drawImages(Graphics g){
			//goes through each of the slots for the team select and draws the currently selected
			//hero at that spot.
			for(int i = 0; i < 4; i++){
				if(players.get(i) != null){
					Image img = null;
					if(players.get(i).getName().equals("Knight")){
						img = player_sprites[0];
					}
					else if(players.get(i).getName().equals("Archer")){
						img = player_sprites[1];
					}
					else if(players.get(i).getName().equals("Wizard")){
						img = player_sprites[2];
					}
					else if(players.get(i).getName().equals("Priest")){
						img = player_sprites[3];
					}
					else if(players.get(i).getName().equals("Paladin")){
						img = player_sprites[4];
					}
					else if(players.get(i).getName().equals("Assassin")){
						img = player_sprites[5];
					}
					g.drawImage(img,playerButtons.get(i).getX(),playerButtons.get(i).getY(),this);
				}
			}
		}
		public int calcWidth(String s){
			//Refer to VictoryPanel.calcWidth().
			//Approximate pixel dimensions for calibri 20pt:
			int[] widths = {12,11,11,12,9,10,13,11,5,6,11,9,16,13,13,11,15,12,10,11,13,13,19,11,12,10,9,11,9,10,10,7,9,10,5,5,9,4,15,10,10,11,10,8,9,7,10,11,16,10,11,10,4};
			int height = 14;
			String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'";
			
			int total = 0;
			
			for(int i = 0; i < s.length(); i++){
				total += widths[letters.indexOf(s.charAt(i))];
				
			}
			
			return total;
		}
		
		public void drawText(Graphics g){
			//draws the info of the currently selected hero out of the list the contains all 6 players.
			//all of this is just getting player stats and displaying them.
			//displays the total, then displays the base + equipment bonuses.
			g.setColor(new Color(246,190,58));
			g.setFont(calFont20);
			
			if(selectedSelectSlot != null){
				Player tmpPlayer = allPlayers.get(selectButtons.indexOf(selectedSelectSlot));
				g.drawString(tmpPlayer.getName(),481-(calcWidth(tmpPlayer.getName())/2),120); //centers the text
				g.drawString(String.format("Level %d",tmpPlayer.getLevel()),343,176);
				g.drawString(String.format("Armor Class: %s",tmpPlayer.getWeight()),343,192);
				g.drawString(String.format("Attack Type: %s",tmpPlayer.getAttackType()),343,208);
				g.drawString(String.format("HP: %d[%d+%d]",tmpPlayer.getTrueMaxHp(),tmpPlayer.getHp(),tmpPlayer.getBonusHp()),343,234);
				g.drawString(String.format("MP: %d[%d+%d]",tmpPlayer.getTrueMaxMana(),tmpPlayer.getMana(),tmpPlayer.getBonusMana()),343,250);
				g.drawString(String.format("MP Regen: %d",tmpPlayer.getManaRegen()),343,266);
				g.drawString(String.format("Def: %d[%d+%d]",tmpPlayer.getTrueDef(),tmpPlayer.getDef(),tmpPlayer.getArmor()),343,282);
				g.drawString(String.format("Damage: %d[%d+%d] ",tmpPlayer.getTrueDamage(),tmpPlayer.getDamage(),tmpPlayer.getWeaponDmg()),343,298);
				g.drawString(String.format("Crit Chance: %.2f[%.2f+%.2f]",tmpPlayer.getTrueCritChance(),tmpPlayer.getCritChance(),tmpPlayer.getBonusCritChance()),343,314);
				g.drawString(String.format("Crit Damage: %.2f[%.2f+%.2f]",tmpPlayer.getTrueCritDmg(),tmpPlayer.getCritDmg(), tmpPlayer.getBonusCritDmg()),343,330);
				g.drawString(String.format("Evasion Chance: %.2f[%.2f+%.2f]",tmpPlayer.getTrueEvasion(),tmpPlayer.getEvasion(),tmpPlayer.getBonusEvasion()),343,346);
				g.drawString(String.format("Atk Spd: %.2f[%.2f+%.2f]",tmpPlayer.getTrueAtkSpd(),tmpPlayer.getAtkSpd(),tmpPlayer.getBonusAtkSpd()),343,364);
			}
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(background,0,0,this);
			
			drawSlots(g);
			drawImages(g);
			drawText(g);
		}
		
		public void update(){
			repaint();
		}
		
		public boolean checkPlayer(int i){
			if(players.get(i) != null){
				return true;
			}
			return false;
		}
		
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			for(AButton button:playerButtons){
				//lets the player switch between selected team slots.
				if(button.collide(p,click)){
					curPlayer = playerButtons.indexOf(button);
					selectedPlayerSlot = button;
				}
			}
			for(AButton button:selectButtons){
				//lets the player switch between different slots for the list containing all heroes.
				if(button.collide(p,click)){
					selectedSelectSlot = button;
				}
			}
			if(setButton.collide(p,click)){
				//set changes the team slot to the selected hero in the allPlayers list. It only
				//sets the hero if the hero is not already in the roster to prevent doubles.
				boolean used = false;
				if(selectedPlayerSlot != null && selectedSelectSlot != null){
					Player selectedSlot = allPlayers.get(selectButtons.indexOf(selectedSelectSlot));
					for(int i = 0; i < 4; i++){
						if(players.get(i) != null){
							if(players.get(i).getName().equals(selectedSlot.getName())){
								used = true;
							}
						}
					}
					if(!used){
						Player tmp = players.get(playerButtons.indexOf(selectedPlayerSlot));
						players.set(playerButtons.indexOf(selectedPlayerSlot),selectedSlot);
					}
				}
			}
			if(clearButton.collide(p,click)){
				//clear just sets the selected team roster spot to empty. It does not allow spot 1
				//to be erased to ensure that the team is never empty.
				if(selectedPlayerSlot != null){
					if(playerButtons.indexOf(selectedPlayerSlot) != 0){
						players.set(playerButtons.indexOf(selectedPlayerSlot),null);
					}
				}
			}
			if(exitButton.collide(p,click)){
				human_player.setTeam(players);
				setVisible(false);
				changeScreen("game");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	
	class SkillsPanel extends JPanel implements MouseListener,MouseMotionListener{
		//SkillsPanel. Displays the different attack skills of the heroes in the player's team.
		//Fairly straightforward code - all of the things used for this panel have been used
		//in the other panels.
		int mx,my;
		boolean click;
		ArrayList<AButton> playerButtons = new ArrayList<AButton>();
		int curPlayer;
		ArrayList<Player> players;
		Image[] backgrounds = new Image[6];
		AButton exitButton;
		
		public SkillsPanel(){
			super();
			
			playerButtons.add(new AButton(39,37,120,50,null,null));
			playerButtons.add(new AButton(39,97,120,50,null,null));
			playerButtons.add(new AButton(39,156,120,50,null,null));
			playerButtons.add(new AButton(39,217,120,50,null,null));
			exitButton = new AButton(0,0,40,40,"Sprites/Buttons/exitButtonOff.png","Sprites/Buttons/exitButtonOn.png");
			
			players = human_player.getTeam();
			curPlayer = 0;
			
			loadBackgrounds();
	
			addMouseListener(this);
			addMouseMotionListener(this);
			
			click=false;
			
			setVisible(false);
	
		}
		
		public void loadBackgrounds(){
			try{
				backgrounds[0] = new ImageIcon("backgrounds/skills_knight.png").getImage();
				backgrounds[1] = new ImageIcon("backgrounds/skills_archer.png").getImage();
				backgrounds[2] = new ImageIcon("backgrounds/skills_wizard.png").getImage();
				backgrounds[3] = new ImageIcon("backgrounds/skills_priest.png").getImage();
				backgrounds[4] = new ImageIcon("backgrounds/skills_paladin.png").getImage();
				backgrounds[5] = new ImageIcon("backgrounds/skills_assassin.png").getImage();
			}
			catch(Exception except){
				System.out.println("Error in SkillsPanel.loadBackgrounds");
			}
		}
		
		public void drawSlots(Graphics g){
			//Refer to InvenPanel.drawSlots() - changing selected hero 
			Point p = new Point(mx,my);
			
			exitButton.resetImage();
			exitButton.collide(p,click);
			g.drawImage(exitButton.getImage(),exitButton.getX(),exitButton.getY(),this);
			
			g.setColor(new Color(255,0,0,125));
			for(AButton button:playerButtons){
				if(button.getRect().contains(p)){
					g.fillRect(button.getX(),button.getY(),120,50);
				}
			}
			g.setColor(new Color(255,183,100,125));
			g.fillRect(playerButtons.get(curPlayer).getX(),playerButtons.get(curPlayer).getY(),120,50);
		}
		
		public void paintComponent(Graphics g){
			//draws a different BG depending on selected hero
			if(players.get(curPlayer).getName().equals("Knight")){
				g.drawImage(backgrounds[0],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Archer")){
				g.drawImage(backgrounds[1],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Wizard")){
				g.drawImage(backgrounds[2],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Priest")){
				g.drawImage(backgrounds[3],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Paladin")){
				g.drawImage(backgrounds[4],0,0,this);
			}
			else if(players.get(curPlayer).getName().equals("Assassin")){
				g.drawImage(backgrounds[5],0,0,this);
			}
			drawSlots(g);
		}
		
		public void update(){
			repaint();
		}
		
		public boolean checkPlayer(int i){
			//refer to InvenPanel.checkPlayer()
			if(players.get(i) != null){
				return true;
			}
			return false;
		}
		
		public void mouseClicked(MouseEvent e){
			Point p = new Point(mx,my);
			click = true;
			for (AButton button: playerButtons){
				//simply changes the hero
				if(button.collide(p,click)){
					if(checkPlayer(playerButtons.indexOf(button))){
						curPlayer = playerButtons.indexOf(button);
					}
				}
			}
			if(exitButton.collide(p,click)){
				setVisible(false);
				curPlayer = 0;
				changeScreen("game");
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){click=true;}
		public void mouseReleased(MouseEvent e){click=false;}
		public void mouseDragged(MouseEvent e){mx=e.getX();my=e.getY();}
		public void mouseMoved(MouseEvent e){mx=e.getX();my=e.getY();}
	}
	//!!! NEW ITEMS NEW ITEMS !!!
	class Battle{
		int area, waves, maxEnemies, money;
		int[] itemsWon;
		ArrayList<Integer> enemySpawns, enemyPerRound;
		public Battle(int a, int w, int me, ArrayList<Integer> es, ArrayList<Integer>epr,int m,int[]iw){
			area = a;
			waves = w;
			maxEnemies = me;
			enemySpawns = es;
			enemyPerRound = epr;
			money = m;
			itemsWon = iw;
		}
		public int getArea(){
			return area;
		}
		public int getWaves(){
			return waves;
		}
		public int maxEnemies(){
			return maxEnemies;
		}
		public int getMoney(){
			return money;
		}
		public int[] itemsWon(){
			return  itemsWon;
		}
		public ArrayList<Integer> getEnemySpawns(){
			return enemySpawns;
		}
		public ArrayList<Integer> getEnemyPerRound(){
			return enemyPerRound;
		}
	}
	//!!!
	
	class TruePlayer{
		//TruePlayer. Class containing all the info that is tracked throughout the game that may be required elsewhere,
		//such as team roster, the different enemies, money, inventory, as well as file IO.
		int money;
		ArrayList<Player> allPlayers = new ArrayList<Player>(), current_team = new ArrayList<Player>();
		Item[][] player_inventory = new Item[7][12];
		LootGenerator loot = new LootGenerator();
		ArrayList<Enemy> baseEnemies1 = new ArrayList<Enemy>(), baseEnemies2 = new ArrayList<Enemy>(),
		baseEnemies3 = new ArrayList<Enemy>(),baseEnemies4 = new ArrayList<Enemy>(),
		baseEnemies5 = new ArrayList<Enemy>();
		
		// !!! NEW ITEMS NEW ITEMS !!!
		int progress;
		//!!!
		
		
		public TruePlayer(){
			for(int i = 0; i < 7; i++){
				for(int j = 0; j < 12; j++){
					player_inventory[i][j] = null;
				}
			}
			allPlayers.add(new Player("Knight",new Sprite("Knight")));
			allPlayers.add(new Player("Archer",new Sprite("Archer")));
			allPlayers.add(new Player("Wizard",new Sprite("Wizard")));
			allPlayers.add(new Player("Priest",new Sprite("Priest")));
			allPlayers.add(new Player("Assassin",new Sprite("Assassin")));
			allPlayers.add(new Player("Paladin",new Sprite("Paladin")));
			loadEquipment();
			loadStats();
			loadTeamOrder();
			
			loadEnemies();
		}
		public void loadEquipment(){
			try{
				Scanner inFile = new Scanner(new BufferedReader(new FileReader("Players/equipment.txt")));
				String l;
				String[] temp;
				Affix pre = null,suff = null;
				for(int i = 0; i < 6; i++){
					//weapon
					l = inFile.nextLine();
					if(!l.equals("x")){
						temp = l.split(" ~ ");
						Weapon weap = new Weapon(temp[0],Integer.parseInt(temp[1]),temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4]),Integer.parseInt(temp[5]),temp[6],temp[7]);
						temp = inFile.nextLine().split(" ~ ");
						pre = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
						temp = inFile.nextLine().split(" ~ ");
						suff = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]), Double.parseDouble(temp[3]));
						weap.setEffects(pre,suff);
						weap.setName(pre.getName()," "+suff.getName());
						weap.setValue(pre.getValue()+weap.getValue()+suff.getValue());
						allPlayers.get(i).equip(weap,0);
					}
					//main armor
					l = inFile.nextLine();
					if(!l.equals("x")){
						temp = l.split(" ~ ");
						Armor arm = new Armor(temp[0],Integer.parseInt(temp[1]),temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4]),Integer.parseInt(temp[5]),temp[6],temp[7],temp[8]);
						temp = inFile.nextLine().split(" ~ ");
						pre = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
						temp = inFile.nextLine().split(" ~ ");
						suff = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]), Double.parseDouble(temp[3]));
						arm.setEffects(pre,suff);
						arm.setName(pre.getName()," "+suff.getName());
						arm.setValue(pre.getValue()+arm.getValue()+suff.getValue());
						allPlayers.get(i).equip(arm,0);
					}
					//secondary armor
					l = inFile.nextLine();
					if(!l.equals("x")){
						temp = l.split(" ~ ");
						Armor arm = new Armor(temp[0],Integer.parseInt(temp[1]),temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4]),Integer.parseInt(temp[5]),temp[6],temp[7],temp[8]);
						temp = inFile.nextLine().split(" ~ ");
						pre = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
						temp = inFile.nextLine().split(" ~ ");
						suff = new Affix(temp[0],temp[1],Integer.parseInt(temp[2]), Double.parseDouble(temp[3]));
						arm.setEffects(pre,suff);
						arm.setName(pre.getName()," "+suff.getName());
						arm.setValue(pre.getValue()+suff.getValue());
						allPlayers.get(i).equip(arm,0);
					}
					//ring1
					l = inFile.nextLine();
					if(!l.equals("x")){
						temp = l.split(" ~ ");
						Accessory acc = new Accessory(temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]),Double.parseDouble(temp[3]),Integer.parseInt(temp[4]),temp[5],temp[6]);
						temp = inFile.nextLine().split(" ~ ");
						pre = new Affix("",temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]));
						temp = inFile.nextLine().split(" ~ ");
						suff = new Affix("",temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]));
						acc.setEffects(pre,suff);
						acc.setValue(pre.getValue()+suff.getValue());
						allPlayers.get(i).equip(acc,1);
					}
					//ring2
					l = inFile.nextLine();
					if(!l.equals("x")){
						temp = l.split(" ~ ");
						Accessory acc = new Accessory(temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]),Double.parseDouble(temp[3]),Integer.parseInt(temp[4]),temp[5],temp[6]);
						temp = inFile.nextLine().split(" ~ ");
						pre = new Affix("",temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]));
						temp = inFile.nextLine().split(" ~ ");
						suff = new Affix("",temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]));
						acc.setEffects(pre,suff);
						acc.setValue(pre.getValue()+suff.getValue());
						allPlayers.get(i).equip(acc,2);
					}
				}
			}
			catch(IOException except){
				System.out.println("Error in TruePlayer.loadEquips");
			}
		}
		public void loadStats(){
			int num_items;
			String l;
			String[] temp, temp2;
			Affix pre = null,suff = null;
			try{
				Scanner inFile = new Scanner(new BufferedReader(new FileReader("Players/trueplayer.txt")));
				progress = Integer.parseInt(inFile.nextLine());
				money = Integer.parseInt(inFile.nextLine());
				for(int i = 0; i < 84; i++){
					int x = i%12;
					int y = i/12;
					l = inFile.nextLine();
					if(!l.equals("x")){
						//weapon
						if(l.equals("Weapon")){
							temp = inFile.nextLine().split(" ~ ");
							Weapon weap = new Weapon(temp[0],Integer.parseInt(temp[1]),temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4]),Integer.parseInt(temp[5]),temp[6],temp[7]);
							temp2 = inFile.nextLine().split(" ~ ");
							pre = new Affix(temp2[0],temp2[1],Integer.parseInt(temp2[2]),Double.parseDouble(temp2[3]));
							temp2 = inFile.nextLine().split(" ~ ");
							suff = new Affix(temp2[0],temp[1],Integer.parseInt(temp2[2]), Double.parseDouble(temp2[3]));
							weap.setEffects(pre,suff);
							weap.setName(pre.getName()," "+suff.getName());
							weap.setValue(pre.getValue()+suff.getValue());

							player_inventory[y][x] = weap;
						}
						//armor
						else if(l.equals("Armor")){
							temp = inFile.nextLine().split(" ~ ");
							Armor arm = new Armor(temp[0],Integer.parseInt(temp[1]),temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4]),Integer.parseInt(temp[5]),temp[6],temp[7],temp[8]);
							temp2 = inFile.nextLine().split(" ~ ");
							pre = new Affix(temp2[0],temp2[1],Integer.parseInt(temp2[2]),Double.parseDouble(temp2[3]));
							temp2 = inFile.nextLine().split(" ~ ");
							suff = new Affix(temp2[0],temp2[1],Integer.parseInt(temp2[2]), Double.parseDouble(temp2[3]));
							arm.setEffects(pre,suff);
							arm.setName(pre.getName()," "+suff.getName());
							arm.setValue(pre.getValue()+suff.getValue());
							
							player_inventory[y][x] = arm;
						}
						else if(l.equals("Accessory")){
							temp = inFile.nextLine().split(" ~ ");
							Accessory acc = new Accessory(temp[0],Integer.parseInt(temp[1]),Double.parseDouble(temp[2]),Double.parseDouble(temp[3]),Integer.parseInt(temp[4]),temp[5],temp[6]);
							temp2 = inFile.nextLine().split(" ~ ");
							pre = new Affix("",temp2[0],Integer.parseInt(temp2[1]),Double.parseDouble(temp2[2]));
							temp2 = inFile.nextLine().split(" ~ ");
							suff = new Affix("",temp2[0],Integer.parseInt(temp2[1]),Double.parseDouble(temp2[2]));
							acc.setEffects(pre,suff);
							acc.setValue(pre.getValue()+suff.getValue());
							
							player_inventory[y][x] = acc;
						}
					}
				}		
			}
			catch(IOException except){
				System.out.println("Error in TruePlayer.loadInventory()");
			}
		}
		public void loadTeamOrder(){
			ArrayList<Player> c_t = new ArrayList<Player>();
			int x;
			try{
				Scanner inFile = new Scanner(new BufferedReader(new FileReader("Players/teamorder.txt")));
				for(int i = 0; i < 4; i++){
					x = Integer.parseInt(inFile.nextLine());
					if(x == -1){
						c_t.add(null);
					}
					else{
						c_t.add(allPlayers.get(x));
					}
				}
				setTeam(c_t);
			}
			catch(IOException except){
				System.out.println("Error occurred in TruePlayer.loadTeamOrder");
			}
		}
		
		public void saveAll(){
			saveTeamOrder();
			saveStats();
			saveEquipment();
			for(Player p:allPlayers){
				p.saveStats();
			}
		}
		public void saveTeamOrder(){
			try{
				PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("Players/teamorder.txt")));
				for(int i = 0; i < 4; i++){
					if(current_team.get(i) != null){
						outFile.println(Integer.toString(allPlayers.indexOf(current_team.get(i))));
					}
					else{
						outFile.println("-1");
					}
				}
				outFile.close();
			}
			catch(IOException except){
				System.out.println("Error in TruePlayer.saveTeamOrder()");
			}
		}
		public void saveStats(){
			try{
				PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("Players/trueplayer.txt")));
				outFile.println(Integer.toString(progress));
				outFile.println(Integer.toString(money));
				for(int y = 0; y < 7; y++){
					for(int x = 0; x < 12; x++){
						Item i = player_inventory[y][x];
						if(i != null){
							String final_output = "";
							Affix[] efx;
							if(i.getType().equals("Weapon")){
								Weapon weap = (Weapon)i;
								outFile.println("Weapon");
								final_output += String.format("%s ~ ",weap.getName());
								final_output += String.format("%d ~ ",weap.getTrueDamage());
								final_output += String.format("%s ~ ",weap.getClassType());
								final_output += String.format("%d ~ ",weap.getRarity());
								final_output += String.format("%f ~ ",weap.getDropChance());
								final_output += String.format("%d ~ ",weap.getTrueValue());
								final_output += "Weapon ~ ";
								final_output += weap.getImagePath();
								outFile.println(final_output);
								
								efx = weap.getAffixes();
								for(int j = 0; j<2; j++){
									final_output = String.format("%s ~ ",efx[j].getName());
									final_output += String.format("%s ~ ",efx[j].getBuff());
									final_output += String.format("%d ~ ",efx[j].getValue());
									final_output += String.format("%f",efx[j].getDropChance());
									outFile.println(final_output);
								}
							}
							else if(i.getType().equals("Armor")){
								Armor arm = (Armor)i;
								outFile.println("Armor");
								final_output += String.format("%s ~ ",arm.getTrueName());
								final_output += String.format("%d ~ ",arm.getTrueArmorLevel());
								final_output += String.format("%s ~ ",arm.getClassType());
								final_output += String.format("%d ~ ",arm.getRarity());
								final_output += String.format("%f ~ ",arm.getDropChance());
								final_output += String.format("%d ~ ",arm.getTrueValue());
								final_output += String.format("%s ~ ",arm.getPart());
								final_output += "Armor ~ ";
								final_output += arm.getImagePath();
								outFile.println(final_output);
								
								efx = arm.getAffixes();
								for(int j = 0; j<2; j++){
									final_output = String.format("%s ~ ",efx[j].getName());
									final_output += String.format("%s ~ ",efx[j].getBuff());
									final_output += String.format("%d ~ ",efx[j].getValue());
									final_output += String.format("%f",efx[j].getDropChance());
									outFile.println(final_output);
								}
							}
							else if(i.getType().equals("Accessory")){
								Accessory acc = (Accessory)i;
								outFile.println("Accessory");
								final_output += String.format("%s ~ ",acc.getTrueName());
								final_output += String.format("%d ~ ",acc.getRarity());
								final_output += String.format("%f ~ ",acc.getMultiplier());
								final_output += String.format("%f ~ ",acc.getDropChance());
								final_output += String.format("%d ~ ",acc.getTrueValue());
								final_output += "Accessory ~ ";
								final_output += acc.getImagePath();
								outFile.println(final_output);
								
								efx = acc.getAffixes();
								for(int j = 0; j<2; j++){
									final_output = String.format("%s ~ ",efx[j].getBuff());
									final_output += String.format("%d ~ ",efx[j].getValue());
									final_output += String.format("%f",efx[j].getDropChance());
									outFile.println(final_output);
								}
							}
						}
						else{
							outFile.println("x");
						}
					}
				}
				outFile.close();
			}
			catch(IOException except){
				System.out.println("Error in TruePlayer.saveStats()");
			}
		}
		public void saveEquipment(){
			try{
				PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("Players/equipment.txt")));
				for(Player p:allPlayers){
					Affix[] efx;
					String final_output;
					if(p.getWeap() != null){
						Weapon weap = p.getWeap();
						final_output = String.format("%s ~ ",weap.getName());
						final_output += String.format("%d ~ ",weap.getTrueDamage());
						final_output += String.format("%s ~ ",weap.getClassType());
						final_output += String.format("%d ~ ",weap.getRarity());
						final_output += String.format("%f ~ ",weap.getDropChance());
						final_output += String.format("%d ~ ",weap.getTrueValue());
						final_output += "Weapon ~ ";
						final_output += weap.getImagePath();
						outFile.println(final_output);
						
						efx = weap.getAffixes();
						for(int j = 0; j<2; j++){
							final_output = String.format("%s ~ ",efx[j].getName());
							final_output += String.format("%s ~ ",efx[j].getBuff());
							final_output += String.format("%d ~ ",efx[j].getValue());
							final_output += String.format("%f",efx[j].getDropChance());
							outFile.println(final_output);
						}
					}
					if(p.getWeap() == null){
						outFile.println("x");
					}
					if(p.getMain() != null){
						Armor arm = p.getMain();
						final_output = String.format("%s ~ ",arm.getTrueName());
						final_output += String.format("%d ~ ",arm.getTrueArmorLevel());
						final_output += String.format("%s ~ ",arm.getClassType());
						final_output += String.format("%d ~ ",arm.getRarity());
						final_output += String.format("%f ~ ",arm.getDropChance());
						final_output += String.format("%d ~ ",arm.getTrueValue());
						final_output += String.format("%s ~ ",arm.getPart());
						final_output += "Armor ~ ";
						final_output += arm.getImagePath();
						outFile.println(final_output);
						
						efx = arm.getAffixes();
						for(int j = 0; j<2; j++){
							final_output = String.format("%s ~ ",efx[j].getName());
							final_output += String.format("%s ~ ",efx[j].getBuff());
							final_output += String.format("%d ~ ",efx[j].getValue());
							final_output += String.format("%f",efx[j].getDropChance());
							outFile.println(final_output);
						}
					}
					if(p.getMain() == null){
						outFile.println("x");
					}
					if(p.getSecondary() != null){
						Armor arm = p.getMain();
						final_output = String.format("%s ~ ",arm.getTrueName());
						final_output += String.format("%d ~ ",arm.getTrueArmorLevel());
						final_output += String.format("%s ~ ",arm.getClassType());
						final_output += String.format("%d ~ ",arm.getRarity());
						final_output += String.format("%f ~ ",arm.getDropChance());
						final_output += String.format("%d ~ ",arm.getTrueValue());
						final_output += String.format("%s ~ ",arm.getPart());
						final_output += "Armor ~ ";
						final_output += arm.getImagePath();
						outFile.println(final_output);
						
						efx = arm.getAffixes();
						for(int j = 0; j<2; j++){
							final_output = String.format("%s ~ ",efx[j].getName());
							final_output += String.format("%s ~ ",efx[j].getBuff());
							final_output += String.format("%d ~ ",efx[j].getValue());
							final_output += String.format("%f",efx[j].getDropChance());
							outFile.println(final_output);
						}
					}
					if(p.getSecondary() == null){
						outFile.println("x");
					}
					if(p.getRing1() != null){
						Accessory acc = p.getRing1();
						final_output = String.format("%s ~ ",acc.getTrueName());
						final_output += String.format("%d ~ ",acc.getRarity());
						final_output += String.format("%f ~ ",acc.getMultiplier());
						final_output += String.format("%f ~ ",acc.getDropChance());
						final_output += String.format("%d ~ ",acc.getTrueValue());
						final_output += "Accessory ~ ";
						final_output += acc.getImagePath();
						outFile.println(final_output);
						
						efx = acc.getAffixes();
						for(int j = 0; j<2; j++){
							final_output = String.format("%s ~ ",efx[j].getBuff());
							final_output += String.format("%d ~ ",efx[j].getValue());
							final_output += String.format("%f",efx[j].getDropChance());
							outFile.println(final_output);
						}
					}					
					if(p.getRing1() == null){
						outFile.println("x");
					}
					if(p.getRing2() != null){
						Accessory acc = p.getRing2();
						final_output = String.format("%s ~ ",acc.getTrueName());
						final_output += String.format("%d ~ ",acc.getRarity());
						final_output += String.format("%f ~ ",acc.getMultiplier());
						final_output += String.format("%f ~ ",acc.getDropChance());
						final_output += String.format("%d ~ ",acc.getTrueValue());
						final_output += "Accessory ~ ";
						final_output += acc.getImagePath();
						outFile.println(final_output);
						
						efx = acc.getAffixes();
						for(int j = 0; j<2; j++){
							final_output = String.format("%s ~ ",efx[j].getBuff());
							final_output += String.format("%d ~ ",efx[j].getValue());
							final_output += String.format("%f",efx[j].getDropChance());
							outFile.println(final_output);
						}
					}
					if(p.getRing2() == null){
						outFile.println("x");
					}
				}
				outFile.close();
			}
			catch(IOException except){
				System.out.println("Error in TruePlayer.saveEquipment()");
			}
		}
		public int getMoney(){
			return money;
		}
		public void setMoney(int m){
			money = m;
		}
		public Item[][] getInventory(){
			return player_inventory;
		}
		public void setInventory(Item[][] newInven){
			player_inventory = newInven;
		}
		
		public ArrayList<Player> getTeam(){
			return current_team;
		}
		public ArrayList<Player> getAllPlayers(){
			return allPlayers;
		}
		public void setTeam(ArrayList<Player> t){
			current_team = t;
		}
		
		//Enemies
		public ArrayList<Enemy> baseEnemies1(){
			return baseEnemies1;
		}
		public ArrayList<Enemy> baseEnemies2(){
			return baseEnemies2;
		}
		public ArrayList<Enemy> baseEnemies3(){
			return baseEnemies3;
		}
		public ArrayList<Enemy> baseEnemies4(){
			return baseEnemies4;
		}
		public ArrayList<Enemy> baseEnemies5(){
			return baseEnemies5;
		}
		public void loadEnemies(){
			//1st set of enemies, total of 16
			baseEnemies1.add(new Enemy("Pirate Monkey",this));
			baseEnemies1.add(new Enemy("Pirate Brawler",this));
			baseEnemies1.add(new Enemy("Pirate Sailor",this));
			baseEnemies1.add(new Enemy("Pirate Leader",this));
			baseEnemies1.add(new Enemy("Pirate Captain",this));
			baseEnemies1.add(new Enemy("Pirate Commander",this));
			baseEnemies1.add(new Enemy("Pirate Boss",this));
			baseEnemies1.add(new Enemy("Pirate King",this));
			baseEnemies1.add(new Enemy("Elf Archer",this));
			baseEnemies1.add(new Enemy("Elf Mage",this));
			baseEnemies1.add(new Enemy("Elf Swordsman",this));
			baseEnemies1.add(new Enemy("Elf Boss",this));
			baseEnemies1.add(new Enemy("Greater Snake",this));
			baseEnemies1.add(new Enemy("Greater Viper",this));
			baseEnemies1.add(new Enemy("Medusa",this));
			baseEnemies1.add(new Enemy("Stheno the Snake Queen",this));
			
			//2nd set of enemies, total of 12
			baseEnemies2.add(new Enemy("Dwarf Swordsman",this));
			baseEnemies2.add(new Enemy("Dwarf Mage",this));
			baseEnemies2.add(new Enemy("Dwarf Axeman",this));
			baseEnemies2.add(new Enemy("Dwarf King",this));
			baseEnemies2.add(new Enemy("Scorpion",this));
			baseEnemies2.add(new Enemy("Scorpion Queen",this));
			baseEnemies2.add(new Enemy("Birdman Soldier",this));
			baseEnemies2.add(new Enemy("Birdman Chief",this));
			baseEnemies2.add(new Enemy("Eagle Sentry",this));
			baseEnemies2.add(new Enemy("Ent",this));
			baseEnemies2.add(new Enemy("Leviathan",this));
			baseEnemies2.add(new Enemy("Phoenix",this));
			
			//3rd set of enemies, total of 12
			baseEnemies3.add(new Enemy("Demon Imp",this));
			baseEnemies3.add(new Enemy("Demon Mage",this));
			baseEnemies3.add(new Enemy("Demon Warrior",this));
			baseEnemies3.add(new Enemy("Demon Berserker",this));
			baseEnemies3.add(new Enemy("Ogre Warrior",this));
			baseEnemies3.add(new Enemy("Ogre Mage",this));
			baseEnemies3.add(new Enemy("Ogre King",this));
			baseEnemies3.add(new Enemy("Dragon",this));
			baseEnemies3.add(new Enemy("Tentacle Swarm",this));
			baseEnemies3.add(new Enemy("White Dragon",this));
			baseEnemies3.add(new Enemy("Minotaur",this));
			baseEnemies3.add(new Enemy("Archdemon Lucifer",this));
		}
	}
}

