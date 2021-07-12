import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BoardView extends BoardController{

  static JFrame frame;

  static JButton bMove,
                 bTakeRole,
                 bAct,
                 bRehearse,
                 bEndTurn,
                 bUpgrade;

  static JTextField moveRoom,
                    takeRole;

  static JLabel playerName,
                playerRank,
                playerMoney,
                playerCredits,
                playerRoom,
                playerRole,
                playerPracs;

  static JComboBox<String> neighborOptions,
                           roleOptions;

  static ImageIcon boardBackground;
  static int boardWidth;

  BoardView(){

    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel board = new JLabel();
    boardBackground = new ImageIcon("Deadboard.jpg");
    boardWidth = boardBackground.getIconWidth();
    board.setIcon(boardBackground);
    board.setBounds(0,0,boardBackground.getIconWidth(),boardBackground.getIconHeight());



    frame.setSize(boardBackground.getIconWidth() + 300,boardBackground.getIconHeight()+100);
    frame.setResizable(false);
    frame.setContentPane(board);
    frame.setLayout(null);

    addButtonsToWindow(gameSystem.currPlayer);
    frame.setVisible(true);
  }

//TODO: add rank up button
public void addButtonsToWindow(Player p){

  //create side pane
  JLabel menu = new JLabel("Menu");
  menu.setBounds(boardBackground.getIconWidth()+15,10,100,20);
  frame.add(menu, new Integer(2));

  //TODO:bounds format = x,y,width,height

  //------Moving Rooms----------//
  bMove = new JButton("Move");
  bMove.setBounds(boardWidth+15, 200,120, 40);
  //boardWidth+15,140,300,20
  bMove.addMouseListener(new boardMouseListener());
  bMove.setVisible(true);
  frame.add(bMove, new Integer(2));

  neighborOptions = new JComboBox<String>();
  ArrayList<Room> currentRoomNeighbors = p.getCurrentRoom().getNeighbors();

  for(int i = 0; i < currentRoomNeighbors.size(); i++){
    neighborOptions.addItem(currentRoomNeighbors.get(i).getName());
  }

  neighborOptions.setBounds(boardWidth+150,200,120,40);
  neighborOptions.setVisible(true);
  frame.add(neighborOptions, new Integer(2));

  //----Taking Roles----------//
  bTakeRole = new JButton("Take Role");
  bTakeRole.setBounds(boardWidth+15, 200,120, 40);
  bTakeRole.addMouseListener(new boardMouseListener());
  frame.add(bTakeRole, new Integer(2));
  frame.setVisible(false); //TODO:COME BACK TO THIS/WHY IS THIS A THING

  roleOptions = new JComboBox<String>();
  roleOptions.setVisible(false);

  
  //----Acting and Rehearsing----//
  bAct = new JButton("Act");
  bAct.setBounds(boardWidth+15, 200,120, 40);
  bAct.addMouseListener(new boardMouseListener());
  frame.add(bAct, new Integer(2));
  frame.setVisible(false); //TODO:COME BACK TO THIS/WHY IS THIS A THING

  bRehearse = new JButton("Rehearse");
  bRehearse.setBounds(boardWidth+150,200,120,40);
  bRehearse.addMouseListener(new boardMouseListener());
  frame.add(bRehearse, new Integer(2));
  frame.setVisible(false); //TODO:COME BACK TO THIS/WHY IS THIS A THING

  //----Upgrading----//
  bUpgrade = new JButton("Rank Up");
  bUpgrade.setBounds(boardWidth+15, 200,120, 40);
  bUpgrade.addMouseListener(new boardMouseListener());
  bUpgrade.setVisible(false);
  frame.add(bUpgrade, new Integer(2));

  //----End Turn Button----//
  bEndTurn = new JButton("End Turn");
  bEndTurn.setBounds(boardWidth+15,280,120, 40);
  bEndTurn.addMouseListener(new boardMouseListener());
  
  frame.add(bEndTurn, new Integer(2));

}

public static int askNumPlayers(){
  int players = 0;
  String[] numP = {"2","3","4","5","6"};
  int playersPLaying = JOptionPane.showOptionDialog(frame,"Select Number of players:","Actors",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                            null, numP, numP[0]);
  int numPlayers = Integer.parseInt(numP[playersPLaying]);

  return numPlayers;
}


public static void addPlayerInfo(Player p){

  //offset for if someone is working

  playerName = new JLabel("Player: " + p.getName());
  playerName.setBounds(boardWidth+15, 40, 300, 20);
  frame.add(playerName,new Integer(2));

  playerRoom = new JLabel("Current Room: " + p.getCurrentRoom().getName());
  playerRoom.setBounds(boardWidth+15, 60, 300, 20);
  frame.add(playerRoom,new Integer(2));

  playerRank = new JLabel("Rank: " + p.getRank());
  playerRank.setBounds(boardWidth+15, 80, 300, 20);
  frame.add(playerRank,new Integer(2));

  playerMoney= new JLabel("Dollars: " + p.getDollars());
  playerMoney.setBounds(boardWidth+15, 100, 300, 20);
  frame.add(playerMoney,new Integer(2));

  playerCredits = new JLabel("Credits: "+ p.getCredits());
  playerCredits.setBounds(boardWidth+15, 120, 300, 20);
  frame.add(playerCredits,new Integer(2));

  playerPracs = new JLabel("Practice Points: "+ p.getPracticePoints());
  playerPracs.setBounds(boardWidth+15, 140,300, 20);
  frame.add(playerPracs,new Integer(2));

  if(p.checkWorking() == true){ //put this in update labels function
    playerRole = new JLabel("Current Role: " + p.getWork().getRole().getName());
    playerRole.setBounds(boardWidth+15,180,300,20);
    frame.add(playerRole,new Integer(2));
  }
}

public static void updateLabelInfo(Player p){
  System.out.println("updating labels");

  playerName.setText("");
  playerName.setText("Player: " + p.getName());

  playerRoom.setText("");
  playerRoom.setText("Current Room: " + p.getCurrentRoom().getName());

  playerRank.setText("");
  playerRank.setText("Rank: " + p.getRank());
  
  playerMoney.setText("");
  playerMoney.setText("Dollars: " + p.getDollars());
  
  playerCredits.setText("");
  playerCredits.setText("Credits: "+ p.getCredits());

  playerPracs.setText("");
  playerPracs.setText("Credits: "+ p.getPracticePoints());
  
  
  System.out.println("done updating labels");
}

public static void updateNeighboringRooms(Player p){

  neighborOptions.removeAllItems();
  ArrayList<Room> currentRoomNeighbors = p.getCurrentRoom().getNeighbors();

  for(int i = 0; i < currentRoomNeighbors.size(); i++){
    neighborOptions.addItem(currentRoomNeighbors.get(i).getName());
  }
  //changeButtons("role",p);
}

public static void changeButtons(String action, Player p){

  if(action.equals("move")){
    changeToMoving(p);
  }
  else if(action.equals("role")){
    changeToTakeRole(p);
  }
  else if(action.equals("act")){
    changeToActing();
  }
  else if(action.equals("rank")){
    changeToUpgrading(p);
  }

}

public static void changeToUpgrading(Player p){
  bAct.setVisible(false);
  bRehearse.setVisible(false);
  bMove.setVisible(false);
  neighborOptions.setVisible(false);
  roleOptions.setVisible(false);

  bUpgrade.setVisible(true);
}

public static void changeToMoving(Player p){
  bAct.setVisible(false);
  bRehearse.setVisible(false);
  
  bTakeRole.setVisible(false);
  roleOptions.setVisible(false);

  bMove.setVisible(true);

  ArrayList<Room> currentRoomNeighbors = p.getCurrentRoom().getNeighbors();

  for(int i = 0; i < currentRoomNeighbors.size(); i++){
    neighborOptions.addItem(currentRoomNeighbors.get(i).getName());
  }

  //neighborOptions.setBounds(boardWidth+150,40,120,40);
  neighborOptions.setVisible(true);
  frame.add(neighborOptions, new Integer(2));
}

public static void changeToTakeRole(Player p){
  bAct.setVisible(false);
  bRehearse.setVisible(false);
  
  bTakeRole.setVisible(true);

  bMove.setVisible(false);
  neighborOptions.setVisible(false);

  roleOptions.setVisible(false);

  roleOptions = new JComboBox<String>();
  ArrayList<Role> onCardScenes = p.getCurrentRoom().getCurrentScene().getRoles();
  ArrayList<Role> offCardScenes = p.getCurrentRoom().getRoles();
  

  for(int i = 0; i < onCardScenes.size(); i++){
    roleOptions.addItem(onCardScenes.get(i).getName());
  }

  for(int i = 0; i < offCardScenes.size(); i++){
    roleOptions.addItem(offCardScenes.get(i).getName());
  }

  roleOptions.setBounds(boardWidth+150,200,120,40);
  roleOptions.setVisible(true);
  frame.add(roleOptions, new Integer(2));
}


public static void changeToActing(){

  bTakeRole.setVisible(false);
  roleOptions.setVisible(false);

  bMove.setVisible(false);
  neighborOptions.setVisible(false);

  bAct.setVisible(true);
  bRehearse.setVisible(true);
}

public static void buyRanks(){

}

public static void alertOfError(String message){
  JOptionPane.showMessageDialog(frame,
    message,"Game Text:",
    JOptionPane.WARNING_MESSAGE);
}

public static int rankUp(Player p){
  int playerMoney = p.getDollars();
  int playerCredit = p.getCredits();

  System.out.println(p.getName() + " has "+ playerMoney + " dollars and " + playerCredit + " credits");
  ArrayList<String> rankOptions = new ArrayList<String>();
  
  String payChoices[] = {"D","C"};

  if(playerMoney < 4  && playerCredit < 5){
    //print out error message of how they dont have enough funds to rank up
    String cantRankUp = "Sorry, not enough funds to rank up. Ending turn";
    popupNotification(4, cantRankUp);
    gameSystem.endTurn();
  }
  else{
    System.out.println("creating rank choice panel");
    if(playerMoney >= 4 || playerCredit >= 5){
      //add rank 2 to available rank list
      rankOptions.add("2");
    }
    if(playerMoney >= 10 || playerCredit >= 10){
      //add rank 3 to available rank list
      rankOptions.add("3");
    }
    if(playerMoney >= 18 || playerCredit >= 15){
      //add rank 4 to available rank list
      rankOptions.add("4");
    }
    if(playerMoney >= 28 || playerCredit >= 20){
      //add rank 5 to available rank list
      rankOptions.add("5");
    }
    if(playerMoney >= 40 || playerCredit >= 25){
      //add rank 5 to available rank list
      rankOptions.add("6");
    }
    
    String[] availableOptions = new String[rankOptions.size()];

    for(int i = 0; i < rankOptions.size(); i++){
      availableOptions[i] = rankOptions.get(i);
    }

    int desiredRank = JOptionPane.showOptionDialog(frame, "Available dollars: " + playerMoney + ", Available Credits: " + playerCredit , 
                                                    "Rank Up:", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                                                    null, availableOptions, availableOptions[0]);
    
    int paymentMethod = JOptionPane.showOptionDialog(frame,"Choose Payment Method: ",
                                                      "Payment",
                                                      JOptionPane.DEFAULT_OPTION, 
                                                      JOptionPane.PLAIN_MESSAGE,
                                                      null, 
                                                      payChoices,
                                                      payChoices[0]);

    BoardController.rankPlayer(p,availableOptions[desiredRank],payChoices[paymentMethod]);
    
    return Integer.parseInt(availableOptions[desiredRank]);
  }

return -1;                                               
  // int players = 0;
  // String[] numP = {"2","3","4","5","6"};
  // int playersPLaying = JOptionPane.showOptionDialog(frame,"Select Number of players:","Actors",
  //                                           JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
  //                                           null, numP, numP[0]);
  // int numPlayers = Integer.parseInt(numP[playersPLaying]);

  // return numPlayers;


}


public static void main(String[] args) {
  new BoardView();
}

}




//
// import javax.swing.*;
// import java.awt.event.*;
//
// public class FirstSwing implements ActionListener{
//     JTextField tf1,tf2,tf3;
//     JButton b1,b2;
//     FirstSwing(){
//         JFrame f= new JFrame();
//         tf1=new JTextField();
//         tf1.setBounds(50,50,150,20);
//
//         tf2=new JTextField();
//         tf2.setBounds(50,100,150,20);
//
//         tf3=new JTextField();
//         tf3.setBounds(50,150,150,20);
//         tf3.setEditable(false);
//
//         b1=new JButton("+");
//         b1.setBounds(50,200,50,50);
//
//         b2=new JButton("-");
//         b2.setBounds(120,200,50,50);
//
//         b1.addActionListener(this);
//         b2.addActionListener(this);
//
//         f.add(tf1);f.add(tf2);f.add(tf3);f.add(b1);f.add(b2);
//         f.setSize(300,300);
//         f.setLayout(null);
//         f.setVisible(true);
//     }
//     public void actionPerformed(ActionEvent e) {
//         String s1=tf1.getText();
//         String s2=tf2.getText();
//         int a=Integer.parseInt(s1);
//         int b=Integer.parseInt(s2);
//         int c=0;
//         if(e.getSource()==b1){
//             c=a+b;
//         }else if(e.getSource()==b2){
//             c=a-b;
//         }
//         String result=String.valueOf(c);
//         tf3.setText(result);
//     }
// public static void main(String[] args) {
//     new FirstSwing();
// }
// }
