//The Euler Tour algorithm finds a tour that visits every edge exactly once, returning to the starting node. It uses a stack to keep track of the current path. Starting from an arbitrary node, it visits each neighbor and removes the edge between the current node and the next node. If a node has no remaining neighbors, it adds it to the Euler Tour. The algorithm continues until the stack is empty.

import java.util.*;

class Node {
    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class EulerTourAlgorithm {
    public static void main(String[] args) {
        List<Node> graph = buildGraph();

        List<Node> eulerTour = findEulerTour(graph);

        System.out.println("Euler Tour:");
        for (Node node : eulerTour) {
            System.out.print(node.id + " ");
        }
    }

    public static List<Node> findEulerTour(List<Node> graph) {
        List<Node> eulerTour = new ArrayList<>();

        Stack<Node> stack = new Stack<>();
        Node startNode = graph.get(0); // Start from any node

        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node current = stack.peek();

            if (current.neighbors.size() > 0) {
                Node next = current.neighbors.remove(0);
                next.neighbors.remove(current);
                stack.push(next);
            } else {
                eulerTour.add(stack.pop());
            }
        }

        return eulerTour;
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
        Node node5 = new Node(5);

        node0.neighbors.add(node1);
        node0.neighbors.add(node3);
        node1.neighbors.add(node0);
        node1.neighbors.add(node2);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node2.neighbors.add(node4);
        node3.neighbors.add(node0);
        node3.neighbors.add(node2);
        node3.neighbors.add(node5);
        node4.neighbors.add(node2);
        node4.neighbors.add(node5);
        node5.neighbors.add(node3);
        node5.neighbors.add(node4);

        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);

        return graph;
    }
}
