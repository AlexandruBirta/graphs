//Certainly! Here's an implementation of the Union-by-Rank algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The find method performs the find operation by recursively traversing the parent array until the root of the set is found. Path compression is applied to improve the efficiency of subsequent find operations.
//
//        The union method performs the union operation by merging the sets containing the given nodes. It compares the ranks of the sets and attaches the shorter tree to the taller tree. The rank array is updated accordingly.
//
//        In the main method, a sample graph is created with eight nodes and several edges. Union-find operations are performed using the union method with the parent and rank arrays. The resulting parent array, which represents the sets after union by rank, is printed.
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
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }

    public void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

public class UnionByRankAlgorithm {
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
        int[] rank = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // Perform union-find operations
        graph.union(parent, rank, 0, 2);
        graph.union(parent, rank, 4, 6);
        graph.union(parent, rank, 1, 3);
        graph.union(parent, rank, 5, 7);

        // Find roots after union by rank
        for (int i = 0; i < numNodes; i++) {
            parent[i] = graph.find(parent, i);
        }

        // Print parent array after union by rank
        System.out.println("Parent Array:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + parent[i]);
        }
    }
}
