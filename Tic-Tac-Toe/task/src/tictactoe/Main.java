package tictactoe;

import java.util.Scanner;

enum Player {
    X("X"),
    O("O");

    String value;

    Player(String value) {
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //Playing in a 3*3 field
        final int playField = 3;
        String[][] matrix = new String[playField][playField];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = " ";
            }
        }


        Player currentPlayer = Player.X;

        while (shouldGoOn(matrix)) {

            //Commented to pass tests
            //System.out.println("Current player: " + currentPlayer.value);

            boolean validEntry = false;

            while (!validEntry) {
                try {
                    System.out.print("Enter the coordinates: ");
                    String coordinates = scan.nextLine();
                    String[] coords = coordinates.split(" ");
                    int inputRow = Integer.valueOf(coords[0]);
                    int inputColumn = Integer.valueOf(coords[1]);
                    if (!(inputRow <= playField && inputRow >= 1 && inputColumn <= playField && inputColumn >= 1)) {
                        System.out.println("Coordinates should be from 1 to 3!");
                    } else {
                        inputRow = --inputRow;
                        inputColumn = Math.abs(matrix.length - inputColumn);

                        if (!matrix[inputColumn][inputRow].equals(" ")) {
                            System.out.println("This cell is occupied! Choose another one!");
                        } else {
                            matrix[inputColumn][inputRow] = currentPlayer.value;

                            if (currentPlayer == Player.X) {
                                currentPlayer = Player.O;
                            } else {
                                currentPlayer = Player.X;
                            }
                            validEntry = true;

                        }
                    }
                } catch (Exception e) {
                    System.out.println("You should enter numbers!");
                    //e.printStackTrace();
                }
            }
        }

    }

    static boolean shouldGoOn(String[][] matrix) {
        printField(matrix);
        if (isImpossible(matrix)) {
            System.out.println("Impossible");
            return false;
        } else if (calculatePlayerWin(matrix, "X")) {
            System.out.println("X wins");
            return false;
        } else if (calculatePlayerWin(matrix, "O")) {
            System.out.println("O wins");
            return false;
        } else if (!noStepsLeft(matrix)) {
            //System.out.println("Game not finished");
            return true;
        } else {
            System.out.println("Draw");
            return false;
        }
    }

    static boolean calculatePlayerWin(String[][] matrix, String matchWith) {

        //Matching rows
        for (int i = 0; i < matrix.length; i++) {
            int matches = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals(matchWith)) {
                    matches++;
                    if (matches == matrix[i].length) {
                        return true;
                    }
                }
            }
        }
        //Matching cols - 0 should be generalized somehow
        for (int i = 0; i < matrix[0].length; i++) {
            int matches = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i].equals(matchWith)) {
                    matches++;
                    if (matches == matrix[i].length) {
                        return true;
                    }
                }
            }
        }
        //Diagonal match
        int matches = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i].equals(matchWith)) {
                ++matches;
            }
        }
        if (matches == matrix.length) {
            return true;
        }

        matches = 0;
        int length = matrix.length - 1;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][length].equals(matchWith)) {
                length--;
                matches++;
            }
        }
        if (matches == matrix.length) {
            return true;
        }

        return false;
    }

    static boolean isImpossible(String[][] matrix) {
        int numOfX = 0;
        int numOfO = 0;

        if (calculatePlayerWin(matrix, "O") && calculatePlayerWin(matrix, "X")) {
            return true;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals("X")) {
                    numOfX++;
                } else if (matrix[i][j].equals("O")) {
                    numOfO++;
                }
            }
        }
        if (Math.abs(numOfO - numOfX) > 1) {
            return true;
        }
        return false;
    }

    static boolean noStepsLeft(String[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    static void printField(String[][] matrix) {
        System.out.println("---------");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("---------");
    }
}
