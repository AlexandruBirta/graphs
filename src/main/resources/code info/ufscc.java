//The UFSCC algorithm finds the strongly connected components in the graph. A strongly connected component is a subgraph in which every pair of nodes is reachable from each other. The algorithm performs a depth-first search (DFS) and assigns unique ids and low-link values to each node. It uses a stack to keep track of the visited nodes and identifies strongly connected components when a node's low-link value matches its id.

import java.util.*;

class Node {
    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }
}

public class UFSCCAlgorithm {
    private static int idCounter;
    private static int[] ids;
    private static int[] lowLinkValues;
    private static boolean[] onStack;
    private static Stack<Integer> stack;
    private static List<List<Integer>> strongComponents;

    public static void main(String[] args) {
        List<Node> graph = buildGraph();

        List<List<Integer>> scc = findStronglyConnectedComponents(graph);

        System.out.println("Strongly Connected Components:");
        for (List<Integer> component : scc) {
            System.out.println(component);
        }
    }

    public static List<List<Integer>> findStronglyConnectedComponents(List<Node> graph) {
        int numNodes = graph.size();
        idCounter = 0;
        ids = new int[numNodes];
        lowLinkValues = new int[numNodes];
        onStack = new boolean[numNodes];
        stack = new Stack<>();
        strongComponents = new ArrayList<>();

        Arrays.fill(ids, -1);

        for (int i = 0; i < numNodes; i++) {
            if (ids[i] == -1) {
                dfs(graph, i);
            }
        }

        return strongComponents;
    }

    public static void dfs(List<Node> graph, int nodeIndex) {
        ids[nodeIndex] = idCounter;
        lowLinkValues[nodeIndex] = idCounter;
        idCounter++;
        stack.push(nodeIndex);
        onStack[nodeIndex] = true;

        for (Node neighbor : graph.get(nodeIndex).neighbors) {
            int neighborIndex = neighbor.id;

            if (ids[neighborIndex] == -1) {
                dfs(graph, neighborIndex);
                lowLinkValues[nodeIndex] = Math.min(lowLinkValues[nodeIndex], lowLinkValues[neighborIndex]);
            } else if (onStack[neighborIndex]) {
                lowLinkValues[nodeIndex] = Math.min(lowLinkValues[nodeIndex], ids[neighborIndex]);
            }
        }

        if (lowLinkValues[nodeIndex] == ids[nodeIndex]) {
            List<Integer> component = new ArrayList<>();
            int index;

            do {
                index = stack.pop();
                onStack[index] = false;
                component.add(index);
            } while (index != nodeIndex);

            strongComponents.add(component);
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
        node1.neighbors.add(node2);
        node2.neighbors.add(node0);
        node2.neighbors.add(node3);
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
