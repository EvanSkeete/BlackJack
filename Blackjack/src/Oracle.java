import java.util.ArrayList;
import java.util.Scanner;


public class Oracle {

	
	public static void main(String[] args) {
		Game game = new Game();
		game.addPlayers();
		
		for(int i=0;i<=game.numPlayers;i++){
    		game.getPlayer(i).giveCard(game.getDeck().drawCard());
    		game.getPlayer(i).giveCard(game.getDeck().drawCard());
    		game.getPlayer(i).giveCard(game.getDeck().drawCard());
    		game.getPlayer(i).calculateTotal();
    		game.getPlayer(i).displayTotals(0);
    		System.out.println("Player " + i + " bust = " + game.getPlayer(i).isBust(0));
    	}
		
		//System.out.println(game.);
	}
	
}
