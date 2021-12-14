import java.util.Comparator;

//SCORECOMPARATOR CLASS CREATES A COMPARATOR OF PLAYERS TOTAL GAME SCORE
public class ScoreComparator implements Comparator <Player> {


    @Override
    public int compare(Player p1, Player p2) {
        return p2.totalGameScore - p1.totalGameScore;
    }


}
