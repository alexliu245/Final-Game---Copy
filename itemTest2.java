/**
 * @(#)itemTest2.java
 *
 *
 * @author 
 * @version 1.00 2014/5/13
 */

import java.util.*;
import java.io.*;
import javax.swing.*;

public class itemTest2 {
	
    public itemTest2() {
    }
    
    public static void main(String[] args) {
    	ArrayList<Item> items = new ArrayList<Item>();
    	
    	Scanner kb = new Scanner(System.in);
        LootGenerator loot = new LootGenerator();
        Player play = new Player("Knight",new Sprite("Knight"));
        int choice = -1;
        
        for(int i = 1; i < 51; i++){
        	Item item = loot.generateItem(8);
        	items.add(item);
        	System.out.println(i+" "+item.getName());
        }
        while(choice != 0){
        	choice = kb.nextInt();
        	if(choice == 51){
        		play.unequip(play.getMain(),0);
        	}
        	else if(choice == 52){
        		play.unequip(play.getSecondary(),0);
        	}
        	else if(choice == 53){
        		play.unequip(play.getWeap(),0);
        	}
        	else if(choice == 54){
        		play.unequip(play.getRing1(),1);
        	}
        	else if(choice == 55){
        		play.printStats();
        	}
        	else{
        		play.equip(items.get(choice-1),1);
        		if(items.get(choice-1).getType().equals("Accessory")){
        			Accessory i = (Accessory)items.get(choice-1);
        		}
        	}
        }
    }
}
