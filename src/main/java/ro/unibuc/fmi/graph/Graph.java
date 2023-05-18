package ro.unibuc.fmi.graph;


import java.util.*;


public class Graph {

    private final Map<Vertex, List<Vertex>> adjVertices;
    private final Map<Vertex, Map<Vertex, Integer>> weights = new HashMap<>();

    private boolean isDirected;

    private boolean isWeighted;

    public Graph() {
        this.adjVertices = new HashMap<>();
        this.isWeighted = false;
        this.isDirected = false;
    }

    public Map<Vertex, List<Vertex>> getAdjVertices() {
        return adjVertices;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public void setWeighted(boolean isWeighted) {
        this.isWeighted = isWeighted;
    }

    public Map<Vertex, Map<Vertex, Integer>> getWeights() {
        return weights;
    }

    public void addVertex(Integer label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void removeVertex(Integer label) {

        Vertex v = new Vertex(label);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));

    }

    public void addEdge(Integer label1, Integer label2, int weight) {

        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);

        if (!isDirected) {
            adjVertices.get(v2).add(v1);
        }

        setWeight(v1, v2, weight);

    }

    public void addEdge(Integer label1, Integer label2) {

        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);

        if (!isDirected) {
            adjVertices.get(v2).add(v1);
        }

    }

    private void setWeight(Vertex one, Vertex two, int weight) {

        weights.computeIfAbsent(one, key -> new HashMap<>());
        weights.computeIfAbsent(two, key -> new HashMap<>());

        weights.get(one).put(two, weight);
        weights.get(two).put(one, weight);

    }

    public void removeEdge(Integer label1, Integer label2) {

        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);

        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);

    }

    public List<Vertex> getAdjVerticesOfVertex(Integer label) {
        return adjVertices.get(new Vertex(label));
    }

    public int findDegree(Integer label) {
        return adjVertices.get(new Vertex(label)).size();
    }

    public boolean areVerticesAdjacent(Integer label1, Integer label2) {
        return adjVertices.get(new Vertex(label1)).contains(new Vertex(label2));
    }

    public String printEdges() {

        StringBuilder sb = new StringBuilder();

        for (Vertex v : adjVertices.keySet()) {
            for (Vertex w : adjVertices.get(v)) {
                if (isWeighted) {
                    sb.append(v).append(" ").append(w).append(" weight ").append(weights.get(v).get(w)).append('\n');
                } else {
                    sb.append(v).append(" ").append(w).append('\n');
                }
            }
        }

        return sb.toString();

    }

    public String printGraph() {

        StringBuilder sb = new StringBuilder();

        for (Vertex v : adjVertices.keySet()) {
            sb.append(v).append(adjVertices.get(v)).append('\n');
        }

        return sb.toString();

    }

    public List<Integer> findShortestPath(int startVertex, int goalVertex, int numProcessors, int numberOfVertices) {

        if (!isWeighted) {
            throw new RuntimeException("Hash Distributed A* cannot run on un-weighted graphs!");
        }

        // Initialize the priority queue and visited array
        PriorityQueue<State> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[numProcessors][numberOfVertices];

        // Create the initial state and add it to the priority queue
        State initialState = new State(startVertex, 0, heuristic(startVertex, goalVertex));
        pq.add(initialState);

        while (!pq.isEmpty()) {

            // Get the state with the minimum estimated cost
            State currentState = pq.poll();
            int currentVertex = currentState.vertex;
            int currentCost = currentState.cost;

            // Check if the goal vertex is reached
            if (currentVertex == goalVertex) {
                return currentState.path;
            }

            // Check if the current state is already visited
            if (visited[currentCost % numProcessors][currentVertex]) {
                continue;
            }

            // Mark the current state as visited
            visited[currentCost % numProcessors][currentVertex] = true;

            // Explore the neighbors of the current vertex
            for (Vertex vertex : adjVertices.keySet()) {

                if (Objects.equals(vertex.label, currentVertex)) {

                    for (Vertex neighbor : adjVertices.get(vertex)) {

                        int neighborVertex = neighbor.label;
                        int neighborCost = currentCost + weights.get(vertex).get(neighbor);
                        List<Integer> newPath = new ArrayList<>(currentState.path);
                        newPath.add(neighborVertex);

                        // Create a new state for the neighbor
                        State newState = new State(neighborVertex, neighborCost, heuristic(neighborVertex, goalVertex), newPath);

                        // Add the new state to the priority queue
                        pq.add(newState);

                    }

                }

            }

        }

        // No path found
        return Collections.emptyList();

    }

    private int heuristic(int source, int destination) {
        return Math.abs(source - destination);
    }

}
