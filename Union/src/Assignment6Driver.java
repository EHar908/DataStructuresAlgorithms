import java.io.File;
import java.util.Scanner;

public class Assignment6Driver {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void main(String[] args) {

        //testGame();
        playGame("moves1.txt");
        System.out.println();
        playGame("moves2.txt");
    }

    private static void playGame(String filename) {
        File file = new File(filename);
        try (Scanner input = new Scanner(file)) {
            HexGame game = new HexGame(11);
            int count = 0;
            while(input.hasNextLine()) {
                count++;
                int move = input.nextInt();
                if (count % 2 == 0) {
                    boolean ifWon = game.playRed(move, false);
                    if(ifWon){ //The T/F is whether or not the win condition has been met.
                        System.out.println("Red wins with move " + move + "!");
                        printGrid(game);
                        break;
                    }
                } else {
                    boolean ifWon = game.playBlue(move, false);
                    if(ifWon){ //The T/F is whether or not the win condition has been met.
                        System.out.println("Blue wins with move " + move + "!");
                        printGrid(game);
                        break;
                    }
                }
            }
            //}
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
    }

    private static void testGame() {
        HexGame game = new HexGame(11); //Had 11 here before; not sure what that means?

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
    }
    private static void printGrid(HexGame game) {
        int rowSpace = 1;
        int firstCellOfRow = 1;
        for(int i = 0; i < game.boardDimension; i++){ //1 -> 11
            for (int j = 0; j < rowSpace; j++) {
                System.out.print(" ");
            }
            rowSpace++;
            for(int k = firstCellOfRow; k <= (firstCellOfRow + game.boardDimension - 1); k++){//1 + 11 - 1 = 11. So,
                if(game.GameBoard.get(k).equals("B")){
                    System.out.print(ANSI_BLUE + game.GameBoard.get(k) + ANSI_RESET + " ");
                }
                else if(game.GameBoard.get(k).equals("R")){
                    System.out.print(ANSI_RED + game.GameBoard.get(k) + ANSI_RESET + " ");
                }
                else{
                    System.out.print(game.GameBoard.get(k) + " ");
                }
            }
            firstCellOfRow += game.boardDimension;
            System.out.println("");
        }
    }
}
