package server.info;

import javax.validation.constraints.NotNull;

public class Leaderboard {
    private int score;
    @NotNull
    private String userName;

    public Leaderboard(String _userName, int _score){
        score = _score;
        userName = _userName;
    }

    public int getScore(){ return score; }
    @NotNull
    public String getUserName() { return userName; }
    public void setScore(int _score) { score = _score; }
    public void setUserName(@NotNull String _userName) { userName = _userName; }
}
