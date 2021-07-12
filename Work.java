import java.util.*;

public class Work {

  private Room currentRoom;
  private Role playerRole;
  private Scene scene;


  /*---------------------------------*/

  public Work(Scene s,Room cR){
    this.scene = s;
    this.currentRoom = cR;
  }

  public Role getRole() {
    return this.playerRole;
  }

  public boolean isWorking() {
    return true;
  }


  // public void deleteScene() {
  //   this.scene = null;
  // }

  public void setWorking(Role role){
    this.playerRole = role;
  }

  public void setRole(Role role){
    this.playerRole = role;
  }

  public Scene getCurrentScene() {
    return this.scene;
  }

  public int act(Player p) {
    System.out.println("");
    System.out.println("Scene budget: " + this.scene.getBudget());

    int pracPoint = 0;
    int diceRoll = 0;

    if (playerRole.isOnCard() == true) {
      pracPoint = p.getPracticePoints();
      // diceRoll = pracPoint + ((int)Math.random() * ((6 - 1) + 1)) + 1;
      Random rand = new Random();
      diceRoll = pracPoint + rand.nextInt(6) + 1;
      System.out.println("Dice Roll (OC): " + diceRoll);
      System.out.println("practice (OC): " + pracPoint);
      int rawDiceRoll = diceRoll-pracPoint;
      System.out.println("Raw Dice Role (OC): " + rawDiceRoll);
      System.out.println("Practice (OC): " + pracPoint);
      if (scene.getBudget() <= diceRoll) {
        p.addCredits(2);
        System.out.println("You now have " + p.getDollars() + " dollar(s) " + " and " + p.getCredits() + " credit(s)");
        return 1;
      } else {
        return 0;
      }
    }
    else { //if acting off the card
      pracPoint = p.getPracticePoints();
      Random rand = new Random();
      diceRoll = pracPoint + rand.nextInt(6) + 1;
      System.out.println("Dice Roll (OFFC): " + diceRoll);
      System.out.println("practice (OFFC): " + pracPoint);
      int rawDiceRoll = diceRoll-pracPoint;
      System.out.println("Raw Dice Roll (OFFC): " + rawDiceRoll);
      System.out.println("Practice (OFFC): " + pracPoint);
      if (scene.getBudget() <= diceRoll) {
        p.addDollars(1);
        p.addCredits(1);
        System.out.println("You now have " + p.getDollars() + " dollar(s) " + " and " + p.getCredits() + " credit(s)");
        return 1;
      } else {
        p.addDollars(1);
        return 0;
      }
    }
  }

  public void rehearse(Player p) {
    if (p.getPracticePoints() < 5) {
      p.addPracticePoints();
    }
  }

}
