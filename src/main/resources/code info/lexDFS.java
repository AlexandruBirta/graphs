//In this implementation, the Node class represents a node in the graph, and each node maintains a list of its neighboring nodes.
//
//        The lexDFS function performs the Lexicographic Depth-First Search algorithm. It starts from a given start node and explores the graph in a depth-first manner, maintaining a lexicographic ordering of the nodes. The algorithm uses a set to keep track of visited nodes and a list (ordering) to store the lexicographic ordering of the nodes. The lexDFSHelper function is a recursive helper function that performs the actual depth-first traversal.
//
//        The buildGraph function is responsible for constructing the graph specific to your problem domain. Nodes are created, and their neighbors are defined by adding them to the neighbors list.
//
//        You can modify the buildGraph function to create and connect the nodes based on your specific graph structure.
//
//        When you run the LexDFS algorithm with this graph and choose a start node, it will output the lexicographic depth-first ordering of the nodes.
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

public class LexDFS {

    public static void main(String[] args) {
        // Build your graph
        List<Node> graph = buildGraph();

        // Perform LexDFS starting from a specific node
        Node startNode = graph.get(0); // Choose the start node
        List<Node> ordering = lexDFS(graph, startNode);

        System.out.println("Lexicographic DFS Ordering:");
        for (Node node : ordering) {
            System.out.println(node.id);
        }
    }

    public static List<Node> lexDFS(List<Node> graph, Node startNode) {
        List<Node> ordering = new ArrayList<>();
        Set<Node> visited = new HashSet<>();

        lexDFSHelper(startNode, visited, ordering);

        return ordering;
    }

    public static void lexDFSHelper(Node node, Set<Node> visited, List<Node> ordering) {
        visited.add(node);
        ordering.add(node);

        List<Node> neighbors = node.neighbors;
        Collections.sort(neighbors, (n1, n2) -> Integer.compare(n1.id, n2.id));

        for (Node neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                lexDFSHelper(neighbor, visited, ordering);
            }
        }
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
