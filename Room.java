import java.lang.System.*;
import java.util.*;

public class Room {

  private String name;
  private int maxShots;
  private int shotsLeft;
  private int x;
  private int y;
  private Player[] players;
  private ArrayList<Player> currentlyInRoom;
  private ArrayList<Role> roles;
  private ArrayList<Room> adjacentRooms;
  private Scene scene;
  private boolean hasScene;

  /*--------------------------------*/

  public Room () {
    this.scene = null;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

  public boolean hasScene() {
    return this.hasScene;
  }
  public void setHasScene(boolean state) {
    this.hasScene = state;
  }
  public ArrayList<Role> getRoles() {
    return this.roles;
  }

  public void setRoomRoles(ArrayList<Role> roles) {
    this.roles = roles;
  }

  public ArrayList<Player> getPlayersInRoom() {
    for (int i=0; i<players.length; i++) {
      if (players[i].getCurrentRoom().equals(currentlyInRoom.get(i))) {
        currentlyInRoom.add(players[i]);
      }
    }

    return this.currentlyInRoom;
  }

  public Scene getCurrentScene() {
    return this.scene;
  }


  public ArrayList<Room> getNeighbors() {
    return this.adjacentRooms;
  }

  public void addNeighbor(Room n) {
    this.adjacentRooms.add(n);
  }

  public void setNeighbors(ArrayList<Room> neighbors){
    this.adjacentRooms = neighbors;
  }

  public void addScene(Scene scene) {
    this.scene = scene;
  }

  public void setStartingShots(int shots) {
    this.maxShots = shots;
    this.shotsLeft = shots;
  }

  public int getShotsLeft() {
    return this.shotsLeft;
  }

  public void removeShot() {
    this.shotsLeft -= 1;
  }

  public void resetShots() {
    this.shotsLeft = this.maxShots;
  }


  public boolean checkSceneforRole(String roleName) {
    ArrayList<Role> sceneRoles = scene.getRoles();
    for (int i = 0; i < sceneRoles.size();i++) {
      if (roleName.equals(sceneRoles.get(i).getName()) && (sceneRoles.get(i).isAvailable() == true)){
        sceneRoles.get(i).setAvailable(false);
        return true;
      }
    }
    return false;
  }

  public boolean checkRoomforRole(String roleName) {
    for (int i = 0; i < this.roles.size();i++) {
      if (roleName.equals(this.roles.get(i).getName()) && (this.roles.get(i).isAvailable() == true)) {
        this.roles.get(i).setAvailable(false);
        return true;
      }
    }
    return false;
  }

}
