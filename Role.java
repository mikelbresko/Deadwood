import java.util.*;

public class Role {

  private String name;
  private String line;
  private boolean onCard;
  private int rank;
  private int x;
  private int y;
  private boolean available;


  /*--------------------------------*/
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getLine() {
    return this.line;
  }
  public void setLine(String line) {
    this.line = line;
  }
  public int getRank() {
    return this.rank;
  }
  public void setRank(int rank) {
    this.rank = rank;
  }

  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  public int getX(){
    return this.x;
  }

  public int getY(){
    return this.y;
  }

  public boolean isAvailable() {
    return this.available;
  }
  public void setAvailable(boolean availability) {
    this.available = availability;
  }
  public boolean isOnCard() {
    return this.onCard;
  }
  public void setOnCard(boolean onCard) {
    this.onCard = onCard;
  }

}
