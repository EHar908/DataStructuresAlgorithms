import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame {
    public LadderGameExhaustive(String dictionaryFile){
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
            System.out.println("Seeking exhaustive solution from " + start + " -> " + end);
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

}
