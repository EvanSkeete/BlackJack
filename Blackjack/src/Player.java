import java.util.*;

	
public class Player{
	

/******************************************************************************************************
 * 											PLAYER CLASS		
 * 													
*******************************************************************************************************/


		
		private int playerNumber; 	//Player 1, 2 etc...
		private int cash; 			//The player's cash
		private int bet; 			//The player's current bet amount
		private int insurance; 		//The player's insurance amount 
		private int numHands = 1; 	//Number of hands the player possesses (2 for split)
		public boolean isInPlay; 	//Is the player still in the current round of play

		private Hand hand1 = new Hand();
		private Hand hand2 = new Hand();

		
		/************************************************************************************************
		 * 											CONSTRUCTOR		
		 * 													
		*************************************************************************************************/		
		
		public Player(int num){
			this.playerNumber = num;
		}
		
		/************************************************************************************************
		 * 											PUBLIC METHODS		
		 * 
		 * 													
		*************************************************************************************************/	
		
		//Return the requested hand
		public Hand getHand(int n){
			switch (n){
			case 1: return hand1;
			case 2:	return hand2;
			default: throw(new IllegalArgumentException("Trying to access a hand other than 1 or 2"));
			}
		}

		//Split the hand
		public void split(){
			hand2.give(hand1.pop());
			setNumHands(2);
		}

		
		//Reset player's state for next round
		public void reset(){
			bet = 0;
			insurance = 0;
			setNumHands(1);
			isInPlay = true;
			hand1.clear();
			hand2.clear();
		}
		
		/************************************************************************************************
		 * 											GETTERS AND SETTERS		
		 * 
		 * 													
		*************************************************************************************************/
		
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

		public int getInsurance() {
			return insurance;
		}

		public void setInsurance(int insurance) {
			this.insurance = insurance;
		}

		public int getNumHands() {
			return numHands;
		}

		public void setNumHands(int numHands) {
			this.numHands = numHands;
		}

		
}
