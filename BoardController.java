import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;
import java.awt.Rectangle;

public class BoardController{

  // This class implements Mouse Events
  class boardMouseListener implements MouseListener {

      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
          
        //call the act method
          if (e.getSource()== BoardView.bAct){
            System.out.println("trying to act");
            if(gameSystem.currPlayer.checkWorking() == true){
              System.out.println("Player " + gameSystem.currPlayer.getName() + " has a role!");
              gameSystem.act();
              //BoardView.addPlayerInfo(gameSystem.currPlayer);
            }
            else{
              System.out.println("Player " + gameSystem.currPlayer.getName() + "does not have a role!");
            }

          }
          //call the rehearse method
          else if (e.getSource()== BoardView.bRehearse){
            System.out.println("rehearsing");
            gameSystem.rehearse();
          }
          
          //call the move method
          else if (e.getSource()== BoardView.bMove){
            String desiredLocation = (String) BoardView.neighborOptions.getSelectedItem();//moveRoom.getText(); //get the input from text field
            gameSystem.move(gameSystem.currPlayer,desiredLocation); //move player into desired room
            System.out.println(gameSystem.currPlayer.getName() + " is now in " + gameSystem.currPlayer.getCurrentRoom().getName());

            if(desiredLocation.equals("Casting Office")){
              BoardView.changeButtons("rank",gameSystem.currPlayer);
              //gameSystem.endTurn();
            }
            else if(gameSystem.currPlayer.getCurrentRoom().hasScene() == true){
              System.out.println("shots left in room: "+gameSystem.currPlayer.getCurrentRoom().getShotsLeft());
              BoardView.changeButtons("role",gameSystem.currPlayer);
            }
            else{
              String noSceneLeft = "Sorry, no more scenes are in this room! Ending turn";
              popupNotification(4, noSceneLeft);
              gameSystem.endTurn();
            }
          }

          //call the takeRole method
          else if (e.getSource()== BoardView.bTakeRole){
            String desiredRole = (String) BoardView.roleOptions.getSelectedItem();

            gameSystem.takeRole(gameSystem.currPlayer, desiredRole);

            if(gameSystem.currPlayer.checkWorking() == true){
              System.out.println(gameSystem.currPlayer.getName() + " is now potraying " + gameSystem.currPlayer.getWork().getRole().getName());
              BoardView.changeButtons("act",gameSystem.currPlayer);
              gameSystem.endTurn();
            }
            else{
              System.out.println("try picking again");
              popupNotification(1,"");
            }
          }
          //call the rank Method
          else if(e.getSource() == BoardView.bUpgrade){
            System.out.println("About to rank up");
            BoardView.rankUp(gameSystem.currPlayer);
          }
 
          else if (e.getSource()== BoardView.bEndTurn){
            //call the endTurn method
            System.out.println("Switching players");
            gameSystem.endTurn();
          }
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }

  }

  /* Error Codes:
      1 - role is unavailable
      2 - you are not a high enough rank for this role!
      3 - not enough resources to buy that role! //sidenote: maybe dont show the upgrade options that are unavailable
      4 - Scene is over, players reset to trailer
      5 - End of day
      6 - endgame
  */
  public static void popupNotification(int popupCode, String message){
    String noRole = "Sorry, role is unavailable";
    String notFamousEnough = "Sorry, you need to be a better actor for that role";
    String broke = "Sorry, not enough resources to buy that rank"; //might not need this if we only give them viable options

    if(popupCode == 1){
      BoardView.alertOfError(noRole);
    }
    else if(popupCode == 2){
      BoardView.alertOfError(notFamousEnough);
    }
    else if(popupCode == 3){
      BoardView.alertOfError(broke);
    }
    else{
      BoardView.alertOfError(message);
    }
  }

  public static int getNumberOfPlayers(){
    return BoardView.askNumPlayers();
  }
  public static void createPlayerInfo(Player p){
    BoardView.addPlayerInfo(p);
  }
  public static void updateInfo(Player p){
    BoardView.updateLabelInfo(p);
    BoardView.updateNeighboringRooms(p);
    //BoardView.addPlayerInfo(p);
  }

public static void rankPlayer(Player p, String chosenRank, String chosenPaymentMethod){
  
}

  public static void thanos(){

  }

}
