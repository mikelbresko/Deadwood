import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class Setup {

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

  public static ArrayList<Room> roomParser(){

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

              //set the x and y coordinates
              NodeList setArea = eElement.getElementsByTagName("area");
              Node coord = setArea.item(0);
              Element realCoords = (Element) coord;
              newRoom.setX(Integer.parseInt(realCoords.getAttribute("x")));
              newRoom.setY(Integer.parseInt(realCoords.getAttribute("y")));

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
        //System.out.println("number of neighbors in this scene: " + n.getLength());
        for(int j = 0; j < n.getLength();j++){
          Node roomNode = n.item(j);
          Element room = (Element) roomNode;
          if(room.getNodeType() == Node.ELEMENT_NODE && !(room.getAttribute("name").equals("trailer")) && !(room.getAttribute("name").equals("office"))) {
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
            //get the part coordinates
            NodeList partCoordList = roomPart.getElementsByTagName("area");
            Node positionCoords = partCoordList.item(0);
            Element realRoleCoords = (Element) positionCoords;

            Role rmPart = new Role();
            rmPart.setOnCard(onCard);
            rmPart.setName(roomPart.getAttribute("name"));
            rmPart.setRank(Integer.parseInt(roomPart.getAttribute("level")));
            rmPart.setLine(roomPart.getElementsByTagName("line").item(0).getTextContent());
            rmPart.setX(Integer.parseInt(realRoleCoords.getAttribute("x")));
            rmPart.setY(Integer.parseInt(realRoleCoords.getAttribute("y")));
            rmPart.setAvailable(true);
            roles.add(rmPart);
          }
        }
         return roles;
      }

}
