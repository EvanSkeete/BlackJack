import java.util.*;


/******************************************************************************************************
 * 											GAME CLASS		
 * 
 * This is the main class for the game
 * 
 * TODO: Enforce max number of players
 * TODO: Enforce max/min buy in, don't allow negative funds
 * TODO: Split up main loop into functions
 * TODO: Allow split hands
 * TODO: Blackjack pays 3:2
 * 													
*******************************************************************************************************/

public class Game {
	//CONSTANTS:
    private final int maxBuyIn = 1000;
    private final int minBuyIn = 100;
    
	private static int numPlayers = 1;	//Number of players
    private static Scanner input = new Scanner( System.in );	//Scanner for reading input

	private Deck deck = new Deck(); //The deck
	private Player dealer = new Player(0); //The dealer
	private ArrayList<Player> players = new ArrayList<Player>(); //ArrayList of players
	
	private boolean exit = false; //exit condition
	
	/************************************************************************************************
	 * 											MAIN PROGRAM		
	 * 													
	*************************************************************************************************/		
	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}	   

	
	
	/************************************************************************************************
	 * 											CONSTRUCTOR		
	 * 													
	*************************************************************************************************/
	public Game(){
		//Create a game with n number of players
		this.players.add(this.dealer); //add dealer to player's list 
		
		}
	
	/************************************************************************************************
	 * 											PUBLIC METHODS		
	 * 													
	*************************************************************************************************/
	//Add the players to the game
	public void addPlayers(){
		for(int i = 1; i<=numPlayers; i++){
			this.players.add(new Player(i));
		}
	}
	
	//Get a Player
	public Player getPlayer(int i){
		return this.players.get(i);
	}
	
	//Hit one card
	public void hit(Player player){
		Card card = deck.drawCard();
		System.out.println("You drew a " + card.getName() + " of " + card.getSuit());
		player.getHand(1).give(card);
		player.getHand(1).computeValues();
		
		if(!player.getHand(1).isBust()){
			player.getHand(1).printValues();
		}
		else{
			System.out.println("Bust!");
		}
	}
	
	//Double down
	public void doubleDown(Player player){
		System.out.println("Double down for how much? (0 - " + player.getBet() + ")");
		try{
			int doubleAmt = input.nextInt();
			if(doubleAmt < 0 || doubleAmt > player.getBet()){
				throw(new Exception("Invalid amount"));
			}
			else{
				player.setBet(player.getBet() + doubleAmt);
			}
		}catch(Exception e){
			System.out.println("Invalid amount, try again");
			doubleDown(player);
		}
		
		
	}
	
	//Carry out one player's turn
	public void playRound(Player player){
		boolean cont = true; 
		Scanner in = new Scanner( System.in );
		int roundNumber = 0;
		while(!player.getHand(1).isBust() && cont){
			roundNumber++;
			//check for split....
		
			//options hit, stand or double
			if(roundNumber > 1){
				System.out.println("Would you like to hit or stand, h/s");
			}
			else{
				System.out.println("Would you like to hit, stand or double?, h/s/d");
			}
			
			char choice = in.nextLine().charAt(0);
			
			switch(choice){
				case 'h':
					//hit
					hit(player);
					break;
				case 's':
					//stand
					cont = false;
					break;
				case 'd':
					if(roundNumber > 1){
						System.out.println("Can't double");
						break;
					}
					else{
						//double
						doubleDown(player);
						System.out.println("Doubling down, one card only!");
						hit(player);
						cont = false;
						break;
					}
				default:
					break;
			}
			System.out.println("");
		}
	}
	
	//Check if a player beats the dealer. Returns 1 if they win, 2 if they lose, 3 if they push.
	public int checkWin(Player player){
		int playerHandValue = player.getHand(1).isBust() ? 0 : player.getHand(1).getValues().get(0);
		int dealerHandValue = dealer.getHand(1).isBust() ? 0 : dealer.getHand(1).getValues().get(0);

		if(playerHandValue > dealerHandValue){
			return 1;
		}
		else if(playerHandValue < dealerHandValue){
			return 2;
		}
		else{
			return 3;
		}	
	}
	
	/************************************************************************************************
 	* 											MAIN GAME LOOP		
 	* 													
 	*************************************************************************************************/
	public void play(){
		//Print the welcome message
		welcome();

		//Set number of players
		System.out.println("How many players?");
	    numPlayers = Integer.parseInt(input.nextLine());
	    addPlayers();
		System.out.println("");

	    //Buy in:
	    for(int i=1;i<=numPlayers;i++){
			System.out.println("Player " + (i) + ", enter your buy in (" + minBuyIn + " - " + maxBuyIn + ")");
		    getPlayer(i).setCash(Integer.parseInt(input.nextLine()));
	    }
		System.out.println("");

	    
		//MAIN LOOP
	    while(!exit){
	    	//Check if the deck needs to be shuffled;
	    	deck.checkShuffle();
	    	
	    	//Place bets
	    	for(int i=1; i<=numPlayers; i++){
	    		System.out.println("Player " + i + " place your bet");
	    		getPlayer(i).setBet(input.nextInt());
	    	}
	    	System.out.println("");
	    	
	    	for(int i = 0; i <= numPlayers; i++){
	    		Player currentPlayer = getPlayer(i); //Set current player
	    		//Draw two cards
	    		currentPlayer.getHand(1).give(deck.drawCard());
	    		currentPlayer.getHand(1).give(deck.drawCard());		
	    		currentPlayer.getHand(1).computeValues(); //compute values
	    	}
	    	
	    	//Reveal Dealer's top card:
	    	System.out.println("The dealer is showing");
	    	Card dealerUpCard = dealer.getHand(1).getCard(1);
	    	dealer.getHand(1).printCard(1);

	    	for(int i=1; i<=numPlayers; i++){
	    		
				System.out.println("********************");
		    	//Reveal Player cards
				Player currentPlayer = getPlayer(i); 
				//Print display player's hand
				System.out.println("Player " + i + " is showing:");
				currentPlayer.getHand(1).printCards();
				//Display player's totals
				System.out.println("Player " + i + "'s hand's value is:");
				currentPlayer.getHand(1).printValues();
				System.out.println("");
	    	
	    
	    	
				//Play the hands	
	    		System.out.println("");
		    	
	    		//Check for insurance condition 
		    	if(dealerUpCard.getName() == "Ace" && !currentPlayer.getHand(1).isBlackjack()){
		    		//TODO: Branch 1 (insurance)
		    		boolean ins = askIns("Insurance");
		    		if(ins){
		    			//Branch insurance
			    		System.out.println("You asked for insurance");
		    		}
		    		else{
		    			//Branch no insurance
			    		System.out.println("Ok no insurance for you");
		    		}
		    	}
		    	
		    	//check for even money condition
		    	else if(dealerUpCard.getName() == "Ace" && currentPlayer.getHand(1).isBlackjack()){
		    		//TODO: Branch (even money)
		    		boolean ins = askIns("Even money");
		    		if(ins){
		    			//Branch insurance
			    		System.out.println("You asked for even money");
		    		}
		    		else{
		    			//Branch no insurance
			    		System.out.println("Ok no even money for you");
		    		}
		    	}
		    	
		    	//check for possible dealer blackjack
		    	else if(dealerUpCard.getValue() == 10){
		    		//TODO: Branch 2 (check for blackjack)
		    		System.out.println("Dealer is checking for blackjack");
		    		if(dealer.getHand(1).isBlackjack()){
		    			//Branch dealer has blackjack
		    			System.out.println("Dealer has blackjack");
		    		}
		    		else{
		    			System.out.println("Dealer has no blackjack");
			    		playRound(currentPlayer);

		    		}

		    	}
		    	
		    	//Normal play, no dealer blackjack
		    	else{
		    		playRound(currentPlayer);
		    			
		    		}
		    	}
	    	
	    	//Dealer draws his cards
    		System.out.println("Dealer is showing: ");
    		dealer.getHand(1).printCards();

	    	dealer.getHand(1).computeValues();
	    	while(!dealer.getHand(1).isBust() && dealer.getHand(1).getValues().get(0) < 17){
	    		Card card = deck.drawCard();
	    		dealer.getHand(1).give(card);
	    		System.out.println("Dealer drew a " + card.getName() + " of " + card.getSuit());
	    		dealer.getHand(1).computeValues();
	    	}
    		System.out.println("Dealer's hand's value is: ");
	    	dealer.getHand(1).printValues();
    		System.out.println("");

    		
    		//Check wins/losses
    		for(int i = 1; i <= numPlayers; i ++){
    			Player currentPlayer = players.get(i);
    			int result = checkWin(currentPlayer);
				System.out.println("Player " + i);
    			switch (result){
	    			case 1:
	    				System.out.println("You win!");
	    				currentPlayer.setCash(currentPlayer.getCash() + currentPlayer.getBet());
	    				break;
	    			case 2:
	    				System.out.println("You lose!");
	    				currentPlayer.setCash(currentPlayer.getCash() - currentPlayer.getBet());
	    				break;
	    			case 3:
	    				System.out.println("Push");
	    				break;
    			}
    			System.out.println("Your total funds are: $" + currentPlayer.getCash());
    			System.out.println("");
    		}
    		
    		//Reset 
    		for(int i = 0; i <= numPlayers; i ++){
    			Player currentPlayer = players.get(i);
    			currentPlayer.reset();
    		}
	    }
	    
	 }
	
		

	/************************************************************************************************
	 * 											IO METHODS		
	 * 													
	 *************************************************************************************************/

	//Print the welcome message
	public void welcome(){
		System.out.println("_________________________");
		System.out.println("");
		System.out.println("  Welcome to BlackJack! ");		
		System.out.println("_________________________");
		System.out.println("");
	}
		
	//Ask the player if they want insurance OR even money
	public boolean askIns(String s){
		System.out.println("Dealer is showing an Ace, would you like " + s + "? (y/n)");
		
		try{
			char ins = input.nextLine().charAt(0);
			switch(ins){
			case 'y': 
				return true;
			case 'n': 
				return false;
			default: 
				System.out.println("Invalid input captured, type y or n");
				return askIns(s);	
			}
		}catch(Exception e){
			System.out.println("Invalid input captured, type y or n");
			return askIns(s);
		}
	}
	
	
	
}
	






