//In this implementation, the Node class represents a node in the graph, and each node maintains a list of its neighboring nodes.
//
//        The TarjansAlgorithm class contains the main logic for Tarjan's algorithm. The findSCCs method initiates the algorithm and returns a list of strongly connected components (SCCs) found in the graph. The algorithm uses an index counter, index, to track the order of node discovery, a list of SCCs, sccList, to store the found components, and a stack, stack, to keep track of visited nodes during the depth-first search.
//
//        The tarjan method is a recursive helper method that performs the actual depth-first search and assigns low-link values to each node. It identifies SCCs by checking if a node's low-link value matches its index value. If a SCC is found, it is added to the sccList.
//
//        The buildGraph method is responsible for constructing the graph specific to your problem domain. Nodes are created, and their neighbors are defined by adding them to the neighbors list.
//
//        You can modify the buildGraph method to create and connect the nodes based on your specific graph structure.
//
//        When you run Tarjan's algorithm with this graph, it will output the strongly connected components in the graph. Each line represents a separate SCC.
//
//        You can try different graph structures to verify the correctness of the algorithm.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


class Node {

    int id;
    List<Node> neighbors;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

}

public class TarjansAlgorithm {

    private int index;
    private List<List<Node>> sccList;
    private Stack<Node> stack;

    public static void main(String[] args) {
        // Build your graph
        List<Node> graph = buildGraph();

        TarjansAlgorithm tarjansAlgorithm = new TarjansAlgorithm();
        List<List<Node>> sccList = tarjansAlgorithm.findSCCs(graph);

        System.out.println("Strongly Connected Components:");
        for (List<Node> scc : sccList) {
            for (Node node : scc) {
                System.out.print(node.id + " ");
            }
            System.out.println();
        }
    }

    public List<List<Node>> findSCCs(List<Node> graph) {
        index = 0;
        sccList = new ArrayList<>();
        stack = new Stack<>();

        int[] lowLink = new int[graph.size()];
        int[] indexMap = new int[graph.size()];
        Arrays.fill(indexMap, -1);

        for (Node node : graph) {
            if (indexMap[node.id] == -1) {
                tarjan(node, lowLink, indexMap);
            }
        }

        return sccList;
    }

    private void tarjan(Node node, int[] lowLink, int[] indexMap) {
        lowLink[node.id] = index;
        indexMap[node.id] = index;
        index++;
        stack.push(node);

        for (Node neighbor : node.neighbors) {
            if (indexMap[neighbor.id] == -1) {
                tarjan(neighbor, lowLink, indexMap);
                lowLink[node.id] = Math.min(lowLink[node.id], lowLink[neighbor.id]);
            } else if (stack.contains(neighbor)) {
                lowLink[node.id] = Math.min(lowLink[node.id], indexMap[neighbor.id]);
            }
        }

        if (lowLink[node.id] == indexMap[node.id]) {
            List<Node> scc = new ArrayList<>();
            Node poppedNode;
            do {
                poppedNode = stack.pop();
                scc.add(poppedNode);
            } while (poppedNode != node);
            sccList.add(scc);
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
