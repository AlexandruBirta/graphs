//Certainly! Here's an implementation of the Union-by-Size algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The find method performs the find operation by iteratively traversing the parent array until the root of the set is found.
//
//        The union method performs the union operation by merging the sets containing the given nodes. It compares the sizes of the sets and attaches the smaller set to the larger set. The size array is updated accordingly.
//
//        In the main method, a sample graph is created with eight nodes and several edges. Union-find operations are performed using the union method with the parent and size arrays. The resulting parent array, which represents the sets after union by size, is printed.
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

    public int find(int[] parent, int node) {
        while (parent[node] != node) {
            node = parent[node];
        }
        return node;
    }

    public void union(int[] parent, int[] size, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX != rootY) {
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }
}

public class UnionBySizeAlgorithm {
    public static void main(String[] args) {
        int numNodes = 8;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);

        int[] parent = new int[numNodes];
        int[] size = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Perform union-find operations
        graph.union(parent, size, 0, 2);
        graph.union(parent, size, 4, 6);
        graph.union(parent, size, 1, 3);
        graph.union(parent, size, 5, 7);

        // Find roots after union by size
        for (int i = 0; i < numNodes; i++) {
            parent[i] = graph.find(parent, i);
        }

        // Print parent array after union by size
        System.out.println("Parent Array:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + parent[i]);
        }
    }
}
