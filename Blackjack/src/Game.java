import java.util.*;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//
// TODO: Enforce max/min buy in
// TODO: Split up main loop into functions
// 
//
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//


public class Game {
	public static int numPlayers = 1; //set number of players
    private static Scanner input = new Scanner( System.in );

    //CONSTANTS:
    private final int maxBuyIn = 1000;
    private final int minBuyIn = 100;
    
	private Deck deck = new Deck(); //initialize a deck
	private Player dealer = new Player(0); //initialize a dealer
	private ArrayList<Player> players = new ArrayList<Player>(); //ArrayList of players
	
	private int activePlayer; //the active player 
	private boolean exit = false; //exit condition
	
	//MAIN PROGRAM
	public static void main(String[] args) {
		Game game = new Game();
		
		//WELCOME MESSAGE 
				System.out.println("_________________________");
				System.out.println("");
				System.out.println("  Welcome to BlackJack! ");		
				System.out.println("_________________________");
				System.out.println("");


				//Set number of players
				System.out.println("How many players?");
			    numPlayers = Integer.parseInt(input.nextLine());
			    game.addPlayers();

			    //Buy in:
			    for(int i=1;i<=numPlayers;i++){
					System.out.println("Player " + (i) + ", enter your buy in (" + game.minBuyIn + " - " + game.maxBuyIn + ")");
				    game.getPlayer(i).setCash(Integer.parseInt(input.nextLine()));
			    }
	    		System.out.println("");

			    
				//MAIN LOOP
			    while(!game.exit){
			    	//Check if the deck needs to be shuffled;
			    	game.deck.checkShuffle();
			    	
			    	//Place bets
			    	for(int i=1; i<=numPlayers; i++){
			    		System.out.println("Player " + i + " place your bet");
			    		game.getPlayer(i).setBet(input.nextInt());
			    	}
			    	
			    	//Deal cards
			    	for(int i=0;i<=numPlayers;i++){
			    		game.getPlayer(i).giveCard(game.deck.drawCard());
			    		game.getPlayer(i).giveCard(game.deck.drawCard());
			    	}
			    	
			    	//Calculate Sum
			    	for(int i=0; i<=numPlayers; i++){
			    		Player currentPlayer = game.getPlayer(i); //Set current player
			    		currentPlayer.calculateTotal();
			    	}
			    	
			    	//Reveal Dealer's top card:
			    	Card dealerUpCard = game.dealer.getHand(0).get(1);
			    	
	    			System.out.println("");
			    	System.out.println(
			    			"Dealer is showing " + dealerUpCard.getName() + " of " + dealerUpCard.getSuit());
	    			System.out.println("");

			    	//Reveal Player cards
			    	for(int i=1; i<=numPlayers; i++){
			    		
			    		if(game.getPlayer(i).getNumHands() == 1){
			    			
			    			Player currentPlayer = game.getPlayer(i); //keeping track of the player

			    			//Print display player's hand
			    			currentPlayer.displayHand();

			    			//Display player's totals
				    		currentPlayer.displayTotals(0);
			    		}
			    	}
			    	
			    	//Play the hands
			    	for(int i=1; i<=numPlayers; i++){
			    		
			    		Player currentPlayer = game.getPlayer(i); //Set current player
			    		System.out.println("Player " + i);
				    	
			    		//Check for insurance condition 
				    	if(dealerUpCard.getName() == "Ace" && !currentPlayer.hasBlackJack()){
				    		//TODO: Branch 1 (insurance)
				    		boolean ins = game.askIns("Insurance");
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
				    	else if(dealerUpCard.getName() == "Ace" && currentPlayer.hasBlackJack()){
				    		//TODO: Branch (even money)
				    		boolean ins = game.askIns("Even money");
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
				    		if(game.dealer.hasBlackJack()){
				    			//Branch dealer has blackjack
				    		}
				    		else{
				    			//Branch dealer has no blackjack
				    		}

				    	}
				    	
				    	//normal play no dealer blackjack
				    	else{
				    		//TODO: Branch 3 (regular play)
				    	}
				    	game.exit = true; 
			    	}
			   }
		   }

	
	
	//CONSTRUCTOR
	public Game(){
		//Create a game with n number of players
		this.players.add(this.dealer); //add dealer to player's list 
		
		}
	
	//Add the players to the game
	public void addPlayers(){
		for(int i = 1; i<=numPlayers; i++){
			this.players.add(new Player(i));
		}
	}
	
	//Access a player
		public Player getPlayer(int i){
			return this.players.get(i);
		}
		
	//Access a deck
		public Deck getDeck(){
			return this.deck;
		}
	
	public boolean askIns(String s){
		System.out.println("Dealer is showing an Ace, would you like " + s + "? (y/n)");
		try{
			char ins = input.nextLine().charAt(0);
			if(ins == 'y'){
				return true;
			}
			else if(ins == 'n'){
				return false;
			}
			else{
				System.out.println("Invalid input captured, type y or n");
				return askIns(s);	
			}
		}
		catch(Exception e){
			System.out.println("Invalid input captured, type y or n");
			return askIns(s);
		}
	}

}

