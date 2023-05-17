package ro.unibuc.fmi.graph;


import java.util.*;


public class Graph {

    private final Map<Vertex, List<Vertex>> adjVertices;
    private final Map<Vertex, Map<Vertex, Double>> weights = new HashMap<>();

    private boolean isDirected;

    private boolean isWeighted;

    public Graph() {
        this.adjVertices = new HashMap<>();
        this.isWeighted = false;
        this.isDirected = false;
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

    public void dijkstra(int numberOfVertices, int startVertex) {

        if (!isWeighted) {
            throw new RuntimeException("Graph is not weighted for Dijkstra algorithm!");
        }

        // for storing distances after removing vertex from Queue
        double[] distances = new double[numberOfVertices];
        // for storing father id's after removing vertex from Queue
        int[] parents = new int[numberOfVertices];
        List<Edge> edges = new ArrayList<>();

        Arrays.fill(parents, -1);

        for (Vertex v : adjVertices.keySet()) {
            for (Vertex w : adjVertices.get(v)) {
                edges.add(new Edge(v, w));
            }
        }

        // set up vertex queue
        PriorityQueue<Vertex> queue = new PriorityQueue<>();

        for (int i = 0; i < numberOfVertices; i++) {

            if (i != startVertex) {
                queue.add(new Vertex(i));
            }

        }

        // add startVertex
        Vertex vertex = new Vertex(startVertex);
        vertex.setDistance(0);
        queue.add(vertex);

        // loop through all vertices
        while (!queue.isEmpty()) {

            // get vertex with the shortest distance
            Vertex u = queue.remove();
            distances[u.label] = u.getDistance();

            // iterate through all neighbours

            for (Edge e : edges) {

                for (Vertex v : queue) {

                    // check if vertex was visited already
                    if (!Objects.equals(e.getDestinationVertex().label, v.label)) {
                        continue;
                    }

                    // check distance 
                    if (v.getDistance() > u.getDistance() + weights.get(e.sourceVertex).get(e.destinationVertex)) {

                        v.setDistance(u.getDistance() + weights.get(e.sourceVertex).get(e.destinationVertex));
                        v.setParent(u);
                        parents[v.label] = v.getParent().label;

                    }

                }

            }

        }

        // print final shortest paths
        System.out.println("Vertex\tDistance\tParent Vertex");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.println(i + "\t" + distances[i] + "\t\t" + parents[i]);
        }

    }


    public List<Integer> aStar(int startNode, int goalNode) {

        PriorityQueue<Vertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(a -> a.f));
        Set<Integer> closedSet = new HashSet<>();

        Vertex start = new Vertex(startNode);
        start.g = 0;
        start.h = calculateHeuristic(startNode, goalNode);
        start.f = start.h;
        openSet.add(start);

        while (!openSet.isEmpty()) {

            Vertex currentNode = openSet.poll();

            if (currentNode.label == goalNode) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode.label);

            for (Vertex v : adjVertices.keySet()) {

                if (Objects.equals(v.label, currentNode.label)) {

                    for (Vertex w : adjVertices.get(v)) {

                        int neighborId = w.label;
                        double neighborWeight = weights.get(new Vertex(currentNode.label)).get(new Vertex(w.label));

                        if (closedSet.contains(neighborId)) continue;

                        Vertex neighborNode = new Vertex(neighborId);
                        neighborNode.g = currentNode.g + neighborWeight;
                        neighborNode.h = calculateHeuristic(neighborId, goalNode);
                        neighborNode.f = neighborNode.g + neighborNode.h;
                        neighborNode.parent = currentNode;

                        boolean isNewNode = true;

                        for (Vertex openNode : openSet) {
                            if (openNode.label == neighborId && neighborNode.f >= openNode.f) {
                                isNewNode = false;
                                break;
                            }
                        }

                        if (isNewNode) {
                            openSet.add(neighborNode);
                        }

                    }

                }

            }

        }

        return new ArrayList<>();

    }

    public double calculateHeuristic(int startNode, int goalNode) {
        return Math.abs(startNode - goalNode);
    }

    public List<Integer> reconstructPath(Vertex vertex) {

        List<Integer> path = new ArrayList<>();
        Vertex current = vertex;

        while (current != null) {
            path.add(0, current.label);
            current = current.parent;
        }

        return path;

    }

}
