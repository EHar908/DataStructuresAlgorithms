import java.util.ArrayList;

public class HexGame {
    //Red Player begins at either the top edge or bottom edge of the board.
    public int boardDimension;
    private int TOP_EDGE; //122
    private int BOTTOM_EDGE;//123

    //Blue Player begins at either the left edge or right edge of the board.
    private int LEFT_EDGE; //124
    private int RIGHT_EDGE; //125
    ArrayList<String> GameBoard;
    DisjointSet PlayerBlue;
    DisjointSet PlayerRed;

    public HexGame(int dimension){
        this.boardDimension = dimension;
        this.TOP_EDGE = (boardDimension * boardDimension) + 1; //122
        this.BOTTOM_EDGE = (boardDimension * boardDimension) + 2; //123
        this.LEFT_EDGE = (boardDimension * boardDimension) + 3; //124
        this.RIGHT_EDGE = (boardDimension * boardDimension) + 4; //125
        this.GameBoard = new ArrayList<>((boardDimension * boardDimension) + 1); //0->120, size 121. So make the size 122 so that we only care about tiles 1->121.
        this.PlayerBlue = new DisjointSet((boardDimension * boardDimension) + 5); //0->120, so now 0->126
        this.PlayerRed = new DisjointSet((boardDimension * boardDimension) + 3); //0->120, so now 0->124
        for(int i = 1; i < (boardDimension * boardDimension) + 2; i++){ //Fill the GameBoard with a bunch of 0's.
            GameBoard.add("0");
        }
    }
    private boolean isOccupied(int position){
        if(!GameBoard.get(position).equals("0")){ //If it isn't zero, then it is occupied. Return true.
            return true;
        }
        return false;
    }
    private String isRedEdge(int position){
        //Top edge.
        for(int i = 1; i <= boardDimension; i++) { //1 -> 11
            if(position == i){
                return "TopEdge";
            }
        }
        //Bottom edge.
        for(int i = ((boardDimension * boardDimension) - boardDimension + 1); i <= (boardDimension * boardDimension); i++){ //111 -> 121
            if(position == i){
                return "BottomEdge";
            }
        }
        return "";
    }
    private String isBlueEdge(int position){
        //Left Edge.
        for(int i = 1; i <= ((boardDimension * boardDimension) - boardDimension + 1); i += boardDimension){ //1->111 (step 11)
            if(position == i){
                return "LeftEdge";
            }
        }
        //Right Edge.
        for(int i = boardDimension; i <= (boardDimension * boardDimension); i += boardDimension){//11->121 (step 11)
            if(position == i){
                return "RightEdge";
            }
        }
        return "";
    }
    private void printNeighbors(int position, String player){
        String playerEdge = "";
        System.out.print("Cell " + position + ": ");
        if(isBlueEdge(position).equals("LeftEdge")){
            if(player.equals("blue")){
                playerEdge = LEFT_EDGE + " ";
            }
            //TOP LEFT
            if(position == 1){
                if(player.equals("red")){
                    playerEdge = TOP_EDGE + " ";
                }
                System.out.println("[ " + playerEdge + (position + 11) + " " + (position + 1)
                        + " ]");
            }
            //BOTTOM LEFT
            else if(position == (boardDimension * boardDimension - (boardDimension - 1))){
                if(player.equals("red")){
                    playerEdge = BOTTOM_EDGE + " ";
                }
                System.out.println("[ "  + (position - 11) + " " + (position - 10) + " " + (position + 1)
                        + " " + playerEdge + "]");
            }
            //ALONG THE LEFT
            else{
                System.out.println("[ "  + playerEdge + (position + 1) + " " + (position - 11) + " " + (position - 10) + " "
                     + (position + 11) + " ]");
            }
        }
        else if(isBlueEdge(position).equals("RightEdge")){
            if(player.equals("blue")){
                playerEdge = RIGHT_EDGE + " ";
            }
            //TOP RIGHT
            if(position == (boardDimension)){
                if(player.equals("red")){
                    playerEdge = TOP_EDGE + " ";
                }
                System.out.println("[ " + playerEdge + (position-1) + " " + (position + 10) + " " + (position + 11)
                        + " ]");
            }
            //BOTTOM RIGHT
            else if (position == (boardDimension * boardDimension)){
                if(player.equals("red")){
                    playerEdge = BOTTOM_EDGE + " ";
                }
                System.out.println("[ " + playerEdge + (position - 11) + " " +  (position - 1)
                        + " ]");
            }

            //ALONG THE RIGHT EDGE
            else{
                System.out.println("[ " + (position - 11) + " " + playerEdge + (position - 1) + " " + (position + 10) + " " + (position + 11)
                    + " ]");
            }
        }
        else if (isRedEdge(position).equals("TopEdge")){
            if(player.equals("red")){
                playerEdge = TOP_EDGE + " ";
            }
            //TOP LEFT
            if(position == 1){
                System.out.println("[ " + playerEdge + (position + 1) + " " + (position + 11) + " ]");
            }
            //TOP RIGHT
            else if (position == (boardDimension)){
                System.out.println("[ " + playerEdge + (position-1) + " " + (position + 10) + " " + (position + 11)
                        + " ]");
            }
            //ALONG THE TOP
            else {
                System.out.println("[ " + playerEdge + (position - 1) + " " + (position + 1) + " " + (position + 10) + " " +
                        (position + 11) + " ]");
            }
        }
        else if (isRedEdge(position).equals("BottomEdge")){
            if(player.equals("red")){
                playerEdge = BOTTOM_EDGE + " ";
            }
            //BOTTOM LEFT
            if(position == (boardDimension * boardDimension - (boardDimension - 1))){
                System.out.println("[ " + playerEdge + (position - 11) + " " + (position - 10) + " " + (position + 1) + " ]");
            }
            //BOTTOM RIGHT
            else if(position == (boardDimension * boardDimension)){
                System.out.println("[ " + playerEdge + (position - 11) + " " + (position - 10) + " " + (position - 1)
                        + " ]");
            }
            //ALONG THE BOTTOM
            else{
                System.out.println("[ " + playerEdge + (position - 1) + " " + (position -11) + " " + (position - 10) + " " +
                        (position + 1) +  " ]");
            }
        }
        else{
            System.out.println("[ " + (position - 11) + " " + (position - 10) + " " + (position - 1) + " " + (position + 1) +
                    " " + (position + 10) + " " + (position + 11) + " ]");
        }
    }
    public boolean playBlue(int position, boolean displayNeighbors){
        if(position < 1 || position > (boardDimension * boardDimension)){
            System.out.println("The position " + position + " is invalid!");
            System.out.println("Please input a value between 1 and " + (boardDimension * boardDimension));
            return false;
        }
        if(displayNeighbors){
            printNeighbors(position, "blue");
        }


        if(isOccupied(position)){
            //System.out.println("The position " + position + " is already occupied!");
            return false;
        }

        //LEFT EDGE
        if(isBlueEdge(position).equals("LeftEdge")){
            GameBoard.set(position, "B");
            PlayerBlue.union(position, LEFT_EDGE);
            //TOP LEFT CORNER
            if(position == 1){ //LEFT EDGE, TOP CORNER
                if(GameBoard.get(position + 1).equals("B")){ //x + 1
                    PlayerBlue.union(position, (position + 1));
                }
                if(GameBoard.get(position + boardDimension).equals("B")){ //x + 11
                    PlayerBlue.union(position, (position + boardDimension));
                }
            }
            //BOTTOM LEFT CORNER
            else if (position == ((boardDimension * boardDimension) - boardDimension + 1)){ //111
                //THE ABOVE IS LEFT EDGE, BOTTOM CORNER
                if(GameBoard.get(position + 1).equals("B")){ //x + 1
                    PlayerBlue.union(position, (position + 1));
                }
                if(GameBoard.get(position - boardDimension).equals("B")){ //x - 11
                    PlayerBlue.union(position, (position - boardDimension));
                }
                if(GameBoard.get(position - boardDimension + 1).equals("B")){ //x - 10
                    PlayerBlue.union(position, (position - boardDimension + 1));
                }
            }

            //ALONG LEFT EDGE
            else{
                if(GameBoard.get(position - boardDimension).equals("B")){
                    PlayerBlue.union(position, position - boardDimension);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("B")){
                    PlayerBlue.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position + 1).equals("B")){
                    PlayerBlue.union(position, position + 1);
                }
                if(GameBoard.get(position + boardDimension).equals("B")){
                    PlayerBlue.union(position, position + boardDimension);
                }
            }
        }

        //RIGHT EDGE
        else if(isBlueEdge(position).equals("RightEdge")){
            GameBoard.set(position, "B");
            PlayerBlue.union(position, RIGHT_EDGE);
            //TOP RIGHT CORNER
            if(position == boardDimension){ //11, RIGHT EDGE, TOP CORNER
                if(GameBoard.get(position - 1).equals("B")){ //x + 1
                    PlayerBlue.union(position, (position - 1));
                }
                if(GameBoard.get(position + boardDimension).equals("B")){ //x + 11
                    PlayerBlue.union(position, (position + boardDimension));
                }
                if(GameBoard.get(position + boardDimension - 1).equals("B")){ //x + 10
                    PlayerBlue.union(position, (position + boardDimension - 1));
                }
            }
            //BOTTOM RIGHT CORNER
            else if (position == (boardDimension * boardDimension)){ //121, RIGHT EDGE, BOTTOM CORNER
                if(GameBoard.get(position - 1).equals("B")){ //x - 1
                    PlayerBlue.union(position, (position - 1));
                }
                if(GameBoard.get(position - boardDimension).equals("B")){ //x - 11
                    PlayerBlue.union(position, (position - boardDimension));
                }
            }

            //ALONG RIGHT EDGE
            else{
                if(GameBoard.get(position - boardDimension).equals("B")){
                    PlayerBlue.union(position, position - boardDimension);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("B")){
                    PlayerBlue.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position - 1).equals("B")){
                    PlayerBlue.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("B")){
                    PlayerBlue.union(position, position + boardDimension);
                }
            }
        }

        //TOP EDGE
        else if(isRedEdge(position).equals("TopEdge")){
            GameBoard.set(position, "B");

            //TOP LEFT CORNER
            if(position == 1){
                if(GameBoard.get(position + 1).equals("B")){ //x + 1
                    PlayerRed.union(position, (position + 1));
                }
                if(GameBoard.get(position + boardDimension).equals("B")){ //x + 11
                    PlayerRed.union(position, (position + boardDimension));
                }
            }
            //TOP RIGHT CORNER
            else if (position == boardDimension){ //11
                if(GameBoard.get(position - 1).equals("B")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("B")){
                    PlayerRed.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("B")){
                    PlayerRed.union(position, position + boardDimension);
                }
            }

            //ALONG TOP EDGE
            else{
                if(GameBoard.get(position - 1).equals("B")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("B")){
                    PlayerRed.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("B")){
                    PlayerRed.union(position, position + boardDimension);
                }
                if(GameBoard.get(position + 1).equals("B")){
                    PlayerRed.union(position, position + 1);
                }
            }
        }

        //BOTTOM EDGE
        else if(isRedEdge(position).equals("BottomEdge")){
            GameBoard.set(position, "B");
            //BOTTOM LEFT CORNER
            if(position == ((boardDimension * boardDimension) - boardDimension + 1)){ //111
                if(GameBoard.get(position - boardDimension).equals("B")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("B")){
                    PlayerRed.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position + 1).equals("B")){
                    PlayerRed.union(position, position + 1);
                }
            }
            //BOTTOM RIGHT CORNER
            else if (position == (boardDimension * boardDimension)){ //121
                if(GameBoard.get(position - 1).equals("B")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position - boardDimension).equals("B")){
                    PlayerRed.union(position, position - boardDimension);
                }
            }

            //ALONG BOTTOM EDGE
            else{
                if(GameBoard.get(position - 1).equals("B")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("B")){
                    PlayerRed.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position - boardDimension).equals("B")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position + 1).equals("B")){
                    PlayerRed.union(position, position + 1);
                }
            }
        }

        //NONE OF THE EDGES
        else{ //For when not touching an edge.
            GameBoard.set(position, "B");
            if(GameBoard.get(position - 1).equals("B")){ //x - 1
                PlayerBlue.union(position, (position - 1));
            }
            if(GameBoard.get(position + 1).equals("B")){ //x + 1
                PlayerBlue.union(position, (position + 1));
            }
            if(GameBoard.get(position - (boardDimension)).equals("B")){ //x - 11
                PlayerBlue.union(position, position - (boardDimension));
            }
            if(GameBoard.get(position - (boardDimension - 1)).equals("B")){ //x - 10
                PlayerBlue.union(position, position - (boardDimension - 1));
            }
            if(GameBoard.get(position + (boardDimension - 1)).equals("B")){ //x + 10
                PlayerBlue.union(position, position + (boardDimension - 1));
            }
            if(GameBoard.get(position + (boardDimension)).equals("B")){ //x + 11
                PlayerBlue.union(position, position + (boardDimension));
            }
        }
        if((PlayerBlue.find(position) == LEFT_EDGE && PlayerBlue.find(RIGHT_EDGE) == LEFT_EDGE) ||
                (PlayerBlue.find(position) == RIGHT_EDGE && PlayerBlue.find(LEFT_EDGE) == RIGHT_EDGE)){
            return true;
        }
        return false;
    }

    public boolean playRed(int position, boolean displayNeighbors){
        if(position < 1 || position > (boardDimension * boardDimension)){
            System.out.println("The move " + position + " is invalid!");
            return false;
        }

        if(displayNeighbors){
            printNeighbors(position, "red");
        }

        if(isOccupied(position)){
            return false;
        }

        //TOP EDGE
        if(isRedEdge(position).equals("TopEdge")){
            GameBoard.set(position, "R");
            PlayerRed.union(position, TOP_EDGE);
            //TOP LEFT CORNER
            if(position == 1){
                if(GameBoard.get(position + 1).equals("R")){ //x + 1
                    PlayerRed.union(position, (position + 1));
                }
                if(GameBoard.get(position + boardDimension).equals("R")){ //x + 11
                    PlayerRed.union(position, (position + boardDimension));
                }
            }
            //TOP RIGHT CORNER
            else if (position == boardDimension){ //11
                if(GameBoard.get(position - 1).equals("R")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("R")){
                    PlayerRed.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("R")){
                    PlayerRed.union(position, position + boardDimension);
                }
            }

            //ALONG TOP EDGE
            else{
                if(GameBoard.get(position - 1).equals("R")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("R")){
                    PlayerRed.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("R")){
                    PlayerRed.union(position, position + boardDimension);
                }
                if(GameBoard.get(position + 1).equals("R")){
                    PlayerRed.union(position, position + 1);
                }
            }
        }

        //BOTTOM EDGE
        else if(isRedEdge(position).equals("BottomEdge")){
            GameBoard.set(position, "R");
            PlayerRed.union(position, BOTTOM_EDGE);
            //BOTTOM LEFT CORNER
            if(position == ((boardDimension * boardDimension) - boardDimension + 1)){ //111
                if(GameBoard.get(position - boardDimension).equals("R")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("R")){
                    PlayerRed.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position + 1).equals("R")){
                    PlayerRed.union(position, position + 1);
                }
            }
            //BOTTOM RIGHT CORNER
            else if (position == (boardDimension * boardDimension)){ //121
                if(GameBoard.get(position - 1).equals("R")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position - boardDimension).equals("R")){
                    PlayerRed.union(position, position - boardDimension);
                }
            }

            //ALONG BOTTOM EDGE
            else{
                if(GameBoard.get(position - 1).equals("R")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("R")){
                    PlayerRed.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position - boardDimension).equals("R")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position + 1).equals("R")){
                    PlayerRed.union(position, position + 1);
                }
            }
        }

        //LEFT EDGE
        else if(isBlueEdge(position).equals("LeftEdge")){
            GameBoard.set(position, "R");
            //TOP LEFT CORNER
            if(position == 1){ //LEFT EDGE, TOP CORNER
                if(GameBoard.get(position + 1).equals("R")){ //x + 1
                    PlayerRed.union(position, (position + 1));
                }
                if(GameBoard.get(position + boardDimension).equals("R")){ //x + 11
                    PlayerRed.union(position, (position + boardDimension));
                }
            }
            //BOTTOM LEFT CORNER
            else if (position == ((boardDimension * boardDimension) - boardDimension + 1)){ //111
                //THE ABOVE IS LEFT EDGE, BOTTOM CORNER
                if(GameBoard.get(position + 1).equals("R")){ //x + 1
                    PlayerRed.union(position, (position + 1));
                }
                if(GameBoard.get(position - boardDimension).equals("R")){ //x - 11
                    PlayerRed.union(position, (position - boardDimension));
                }
                if(GameBoard.get(position - boardDimension + 1).equals("R")){ //x - 10
                    PlayerRed.union(position, (position - boardDimension + 1));
                }
            }

            //ALONG LEFT EDGE
            else{
                if(GameBoard.get(position - boardDimension).equals("R")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position - boardDimension + 1).equals("R")){
                    PlayerRed.union(position, position - boardDimension + 1);
                }
                if(GameBoard.get(position + 1).equals("R")){
                    PlayerRed.union(position, position + 1);
                }
                if(GameBoard.get(position + boardDimension).equals("R")){
                    PlayerRed.union(position, position + boardDimension);
                }
            }
        }

        //RIGHT EDGE
        else if(isBlueEdge(position).equals("RightEdge")){
            GameBoard.set(position, "R");
            //TOP RIGHT CORNER
            if(position == boardDimension){ //11, RIGHT EDGE, TOP CORNER
                if(GameBoard.get(position - 1).equals("R")){ //x + 1
                    PlayerRed.union(position, (position - 1));
                }
                if(GameBoard.get(position + boardDimension).equals("R")){ //x + 11
                    PlayerRed.union(position, (position + boardDimension));
                }
                if(GameBoard.get(position + boardDimension - 1).equals("R")){ //x + 10
                    PlayerRed.union(position, (position + boardDimension - 1));
                }
            }
            //BOTTOM RIGHT CORNER
            else if (position == (boardDimension * boardDimension)){ //121, RIGHT EDGE, BOTTOM CORNER
                if(GameBoard.get(position - 1).equals("R")){ //x - 1
                    PlayerRed.union(position, (position - 1));
                }
                if(GameBoard.get(position - boardDimension).equals("R")){ //x - 11
                    PlayerRed.union(position, (position - boardDimension));
                }
            }

            //ALONG RIGHT EDGE
            else{
                if(GameBoard.get(position - boardDimension).equals("R")){
                    PlayerRed.union(position, position - boardDimension);
                }
                if(GameBoard.get(position + boardDimension - 1).equals("R")){
                    PlayerRed.union(position, position + boardDimension - 1);
                }
                if(GameBoard.get(position - 1).equals("R")){
                    PlayerRed.union(position, position - 1);
                }
                if(GameBoard.get(position + boardDimension).equals("R")){
                    PlayerRed.union(position, position + boardDimension);
                }
            }
        }


        //NO EDGES
        else{ //For when not touching an edge.
            GameBoard.set(position, "R");
            if(GameBoard.get(position - 1).equals("R")){ //x - 1
                PlayerRed.union(position, (position - 1));
            }
            if(GameBoard.get(position + 1).equals("R")){ //x + 1
                PlayerRed.union(position, (position + 1));
            }
            if(GameBoard.get(position - (boardDimension)).equals("R")){ //x - 11
                PlayerRed.union(position, position - (boardDimension));
            }
            if(GameBoard.get(position - (boardDimension - 1)).equals("R")){ //x - 10
                PlayerRed.union(position, position - (boardDimension - 1));
            }
            if(GameBoard.get(position + (boardDimension - 1)).equals("R")){ //x + 10
                PlayerRed.union(position, position + (boardDimension - 1));
            }
            if(GameBoard.get(position + (boardDimension)).equals("R")){ //x + 11
                PlayerRed.union(position, position + (boardDimension));
            }
        }
        if((PlayerRed.find(position) == TOP_EDGE && PlayerRed.find(BOTTOM_EDGE) == TOP_EDGE) ||
                (PlayerRed.find(position) == BOTTOM_EDGE && PlayerRed.find(TOP_EDGE) == BOTTOM_EDGE)){
            return true;
        }
        return false;
    }
}
