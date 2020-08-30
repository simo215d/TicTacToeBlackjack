package blackjack;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",8000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int playerNumber = in.readInt();
            System.out.println("Successfully connected to server. You are player "+playerNumber);
            if (playerNumber==1) {
                System.out.println("Waiting for player 2...");
            }
            Scanner scanner = new Scanner(System.in);
            //the game will begin.
            System.out.println(in.readUTF());
            //read game state
            System.out.println(in.readUTF());
            while (true){
                String firstMessage = in.readUTF();
                System.out.println(firstMessage);
                if (firstMessage.equals("BLACKJACK")){
                    break;
                }
                String response = scanner.next();
                out.writeUTF(response);
                if (response.equals("HIT")){
                    System.out.println(in.readUTF());
                    String wonLostContinue = in.readUTF();
                    System.out.println("WHAT DOES THE GAME SAY ABOUT MY SCORE: "+wonLostContinue);
                    if (wonLostContinue.equals("LOSE")){
                        System.out.println("haha you lost. now next players turn");
                        break;
                    }
                    if (wonLostContinue.equals("BLACKJACK")){
                        System.out.println("WOW you got \n---blackjack---\n---blackjack---\n---blackjack---\n. now next players turn");
                        break;
                    }
                }
                if (response.equals("STAND")){
                    System.out.println("Now next players turn!");
                    break;
                }
            }
            System.out.println(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}