import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WritePoetry {
    public String writePoem(String file, String startWord, int length, boolean printHashtable){
        ArrayList<String> wordList = openFile(file); //Does it HAVE to be an array of string lists?
        //Verify that this is
        HashTable<String, WordFreqInfo> table = new HashTable<>();
        for(int i = 0; i < wordList.size()-1; i++){//cuz no follow-up word
            if(table.contains(wordList.get(i))){ //If the hashtable already contains this key
                //Have to look through the list of words in the table value, seeing if any of them equal to the temporary wordfreqinfo we just created.
                table.find(wordList.get(i)).updateFollows(wordList.get(i+1));
            }
            else{ //If the hashtable doesn't contain this key; it's gonna already not have a value, if so.
                WordFreqInfo followUpWord = new WordFreqInfo(wordList.get(i), 0);
                table.insert(wordList.get(i), followUpWord);
                table.find(wordList.get(i)).updateFollows(wordList.get(i+1));
            }
        }

        //Table is now created.
        Random rnd = new Random(); // do this one time outside of the loop, then use to generate random numbers
        String currentWord = startWord; //Print the startWord
        String poem = ""; //We aren't printing each word; we're making a string out of it.
        for(int i = 0; i < length; i++){ //We start by going through the whole table here.
            char[] chars = currentWord.toCharArray(); //Break up the current word into characters to verify that it is a punctuation or not.
            if(chars.length == 1){ //First verify that the length is 1, which punctuations have to be.
                if(!Character.isLetter(chars[0])){ //Then we see whether or not it's a letter.
                    poem = poem.stripTrailing(); //Removes the trailing space at the end.
                    poem = poem + currentWord + "\n"; //If it ISN'T a letter, then we add a new line afterwards.
                }
                else{
                    poem = poem + currentWord + " ";
                }
            }
            else{
                poem = poem + currentWord + " ";  //Print the word. Need to verify if follow-up is another word or a punctuation; if it's a punctuation, you don't add a space, otherwise you do.
            }
            int count = rnd.nextInt(table.find(currentWord).getOccurCount()); //0 -> (n-1). We already generate the random value based on the number of values/occurrances.
            currentWord = table.find(currentWord).getFollowWord(count);
        }

        if(printHashtable){
            System.out.println();
            System.out.println(String.format("--- %s Table ---", startWord));
            System.out.println(table.toString(table.size()));
        }

        poem = poem.stripTrailing();
        char[] checkingPoem = poem.toCharArray();
        if(Character.isLetter(checkingPoem[checkingPoem.length - 1])){
            poem = poem + ".";
        }
        return poem;
    }

    private ArrayList<String> openFile(String file){ //Should this just be a static/void method that adds each word and its frequency to the HashTable?
        File poemfile = new File(file);
        ArrayList<String> fileRead = new ArrayList<>();
        ArrayList<String> splitFile = new ArrayList<>();
        try (Scanner input = new Scanner(poemfile)) { //why is it raising error?
            while (input.hasNext()) {
                String word = input.next().toLowerCase(); //the "next" here ignores all the empty space and any "\n" characters.
                fileRead.add(word); //Collect each line of the file into an array.
            }
        }
        catch (Exception ex) {
            System.out.println("No file found.");
        }



        for(int j = 0; j < fileRead.size(); j++){
            char[] chars = fileRead.get(j).toCharArray();
            //We then look at each word, chop them up into letters put into another list, and check to see if there's any punctuation at the end.
            if(!Character.isLetter(chars[chars.length - 1])){ //If the last character of the word ISN'T a letter.
                splitFile.add(fileRead.get(j).substring(0, fileRead.get(j).length() - 1)); //shaving off the punctuation at the end; it's either -1 or -2, can't recall.
                splitFile.add(fileRead.get(j).substring(chars.length - 1, chars.length)); //Then add the punctuation, we still want that.
            }
            else{
                splitFile.add(fileRead.get(j));
            }
        }

        return splitFile;
    }

}
