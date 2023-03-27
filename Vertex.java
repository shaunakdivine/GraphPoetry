package assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Vertex<T> {
    private T name;
    private Map<T, Integer> edges; // T is a vertex, Integer is the weight

    public Vertex(String word){
        this.name = (T) word;
        edges = new HashMap<>();

    }

    public T getName(){
        return name;
    }

    public Map getEdges(){
        return edges;
    }

    public int getWeight(String key){
        if (edges.containsKey(key)){
            return edges.get(key);
        }
        else{
            return 0;
        }

    }

    public String getEdge(int index){
        Set<String> edgesSet = new HashSet<>();
        edgesSet = (Set<String>) edges.keySet();
        Iterator iter = edgesSet.iterator();
        String next = "";
        for (int i = 0; i < index + 1; i++){
            if(iter.hasNext()) {
                next = iter.next().toString();
            }
            else{
                return next;
            }
        }
        return next;
    }

}
