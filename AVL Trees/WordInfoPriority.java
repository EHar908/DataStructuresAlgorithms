public class WordInfoPriority extends WordInfo implements Comparable<WordInfoPriority> {

    private String word;
    private int moves;
    private String history;
    private int estimatedWork;
    private int priority;

    public void setPriority(int value){ this.priority = value; }

    public WordInfoPriority(){}

    public WordInfoPriority(String word, int moves){
        this.word = word;
        this.moves = moves;
        this.history = word;
    }
    public WordInfoPriority(String word, int moves, String history) {
        this.word = word;
        this.moves = moves;
        this.history = history;
    }

    public String getWord() {
        return this.word;
    }

    public int getMoves() {
        return this.moves;
    }

    public String getHistory() {
        return this.history;
    }

    public WordInfoPriority(String word, int moves, int estimatedWork){
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
        this.priority = this.moves + this.estimatedWork;
    }
    public WordInfoPriority(String word, int moves, int estimatedWork, String history){
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
        this.history = history;
        this.priority = this.moves + this.estimatedWork;
    }

    @Override
    public int compareTo(WordInfoPriority o) {
        if(priority > o.priority){
            //If word 1 has a higher ETW than word 2
            return 1;
        }
        else if(priority < o.priority){
            //If word 2 has a higher ETW than word 1
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return String.format("Word %s Moves %d : History[%s]",
                word, moves, history);
    }

}
