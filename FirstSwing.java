import javax.swing.*;
import java.awt.event.*;

public class BoardView implements ActionListener{
  JButton bMove,bTakeRole,bAct,bRehearse,bEndTurn;
  JTextField moveRoom,takeRole;

  BoardView(){
    JFrame frame = new JFrame();

    moveRoom = new JTextField();
    moveRoom.setBounds(130,80,100,20);
    //new room is to test
    // newRoom = new JTextField();
    // newRoom.setBounds(130,150,100,20);
    // newRoom.setEditable(false);

    bMove = new JButton("Move Rooms");
    bMove.setBounds(130,100,100,40);
    bTakeRole = new JButton("Move Rooms");
    bTakeRole.setBounds(130,100,100,40);
    bAct = new JButton("Move Rooms");
    bAct.setBounds(130,100,100,40);
    bRehearse = new JButton("Move Rooms");
    bRehearse.setBounds(130,100,100,40);
    bEndTurn = new JButton("Move Rooms");
    bEndTurn.setBounds(130,100,100,40);

    buttonMove.addActionListener(this);

    frame.add(moveRoom);
    frame.add(newRoom);
    frame.add(buttonMove);


    frame.setSize(400,500);
    frame.setLayout(null);
    frame.setVisible(true);
  }

  //use this method to handle all the button pushes
  //Buttons:Move, Act, Reherse, Take Role, End Turn
  public void actionPerformed(ActionEvent e){
    String desiredRoom = moveRoom.getText();
    if(e.getSource() == buttonMove){
      newRoom.setText(desiredRoom);
    }
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
