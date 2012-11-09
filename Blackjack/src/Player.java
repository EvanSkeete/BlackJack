import java.util.*;


public class Player{
	private int cash; //this is the players pool of money
	private int bet; //this is the player's current bet amount
	private int insurance; //the player's insurance amount 
	
	private ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>(); //ArrayList of player's hands
	private int numHands = 1; //Number of hands the player is playing (2 for split)
	private int playerNumber; //Player 1, Player 2 etc;
	
	//This is a nested ArrayList. Contains the (multiple) totals of values of the cards is hands 1 and 2
	private ArrayList<ArrayList<Integer>> totals = new ArrayList<ArrayList<Integer>>();
	
	
	public boolean isInPlay; //Is the player still in the current round of play
	
	//CONSTRUCTOR
	public Player(int playerNumber){
		//Add in the two hands
		ArrayList<Card> hand = new ArrayList<Card>(); //this is the player's current hand
		ArrayList<Card> hand2 = new ArrayList<Card>(); //this is the player's current hand
		this.hands.add(hand);
		this.hands.add(hand2);
		
		this.totals.add(new ArrayList<Integer>());
		this.totals.add(new ArrayList<Integer>());

		this.totals.get(0).add(0);
		this.totals.get(1).add(0);
		
		this.playerNumber = playerNumber;
	}
	
	//PUBLIC METHODS
	public void giveCard(Card card){
	//add a card to the player's hand
		this.hands.get(0).add(card);
	};
	
	public void clearHand(){
	//clear the player's hand
		this.hands.get(0).clear();
	}
	
		
	public void giveCard(Card card, int handNum){
	//add a card to the player's hand (option to choose hand 1 or 2)
		this.hands.get(handNum).add(card);
	};
	
	public void clearHand(int handNum){
	//clear the player's hand  (option to choose hand 1 or 2)
		this.hands.get(handNum).clear();
	}
	
	public ArrayList<Card> getHand(int num){
		//Return the player's hand
		return this.hands.get(num);
	}
	
	public void calculateTotal(){
		for(int i=0; i<2; i++){ //i refers to hand 1 or 2
			ArrayList<Card> currentHand = hands.get(i); //get the current hand
			int aceCount=0; //count the number of Aces
			
			
			for(int j=0; j<this.totals.get(i).size(); j++){
				this.totals.get(i).set(0, 0);
			}
			
			for(int j=0; j<currentHand.size(); j++){ //j refers to the card number in the hand
				
				try{
					this.totals.get(i).set(0, this.totals.get(i).get(0) + currentHand.get(j).getValue());
				}
				catch(IndexOutOfBoundsException e){
					this.totals.get(i).add(0, this.totals.get(i).get(0) + currentHand.get(j).getValue());
				}
				
				if(currentHand.get(j).getName() == "Ace"){
					aceCount++; //check if the current card is an ace and increment the ace count
				}
			}
			
			if(aceCount > 0){ //calculate the possible other scores for hands with aces
				for(int j=1; j<=aceCount; j++){
					try{
						this.totals.get(i).set(j, this.totals.get(i).get(j-1) - 10);
					}
					catch(IndexOutOfBoundsException e){
						this.totals.get(i).add(j, this.totals.get(i).get(j-1) - 10);
					}
				}
				
			}
			
			//Delete those entries which are greater than 21
			for(int j=0; j<this.totals.get(i).size(); j++){
				if(this.totals.get(i).get(j)>21){
					this.totals.get(i).remove(j);
				}
			}
			
		}
	}
	
	public ArrayList<ArrayList<Integer>> getTotals(){
		return this.totals;
	}
	
	public void spilt(){
	//Split the player's hands
		this.setNumHands(2);
		this.hands.get(1).add(this.hands.get(0).get(1));
		this.hands.get(0).remove(1);
	}

	//ACCESSORS
	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getNumHands() {
		return numHands;
	}

	public void setNumHands(int numHands) {
		this.numHands = numHands;
	};
	
	//Resets the player's state for the next round
	public void reset(){
		this.bet = 0;
		this.insurance = 0;
		this.numHands = 0;
		this.clearHand(0);
		this.clearHand(1);
	}
	
	//Display's the players hand
	public void displayHand(){
		System.out.println("Player " + playerNumber + " is showing: ");
		for(int j = 0; j< this.getHand(0).size(); j++){
			System.out.println(
					this.getHand(0).get(j).getName() + 
	    			" of " + this.getHand(0).get(j).getSuit());
		}
		System.out.println("");
	}
	
	//Display the totals for the player's hand
	public void displayTotals(int hand){
	ArrayList<ArrayList<Integer>> totals = this.getTotals();
	System.out.println("Player " + playerNumber + "s total is ");
	for(int j=0; j<totals.get(hand).size(); j++){
		if (totals.get(hand).get(j) <= 21){
			System.out.println(totals.get(hand).get(j));
		}
	}
	System.out.println("");	
	}
	
	//checks if the player has blackjack
	public boolean hasBlackJack(){
		if(this.getHand(0).get(0).getValue() + this.getHand(0).get(0).getValue() == 21){
			return true;
		}
		else{
			return false;
		}
	}
	
	//checks if the player's hand busts
	public boolean isBust(int hand){
		calculateTotal();
		int size = totals.get(hand).size();
		
		if(size==0){
			return true;
		}
		else{
			for(int i = 0; i<size; i++){
				System.out.println(totals.get(hand).get(i));
				if(totals.get(hand).get(i) <= 21){
					return false;
					}
			}
		return true;
		}
	}
	
	//Checks if this player is beaten by the other player, in terms of hand value
	//This is used to check the dealers hand against the players hand(s)
	public boolean isBeaten(Player player){
		//TODO implement this
		player.calculateTotal();
		calculateTotal();
		boolean beat = false;
		for(int i=0; i<2; i++){ //i refers to hand 1 or 2
			if(this.totals.get(0).get(0) < player.totals.get(0).get(0)){
				beat = true; 
			}
		}
		return beat;
	}
}
