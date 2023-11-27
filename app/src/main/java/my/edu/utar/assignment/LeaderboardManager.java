package my.edu.utar.assignment;

import android.content.SharedPreferences;


public class LeaderboardManager {
    static final int maxRow = 25;
    private static int[] rankingPointList = new int[maxRow];
    private static String[] rankingNameList = new String[maxRow];

    //function for loading the data of the leaderboard
    public static void loadLeaderboardData(SharedPreferences sharedPreferences) {
        for (int i = 0; i < maxRow; i++) {
            rankingPointList[i] = sharedPreferences.getInt("points" + i, 0);
            rankingNameList[i] = sharedPreferences.getString("name" + i, "N/A");
        }
    }

    //function for sorting the leaderboard data based on the higher score
    public static void sortLeaderboardData() {
        for (int i = 0; i < maxRow - 1; i++) {
            for (int j = i + 1; j < maxRow; j++) {
                if (rankingPointList[j] > rankingPointList[i]) {
                    // Swap points
                    int tempPoints = rankingPointList[i];
                    rankingPointList[i] = rankingPointList[j];
                    rankingPointList[j] = tempPoints;

                    // Swap names
                    String tempName = rankingNameList[i];
                    rankingNameList[i] = rankingNameList[j];
                    rankingNameList[j] = tempName;
                }
            }
        }
    }

    //Function to save updated leaderboard
    public static void saveLeaderboardData(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < maxRow; i++) {
            editor.putInt("points" + i, rankingPointList[i]);
            editor.putString("name" + i, rankingNameList[i]);
        }
        editor.apply();
    }

    //save the score and the name of the user to the SharedPreference
    public static void updateLeaderboard(int points, String name) {
        int positionToSave = findPositionToSave(points);

        rankingPointList[positionToSave] = points;
        rankingNameList[positionToSave] = name;
    }

    //find the suitable position base on the score
    private static int findPositionToSave(int points) {
        int lowestScore = Integer.MAX_VALUE;
        int positionToSave = -1;

        for (int i = 0; i < maxRow; i++) {
            if (rankingPointList[i] < lowestScore) {
                lowestScore = rankingPointList[i];
                positionToSave = i;
            }
        }

        // If the leaderboard is full and the user's score is higher than the lowest score, replace the lowest score
        if (points > lowestScore) {
            positionToSave = maxRow - 1;
        }

        return positionToSave;
    }

    public static int[] getRankingPointList() {
        return rankingPointList;
    }

    public static String[] getRankingNameList() {
        return rankingNameList;
    }
}

