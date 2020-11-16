package ohtu;

public class TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;
    private final String player1Name;
    private final String player2Name;
    private String scoreString = "";

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals("player1")) {
            player1Score += 1;
        } else {
            player2Score += 1;
        }
    }

    public String getScore() {
        if (player1Score == player2Score) {
            scoreString = getEqualScoreString();
        } else if (player1Score >= 4 || player2Score >= 4) {
            scoreString = getAdvantageOrWinningScoreString();
        } else {
            scoreString = getScoreString(player1Score) + "-" + getScoreString(player2Score);
        }
        return scoreString;
    }

    private String getScoreString(int playerScore) {
        switch (playerScore) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "wtf";
        }
    }

    private String getAdvantageOrWinningScoreString() {
        String score;
        int minusResult = player1Score - player2Score;
        if (minusResult == 1) score = "Advantage player1";
        else if (minusResult == -1) score = "Advantage player2";
        else if (minusResult >= 2) score = "Win for player1";
        else score = "Win for player2";
        return score;
    }

    private String getEqualScoreString() {
        switch (player1Score) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            case 3:
                return "Forty-All";
            default:
                return "Deuce";
        }
    }
}