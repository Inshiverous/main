package org.example.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ScoreCalculator {


    private int[] highestPoints;
    private final int maxPoints = 10000;
    private int currentPoints = 0;
    private int firmX;
    private int firmY;
    private int[] percents = {65,12,9,4,2,2,2,1,2,2};
    private int[][] minorList;

    public ScoreCalculator(int x, int y, int[][] array) {
        firmX = x;
        firmY = y;
        minorList = array;
    }

    public int getPoints(int[][] region) {
        
        for (int i = 0; i < 10; i++) {
            typePoints(i);
        }
        if (currentPoints >= 10000) {
            return 10000;
        }

        if (currentPoints < 100) {
            currentPoints = 100;
        }

        return currentPoints;
    }


    public void typePoints(int type) {
        int thisMax = (maxPoints * percents[type]) / 100;
        currentPoints += (int) (1.055 * (thisMax * ((1000.0 - (findClosest(type) * 1.5)) / 1000.0)));
    }

    public int findClosest(int type) {
        int[] distances = new int[minorList.length];
        int close = Integer.MAX_VALUE;
        for (int i = 0; i < minorList.length; i++) {
            if (type == minorList[i][0]) {
                distances[i] = this.getDistance(minorList[i][1], minorList[i][2]);
            } else {
                distances[i] = Integer.MAX_VALUE;
            }

            if (close >= distances[i]) {
                close = distances[i];
            }
        }
        return close;
    }

    public int getDistance(int X, int Y) {
        int Xdis = Math.abs(firmX - X);
        int Ydis = Math.abs(firmY - Y);
        return ((int)(Math.sqrt((Xdis*Xdis)+(Ydis*Ydis))));
    }
}
