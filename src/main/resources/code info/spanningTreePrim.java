//In this implementation, the Node class represents a node in the graph, and each node maintains a list of its adjacent edges. The Edge class represents an edge between two nodes, including the source node, destination node, and weight.
//
//        The MinimumSpanningTree class contains the main logic for finding the minimum spanning tree using Prim's algorithm. The findMinimumSpanningTree method takes a graph as input and returns a list of edges representing the minimum spanning tree. It uses a priority queue to select the minimum-weight edge at each step and maintains a set of visited nodes to track the progress.
//
//        The buildGraph method is responsible for constructing the graph specific to your problem domain. Nodes are created, and their edges (connections) are defined by adding them to the edges list.
//
//        When you run the code with this graph, it will find the minimum spanning tree and output the edges included in the tree.
//
//        You can modify the buildGraph method to create and connect the nodes based on your specific graph structure, including the weights of the edges.
//
//        Feel free to experiment with different graph structures and weights to test the algorithm and verify its correctness.

import java.util.*;

class Edge {
    int src;
    int dest;
    int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Node {
    int id;
    List<Edge> edges;

    public Node(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
    }
}

public class MinimumSpanningTree {
    public static void main(String[] args) {
        // Build your graph
        List<Node> graph = buildGraph();

        // Find the minimum spanning tree
        List<Edge> mst = findMinimumSpanningTree(graph);

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " -- " + edge.dest + " : " + edge.weight);
        }
    }

    public static List<Edge> findMinimumSpanningTree(List<Node> graph) {
        List<Edge> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        Node startNode = graph.get(0); // Choose the start node
        visited.add(startNode);

        for (Edge edge : startNode.edges) {
            pq.add(edge);
        }

        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();
            Node destNode = graph.get(minEdge.dest);

            if (!visited.contains(destNode)) {
                mst.add(minEdge);
                visited.add(destNode);

                for (Edge edge : destNode.edges) {
                    if (!visited.contains(graph.get(edge.dest))) {
                        pq.add(edge);
                    }
                }
            }
        }

        return mst;
    }

    public static List<Node> buildGraph() {
        // Build your graph here
        List<Node> graph = new ArrayList<>();

        // Add nodes and their edges (connections)
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node0.edges.add(new Edge(0, 1, 2));
        node0.edges.add(new Edge(0, 2, 3));
        node0.edges.add(new Edge(0, 3, 4));

        node1.edges.add(new Edge(1, 0, 2));
        node1.edges.add(new Edge(1, 2, 1));

        node2.edges.add(new Edge(2, 0, 3));
        node2.edges.add(new Edge(2, 1, 1));
        node2.edges.add(new Edge(2, 3, 5));
        node2.edges.add(new Edge(2, 4, 6));

        node3.edges.add(new Edge(3, 0, 4));
        node3.edges.add(new Edge(3, 2, 5));
        node3.edges.add(new Edge(3, 4, 7));

        node4.edges.add(new Edge(4, 2, 6));
        node4.edges.add(new Edge(4, 3, 7));

        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);

        return graph;
    }
}
