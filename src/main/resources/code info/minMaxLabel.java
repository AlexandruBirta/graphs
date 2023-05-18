//
//The minimum/maximum labeling algorithm, also known as the maximum adjacency labeling algorithm, is used to assign labels to the nodes of a graph such that the labels are unique and maximized or minimized based on specific criteria. Here's an implementation of the minimum/maximum labeling algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The maximumLabeling method assigns labels to each node such that the labels are unique and maximized. It iterates over each node, considers its neighbors, and assigns the maximum label among the visited neighbors plus one as the label for the current node.
//
//        The minimumLabeling method assigns labels to each node such that the labels are unique and minimized. It iterates over each node, considers its neighbors, and checks which labels have been used by the visited neighbors. It then assigns the smallest available label as the label for the current node.
//
//        In the main method, a sample graph is created with six nodes and several edges. The maximumLabeling and minimumLabeling methods are called, and the resulting labels are printed.
//
//        You can modify the main method to create and connect nodes based on your specific graph structure.

import java.util.*;

class Graph {
    private int numNodes;
    private List<List<Integer>> adjList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.adjList = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    public int[] maximumLabeling() {
        int[] labels = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        labels[0] = 0; // Start with node 0 as the first label
        visited[0] = true;

        for (int node = 1; node < numNodes; node++) {
            int maxLabel = 0;
            for (int neighbor : adjList.get(node)) {
                if (visited[neighbor]) {
                    maxLabel = Math.max(maxLabel, labels[neighbor]);
                }
            }
            labels[node] = maxLabel + 1;
            visited[node] = true;
        }

        return labels;
    }

    public int[] minimumLabeling() {
        int[] labels = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        labels[0] = 0; // Start with node 0 as the first label
        visited[0] = true;

        for (int node = 1; node < numNodes; node++) {
            Set<Integer> usedLabels = new HashSet<>();
            for (int neighbor : adjList.get(node)) {
                if (visited[neighbor]) {
                    usedLabels.add(labels[neighbor]);
                }
            }

            int minLabel = 0;
            while (usedLabels.contains(minLabel)) {
                minLabel++;
            }
            labels[node] = minLabel;
            visited[node] = true;
        }

        return labels;
    }
}

public class MinimumMaximumLabelingAlgorithm {
    public static void main(String[] args) {
        int numNodes = 6;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);

        int[] maxLabels = graph.maximumLabeling();
        int[] minLabels = graph.minimumLabeling();

        System.out.println("Maximum Labeling:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + maxLabels[i]);
        }

        System.out.println("Minimum Labeling:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + minLabels[i]);
        }
    }
}
