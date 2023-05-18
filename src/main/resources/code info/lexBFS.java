// In this implementation, the Node class represents a node in the graph, and each node maintains a list of its neighboring nodes.

// The lexBFS function performs the Lexicographic Breadth-First Search algorithm. It starts from a given start node and explores the graph in a breadth-first manner, maintaining a lexicographic ordering of the nodes. The algorithm uses a queue to store the nodes to be processed and a set to keep track of visited nodes. At each step, the algorithm dequeues a node, adds it to the ordering list, sorts its neighbors lexicographically, and enqueues the unvisited neighbors.

// The buildGraph function is responsible for constructing the graph specific to your problem domain. Nodes are created, and their neighbors are defined by adding them to the neighbors list.

// You can modify the buildGraph function to create and connect the nodes based on your specific graph structure.

//When you run the LexBFS algorithm with this graph and choose a start node, it will output the lexicographic breadth-first ordering of the nodes.
//
//        Please note that the above implementation assumes an undirected graph. Modify the code accordingly if you are working with a directed graph or if your graph structure differs.

import java.util.*;


class Node {

    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

}

public class LexBFS {

    public static void main(String[] args) {
        // Build your graph
        List<Node> graph = buildGraph();

        // Perform LexBFS starting from a specific node
        Node startNode = graph.get(0); // Choose the start node
        List<Node> ordering = lexBFS(graph, startNode);

        System.out.println("Lexicographic BFS Ordering:");
        for (Node node : ordering) {
            System.out.println(node.id);
        }
    }

    public static List<Node> lexBFS(List<Node> graph, Node startNode) {
        List<Node> ordering = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            ordering.add(node);

            List<Node> neighbors = node.neighbors;
            Collections.sort(neighbors, (n1, n2) -> Integer.compare(n1.id, n2.id));

            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return ordering;
    }

    public static List<Node> buildGraph() {
        // Build your graph here
        List<Node> graph = new ArrayList<>();

        // Add nodes and their neighbors
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node0.neighbors.add(node1);
        node0.neighbors.add(node2);

        node1.neighbors.add(node0);
        node1.neighbors.add(node2);
        node1.neighbors.add(node3);

        node2.neighbors.add(node0);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node2.neighbors.add(node4);

        node3.neighbors.add(node1);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);

        node4.neighbors.add(node2);
        node4.neighbors.add(node3);

        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);

        return graph;
    }

}