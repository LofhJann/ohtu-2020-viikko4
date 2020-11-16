package ohtu;

public class TennisGame {

    private int player1Score = 0;
    private int player2Score = 0;
    private final String player1Name = "player1";
    private final String player2Name = "player2";
    private String scoreString = "";

    public void wonPoint(String playerName) {
        if (playerName.equals("player1")) {
            player1Score += 1;
        } else {
            player2Score += 1;
        }
    }

    public String getScore() {
        if (player1Score == player2Score) {
            return getEqualScoreString();
        } else if (isBreakPoint()) {
            return getAdvantageOrWinningScoreString();
        } else {
            return getScoreString(player1Score) + "-" + getScoreString(player2Score);
        }
    }

    private boolean isBreakPoint() {
        return isBreak();
    }

    private boolean isBreak() {
        return player1Score >= 4 || player2Score >= 4;
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
        int scoreDifference = player1Score - player2Score;
        if (scoreDifference == 1) {
            return "Advantage player1";
        } else if (scoreDifference == -1) {
            return "Advantage player2";
        } else if (scoreDifference >= 2) {
            return "Win for player1";
        } else {
            return "Win for player2";
        }
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