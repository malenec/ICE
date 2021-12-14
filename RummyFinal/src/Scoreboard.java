import java.util.ArrayList;

//SCOREBOARD CLASS: HOW THE SCOREBOARD IS SET UP
public class Scoreboard {
    ArrayList<Player> list = new ArrayList<>();

    //SORTS THE LIST ARRAYLIST IN ORDER OF HIGHEST SCORE TO LOWEST SCORE
    public Iterable<Player> getList (){
        list.sort(new ScoreComparator());
        return list;
    }

    //ADDS A PLAYER TO THE LIST ARRAYLIST
    public void addPlayer(Player p) {
        this.list.add(p);
    }

    //FINDS THE HIGHEST SCORE
    public int getHighestScore() {
        list.sort(new ScoreComparator());
        return list.get(0).totalGameScore;
    }

    //RETURNS A STRING OF SCOREBOARD THAT IS READABLE
    @Override
    public String toString() {
        String s = "";
        s+= "\nPLAYER  SCORE";
        for (Player p: getList()) {
            s += "\n" + p.getName() + ":   " + p.totalGameScore;

        }

        s += "\n";

        return s;
    }
}
