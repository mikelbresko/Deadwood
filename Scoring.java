public class Scoring {

  /*--------------------------------*/

  final static int rankMultiplier = 5;

  public static int calculateScore(Player p) {
    int rankP = rankToPoints(p.getRank());
    int dollars = p.getDollars();
    int credits = p.getCredits();
    int total = rankP+dollars+credits;
    System.out.println(p.getName() + " has scored " + total + " points!");

    return total;
  }

  public static int rankToPoints(int rank) {
    int rPoints = rank * rankMultiplier;
    return rPoints;
  }

  public static Player determineWinner(Player[] players) {
    int max = 0;
    Player winner = new Player("P");

    for (int i=0; i<players.length; i++) {
      if (players[i].getScore() > max) {
        winner = players[i];
        max = players[i].getScore();
      }
    }
    return winner;
  }


}
