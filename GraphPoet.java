
package assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphPoet {
    private ArrayList<Vertex> vertices; //holds the actual graph
    private ArrayList<String> inputWords; //holds the words from input but removes duplicates
    private ArrayList<String> original; //holds each word from the input in an ArrayList
    private ArrayList<String> editedPoem;


    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {
        Scanner s = new Scanner(new FileReader(corpus));
        original = new ArrayList<String>();
        inputWords = new ArrayList<String>();
        vertices = new ArrayList<Vertex>();
        int index = 0;

        while (s.hasNext()){
            String word = s.next();
            word = word.toLowerCase();
            original.add(word);
            if (!inputWords.contains(word)){
                inputWords.add(word);
            }
        }

        for (int i  = 0; i < inputWords.size(); i++){
            vertices.add(new Vertex(inputWords.get(i)));
        }

        for (int i = 0; i < vertices.size(); i++){
            for (int j = 0; j < original.size() - 1; j++){
                if (vertices.get(i).getName().equals(original.get(j))){
                    //vertices.get(i).addEdge(original.get(j+1), vertices);
                    index = findVertexIndexByName(original.get(j+1));
                    if (vertices.get(i).getWeight(original.get(j+1)) > 0){
                        vertices.get(i).getEdges().put(vertices.get(index).getName(), vertices.get(i).getWeight(original.get(j+1)) + 1);
                        continue;
                    }
                    vertices.get(i).getEdges().put(vertices.get(index).getName(), 1);
                }
            }
        }
        /* Read in the File and place into graph here */
    }

    public int findVertexIndexByName(String name){
        for (int i = 0; i < vertices.size(); i++){
            if (name.toLowerCase().equals(vertices.get(i).getName().toString().toLowerCase())){
                return i;
            }
        }
        return -1;
    }

    public boolean checkCurr(String current){
        int flag = findVertexIndexByName(current);
        if (flag >= 0){
            return true;
        }
        return false;
    }

    public boolean checkFinal(String last){
        int flag = findVertexIndexByName(last);
        if (flag >= 0){
            return true;
        }
        return false;
    }

    public String findBridgeWord(String first, String second){
        String mostOften = "$";
        String temp = "";
        int currMax = 0;
        int firstIndex = findVertexIndexByName(first);
        for (int i = 0; i< vertices.get(firstIndex).getEdges().size(); i++){
                String check = vertices.get(firstIndex).getEdge(i);
                int secondIndex = findVertexIndexByName(check);
                if (vertices.get(secondIndex).getEdges().containsKey(second)){
                    if (vertices.get(secondIndex).getWeight(second) >= currMax){
                        mostOften = check;
                        currMax = vertices.get(secondIndex).getWeight(second);
                    }
                }
        }
        return mostOften;
    }



    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
        Scanner s = new Scanner(new FileReader(input));
        editedPoem = new ArrayList<String>();
        String currentWord = "*";
        String finalWord = "*";
        String bridgeWord = "";
        String poem = "";

        while (s.hasNext()){
            finalWord = s.next();
            if (!currentWord.equals("*") && !finalWord.equals("*")){
                if (checkCurr(currentWord) && checkFinal(finalWord)){
                    bridgeWord = findBridgeWord(currentWord, finalWord);
                    if (!bridgeWord.equals("$")){
                        poem = poem + bridgeWord + " " + finalWord + " ";
                        currentWord = finalWord;
                        continue;
                    }
                }
            }
            currentWord = finalWord;
            poem = poem + currentWord + " ";
        }
        return poem;
    }

}