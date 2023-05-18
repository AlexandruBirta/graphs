// In this implementation, the B heuristic algorithm starts from both the source node and the target node and explores their respective neighbors simultaneously. The algorithm stops when the exploration from both sides meets at an intersection point, indicating the presence of a path.

// The bHeuristicSearch function takes the graph, the source node, and the target node as input and performs the B heuristic search. It maintains separate visited sets, queues, and parent maps for the source and target sides. The algorithm iterates until either of the queues becomes empty or an intersection point is found. It explores the neighbors of the current nodes on both sides, enqueues them if they haven't been visited, and updates the parent maps accordingly. If an intersection is found, the path is reconstructed using the intersection node and the parent maps.

// The reconstructPath function is used to reconstruct the path from the source node to the target node using the intersection node and the parent maps. It traces back the path from the intersection node to the source node and from the intersection node to the target node, combining them into a single path.

// The buildGraph function is responsible for constructing the graph specific to your problem domain. Nodes are created, and their neighbors are defined by adding them to the neighbors list.

// You can modify the buildGraph function to create and connect the nodes based on your specific graph structure.

// Please note that this implementation assumes an undirected graph, where each edge is represented as a connection between two nodes in both directions. Modify the code accordingly if you are working with a directed graph or if your graph structure differs.

import java.util.*;


class Node {

    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

}

public class BHeuristicSearch {

    public static void main(String[] args) {
        // Build your graph
        List<Node> graph = buildGraph();

        // Perform B heuristic search starting from a specific source and target node
        Node sourceNode = graph.get(0); // Choose the source node
        Node targetNode = graph.get(4); // Choose the target node
        List<Node> path = bHeuristicSearch(graph, sourceNode, targetNode);

        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path found!");
            for (Node node : path) {
                System.out.println("Visited Node: " + node.id);
            }
        }
    }

    public static List<Node> bHeuristicSearch(List<Node> graph, Node sourceNode, Node targetNode) {
        Set<Node> visitedSource = new HashSet<>();
        Set<Node> visitedTarget = new HashSet<>();
        Queue<Node> queueSource = new LinkedList<>();
        Queue<Node> queueTarget = new LinkedList<>();
        Map<Node, Node> parentsSource = new HashMap<>();
        Map<Node, Node> parentsTarget = new HashMap<>();

        // Enqueue the source and target nodes and mark them as visited
        queueSource.offer(sourceNode);
        visitedSource.add(sourceNode);

        queueTarget.offer(targetNode);
        visitedTarget.add(targetNode);

        while (!queueSource.isEmpty() && !queueTarget.isEmpty()) {
            // Explore nodes from the source side
            int sourceSize = queueSource.size();
            for (int i = 0; i < sourceSize; i++) {
                Node currentSource = queueSource.poll();
                for (Node neighbor : currentSource.neighbors) {
                    if (!visitedSource.contains(neighbor)) {
                        queueSource.offer(neighbor);
                        visitedSource.add(neighbor);
                        parentsSource.put(neighbor, currentSource);

                        if (visitedTarget.contains(neighbor)) {
                            return reconstructPath(neighbor, parentsSource, parentsTarget);
                        }
                    }
                }
            }

            // Explore nodes from the target side
            int targetSize = queueTarget.size();
            for (int i = 0; i < targetSize; i++) {
                Node currentTarget = queueTarget.poll();
                for (Node neighbor : currentTarget.neighbors) {
                    if (!visitedTarget.contains(neighbor)) {
                        queueTarget.offer(neighbor);
                        visitedTarget.add(neighbor);
                        parentsTarget.put(neighbor, currentTarget);

                        if (visitedSource.contains(neighbor)) {
                            return reconstructPath(neighbor, parentsSource, parentsTarget);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    public static List<Node> reconstructPath(Node intersectionNode, Map<Node, Node> parentsSource, Map<Node, Node> parentsTarget) {
        List<Node> path = new ArrayList<>();
        Node currentNode = intersectionNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = parentsSource.get(currentNode);
        }

        currentNode = parentsTarget.get(intersectionNode);
        while (currentNode != null) {
            path.add(0, currentNode);
            currentNode = parentsTarget.get(currentNode);
        }

        return path;
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
