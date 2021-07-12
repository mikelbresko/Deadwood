import java.util.*;

public class gameSystem {

  private static Player[] players;
  private static ArrayList<Scene> deckOfScenes;
  private static ArrayList<Scene> tempScenes;
  private static Board board;
  private static ArrayList<Room> rooms;


  public static Player currPlayer;
  public static BoardView gameBoard;

  public static int numScenesLeft,
                    numPlayers,
                    day,
                    p;

  public static String roomName;


  public static void main(String[] args) {
    numPlayers = BoardController.getNumberOfPlayers();
    players =  new Player[numPlayers];
    setUp(numPlayers);
    gameBoard = new BoardView();
    BoardController.createPlayerInfo(currPlayer);

    //play();
  }

  public static void setUp(int numberOfPlayers) {
    deckOfScenes = Setup.cardParser();
    rooms = Setup.roomParser();
    for (int i=0; i<rooms.size(); i++) {
      rooms.get(i).setHasScene(true);
    }

    //initialize initial players
    for (int h = 0; h < numberOfPlayers; h++) {
      Player newPlayer =  new Player(Integer.toString(h+1));
      players[h] = newPlayer;
    }

    //This will change as trailer will be created when parsing board.xml
    Room trailer = new Room();
    trailer.setName("Trailer");
    rooms.add(trailer);
    Room castOffice = new Room();
    castOffice.setName("Casting Office");
    rooms.add(castOffice);

    //set up Trailer & Casting Office
    ArrayList<Room> tNeighbors = new ArrayList<Room>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getName().equals("Main Street")) {
        tNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(trailer);
      }
      else if (rooms.get(i).getName().equals("Saloon")) {
        tNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(trailer);
      }
      else if (rooms.get(i).getName().equals("Hotel")) {
        tNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(trailer);
      }
    }
    // Set up Casting Office
    ArrayList<Room> cNeighbors = new ArrayList<Room>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getName().equals("Train Station")) {
        cNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(castOffice);
      }
      else if (rooms.get(i).getName().equals("Ranch")) {
        cNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(castOffice);
      }
      else if (rooms.get(i).getName().equals("Secret Hideout")) {
        cNeighbors.add(rooms.get(i));
        rooms.get(i).addNeighbor(castOffice);
      }
    }

    Random rand = new Random();
    p = rand.nextInt(numberOfPlayers);
    System.out.println("p = "+p + "Player name is " + players[p].getName());
    currPlayer = players[p];

    System.out.println("");

    day = 1;
    tempScenes = roomsSetScenes(deckOfScenes, rooms);
    numScenesLeft = tempScenes.size();

    rooms.add(trailer);
    rooms.add(castOffice);
    trailer.setNeighbors(tNeighbors);
    castOffice.setNeighbors(cNeighbors);
    trailer.setHasScene(false);
    castOffice.setHasScene(false);
    board = new Board(players.length, players, currPlayer, day, deckOfScenes, rooms);
    board.placeInTrailers(getTrailer(rooms));
  }



  public static Role desiredPart(String roleName,ArrayList<Role> roles){
      for(int i = 0; i < roles.size(); i++){
        if(roleName.equals(roles.get(i).getName())){
          return roles.get(i);
        }
      }
      System.out.println("Role not found!");
      return null;
  }

  public static ArrayList<Scene> roomsSetScenes(ArrayList<Scene> scenes, ArrayList<Room> rooms){
    Random randScene = new Random();
    ArrayList<Scene> tempScenes = new ArrayList<Scene>();
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getName().equals("Trailer") || (rooms.get(i).getName().equals("Casting Office"))) {
        //Nothing
      }
      else {
        int sceneNum =  randScene.nextInt(scenes.size());
        rooms.get(i).addScene(scenes.get(sceneNum));
        scenes.get(sceneNum).setCurrentRoom(rooms.get(i));
        tempScenes.add(rooms.get(i).getCurrentScene());
      }
    }
    return tempScenes;
  }

  //function to check if room being moved into is adjacent to room currently in
  public static boolean isNeigbor(String roomName, ArrayList<Room> neighbors){
    for (int i = 0; i < neighbors.size(); i++) {
      if (roomName.equals(neighbors.get(i).getName())) {
        return true;
      }
    }
    return false;
  }

  //loop through arry list to get trailer
  public static Room getTrailer(ArrayList<Room> rooms){
    for (int i = 0; i < rooms.size(); i++) {
      if (rooms.get(i).getName().equals("Trailer")) {
        return rooms.get(i);
      }
    }
    return null;
  }
//-----------------------------------BoardController functions------------------------------------------------------//
/*
  functions needed for board controller:
    1)Move - Finish updating view
    2)Take Role - Finish updateing view
    3)Act - DONE - Finish updateing view
    4)Rehearse - DONE
    5)Upgrade
    5)End turn - DONE - Finish updateing view
*/

public static void move(Player currentP, String movingTo){

  ArrayList<Room> currentRoomNeighbors = currentP.getCurrentRoom().getNeighbors();
  int validRoom = 0;

  for (int i=0; i<currentRoomNeighbors.size(); i++) {
    if (currentRoomNeighbors.get(i).getName().equals(movingTo)) {
      validRoom = 1;
    }
  }
  if (validRoom != 1) {
    System.out.println("Sorry, not an adjacent room. Try again."); //change to popup window
  }else{

    System.out.println("");
    ArrayList<Room> newRoomNeighbors = currPlayer.getCurrentRoom().getNeighbors();

    // Check for valid room and find room object
    Room r = new Room();
    for (int i=0; i<rooms.size(); i++) {
      if (movingTo.equals(rooms.get(i).getName()) && (isNeigbor(movingTo,newRoomNeighbors) == true)) {
        r = rooms.get(i);
        currentP.setCurrentRoom(r); //set the player in the adjacent room
        // gameBoard.addPlayerInfo();
        i = rooms.size(); //break out of the loop
      }
    }
  }
  if(currentP.getCurrentRoom().getShotsLeft() != 0){
    BoardController.updateInfo(currentP);
  }
  else if(!(currentP.getCurrentRoom().getName().equals("Casting Office"))){
    endTurn();
  }
}

public static void takeRole(Player p, String requestedRole){

  Room r = p.getCurrentRoom();

  // Print roles
  System.out.println("Roles off card: ");
  for (int i=0; i<p.getCurrentRoom().getRoles().size(); i++) {
    System.out.println(p.getCurrentRoom().getRoles().get(i).getName());
  }
  System.out.println("");
  System.out.println("Roles on card: ");
  for (int i=0; i<p.getCurrentRoom().getCurrentScene().getRoles().size(); i++) {
    System.out.println(p.getCurrentRoom().getCurrentScene().getRoles().get(i).getName());
  }//----------------
  System.out.println("");

  System.out.println("Desired role: " + requestedRole);
  int validRole = 0;

  String roleName = requestedRole;//roleChoice.nextLine();

  if(roleName.equals("done")){
    validRole = 1;
    System.out.println("choosing not to work, ending turn");
  }
  else if(r.checkSceneforRole(roleName) == true){ // and role is available?
    validRole = 1;
    Scene desiredScene = p.getCurrentRoom().getCurrentScene(); //get the scene with the desired role
    ArrayList<Role> desiredSceneRoles = desiredScene.getRoles(); //get the list of roles from the scene
    Role desiredRole = desiredPart(roleName,desiredSceneRoles); //get the specific role
    currPlayer.setIsWorking(true);
    Work newJob = new Work(p.getCurrentRoom().getCurrentScene(),p.getCurrentRoom()); //must create constructor
    newJob.setWorking(desiredRole);
    p.setWork(newJob);
    System.out.println("Now working on " + p.getWork().getRole().getName());
  }
  else if (r.checkRoomforRole(roleName) == true) {
    Room currRoom = currPlayer.getCurrentRoom(); //get the scene with the desired role
    ArrayList<Role> desiredRoomRoles = currRoom.getRoles(); //get the list of roles from the scene
    if (desiredPart(roleName,desiredRoomRoles).getRank() > currPlayer.getRank()) {
      System.out.println("");
      System.out.println("Sorry, you're not skilled enough yet! Choose another roles (Input 'done' if you just want to end your turn).");
    }
    else {
      validRole = 1;
      Role desiredRole = desiredPart(roleName,desiredRoomRoles); //get the specific role
      currPlayer.setIsWorking(true);
      Work newJob = new Work(currPlayer.getCurrentRoom().getCurrentScene(),currPlayer.getCurrentRoom()); //must create constructor
      newJob.setRole(desiredRole);
      currPlayer.setWork(newJob);
      System.out.println("Now working on " + currPlayer.getWork().getRole().getName());
    }
  }

  else{ //TODO: Make this into a popup window on the game board
    System.out.println(" ");//(Input 'done' if you would like to not choose role and end turn)!");
  }
  BoardController.updateInfo(p);
}// last curly brace of takeRole

// public static void upgrade(){
//   if (currPlayer.getCurrentRoom().getName().equals("Casting Office")) {
//     CastOffice.rankUp(currPlayer);
//   }
//   else{
//     //show a popup that they cant rank up
//   }
//   BoardController.updateInfo(currPlayer);
// }


public static void act(){
  int result = currPlayer.getWork().act(currPlayer);
  if (result == 1) {
    System.out.println("Congratulations, you're a star!");
    if (currPlayer.getCurrentRoom().getShotsLeft() > 0) {
      currPlayer.getCurrentRoom().removeShot();
      System.out.println("Shots Left: " + currPlayer.getCurrentRoom().getShotsLeft());
      System.out.println("");
        //if the scene has ended
        if (currPlayer.getCurrentRoom().getShotsLeft() == 0) {
          currPlayer.getCurrentRoom().resetShots();
          board.distributeBonuses(currPlayer.getCurrentRoom().getCurrentScene());
          board.endScene(currPlayer.getCurrentRoom().getCurrentScene()); //THIS IS WHERE WE END A SCENE
          numScenesLeft--;
          String message = "Only " + numScenesLeft + " scenes left!";
          BoardController.popupNotification(4, message);
          currPlayer.getCurrentRoom().setHasScene(false);
          currPlayer.getCurrentRoom().getCurrentScene().setAvailable(false);
          deckOfScenes.remove(currPlayer.getCurrentRoom().getCurrentScene());

          /*
          put all of those working on the scene back in the trailer and reset view

          */
          //add end of day check
          if(numScenesLeft == 1){
            day++;

            // Find Trailer
            Room t = new Room();
            for (int i=0; i<rooms.size(); i++) {
              if (rooms.get(i).getName().equals("Trailer")) {
                t = rooms.get(i);
              }
            }
            board.placeInTrailers(t);
            tempScenes = roomsSetScenes(deckOfScenes, rooms);
            numScenesLeft = tempScenes.size();

            // Reset off card roles
            for (int i=0; i<rooms.size(); i++) {
              if (rooms.get(i).getName().equals("Trailer") || (rooms.get(i).getName().equals("Trailer"))) {

              } else {
                for (int r=0; r>rooms.get(i).getRoles().size(); r++) {
                  rooms.get(i).getRoles().get(r).setAvailable(true);
                }
              }
            }

            // Reset player works
            for (int i=0; i<numPlayers; i++) {
              players[i].setIsWorking(false);
              players[i].setWork(null);
            }

            if(day == 4){
              //endGame
              BoardController.thanos();
            }
            return;
          }
          for (int i=0; i < numPlayers; i++) {
            if (players[i].checkWorking()) {
              if (players[i].getWork().getCurrentScene().getName().equals(currPlayer.getCurrentRoom().getCurrentScene().getName())) {
                players[i].setIsWorking(false);
              }
            }
          }
        }
    } else {
      System.out.println("You didn't quite cut it this time. Maybe later...");
    }
  }
  BoardController.updateInfo(currPlayer);
  if(!(currPlayer.getCurrentRoom().getName().equals("Casting Office"))){
    endTurn();
  }
}//last curly brace of act

public static void rehearse(){
  currPlayer.getWork().rehearse(currPlayer);
  BoardController.updateInfo(currPlayer);
}//last curly brace of rehearse


public static void endTurn(){
  BoardView.changeButtons("move",players[p]);
  System.out.println("ending turn");
  int numPlayers = players.length;
  Player temPlayer;

  // // Get random number = i;
  // Random rand = new Random();
  // int p = rand.nextInt(numPlayers);
  // System.out.println("p before conditionals: " + p);
  //switching currPlayer
  if (p == numPlayers-1) {
    p = 0;
    currPlayer = players[p];
    //board.setActivePlayer(temPlayer);
  } else {
    p++;
    currPlayer = players[p];
    //board.setActivePlayer(temPlayer);
  }
  System.out.println("");
  /*String nextPlayer = */System.out.println("Next player up: " + players[p].getName()) ;
  //BoardController.popupNotification(4, nextPlayer);

  if(players[p].checkWorking() == true){
    BoardView.changeButtons("act", players[p]);
  }
  BoardController.updateInfo(players[p]);
}//last curly brace of endTurn

//-------------------------------------------------------End of board controller functions-----------------------------------//

//   public static void play() {


//     // Get random number = i;
//     Random rand = new Random();
//     p = rand.nextInt(numPlayers);
//     currPlayer = players[p];
//     // gameBoard.addPlayerInfo(currPlayer);


//     // Day loop
//     while (day < 5) {
//       //Give each room a random scenes
//       ArrayList<Scene> tempScenes = roomsSetScenes(deckOfScenes, rooms);
//       // WHERE DO WE REMOVE COMPLETED SCENES FROM DECKOFSCENES
//       numScenesLeft = tempScenes.size();
//       System.out.println("DAY " + day);

//       // Turn loop
//       while (numScenesLeft > 1) {
//         //------------------------------start of segment to be moved into setup method-------------------------//
//         // currPlayer = players[p];
//         // System.out.println("");

//         // System.out.println("Current Player: Player "+ currPlayer.getName() + " has " + currPlayer.getDollars() + " in da baaaank and " + currPlayer.getCredits() + " credits");
//         // if(currPlayer == null){
//         //   System.out.println("no real player");
//         // }
//         // BoardController.createPlayerInfo(currPlayer);
//         // System.out.println("");

//         //------------------------------end of segment going to be moved----------------------//

//         System.out.println("Current Player: Player "+ currPlayer.getName() + " (Rank " + currPlayer.getRank() + ")" + " has " + currPlayer.getDollars() + " in da baaaank and " + currPlayer.getCredits() + " credits");
//         System.out.println("");
//         // Check if player is working
//         // -> if they are = act/rehearse
//         // -> else = move and/or take role
//         if (currPlayer.checkWorking()) {
//           // Get input for act or rehearse
//           Scanner sc = new Scanner(System.in);
//           System.out.println("Scene budget: " + currPlayer.getCurrentRoom().getCurrentScene().getBudget());
//           System.out.println("You are working, do you want to 'act' or 'rehearse' (Practice Points: " + currPlayer.getPracticePoints() +")?"); // need to account for practice points
//           String choice = sc.nextLine();
//           switch(choice){
//             case "act":
//               // int result = currPlayer.getWork().act(currPlayer);
//               // if (result == 1) {
//               //   System.out.println("Congratulations, you're a star!");
//               //   if (currPlayer.getCurrentRoom().getShotsLeft() > 0) {
//               //     currPlayer.getCurrentRoom().removeShot();
//               //     System.out.println("Shots Left: " + currPlayer.getCurrentRoom().getShotsLeft());
//               //   System.out.println("");
//               //   if (currPlayer.getCurrentRoom().getShotsLeft() > 0) {
//               //     currPlayer.getCurrentRoom().removeShot();
//               //     if (currPlayer.getCurrentRoom().getShotsLeft() != 0) {
//               //       System.out.println("Shots Left: " + currPlayer.getCurrentRoom().getShotsLeft());
//               //     }
//               //     // Check if shots == 0
//               //     if (currPlayer.getCurrentRoom().getShotsLeft() == 0) {
//               //       board.endScene(currPlayer.getCurrentRoom().getCurrentScene());
//               //       currPlayer.getCurrentRoom().resetShots();
//               //       board.distributeBonuses(currPlayer.getCurrentRoom().getCurrentScene());
//               //       numScenesLeft--;
//               //       System.out.println("Only " + numScenesLeft + " scenes left!");
//               //       // for (int i=0; i<numPlayers; i++) {
//               //       //   if (players[i].getWork().getCurrentScene().getName().equals(currPlayer.getCurrentRoom().getCurrentScene())) {
//               //       //     players[i].getWork().deleteScene();
//               //       //   }
//               //       // }
//               //       currPlayer.getCurrentRoom().setHasScene(false);
//               //       currPlayer.getCurrentRoom().getCurrentScene().setAvailable(false);
//               //       deckOfScenes.remove(currPlayer.getCurrentRoom().getCurrentScene());
//               //       numScenesLeft--;
//               //       System.out.println("Only " + numScenesLeft + " scenes left!");
//               //       for (int i=0; i<numPlayers; i++) {
//               //         if (players[i].getWork().getCurrentScene().getName().equals(currPlayer.getCurrentRoom().getCurrentScene().getName())) {
//               //           players[i].setIsWorking(false);
//               //         }
//               //       }
//               //     }
//               //   }
//               // } else {
//               //   System.out.println("You didn't quite cut it this time. Maybe later...");
//               // }
//               break;
//             case "rehearse":
//               //currPlayer.getWork().rehearse(currPlayer);
//               break;
//           }
//         }
//         // If player is not working
//         else {
//           // Get optional move input
//           Scanner sc = new Scanner(System.in);
//           System.out.println("How many (0 or 1) spaces would you like to move?");
//           int mv = sc.nextInt();
//           if(mv == 0) {
//             //should this case continue to offer a role?
//               System.out.println("You are still.");
//             }
//           else if(mv == 1){
//           int validMove = 0;
//           int mvt = -1;
//           while (validMove == 0) {
//             Scanner sct = new Scanner(System.in);
//             System.out.println("How many (0 or 1) spaces would you like to move?");
//             try {
//               mv = sct.nextInt();
//             } catch (InputMismatchException a) {
//               System.out.println("Please enter a 0 or a 1");
//               System.out.println("");
//             }
//             if ((mv == 0) || (mv == 1)) {
//               validMove = 1;
//             }
//           }


//           if (mv == 0) {
//             //should this case continue to offer a role?
//               System.out.println("You are still.");
//             }
//           else if (mv == 1) {
//             //decide where they are moving
//             int validRoom = 0;
//             roomName = "";
//             ArrayList<Room> currentRoomNeighbors = currPlayer.getCurrentRoom().getNeighbors();
//             System.out.println("Where would you like to move?");

//             while (validRoom == 0) {
//               Scanner scMove = new Scanner(System.in);
//               // Print out adjacent rooms somehow
//               roomName = scMove.nextLine();
//               for (int i=0; i<currentRoomNeighbors.size(); i++) {
//                 if (currentRoomNeighbors.get(i).getName().equals(roomName)) {
//                   validRoom = 1;
//                 }
//               }
//               if(validRoom != 1){
//                 System.out.println("Sorry, not an adjacent room; Try again.");
//               }
//             }
//               ArrayList<Room> newRoomNeighbors = currPlayer.getCurrentRoom().getNeighbors();

//               // Check for valid room and find room object
//               Room r = new Room();

//               for (int i=0; i<rooms.size(); i++) {
//                 if (roomName.equals(rooms.get(i).getName()) && isNeigbor(roomName,newRoomNeighbors) == true) {
//                   r = rooms.get(i);
//                   currPlayer.setCurrentRoom(r); //set the player in the adecent room
//                   i = rooms.size(); //break out of the loop
//                 }
//               }

//               if (currPlayer.getCurrentRoom().getName().equals("Trailer") || (currPlayer.getCurrentRoom().getName().equals("Casting Office"))) {
//                 System.out.println("Player " + currPlayer.getName() + " is now in " + currPlayer.getCurrentRoom().getName());
//               } else {
//                 System.out.println("Player " + currPlayer.getName() + " is now in " + currPlayer.getCurrentRoom().getName());
//                 System.out.println("Current Scene -> " + currPlayer.getCurrentRoom().getCurrentScene().getName());
//               }

//               // Check if player wants to take a role
//               if (validRoom != 1) {
//                 System.out.println("Sorry, not an adjacent room. Try again.");
//               }
//             }
//             System.out.println("");
//             ArrayList<Room> newRoomNeighbors = currPlayer.getCurrentRoom().getNeighbors();

//             // Check for valid room and find room object
//             Room r = new Room();
//             for (int i=0; i<rooms.size(); i++) {
//               if (roomName.equals(rooms.get(i).getName()) && isNeigbor(roomName,newRoomNeighbors) == true) {
//                 r = rooms.get(i);
//                 currPlayer.setCurrentRoom(r); //set the player in the adecent room
//                 i = rooms.size(); //break out of the loop
//               }
//             }

//             if (currPlayer.getCurrentRoom().getName().equals("Trailer") || (currPlayer.getCurrentRoom().getName().equals("Casting Office"))) {
//               System.out.println("Player " + currPlayer.getName() + " is now in " + currPlayer.getCurrentRoom().getName());
//             } else {
//               System.out.println("Player " + currPlayer.getName() + " is now in " + currPlayer.getCurrentRoom().getName());
//               if (currPlayer.getCurrentRoom().hasScene() == true) {
//                 System.out.println("Current Scene -> " + currPlayer.getCurrentRoom().getCurrentScene().getName());
//               } else {
//                 System.out.println("Scene here has been wrapped already");
//               }
//             }
//             // if (currPlayer.getCurrentRoom().getName().equals("Casting Office")) {
//             //   CastOffice.rankUp(currPlayer);
//             // }
//             // Check if player wants to take a role
//             if (currPlayer.getCurrentRoom().hasScene()) {

//               Scanner s = new Scanner(System.in);
//               System.out.println("Take a role (Y/N)?");
//               char answer = s.next().charAt(0);
//               //ask if they would like a role on the scene card
//               if ((answer == 'Y') || (answer == 'y')) {
//                 // Print roles
//             //     System.out.println("Roles off card: ");
//             //     for (int i=0; i<currPlayer.getCurrentRoom().getRoles().size(); i++) {
//             //       System.out.println(" " + currPlayer.getCurrentRoom().getRoles().get(i).getName() + " (Rank " + currPlayer.getCurrentRoom().getRoles().get(i).getRank() + ")");
//             //     }
//             //     System.out.println("");
//             //     System.out.println("Roles on card: ");
//             //     for (int i=0; i<currPlayer.getCurrentRoom().getCurrentScene().getRoles().size(); i++) {
//             //       System.out.println(" " + currPlayer.getCurrentRoom().getCurrentScene().getRoles().get(i).getName() + " (Rank " + currPlayer.getCurrentRoom().getCurrentScene().getRoles().get(i).getRank() + ")");
//             //     }
//             //     System.out.println("");

//             //   //ask if they would like a role on the scene card
//             //   if((answer == 'Y') || (answer == 'y')) {
//             //     // Print roles
//             //     System.out.println("Roles off card: ");
//             //     for (int i=0; i<currPlayer.getCurrentRoom().getRoles().size(); i++) {
//             //       System.out.println(currPlayer.getCurrentRoom().getRoles().get(i).getName());
//             //     }
//             //     System.out.println("");
//             //     System.out.println("Roles on card: ");
//             //     for (int i=0; i<currPlayer.getCurrentRoom().getCurrentScene().getRoles().size(); i++) {
//             //       System.out.println(currPlayer.getCurrentRoom().getCurrentScene().getRoles().get(i).getName());
//             //     }//----------------
//             //     System.out.println("");

//             //     System.out.println("Which role do you want?");
//             //     int validRole = 0;
//             //     while(validRole == 0){
//             //       Scanner roleChoice = new Scanner(System.in);
//             //       String roleName = roleChoice.nextLine();
//             //       // if scene in room has the role, give it to player
//             //       //Scene desiredScene = currPlayer.getCurrentRoom().getCurrentScene(); //get the scene with the desired role

//             //       // if(desiredPart(roleName,desiredSceneRoles).getRank() >= currPlayer.getRank()){
//             //       //   System.out.println("Sorry, you're not skilled enough yet!");
//             //       // }
//             //       // else if(desiredPart(roleName,desiredRoomRoles).getRank() >= currPlayer.getRank()){
//             //       //   System.out.println("Sorry, you're not skilled enough yet!");
//             //       // }
//             //       // else
//             //       if(roleName.equals("done")){
//             //         validRole = 1;
//             //         System.out.println("choosing not to work, ending turn");
//             //       }
//             //       else if(r.checkSceneforRole(roleName) == true){ // and role is available?
//             //         validRole = 1;
//             //         Scene desiredScene = currPlayer.getCurrentRoom().getCurrentScene(); //get the scene with the desired role
//             //         ArrayList<Role> desiredSceneRoles = desiredScene.getRoles(); //get the list of roles from the scene
//             //         Role desiredRole = desiredPart(roleName,desiredSceneRoles); //get the specific role
//             //         currPlayer.setIsWorking(true);
//             //         Work newJob = new Work(currPlayer.getCurrentRoom().getCurrentScene(),currPlayer.getCurrentRoom()); //must create constructor
//             //         newJob.setWorking(desiredRole);
//             //         currPlayer.setWork(newJob);
//             //         System.out.println("Now working on " + currPlayer.getWork().getRole().getName());
//             //       }
//             //       else if(r.checkRoomforRole(roleName) == true){
//             //         validRole = 1;
//             //         Room currRoom = currPlayer.getCurrentRoom(); //get the scene with the desired role
//             //         ArrayList<Role> desiredRoomRoles = currRoom.getRoles(); //get the list of roles from the scene
//             //         Role desiredRole = desiredPart(roleName,desiredRoomRoles); //get the specific role
//             //         currPlayer.setIsWorking(true);
//             //         Work newJob = new Work(currPlayer.getCurrentRoom().getCurrentScene(),currPlayer.getCurrentRoom()); //must create constructor
//             //         newJob.setWorking(desiredRole);
//             //         currPlayer.setWork(newJob);
//             //         System.out.println("Now working at " + currPlayer.getWork().getRole().getName());
//             //       }
//             //       else{
//             //         System.out.println("Invalid role, pick another (Input 'done' if you would like to not choose role and end turn)!");
//             //       }
//             //      }
//             //     }
//             //   }

//             //       // else (until valid input) System.out.println("Not a valid role");
//             //   else{
//             //     System.out.println("You have chosen not to work");
//             //     System.out.println("Which role do you want?");
//             //     int validRole = 0;
//             //     while (validRole == 0) {
//             //       Scanner roleChoice = new Scanner(System.in);
//             //       String roleName = roleChoice.nextLine();


//             //       if (roleName.equals("done")) {
//             //         validRole = 1;
//             //         System.out.println("You have chosen not to work, your turn ends.");
//             //       }
//             //       else if (r.checkSceneforRole(roleName) == true) { // and role is available?
//             //         Scene desiredScene = currPlayer.getCurrentRoom().getCurrentScene(); //get the scene with the desired role
//             //         ArrayList<Role> desiredSceneRoles = desiredScene.getRoles(); //get the list of roles from the scene
//             //         if (desiredPart(roleName,desiredSceneRoles).getRank() > currPlayer.getRank()){
//             //           System.out.println("");
//             //           System.out.println("Sorry, you're not skilled enough yet! Choose another roles (Input 'done' if you just want to end your turn)");
//             //         }
//             //         else {
//             //           validRole = 1;
//             //           Role desiredRole = desiredPart(roleName,desiredSceneRoles); //get the specific role
//             //           currPlayer.setIsWorking(true);
//             //           Work newJob = new Work(currPlayer.getCurrentRoom().getCurrentScene(),currPlayer.getCurrentRoom()); //must create constructor
//             //           newJob.setRole(desiredRole);
//             //           currPlayer.setWork(newJob);
//             //           System.out.println("Now working on " + currPlayer.getWork().getRole().getName());
//             //         }
//             //       }
//             //       else if (r.checkRoomforRole(roleName) == true) {
//             //         Room currRoom = currPlayer.getCurrentRoom(); //get the scene with the desired role
//             //         ArrayList<Role> desiredRoomRoles = currRoom.getRoles(); //get the list of roles from the scene
//             //         if (desiredPart(roleName,desiredRoomRoles).getRank() > currPlayer.getRank()) {
//             //           System.out.println("");
//             //           System.out.println("Sorry, you're not skilled enough yet! Choose another roles (Input 'done' if you just want to end your turn).");
//             //         }
//             //         else {
//             //           validRole = 1;
//             //           Role desiredRole = desiredPart(roleName,desiredRoomRoles); //get the specific role
//             //           currPlayer.setIsWorking(true);
//             //           Work newJob = new Work(currPlayer.getCurrentRoom().getCurrentScene(),currPlayer.getCurrentRoom()); //must create constructor
//             //           newJob.setRole(desiredRole);
//             //           currPlayer.setWork(newJob);
//             //           System.out.println("Now working on " + currPlayer.getWork().getRole().getName());
//             //         }
//             //       }
//             //       else {
//             //         System.out.println("Invalid role, pick another (Input 'done' if you would like to not choose role and end turn)!");
//             //       }
//             //     }
//             //   }
//             // }
//           //switching currPlayer
//           if (p == numPlayers-1) {
//             p = 0;
//             board.setActivePlayer(players[p]);
//           } else {
//             p++;
//             board.setActivePlayer(players[p]);
//           }
//       }
//       System.out.println("Day " + day + " is OVER!");
//       board.placeInTrailers(getTrailer(rooms));
//     }
//     }
//   }
// }
//     }
// }



}//last curly brace of the class file



