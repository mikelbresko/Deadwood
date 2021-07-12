import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class Parse {

  // public static void main(String[] args) {
  //   ArrayList<Room> rooms = roomParse();
  //   ArrayList<Scene> scene = cardParser();
  //   printRooms(rooms);
  //   System.out.println("---------------------------------------------------");
  //   printScene(scene);
  // }

  public static ArrayList<Scene> cardParser() {

    ArrayList<Scene> deckOfScenes = new ArrayList<Scene>();

    try {

      File fXmlFile = new File("cards.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);

      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("card");

      for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

          Element eElement = (Element) nNode;
          Scene newCard = new Scene();

          newCard.setName(eElement.getAttribute("name"));
          newCard.addBudget(Integer.parseInt(eElement.getAttribute("budget")));
          NodeList roles = eElement.getElementsByTagName("part");

          //fill out the roles
          ArrayList<Role> sceneRoles = findRoles(roles,true);
          newCard.addRoles(sceneRoles);
          deckOfScenes.add(newCard);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
   return deckOfScenes;
  }

  public static ArrayList<Room> roomParse(){

        ArrayList<Room> allRooms = new ArrayList<Room>();

        try {
          File fXmlFile = new File("board.xml");
          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(fXmlFile);

          doc.getDocumentElement().normalize();

          NodeList nList = doc.getElementsByTagName("set");

          for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

              Element eElement = (Element) nNode;
              Room newRoom = new Room();

              //set the name of the room
              newRoom.setName(eElement.getAttribute("name"));

              //get neighboring rooms
              NodeList neigh = eElement.getElementsByTagName("neighbor");

              //fill out the neighbor list
              ArrayList<Room> neighbors = findNeighbors(neigh);

              //add the list of neighbors to the current room
              newRoom.setNeighbors(neighbors);

              //get the number of shots in a room
              NodeList shots = eElement.getElementsByTagName("take");
              newRoom.setStartingShots(shots.getLength());

              //get the parts
              NodeList parts = eElement.getElementsByTagName("part");
              ArrayList<Role> roomRoles = findRoles(parts,false);

              //add the newly populated room to the list of rooms on the board
              newRoom.setRoomRoles(roomRoles);
              allRooms.add(newRoom);
            }
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
       return allRooms;
      }

      public static ArrayList<Room> findNeighbors(NodeList n){
        ArrayList<Room> neighbors = new ArrayList<Room>();
        System.out.println("number of neighbors in this scene: " + n.getLength());
        for(int j = 0; j < n.getLength();j++){
          Node roomNode = n.item(j);
          Element room = (Element) roomNode;
          if(room.getNodeType() == Node.ELEMENT_NODE){
            Room newNeighbor = new Room();
            newNeighbor.setName(room.getAttribute("name"));
            neighbors.add(newNeighbor);
          }
         }
         return neighbors;
      }

      public static ArrayList<Role> findRoles(NodeList r, boolean onCard){
        ArrayList<Role> roles = new ArrayList<Role>();
        for(int k = 0; k < r.getLength(); k++){
          Node roomPartNode = r.item(k);
          Element roomPart = (Element) roomPartNode;
          if (roomPart.getNodeType() == Node.ELEMENT_NODE) {
            Role rmPart = new Role();
            rmPart.setOnCard(onCard);
            rmPart.setName(roomPart.getAttribute("name"));
            rmPart.setRank(Integer.parseInt(roomPart.getAttribute("level")));
            rmPart.setLine(roomPart.getElementsByTagName("line").item(0).getTextContent());
            roles.add(rmPart);
          }
        }
         return roles;
      }

      // public static void printRooms(ArrayList<Room> rooms){
      //
      //   System.out.println("");
      //
      //   for(int i = 0; i < rooms.size(); i++){
      //     Room tempRoom = rooms.get(i);
      //     System.out.println("Room Name: " + tempRoom.getName());
      //     System.out.println("Number of neighbors: " + tempRoom.getNeighbors().size());
      //     System.out.println("Number of parts: " + tempRoom.getRoles().size());
      //     System.out.println("Number of shots: " + tempRoom.getShotsRemaining());
      //     System.out.println("Neighbors:");
      //     ArrayList<Room> neighbors = tempRoom.getNeighbors();
      //     for(int j = 0; j < neighbors.size(); j++){
      //       Room n = neighbors.get(j);
      //       System.out.println((j+1)+") Name: "+n.getName());
      //     }
      //     ArrayList<Role> role = tempRoom.getRoles();
      //     for(int k = 0; k < role.size(); k++){
      //       Role tempRole = role.get(k);
      //       System.out.println("Role: "+tempRole.getName());
      //       System.out.println("Line: "+tempRole.getLine());
      //       if(tempRole.isOnCard() == true){
      //         System.out.println("On card");
      //       }
      //       else{
      //         System.out.println("Off card");
      //       }
      //     }
      //     System.out.println("");
      //   }
      // }
      //
      // public static void printScene(ArrayList<Scene> scene){
      //
      //   System.out.println("");
      //
      //   for(int i = 0; i < scene.size(); i++){
      //     Scene tempScene = scene.get(i);
      //     System.out.println("Scene Name: " + tempScene.getName());
      //     System.out.println("Budget: " + tempScene.getBudget());
      //     System.out.println("Number of parts: " + tempScene.getRoles().size());
      //
      //     ArrayList<Role> role = tempScene.getRoles();
      //     for(int k = 0; k < role.size(); k++){
      //       Role tempRole = role.get(k);
      //       System.out.println("Role: "+tempRole.getName());
      //       System.out.println("Line: "+tempRole.getLine());
      //       if(tempRole.isOnCard() == true){
      //         System.out.println("On card");
      //       }
      //       else{
      //         System.out.println("Off card");
      //       }
      //     }
      //     System.out.println("");
      //   }
      // }

}
