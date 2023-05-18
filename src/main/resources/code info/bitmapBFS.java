//The Bitmap BFS algorithm starts from the start node (node 0) and traverses the graph in a breadth-first manner. It assigns levels to each node based on their distance from the start node. The algorithm uses a bitmap to keep track of visited nodes and a queue to process nodes in a FIFO manner.
//
//        You can modify the buildGraph method to create and connect the nodes based on your specific graph structure. Additionally, you can experiment with different graph structures and observe the resulting node levels to test the correctness and performance of the algorithm.


import java.util.*;

class Node {
    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class BitmapBFS {
    public static void main(String[] args) {
        List<Node> graph = buildGraph();

        bitmapBFS(graph);
    }

    private static void bitmapBFS(List<Node> graph) {
        int numNodes = graph.size();
        int[] level = new int[numNodes];
        boolean[] visited = new boolean[numNodes];

        int startNode = 0; // Start node for traversal

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            for (Node neighbor : graph.get(currentNode).neighbors) {
                int neighborId = neighbor.id;
                if (!visited[neighborId]) {
                    level[neighborId] = level[currentNode] + 1;
                    visited[neighborId] = true;
                    queue.offer(neighborId);
                }
            }
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
