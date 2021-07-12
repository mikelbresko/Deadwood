public class Player{

  private String name;
  private int dollars;
  private int credits;
  private int rank;
  private int score;
  private int practicePoints;
  private boolean working;
  private Work work;
  private Room currentRoom;

  /*--------------------------------*/

  public Player(String name) {//, Room trailer) {
    this.name = name;
    this.dollars = 0;
    this.credits = 0;
    this.rank = 1;
    this.score = 0;
    this.practicePoints = 0;
    this.working = false;
    this.currentRoom = null;
  }

  public String getName() {
    return this.name;
  }

  public int getRank() {
    return this.rank;
  }
  public void setRank(int r) {
    this.rank = r;
  }

  public int getDollars() {
    return this.dollars;
  }
  public void setDollars(int dollars) {
    this.dollars = dollars;
  }
  public void addDollars(int dollars) {
    this.dollars += dollars;
  }
  public int getCredits() {
    return this.credits;
  }
  public void setCredits(int credits){
    this.credits = credits;
  }
  public void addCredits(int credits) {
    this.credits += credits;
  }
  public Room getCurrentRoom() {
    return this.currentRoom;
  }
  public void setCurrentRoom(Room rm) {
    this.currentRoom = rm;
  }
  public Work getWork() {
    return this.work;
  }

  public void setWork(Work w){
    this.work = w;
  }

  public boolean checkWorking() {
    if(this.working == true) {
      return true;
    }
    return false;
  }

  public void setIsWorking(boolean isWorking) { //pass in true or false to let know if player is working
    this.working = isWorking;
  }

  public int getPracticePoints() {
    return this.practicePoints;
  }

  public void addPracticePoints() {
    this.practicePoints += 1;
  }

  public void resetPracticePoints() {
    this.practicePoints = 0;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
