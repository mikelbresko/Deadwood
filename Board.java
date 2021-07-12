import java.util.*;

public class Board {

  private int numPlayers;
  private Player[] players;
  private Player activePlayer;
  private int currentDay;
  private ArrayList<Scene> allScenes;
  private ArrayList<Room> rooms;

  /*--------------------------------*/

  public Board(int np, Player[] p, Player firstP, int currDay, ArrayList<Scene> scenes, ArrayList<Room> r) {
    this.numPlayers = np;
    this.players = p;
    this.activePlayer = firstP;
    this.currentDay = currDay;
    this.allScenes = scenes;
    this.rooms = r;
  }


  public void endScene(Scene s) {
    System.out.println(s.getName() + " is a wrap!");
    allScenes.remove(s);
  }


  public void setActivePlayer(Player p) {
    System.out.println("made it");
    for (int i=0; i<numPlayers; i++) {
      if (p.getName().equals(players[i].getName())) {
        this.activePlayer = p;
      }
    }
  }

  public void distributeBonuses(Scene s) {
    int[] rands = new int[s.getBudget()];

    for (int i=0; i<s.getBudget(); i++) {
      Random r = new Random();
      rands[i] = r.nextInt(6) + 1;
    }
    Arrays.sort(rands);
    int randIndex = rands.length-1;
    int maxRoleIndex = s.getRoles().size()-1; // highest role rank assuming the role order on card
    int roleIndex = maxRoleIndex;

    // Take care of no one on card situation
    int bonus = 0;
    for (int i=0; i<numPlayers; i++) {
      if (players[i].checkWorking()) {
        if (players[i].getWork().getRole().isOnCard() && (players[i].getWork().getCurrentScene().getName().equals(s.getName()))) {
          bonus = 1;
        }
      }
    }
    if (bonus == 1) {
      // On card bonuses
      for (int i=rands.length-1; i>-1; i--) {
        for (int j=0; j<numPlayers; j++) {
          if (players[j].checkWorking()) {
            if (players[j].getWork().getRole().getName().equals(s.getRoles().get(roleIndex).getName())) {
              players[j].addDollars(rands[i]);
            }
          }
        }
      }
      // Off card bonuses
      Room r = s.getCurrentRoom();
      for (int i=0; i<r.getRoles().size(); i++) {
        for (int j=0; j<numPlayers; j++) {
          if (players[j].checkWorking()) {
            if (this.players[j].getWork().getRole().getName().equals(r.getRoles().get(i).getName())) {
              this.players[j].addDollars(this.players[j].getWork().getRole().getRank());
            }
          }
        }
      }

    } else {
      System.out.println("No one on card, no bonuses!");
    }
  }

  public void placeInTrailers(Room trailer) {
    for (int i=0; i<numPlayers; i++) {
      players[i].setCurrentRoom(trailer); // Initializing trail where
    }
  }
}
