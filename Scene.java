import java.util.*;

public class Scene {

  private String name;
  private int budget;
  private ArrayList<Role> roles;
  private Room currentRoom;
  private boolean available;

  public Scene() {
    this.roles = new ArrayList<Role>();
    this.available = true;
  }

  /*--------------------------------*/

  public String getName() {
    return this.name;
  }
  public void setName(String n) {
    this.name = n;
  }

  public boolean getAvailable() {
    return this.available;
  }
  public void setAvailable(boolean state) {
    this.available = state;
  }

  public void setCurrentRoom(Room r) {
    this.currentRoom = r;
  }
  public Room getCurrentRoom() {
    return this.currentRoom;
  }
  public void setRoom(Room currRoom){
    this.currentRoom = currRoom;
  }

  public int getBudget() {
    return this.budget;
  }
  public void addBudget(int b) {
    this.budget = b;
  }

  public ArrayList<Role> getRoles() {
    return this.roles;
  }
  public ArrayList<Role> getRemainingRoles() {
    ArrayList<Role> availableRoles = new ArrayList<Role>();
    for (int i=0; i<roles.size(); i++) {
      if (roles.get(i).isAvailable()) {
        availableRoles.add(roles.get(i));
      }
    }
    return availableRoles;
  }
  public void addRoles(ArrayList<Role> roles) {
    this.roles = roles;
  }

}
