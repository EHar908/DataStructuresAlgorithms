import java.io.File;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class LadderGame {
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

    public void play(String start, String end){}

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