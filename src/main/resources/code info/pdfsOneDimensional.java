//The PDFS algorithm with 1-dimensional partitioning starts from the start node (node 0) and traverses the graph in a depth-first manner.
//        It assigns levels to each node based on their distance from the start node. The algorithm uses multiple processors, and message passing between processors is simulated using auxiliary vectors.

import java.util.*;

class Node {
    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class PDFS1DPartitioning {
    public static void main(String[] args) {
        List<Node> graph = buildGraph();

        pdfsOneDimensional(graph);
    }

    private static void pdfsOneDimensional(List<Node> graph) {
        int numProcessors = 4; // Number of processors
        int[] processorStack = new int[numProcessors];
        int[] nextProcessorStack = new int[numProcessors];

        int numNodes = graph.size();
        boolean[] visited = new boolean[numNodes];
        int[] level = new int[numNodes];

        Arrays.fill(visited, false);
        Arrays.fill(level, -1);

        int startNode = 0; // Start node for traversal

        int processorId = 0;
        processorStack[processorId] = startNode; // Start node in the first processor's stack
        level[startNode] = 0; // Set the level of the start node

        while (processorStack[processorId] != -1) {
            int currentNode = processorStack[processorId];

            if (!visited[currentNode]) {
                visited[currentNode] = true;

                for (Node neighbor : graph.get(currentNode).neighbors) {
                    int neighborId = neighbor.id;

                    if (!visited[neighborId]) {
                        level[neighborId] = level[currentNode] + 1;
                        int nextProcessorId = neighborId % numProcessors;
                        nextProcessorStack[nextProcessorId] = neighborId;

                        // Simulate message passing by updating the next processor's stack
                        if (nextProcessorId != processorId) {
                            nextProcessorStack[nextProcessorId] = neighborId;
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

        System.out.println("Node Levels:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": Level " + level[i]);
        }
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
        node0.neighbors.add(node2);
        node1.neighbors.add(node0);
        node1.neighbors.add(node3);
        node2.neighbors.add(node0);
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
