import java.util.ArrayList;
import java.util.Iterator;

/*******************************************************************************************************
 * 											HAND CLASS		
 * 							
 *	- Data Structure representing a hand that can be possessed by the dealer or a player																				
 *																					
 ******************************************************************************************************/

public class Hand {
	private ArrayList<Card>	cards = new ArrayList<Card>();	// The cards in the hand
	private ArrayList<Integer>values = new ArrayList<Integer>();	// Array of possible values for the hand
	
/******************************************************************************************************
 * 											PUBLIC METHODS
 *
 *****************************************************************************************************/
	
	//Returns the number of cards in the hand
	public int getSize(){
		return cards.size();
	}
	
	//Add a card to the hand
	public void give(Card card){
		cards.add(card);
	}
	
	//Get a card
	public Card getCard(int i){
		return cards.get(i);
	}
	
	//Pop the top card from the hand
	public Card pop(){
		Card card = cards.get(cards.size()-1);
		cards.remove(cards.size()-1);
		return card;
	}
	
	//Clear the hand
	public void clear(){
		cards.removeAll(cards);
	}

	//Compute the possible values for the hand
	public void computeValues(){
		
		int aceCount = 0;	
		values.removeAll(values);
		
		//Calculate initial value counting all aces as 11
		values.add(0);
		for(int i = 0; i < cards.size(); i++){
			Card currentCard = cards.get(i);
			values.set(0, currentCard.getValue() + values.get(0));
			if(currentCard.getValue() == 11){
				aceCount++;
			}
		}
		
		//Calculate possible other values for hands with aces
		for(int i = 0; i < aceCount; i++){
			values.add(values.get(i) - 10);
		}
		
		//Delete those values that are greater than 21
		Iterator<Integer> valuesIterator = values.iterator();
		while(valuesIterator.hasNext()){
			int value = (Integer) valuesIterator.next();
			if(value > 21){
				valuesIterator.remove();
			}
		}
		
	}

	//Get the possible values for the hand
	public ArrayList<Integer> getValues(){
		computeValues();
		return values;
	}

	//Print the names of the cards in the hand
	public void printCards(){
		for(int i = 0; i < cards.size(); i++){
			printCard(i);
		}
		System.out.println("");
	}
	
	//Print the names of the cards in the hand
	public void printCard(int i){
		Card currentCard = cards.get(i);
		System.out.println(currentCard.getName() + " of " + currentCard.getSuit());
	}

	//Print the possible values of the hand
	public void printValues(){
		if(!isBust()){
			if(isBlackjack() && cards.size() == 2){
				System.out.print("Blackjack!");
			}
			else{
				System.out.print(values.get(0));
				for(int i = 1; i < values.size(); i++){
					System.out.println(" or " + values.get(i));
				}
			
			}
		}
		else{
			System.out.print("Bust!");
		}
		System.out.println("");
	}

	//Check if the hand is a blackjack
	public boolean isBlackjack(){
		return (cards.get(0).getValue() + cards.get(1).getValue()) == 21;
	}

	//Check if the hand is a bust
	public boolean isBust(){
		return values.size() == 0;
	}
}
