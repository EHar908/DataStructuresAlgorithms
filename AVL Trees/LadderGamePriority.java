import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LadderGamePriority extends LadderGame {
    public LadderGamePriority(String dictionaryFile){
        super(dictionaryFile);
    }

    public void play(String start, String end) {
        start = start.toLowerCase();
        end = end.toLowerCase();

        if(start.length() != end.length()){
            System.out.println("Error: The Start Word and End Word must be the same length.");
        }

        if(!inDictionary(start)){
            System.out.println("Error: The Start Word is not in the dictionary.");
        }

        else if(!inDictionary(end)){
            System.out.println("Error: The End Word is not in the dictionary.");
        }

        AVLTree<WordInfoPriority> thetree = new AVLTree<WordInfoPriority>();
        AVLTree<String> wordsUsed = new AVLTree<String>();

        WordInfoPriority firstWord = new WordInfoPriority(start, 0, difLetters(start, end));
        thetree.insert(firstWord);
        int totalEnqueues = 1;

        boolean found = false;
        while(!thetree.isEmpty() && !found){
            WordInfoPriority dequeuedWord = thetree.deleteMin(); //Pick off the smallest node
            ArrayList<String> oneAwayWords = oneAway(dequeuedWord.getWord(), true); //Collect the neighbors here.
            for (int i = 0; i < oneAwayWords.size(); i++) { //Go through the list of the neighbors
                if(oneAwayWords.get(i).equals(end)){
                    found = true;
                    WordInfoPriority newWord = new WordInfoPriority(oneAwayWords.get(i), dequeuedWord.getMoves() + 1, dequeuedWord.getHistory() + " " + oneAwayWords.get(i));
                    firstWord = newWord;
                    break;

                }
                else if (oneAwayWords.get(i) != end){
                    if(!wordsUsed.contains(oneAwayWords.get(i))){ //If wordsUsed does NOT contain oneAwayWords.get(i)
                        wordsUsed.insert(oneAwayWords.get(i));
                        WordInfoPriority newWord = new WordInfoPriority(oneAwayWords.get(i), dequeuedWord.getMoves() + 1, difLetters(dequeuedWord.getWord(), end), dequeuedWord.getHistory() + " " + oneAwayWords.get(i));
                        thetree.insert(newWord);
                        totalEnqueues++;
                    }
                }

            }
        }

        if(found){
            System.out.println("Seeking A* solution from " + start + " -> " + end);
            System.out.println("[" + firstWord.getHistory() + "] " + "Total Enqueues: "+  totalEnqueues);
        }
        else if (!found){
            System.out.println(start + " -> " + end + " : " + "No ladder was found");
        }



        orgWords = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < orgWordsCopy.size(); i++) {
            orgWords.add(new ArrayList<String>());
            for (int j = 0; j < orgWordsCopy.get(i).size(); j++) {
                orgWords.get(i).add(orgWordsCopy.get(i).get(j));
            }
        }

    }

    public int difLetters(String oneWord, String otherWord){
        int diffLetters = 0;
        for (int i = 0; i < oneWord.length(); i++) {
            if(oneWord.charAt(i) != otherWord.charAt(i)){
                diffLetters++;
            }
        }
        return diffLetters;
    }

}



