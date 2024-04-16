import java.util.*;

public class Graph {
    private final GraphNode[] vertices;  // Adjacency list for graph.
    private final String name;  //The file from which the graph was created.
    private int[][] residualGraph;
    private boolean[][] finalGraph;
    private int[][] capacityLog;
    private ArrayList<Integer> R = new ArrayList<>();
    private ArrayList<String> finalFlow = new ArrayList<>();

    public Graph(String name, int vertexCount) {
        this.name = name;

        vertices = new GraphNode[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            vertices[vertex] = new GraphNode(vertex);
        }
        residualGraph = new int[vertexCount][vertexCount];
        capacityLog = new int[vertexCount][vertexCount];
        finalGraph = new boolean[vertexCount][vertexCount];
    }

    public boolean addEdge(int source, int destination, int capacity) {
        // A little bit of validation
        if (source < 0 || source >= vertices.length) return false;
        if (destination < 0 || destination >= vertices.length) return false;

        // This adds the actual requested edge, along with its capacity
        vertices[source].addEdge(source, destination, capacity);
        residualGraph[source][destination] = capacity;
        capacityLog[source][destination] = capacity;

        vertices[destination].addEdge(destination, source, 0);

        return true;
    }

    /**
     * Algorithm to find max-flow in a network
     */
    public int findMaxFlow(int s, int t, boolean report) {
        if(report){
            System.out.println("-- Max Flow: " + name + " --");
        }
        int totalFlow = 0;
        while(hasAugmentingPath(s, t)) {
            int availableFlow = Integer.MAX_VALUE;
            ArrayList<Integer> printPath = new ArrayList<>();
            for (int v = t; v != s; v = vertices[v].parent) { //Iterating through the nodes between the starting and the terminating. v represents the current node.
                if (report) {
                    printPath.add(v);
                }
                if (availableFlow > residualGraph[vertices[v].parent][v]) {
                    availableFlow = residualGraph[vertices[v].parent][v];
                }
            }
            for (int v = t; v != s; v = vertices[v].parent) {
                residualGraph[vertices[v].parent][v] -= availableFlow; //s -> t, so we're doing it forward.
                residualGraph[v][vertices[v].parent] += availableFlow;
            }
            if (report) {
                System.out.print("Flow " + availableFlow + ": " + s + " ");
                for (int i = printPath.size() - 1; i > -1; i--) {
                    System.out.print(printPath.get(i) + " ");
                }
                System.out.println();

            }
            totalFlow += availableFlow;
        }
        if(report){
            for (int i = 0; i < residualGraph.length; i++) {
                for (var edge : vertices[i].successor) {
                    if (!(capacityLog[edge.from][edge.to] == 0)) {
                        if(capacityLog[edge.from][edge.to] != residualGraph[edge.from][edge.to]){
                            finalFlow.add("Edge (" + edge.from + ", " + edge.to + ") transports " +
                                    residualGraph[edge.to][edge.from] + " items.");
                        }
                    }
                }
            }
            for(String result : finalFlow){
                System.out.println(result);
            }
        }

            return totalFlow;

    }

    /**
     * Algorithm to find an augmenting path in a network
     */
    private boolean hasAugmentingPath(int s, int t) {
        for(int v = s; v <= t; v++){
            vertices[v].parent = -1;
        }
        Queue<Integer> vertexQueue = new LinkedList<>();
        vertexQueue.add(s);
        while(!(vertexQueue.isEmpty()) && (vertices[t].parent == -1)){
            int v = vertexQueue.remove();
            for(var edge : vertices[v].successor){
                int w = edge.to;
                if((residualGraph[v][w] > 0) && (vertices[w].parent == -1) && (w != s)){
                    vertices[w].parent = v;
                    vertexQueue.add(w);
                }
            }
        }
        if(vertices[t].parent != -1){
            return true;
        }
        return false;
    }

    /**
     * Algorithm to find the min-cut edges in a network
     */
    public void findMinCut(int s) {
        for(int v = 0; v < vertices.length; v++){
            for(var edge : vertices[v].successor){
                if(!(capacityLog[edge.from][edge.to] == 0)) {
                    if (residualGraph[edge.from][edge.to] == 0) { //THERE IS NO LEFTOVER CAPACITY
                        finalGraph[edge.to][edge.from] = true;
                    } else if (residualGraph[edge.from][edge.to] == capacityLog[edge.from][edge.to]) { //If there is NO FLOW going through at all
                        finalGraph[edge.to][edge.from] = false;
                        finalGraph[edge.from][edge.to] = false;
                    }
                     else { // Otherwise, there must be some flow going through, so you can travel either way.
                        finalGraph[edge.to][edge.from] = true;
                        finalGraph[edge.from][edge.to] = true;
                    }
                }
            }
        }
        R.add(s);
        isReachable(s);
        System.out.println();
        ArrayList<String> thingsToCut = new ArrayList<>();
        for(int i = 0; i < R.size(); i++){
            for(var edge : vertices[i].successor){
                if(!(capacityLog[edge.from][edge.to] == 0)){
                    thingsToCut.add("(" + i + ", " + edge.to + ")");
                }
            }
        }
        System.out.println("-- Min Cut: " + "demands" + " --");
        for(int i = 0; i < thingsToCut.size(); i++){
            System.out.println("Min Cut Edge: " + thingsToCut.get(i));
        }
        System.out.println();
    }
    private void isReachable(int v){
        for(var edge : vertices[v].successor){
            if(!(capacityLog[edge.from][edge.to] == 0)) {
                if (finalGraph[v][edge.to] && !(R.contains(edge.to))) { // s <- t
                    R.add(edge.to);
                    isReachable(edge.to);
                }
            }
        }

    }
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("The Graph " + name + " \n");
        for (var vertex : vertices) {
            sb.append((vertex.toString()));
        }
        return sb.toString();
    }
}
