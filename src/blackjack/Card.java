package blackjack;

import java.io.Serializable;

public class Card implements Serializable {
    //card types
    public static String NUMBER = "NUMBER";
    public static String IMAGE = "IMAGE";
    public static String ACE = "ACE";
    //card suits
    public static String DIAMOND = "DIAMOND";
    public static String HEART = "HEART";
    public static String CLUB = "CLUB";
    public static String SPADE = "SPADE";

    private String type = "";
    private String suit = "";
    private int value = 0;

    public Card(String type, int value, String suit){
        this.type=type;
        this.value=value;
        this.suit=suit;
    }

    public String getType() {
        return type;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}
