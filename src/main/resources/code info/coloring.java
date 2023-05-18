//The Coloring algorithm assigns colors to each node in such a way that no two adjacent nodes have the same color. The algorithm iterates through each node and checks the colors of its neighbors. It then assigns the first available color that is not used by any of its neighbors.

import java.util.*;

class Node {
    int id;
    List<Node> neighbors;
    int color;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
        this.color = -1; // Unassigned color
    }
}

public class ColoringAlgorithm {
    public static void main(String[] args) {
        List<Node> graph = buildGraph();

        int numColors = 3; // Number of colors available

        boolean isColoringPossible = colorGraph(graph, numColors);

        if (isColoringPossible) {
            System.out.println("Graph can be colored with " + numColors + " colors.");
            System.out.println("Coloring result:");
            for (Node node : graph) {
                System.out.println("Node " + node.id + ": Color " + node.color);
            }
        } else {
            System.out.println("Graph cannot be colored with " + numColors + " colors.");
        }
    }

    public static boolean colorGraph(List<Node> graph, int numColors) {
        for (Node node : graph) {
            Set<Integer> usedColors = new HashSet<>();
            for (Node neighbor : node.neighbors) {
                if (neighbor.color != -1) {
                    usedColors.add(neighbor.color);
                }
            }

            for (int color = 0; color < numColors; color++) {
                if (!usedColors.contains(color)) {
                    node.color = color;
                    break;
                }
            }

            if (node.color == -1) {
                return false; // Unable to color the graph with the given number of colors
            }
        }

        return true;
    }

    public static List<Node> buildGraph() {
        // Build your graph here
        List<Node> graph = new ArrayList<>();

        // Add nodes and their neighbors (connections)
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node0.neighbors.add(node1);
        node1.neighbors.add(node0);
        node1.neighbors.add(node2);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node3);

        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);

        return graph;
    }
}
