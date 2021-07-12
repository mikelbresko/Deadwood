import java.util.*;

public class CastOffice {

  /*--------------------------------*/
  final static int[] ranks = {2,3,4,5,6};
  final static int[] dollarEquiv = {4,10,18,28,40};
  final static int[] creditEquiv = {5,10,15,20,25};

  public static void rankUp(Player p, int desiredRank, String paymentType) {
    // Display all the rank for gui

    int rankIndex = findRank(desiredRank);

        else {
          switch (paymentType) {
            case 'D':
              if (p.getDollars() >= dollarEquiv[rankIndex]) {
                p.setRank(getRank);
                System.out.println("Congrats, you're now a rank " + p.getRank() + " actor!");
                removeMoney(p,dollarEquiv[rankIndex],0);
                hasRanked = 1;
              } else {
                System.out.println("You don't have enough cash, sorry.");
              }
              break;
            case 'C':
              if (p.getCredits() >= creditEquiv[rankIndex]) {
                p.setRank(getRank);
                System.out.println("Congrats, you're now a rank " + p.getRank() + " actor!");
                removeMoney(p,creditEquiv[rankIndex],1);
                hasRanked = 1;
              } else {
                System.out.println("Your card's maxed out, sorry.");
              }
              break;
          }
          if (hasRanked == 0) {
            int check = 0;
            char ans = '0';
            while (check == 0) {
              System.out.println("Try again (Y/N)?");
              Scanner t = new Scanner(System.in);
              try {
                ans = t.next().charAt(0);
                check = 1;
              } catch (InputMismatchException a) {
                System.out.println("Y or N");
              }
            }
            if (ans == 'Y') {
              System.out.println("Ok, don't fuck it up this time lol");
              System.out.println("");
            } else {
              tryAgain = 0;
            }
          }
        }
      } else {
        System.out.println("No point in choosing that rank!");
      }
    }
  }

  public static int findRank(int desiredRank){
    for(int i = 0; i < ranks.length; i++){
      //if(desiredRank == )
    }
  }

  // 0 for dollars, 1 for credits
  public static void removeMoney(Player p, int amount, int type) {
    switch (type) {
      case 0:
        p.setDollars(p.getDollars()-amount);
        System.out.println("You now have " + p.getDollars() + " dollars");
        break;
      case 1:
        p.setCredits(p.getCredits()-amount);
        System.out.println("You now have " + p.getCredits() + " credits");
        break;
    }
  }
}
