package blackjack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Server {

    public static void main(String[] args) {
        int p1Bet = 0;
        int p2Bet = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server online: "+new Date().toString());
            //accept player 1 and tell him he is player1
            Socket socketPlayer1 = serverSocket.accept();
            System.out.println("Client Accepted: player1");
            DataOutputStream out1 = new DataOutputStream(socketPlayer1.getOutputStream());
            DataInputStream in1 = new DataInputStream(socketPlayer1.getInputStream());
            out1.writeInt(1);
            //accept player 2 and tell him he is player2
            Socket socketPlayer2 = serverSocket.accept();
            System.out.println("Client Accepted: player2");
            DataOutputStream out2 = new DataOutputStream(socketPlayer2.getOutputStream());
            DataInputStream in2 = new DataInputStream(socketPlayer2.getInputStream());
            out2.writeInt(2);
            //now the game begins
            Game game = new Game();
            out1.writeUTF("Player 1 ur turn now!");
            out2.writeUTF("Player 2 you must wait for your turn!");
            //print game states
            out1.writeUTF(game.getGameState());
            out2.writeUTF(game.getGameState());
            //handle player1 turn
            System.out.println("now handling player1");
            while (true){
                if (game.checkHand(1).equals("BLACKJACK")){
                    out1.writeUTF("BLACKJACK");
                    break;
                }
                if (game.checkHand(1).equals("LOSE")){
                    out1.writeUTF("LOSE");
                    break;
                }
                out1.writeUTF("HIT or STAND?");
                String response = in1.readUTF();
                System.out.println("OMG CLIENT RESPONEDE DE Dz<<<<3<3<3<3<3<3<");
                if (response.equals("HIT")){
                    game.drawCard(1);
                    out1.writeUTF(game.getGameState());
                    String wonLostContinue = game.checkHand(1);
                    out1.writeUTF(wonLostContinue);
                    if (wonLostContinue.equals("LOSE")) break;
                    if (wonLostContinue.equals("BLACKJACK")) break;
                } else if (response.equals("STAND")){
                    break;
                }
            }
            //handle player2 turn
            System.out.println("now handling player2");
            while (true){
                if (game.checkHand(2).equals("BLACKJACK")){
                    out2.writeUTF("BLACKJACK");
                    break;
                }
                if (game.checkHand(2).equals("LOSE")){
                    out1.writeUTF("LOSE");
                    break;
                }
                out2.writeUTF("HIT or STAND?");
                String response = in2.readUTF();
                System.out.println("OMG CLIENT RESPONEDE DE Dz<<<<3<3<3<3<3<3<");
                if (response.equals("HIT")){
                    game.drawCard(2);
                    out2.writeUTF(game.getGameState());
                    String wonLostContinue = game.checkHand(2);
                    out2.writeUTF(wonLostContinue);
                    if (wonLostContinue.equals("LOSE")) break;
                    if (wonLostContinue.equals("BLACKJACK")) break;
                } else if (response.equals("STAND")){
                    break;
                }
            }
            System.out.println("IT IS NOW DEALERS TURN HYYYYPE");
            game.dealersTurn();
            if (game.getDealerScore()>21){
                out1.writeUTF("Dealers turn is now done. HE GOT OVER 21 AND LOST. This is the game state: \n"+game.getGameState());
                out2.writeUTF("Dealers turn is now done. HE GOT OVER 21 AND LOST. This is the game state: \n"+game.getGameState());
            } else if (game.getDealerScore()<21){
                out1.writeUTF("Dealers turn is now done. If you got score lower than dealer then you lost btw. This is the game state: \n"+game.getGameState());
                out2.writeUTF("Dealers turn is now done. If you got score lower than dealer then you lost btw. This is the game state: \n"+game.getGameState());
            } else {
                out1.writeUTF("Dealers turn is now done. HE GOT BLACKJACK WOW. This is the game state: \n"+game.getGameState());
                out2.writeUTF("Dealers turn is now done. HE GOT BLACKJACK WOW. This is the game state: \n"+game.getGameState());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
