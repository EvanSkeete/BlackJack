
public class Card{
	
	private int id;
	
	private int[] values = {
			11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10
			} ;
	
	private String[] names = {
			"Ace", "Two", "Three", "Four", "Five", "Six", 
			"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King",
			};
	
	private String suit;
	private String name;
	private int value;
	
	
	public Card(int id){
		//create the card with specified id
		this.setId(id);
		this.setSuit();
		this.setName();
		this.setValue();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		this.setSuit();
		this.setName();
		this.setValue();
	}
	
	private void setValue() {
		this.value = this.values[id%13];		
	}

	private void setName() {
		this.name = this.names[id%13];
		
	}

	private void setSuit() {
		if(this.id < 13*Deck.numDecks){
			this.suit = "Hearts";
		}
		else if(this.id < 13*2*Deck.numDecks){
			this.suit = "Diamonds";
		}
		else if(this.id < 13*3*Deck.numDecks){
			this.suit = "Spades";
		}
		else if(this.id < 13*4*Deck.numDecks){
			this.suit = "Clubs";
		}
		
	}

	public String getName() {
		return this.name;
	}

	public int getValue() {
		return value;
	}

	public String getSuit(){
		return this.suit;
	}
	
}
