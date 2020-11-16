package ohtu;

public class TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;
    private final String player1Name;
    private final String player2Name;

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
        String scoreString = "";
        int tempScore = 0;
        if (player1Score == player2Score) {
            scoreString = getEqualScoreString();
        } else if (player1Score >= 4 || player2Score >= 4) {
            scoreString = getAdvantageOrWinningScoreString();
        } else {
            for (int i = 1; i < 3; i++) {
                if (i == 1) tempScore = player1Score;
                else {
                    scoreString += "-";
                    tempScore = player2Score;
                }
                scoreString = getScoreString(scoreString, tempScore);
            }
        }
        return scoreString;
    }

    private String getScoreString(String scoreString, int tempScore) {
        switch (tempScore) {
            case 0:
                scoreString += "Love";
                break;
            case 1:
                scoreString += "Fifteen";
                break;
            case 2:
                scoreString += "Thirty";
                break;
            case 3:
                scoreString += "Forty";
                break;
        }
        return scoreString;
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
        String score;
        switch (player1Score) {
            case 0:
                score = "Love-All";
                break;
            case 1:
                score = "Fifteen-All";
                break;
            case 2:
                score = "Thirty-All";
                break;
            case 3:
                score = "Forty-All";
                break;
            default:
                score = "Deuce";
                break;

        }
        return score;
    }
}