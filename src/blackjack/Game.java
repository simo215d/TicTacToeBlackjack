package blackjack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game implements Serializable {
    private String currentPlayersTurn = "PLAYER1TURN";
    private String action = "";
    private int player1Bet = 0;
    private int player2Bet = 0;

    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> player1Hand = new ArrayList<>();
    private ArrayList<Card> player2Hand = new ArrayList<>();
    private ArrayList<Card> dealerHand = new ArrayList<>();

    public Game(){
        //this.player1Bet=player1Bet;
        //this.player2Bet=player2Bet;
        putCardsInDeck();
        shuffle();
        round1Deal();
    }

    private void round1Deal(){
        drawCard(1);
        drawCard(1);
        drawCard(2);
        drawCard(2);
        drawCard(0);
    }

    //get the card -> delete it from list -> return it. 1=player1. 2=player2. 0=dealer. for parameters.
    public Card drawCard(int player){
        int cardToDraw = new Random().nextInt(deck.size()-1 + 1);
        System.out.println("CARD: "+cardToDraw+" which is: "+deck.get(cardToDraw).getValue());
        Card drawnCard = deck.get(cardToDraw);
        deck.remove(cardToDraw);
        switch (player){
            case 1: player1Hand.add(drawnCard); break;
            case 2: player2Hand.add(drawnCard); break;
            case 0: dealerHand.add(drawnCard); break;
        }
        return drawnCard;
    }

    private void putCardsInDeck(){
        putInCardSuit(Card.HEART);
        putInCardSuit(Card.DIAMOND);
        putInCardSuit(Card.SPADE);
        putInCardSuit(Card.CLUB);
    }

    private void putInCardSuit(String suit){
        deck.add(new Card(Card.ACE, 11, suit));
        for (int i = 2; i <= 10; i++) {
            deck.add(new Card(Card.NUMBER, i, suit));
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Card(Card.IMAGE, 10, suit));
        }
    }

    private void shuffle(){
        Collections.shuffle(deck);
        int cardsTotal = 0;
        for(Card card : deck){
            System.out.println(card.getSuit()+card.getType()+card.getValue());
            cardsTotal++;
        }
        System.out.println("CARDS: "+cardsTotal);
    }

    public String getGameState() {
        String state = "__PLAYER1HAND=";
        for (int i = 0; i < player1Hand.size(); i++) {
            state+="_"+player1Hand.get(i).getValue()+player1Hand.get(i).getSuit();
        }
        state+="__";
        state+="_PLAYER2HAND=";
        for (int i = 0; i < player2Hand.size(); i++) {
            state+="_"+player2Hand.get(i).getValue()+player2Hand.get(i).getSuit();
        }
        state+="__";
        state+="_DEALERHAND=";
        for (int i = 0; i < dealerHand.size(); i++) {
            state+="_"+dealerHand.get(i).getValue()+dealerHand.get(i).getSuit();
        }
        return state;
    }

    public String checkHand(int player){
        int score = 0;
        if (player==1){
            for (Card card : player1Hand){
                score+=card.getValue();
            }
        }
        if (player==2){
            for (Card card : player2Hand){
                score+=card.getValue();
            }
        }
        if (player==0){
            for (Card card : dealerHand){
                score+=card.getValue();
            }
        }
        if (score>21){
            return "LOSE";
        } else if (score==21) {
            return "BLACKJACK";
        } else return "CONTINUE";
    }

    public void dealersTurn(){
        while (true) {
            int score = 0;
            for (Card card : dealerHand) {
                score += card.getValue();
            }
            if (score < 17) {
                drawCard(0);
            } else return;
            switch (checkHand(0)) {
                case "BLACKJACK" :
                case "LOSE" :
                    return;
            }
        }
    }

    public int getDealerScore(){
        int score = 0;
        for (Card card : dealerHand) {
            score += card.getValue();
        }
        return score;
    }

    public int getPlayerScore(int player){
        int score = 0;
        if (player==1){
            for (Card card : player1Hand) {
                score += card.getValue();
            }
        } else if (player==2){
            for (Card card : player2Hand) {
                score += card.getValue();
            }
        }
        return score;
    }
}
