package ro.unibuc.fmi.graph;


import java.util.*;


public class Graph {

    private final Map<Vertex, List<Vertex>> adjVertices;
    private final Map<Vertex, Map<Vertex, Double>> weights = new HashMap<>();

    private boolean isDirected;

    private boolean isWeighted;

    private int index;
    private List<List<Vertex>> sccList;
    private Stack<Vertex> sccStack;

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

    public Map<Vertex, Map<Vertex, Double>> getWeights() {
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

    public List<Integer> aStar(int startVertex, int goalVertex) {

        PriorityQueue<Vertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(a -> a.f));
        Set<Integer> closedSet = new HashSet<>();

        Vertex start = new Vertex(startVertex);
        start.g = 0;
        start.h = calculateHeuristic(startVertex, goalVertex);
        start.f = start.h;
        openSet.add(start);

        while (!openSet.isEmpty()) {

            Vertex currentVertex = openSet.poll();

            if (currentVertex.label == goalVertex) {
                return reconstructAStarPath(currentVertex);
            }

            closedSet.add(currentVertex.label);

            for (Vertex v : adjVertices.keySet()) {

                if (Objects.equals(v.label, currentVertex.label)) {

                    for (Vertex w : adjVertices.get(v)) {

                        int neighborId = w.label;
                        double neighborWeight = weights.get(new Vertex(currentVertex.label)).get(new Vertex(w.label));

                        if (closedSet.contains(neighborId)) continue;

                        Vertex neighborVertex = new Vertex(neighborId);
                        neighborVertex.g = currentVertex.g + neighborWeight;
                        neighborVertex.h = calculateHeuristic(neighborId, goalVertex);
                        neighborVertex.f = neighborVertex.g + neighborVertex.h;
                        neighborVertex.parent = currentVertex;

                        boolean isNewVertex = true;

                        for (Vertex openVertex : openSet) {
                            if (openVertex.label == neighborId && neighborVertex.f >= openVertex.f) {
                                isNewVertex = false;
                                break;
                            }
                        }

                        if (isNewVertex) {
                            openSet.add(neighborVertex);
                        }

                    }

                }

            }

        }

        return new ArrayList<>();

    }

    public double calculateHeuristic(int startVertex, int goalVertex) {
        return Math.abs(startVertex - goalVertex);
    }

    public List<Integer> reconstructAStarPath(Vertex vertex) {

        List<Integer> path = new ArrayList<>();
        Vertex current = vertex;

        while (current != null) {
            path.add(0, current.label);
            current = current.parent;
        }

        return path;

    }

    public List<Vertex> bidirectionalSearch(Vertex sourceVertex, Vertex targetVertex) {

        if (isDirected) {
            throw new RuntimeException("Directed graphs are not allowed for bidirectional search!");
        }

        Set<Vertex> visitedSource = new HashSet<>();
        Set<Vertex> visitedTarget = new HashSet<>();
        Queue<Vertex> queueSource = new LinkedList<>();
        Queue<Vertex> queueTarget = new LinkedList<>();
        Map<Vertex, Vertex> parentsSource = new HashMap<>();
        Map<Vertex, Vertex> parentsTarget = new HashMap<>();

        // Enqueue the source and target vertices and mark them as visited
        queueSource.offer(sourceVertex);
        visitedSource.add(sourceVertex);

        queueTarget.offer(targetVertex);
        visitedTarget.add(targetVertex);

        while (!queueSource.isEmpty() && !queueTarget.isEmpty()) {

            // Explore vertices from the source side
            Vertex currentSource = queueSource.poll();

            for (Vertex v : adjVertices.keySet()) {

                if (Objects.equals(v.label, currentSource.label)) {

                    for (Vertex w : adjVertices.get(v)) {

                        if (!visitedSource.contains(w)) {

                            queueSource.offer(w);
                            visitedSource.add(w);
                            parentsSource.put(w, currentSource);

                            if (visitedTarget.contains(w)) {
                                return reconstructBidirectionalSearchPath(w, parentsSource, parentsTarget);
                            }

                        }
                    }
                }

            }


            // Explore vertices from the target side
            Vertex currentTarget = queueTarget.poll();

            for (Vertex u : adjVertices.keySet()) {

                if (Objects.equals(u.label, currentSource.label)) {

                    for (Vertex w : adjVertices.get(u)) {

                        if (!visitedTarget.contains(w)) {
                            queueTarget.offer(w);
                            visitedTarget.add(w);
                            parentsTarget.put(w, currentTarget);

                            if (visitedSource.contains(w)) {
                                return reconstructBidirectionalSearchPath(w, parentsSource, parentsTarget);
                            }

                        }

                    }

                }

            }

        }

        return new ArrayList<>(); // No path found

    }

    public List<Vertex> reconstructBidirectionalSearchPath(Vertex intersectionVertex, Map<Vertex, Vertex> parentsSource, Map<Vertex, Vertex> parentsTarget) {

        List<Vertex> path = new ArrayList<>();
        Vertex currentVertex = intersectionVertex;

        while (currentVertex != null) {
            path.add(currentVertex);
            currentVertex = parentsSource.get(currentVertex);
        }

        currentVertex = parentsTarget.get(intersectionVertex);

        while (currentVertex != null) {
            path.add(0, currentVertex);
            currentVertex = parentsTarget.get(currentVertex);
        }

        return path;

    }

    public List<Vertex> bHeuristicSearch(Vertex sourceVertex, Vertex targetVertex) {

        Set<Vertex> visitedSource = new HashSet<>();
        Set<Vertex> visitedTarget = new HashSet<>();

        Queue<Vertex> queueSource = new LinkedList<>();
        Queue<Vertex> queueTarget = new LinkedList<>();

        Map<Vertex, Vertex> parentsSource = new HashMap<>();
        Map<Vertex, Vertex> parentsTarget = new HashMap<>();

        // Enqueue the source and target vertices and mark them as visited
        queueSource.offer(sourceVertex);
        visitedSource.add(sourceVertex);

        queueTarget.offer(targetVertex);
        visitedTarget.add(targetVertex);

        while (!queueSource.isEmpty() && !queueTarget.isEmpty()) {

            // Explore vertices from the source side
            int sourceSize = queueSource.size();

            for (int i = 0; i < sourceSize; i++) {

                Vertex currentSource = queueSource.poll();

                for (Vertex v : adjVertices.keySet()) {

                    if (Objects.equals(v.label, currentSource.label)) {

                        for (Vertex w : adjVertices.get(v)) {

                            if (!visitedSource.contains(w)) {

                                queueSource.offer(w);
                                visitedSource.add(w);
                                parentsSource.put(w, currentSource);

                                if (visitedTarget.contains(w)) {
                                    return reconstructBHeuristicPath(w, parentsSource, parentsTarget);
                                }

                            }

                        }

                    }

                }

                // Explore vertices from the target side
                int targetSize = queueTarget.size();

                for (int j = 0; j < targetSize; j++) {

                    Vertex currentTarget = queueTarget.poll();

                    for (Vertex v : adjVertices.keySet()) {

                        if (Objects.equals(v.label, currentSource.label)) {

                            for (Vertex w : adjVertices.get(v)) {

                                if (!visitedTarget.contains(w)) {
                                    queueTarget.offer(w);
                                    visitedTarget.add(w);
                                    parentsTarget.put(w, currentTarget);

                                    if (visitedSource.contains(w)) {
                                        return reconstructBHeuristicPath(w, parentsSource, parentsTarget);
                                    }

                                }

                            }
                        }

                    }

                }

            }

        }

        return new ArrayList<>(); // No path found

    }

    public List<Vertex> reconstructBHeuristicPath(Vertex intersectionVertex, Map<Vertex, Vertex> parentsSource, Map<Vertex, Vertex> parentsTarget) {

        List<Vertex> path = new ArrayList<>();
        Vertex currentVertex = intersectionVertex;

        while (currentVertex != null) {
            path.add(currentVertex);
            currentVertex = parentsSource.get(currentVertex);
        }

        currentVertex = parentsTarget.get(intersectionVertex);
        while (currentVertex != null) {
            path.add(0, currentVertex);
            currentVertex = parentsTarget.get(currentVertex);
        }

        return path;
    }


    public List<Vertex> hashDistributedAStar(Vertex sourceVertex, Vertex targetVertex) {

        if (!isWeighted) {
            throw new RuntimeException("Graph needs to be weighted to execute hash distributed A* algorithm!");
        }

        Map<Vertex, Integer> gScores = new HashMap<>();
        Map<Vertex, Integer> fScores = new HashMap<>();

        PriorityQueue<State> openSet = new PriorityQueue<>();
        Set<Vertex> closedSet = new HashSet<>();

        Map<Vertex, Vertex> parents = new HashMap<>();

        // Initialize the g-scores and f-scores for all vertices
        for (Vertex vertex : adjVertices.keySet()) {
            gScores.put(vertex, Integer.MAX_VALUE);
            fScores.put(vertex, Integer.MAX_VALUE);
        }

        // Set the g-score of the source vertex to 0
        gScores.put(sourceVertex, 0);

        // Set the f-score of the source vertex to the estimated heuristic cost to reach the target vertex
        fScores.put(sourceVertex, hashAStarHeuristicCost(sourceVertex, targetVertex));

        // Add the source vertex to the open set
        openSet.offer(new State(sourceVertex, 0, fScores.get(sourceVertex)));

        while (!openSet.isEmpty()) {
            // Get the vertex with the lowest f-score from the open set
            State current = openSet.poll();
            Vertex currentVertex = current.vertex;

            if (currentVertex == targetVertex) {
                // Reconstruct the path
                return reconstructHashAStarPath(parents, targetVertex);
            }

            closedSet.add(currentVertex);

            // Process each neighbor of the current vertex
            for (Vertex v : adjVertices.keySet()) {

                if (Objects.equals(v.label, currentVertex.label)) {

                    for (Vertex w : adjVertices.get(v)) {

                        if (closedSet.contains(w)) {
                            continue; // Ignore already processed neighbors
                        }

                        int tentativeGScore = (int) (gScores.get(currentVertex) + weights.get(v).get(w));

                        if (openSet.stream().noneMatch(state -> state.vertex == w)) {
                            openSet.offer(new State(w, tentativeGScore, tentativeGScore + hashAStarHeuristicCost(w, targetVertex)));
                        } else if (tentativeGScore >= gScores.get(w)) {
                            continue; // This is not a better path
                        }

                        // Update the parent and g-score
                        parents.put(w, currentVertex);
                        gScores.put(w, tentativeGScore);
                        fScores.put(w, tentativeGScore + hashAStarHeuristicCost(w, targetVertex));

                    }

                }

            }

        }

        return new ArrayList<>(); // No path found
    }

    public List<Vertex> reconstructHashAStarPath(Map<Vertex, Vertex> parents, Vertex targetVertex) {
        List<Vertex> path = new ArrayList<>();
        Vertex currentVertex = targetVertex;

        while (currentVertex != null) {
            path.add(currentVertex);
            currentVertex = parents.get(currentVertex);
        }

        Collections.reverse(path);
        return path;
    }

    public int hashAStarHeuristicCost(Vertex sourceVertex, Vertex targetVertex) {
        return Math.abs(sourceVertex.label - targetVertex.label);
    }

    public List<Vertex> lexBFS(Vertex startVertex) {

        if (isDirected) {
            throw new RuntimeException("Directed graphs not allowed for lexBFS call!");
        }

        List<Vertex> ordering = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        queue.offer(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()) {

            Vertex vertex = queue.poll();
            ordering.add(vertex);

            List<Vertex> neighbors = new ArrayList<>();

            for (Vertex v : adjVertices.keySet()) {

                if (Objects.equals(v.label, vertex.label)) {
                    neighbors.addAll(adjVertices.get(v));
                }

            }

            neighbors.sort(Comparator.comparingInt(n -> n.label));

            for (Vertex neighbor : neighbors) {

                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }

            }

        }

        return ordering;

    }

    public List<Vertex> lexDFS(Vertex startVertex) {

        List<Vertex> ordering = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();

        lexDFSHelper(startVertex, visited, ordering);

        return ordering;

    }

    public void lexDFSHelper(Vertex vertex, Set<Vertex> visited, List<Vertex> ordering) {

        visited.add(vertex);
        ordering.add(vertex);

        List<Vertex> neighbors = new ArrayList<>();

        for (Vertex v : adjVertices.keySet()) {

            if (Objects.equals(v.label, vertex.label)) {
                neighbors.addAll(adjVertices.get(v));
            }

        }

        neighbors.sort(Comparator.comparingInt(n -> n.label));

        for (Vertex neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                lexDFSHelper(neighbor, visited, ordering);
            }
        }

    }

    public List<List<Vertex>> findSCCs(int numberOfVertices) {

        index = 0;
        sccList = new ArrayList<>();
        sccStack = new Stack<>();

        int[] lowLink = new int[numberOfVertices];
        int[] indexMap = new int[numberOfVertices];
        Arrays.fill(indexMap, -1);

        for (Vertex vertex : adjVertices.keySet()) {
            if (indexMap[vertex.label] == -1) {
                tarjan(vertex, lowLink, indexMap);
            }
        }

        return sccList;

    }

    public void tarjan(Vertex vertex, int[] lowLink, int[] indexMap) {

        lowLink[vertex.label] = index;
        indexMap[vertex.label] = index;
        index++;
        sccStack.push(vertex);

        for (Vertex u : adjVertices.keySet()) {

            if (Objects.equals(u.label, vertex.label)) {

                for (Vertex w : adjVertices.get(u)) {

                    if (indexMap[w.label] == -1) {
                        tarjan(w, lowLink, indexMap);
                        lowLink[vertex.label] = Math.min(lowLink[vertex.label], lowLink[w.label]);
                    } else if (sccStack.contains(w)) {
                        lowLink[vertex.label] = Math.min(lowLink[vertex.label], indexMap[w.label]);
                    }

                }

            }

        }

        if (lowLink[vertex.label] == indexMap[vertex.label]) {

            List<Vertex> scc = new ArrayList<>();
            Vertex poppedVertex;

            do {
                poppedVertex = sccStack.pop();
                scc.add(poppedVertex);
            } while (poppedVertex != vertex);

            sccList.add(scc);

        }
    }

    public List<Edge> findMinimumSpanningTree(int startVertexLabel) {

        if(!isWeighted) {
            throw new RuntimeException("Graph must be weighted for Prim's minimum spanning tree algorithm!");
        }

        List<Edge> mst = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> weights.get(e.getSourceVertex()).get(e.getDestinationVertex())));

        Vertex startVertex = new Vertex(startVertexLabel); // Choose the start vertex
        visited.add(startVertex);


        for (Vertex u : adjVertices.keySet()) {

            if (Objects.equals(u.label, startVertex.label)) {

                for (Vertex w : adjVertices.get(u)) {
                    pq.add(new Edge(u, w));
                }

            }

        }

        while (!pq.isEmpty()) {

            Edge minEdge = pq.poll();
            Vertex destVertex = minEdge.destinationVertex;

            if (!visited.contains(destVertex)) {

                mst.add(minEdge);
                visited.add(destVertex);

                for (Vertex u : adjVertices.keySet()) {

                    if (Objects.equals(u.label, destVertex.label)) {

                        for (Vertex w : adjVertices.get(u)) {

                            if (!visited.contains(w)) {
                                pq.add(new Edge(u, w));
                            }

                        }

                    }

                }

            }

        }

        return mst;
    }

    public void pbfsOneDimensional(int numberOfVertices) {

        int numProcessors = 4; // Number of processors
        int[] processorQueue = new int[numProcessors];
        int[] nextProcessorQueue = new int[numProcessors];

        int[] level = new int[numberOfVertices];
        Arrays.fill(level, -1);
        level[0] = 0; // Start vertex at level 0

        int processorId = 0;
        processorQueue[processorId] = 0; // Start vertex in the first processor's queue

        while (processorQueue[processorId] != -1) {
            int currentVertex = processorQueue[processorId];

                for (Vertex u : adjVertices.keySet()) {

                    if (Objects.equals(u.label, currentVertex)) {

                        for (Vertex w : adjVertices.get(u)) {

                            int neighborId = w.label;

                            if (level[neighborId] == -1) {
                                level[neighborId] = level[currentVertex] + 1;
                                int nextProcessorId = neighborId % numProcessors;
                                nextProcessorQueue[nextProcessorId] = neighborId;

                                // Simulate message passing by updating the next processor's queue
                                if (nextProcessorId != processorId) {
                                    nextProcessorQueue[nextProcessorId] = neighborId;
                                }

                            }

                        }

                    }

                }


            processorQueue[processorId] = -1; // Mark the current processor's queue as empty

            // Simulate message passing by updating the current processor's queue
            if (processorId != nextProcessorQueue[processorId] % numProcessors) {
                processorQueue[processorId] = nextProcessorQueue[processorId];
            }

            // Switch to the next processor
            processorId = (processorId + 1) % numProcessors;

        }

        System.out.println("Vertex Levels:");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.println("Vertex " + i + ": Level " + level[i]);
        }

    }


    public void pdfsOneDimensional(int numberOfVertices) {

        int numProcessors = 4; // Number of processors
        int[] processorStack = new int[numProcessors];
        int[] nextProcessorStack = new int[numProcessors];

        boolean[] visited = new boolean[numberOfVertices];
        int[] level = new int[numberOfVertices];

        Arrays.fill(visited, false);
        Arrays.fill(level, -1);

        int startVertex = 0; // Start vertex for traversal

        int processorId = 0;
        processorStack[processorId] = startVertex; // Start vertex in the first processor's stack
        level[startVertex] = 0; // Set the level of the start vertex

        while (processorStack[processorId] != -1) {

            int currentVertex = processorStack[processorId];

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;

                for (Vertex u : adjVertices.keySet()) {

                    if (Objects.equals(u.label, currentVertex)) {

                        for (Vertex w : adjVertices.get(u)) {

                            int neighborId = w.label;

                            if (!visited[neighborId]) {
                                level[neighborId] = level[currentVertex] + 1;
                                int nextProcessorId = neighborId % numProcessors;
                                nextProcessorStack[nextProcessorId] = neighborId;

                                // Simulate message passing by updating the next processor's stack
                                if (nextProcessorId != processorId) {
                                    nextProcessorStack[nextProcessorId] = neighborId;
                                }
                            }

                        }

                    }

                }

            }

            processorStack[processorId] = -1; // Mark the current processor's stack as empty

            // Simulate message passing by updating the current processor's stack
            if (processorId != nextProcessorStack[processorId] % numProcessors) {
                processorStack[processorId] = nextProcessorStack[processorId];
            }

            // Switch to the next processor
            processorId = (processorId + 1) % numProcessors;
        }

        System.out.println("Vertex Levels:");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.println("Vertex " + i + ": Level " + level[i]);
        }

    }

}