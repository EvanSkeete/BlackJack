import java.util.*;

public class Deck {
	public static int numDecks = 2;
	
	private int size = numDecks * 52; //total number of cards in the deck
	private int location = 0; //current position in the deck
	private ArrayList<Card> deck = new ArrayList<Card>(size); //the deck of cards
	
	private int shufflePosition = size/2; //position at which dealer will shuffle deck on next round
	private boolean shuffleNextRound = false; //condition to shuffle deck on next round
	
	//CONSTRUCTOR
	public Deck(){
		shuffleDeck();
	}
	
	public void shuffleDeck(){
		//shuffles the deck		
		Random gen = new Random();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for(int i = 0; i<size; i++){
		//generating unique ids
			ids.add(i); 
		}
		
	
		for(int i = 0; i<size; i++){
		//ordering the deck of unique cards
			int id = ids.get(gen.nextInt(ids.size()));
			deck.add(new Card(id));
			ids.remove(ids.indexOf(id));
		}
	}
	//Check if its time to shuffle
	public void checkShuffle(){
		if(shuffleNextRound){
			shuffleNextRound = false;
			System.out.println("SHUFFLING DECK...");
			this.shuffleDeck();
			this.location = 0;
		}
	}
	
	//Draw the top card of the deck
	public Card drawCard(){
		location ++;
		//Check if deck needs to be shuffled 
		if (location == this.shufflePosition){
			this.shuffleNextRound = true;
		}
		return deck.get(location - 1);
	}
	
	//Prints the contents of the deck
	public void printDeck(){
		for(int i = 0; i<size; i++){
			System.out.println(i+1 + " " + deck.get(i).getName() + " " + deck.get(i).getSuit());
			if(i%52 == 0){
				System.out.println("");
			}
		}
	}
	
	
	//MAIN (Test program)
	public static void main(String[] args) {
		Deck myDeck = new Deck();
		myDeck.shuffleDeck();
		myDeck.printDeck();
	}

}
