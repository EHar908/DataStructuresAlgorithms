import java.io.File;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {

    ArrayList<ArrayList<String>> orgWords = new ArrayList<>();
    ArrayList<ArrayList<String>> orgWordsCopy = new ArrayList<>();



    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public boolean inDictionary(String start) {
        ArrayList<String> sameLengthList = orgWords.get(start.length());
        for (int i = 0; i < sameLengthList.size(); i++) {
            if (sameLengthList.get(i).equals(start)) {
                return true;
            }
        }
        return false;
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

        Queue<WordInfo> startLadder = new Queue<WordInfo>();
        WordInfo firstWord = new WordInfo(start, 0);
        startLadder.enqueue(firstWord);
        int totalEnqueues = 1;

        boolean found = false;
        while(!startLadder.isEmpty() && !found){
            WordInfo dequeuedWord = startLadder.dequeue();
            ArrayList<String> oneAwayWords = oneAway(dequeuedWord.getWord(), true);
            for (int i = 0; i < oneAwayWords.size(); i++) {
                if(oneAwayWords.get(i).equals(end)){
                    found = true;
                    WordInfo newWord = new WordInfo(oneAwayWords.get(i), dequeuedWord.getMoves() + 1, dequeuedWord.getHistory() + " " + oneAwayWords.get(i));
                    firstWord = newWord;
                    break;

                }
                else if (oneAwayWords.get(i) != end){
                    WordInfo newWord = new WordInfo(oneAwayWords.get(i), dequeuedWord.getMoves() + 1, dequeuedWord.getHistory() + " " + oneAwayWords.get(i));
                    startLadder.enqueue(newWord);
                    totalEnqueues++;

                }

            }
        }

        if(found){
            System.out.println(start + " -> " + end + " : " + firstWord.getMoves() + " Moves [" + firstWord.getHistory() + "] " + "Total Enqueues: "+  totalEnqueues);
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

    public ArrayList<String> getSameLengths(int index){
        return orgWords.get(index);
    }

    public boolean isOneAway(String oneWord, String otherWord){
        int diffLetters = 0;
        for (int i = 0; i < oneWord.length(); i++) {

            if(oneWord.charAt(i) != otherWord.charAt(i)){
                diffLetters++;
            }
        }
        return diffLetters == 1;
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> oneAwayWords = new ArrayList<>();
        ArrayList<String> sameLengthWords = getSameLengths(word.length());
        for (int i = 0; i < sameLengthWords.size(); i++) {
            String otherWord = sameLengthWords.get(i);
            if(isOneAway(word, otherWord)){
                {
                    oneAwayWords.add(otherWord);
                }
            }
        }

        if(withRemoval){
            for (String removeWord : oneAwayWords) {
                ArrayList<String> orgWordsList = orgWords.get(word.length());
                for (int j = 0; j < orgWordsList.size(); j++) {
                    if (removeWord.equals(orgWordsList.get(j))) {
                        orgWords.get(word.length()).remove(removeWord);
                        break;
                    }
                }
            }
        }

        return oneAwayWords;
    }

    public void listWords(int length, int howMany) {
        ArrayList<String> printedWords = orgWords.get(length);
        for(int i = 0; i < howMany; i++){
            System.out.println(printedWords.get(i));
        }
    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            //System.out.println(allWords);

            for (int i = 0; i <= longestWord; i++){
                orgWords.add(new ArrayList<>());
                orgWordsCopy.add(new ArrayList<>());
                for (String word : allWords){
                    if (word.length() == i){
                        orgWords.get(i).add(word);
                        orgWordsCopy.get(i).add(word);
                    }
                }

            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}