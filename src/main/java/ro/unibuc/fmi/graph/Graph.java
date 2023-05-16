package ro.unibuc.fmi.graph;


import java.util.*;


public class Graph {

    private final Map<Vertex, List<Vertex>> adjVertices;
    private final Map<Vertex, Map<Vertex, Double>> weights = new HashMap<>();

    private boolean isWeighted;

    public Graph() {
        this.adjVertices = new HashMap<>();
        this.isWeighted = false;
    }


    public boolean isWeighted() {
        return isWeighted;
    }

    public void setWeighted(boolean isWeighted) {
        this.isWeighted = isWeighted;
    }

    public void addVertex(Integer label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void removeVertex(Integer label) {
        Vertex v = new Vertex(label);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    public void addEdge(Integer label1, Integer label2, double weight) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
        setWeight(v1, v2, weight);
    }

    public void addEdge(Integer label1, Integer label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    private void setWeight(Vertex one, Vertex two, double weight) {
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

    public List<Vertex> getAdjVertices(Integer label) {
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
                if(isWeighted) {
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

    public Set<Integer> depthFirstTraversal(Graph graph, Integer root) {

        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {

            Integer vertex = stack.pop();

            if (!visited.contains(vertex)) {

                visited.add(vertex);

                for (Vertex v : graph.getAdjVertices(vertex)) {
                    stack.push(v.label);
                }

            }

        }

        return visited;

    }

    public Set<Integer> breadthFirstTraversal(Graph graph, Integer root) {

        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {

            Integer vertex = queue.poll();

            for (Vertex v : graph.getAdjVertices(vertex)) {

                if (!visited.contains(v.label)) {
                    visited.add(v.label);
                    queue.add(v.label);
                }

            }

        }

        return visited;

    }

}