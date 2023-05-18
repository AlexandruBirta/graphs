//The path compression algorithm is typically used in union-find data structures to optimize the "find" operation by compressing the paths between nodes. Here's an implementation of the path compression algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The find method performs the find operation by recursively traversing the parent array until it reaches the root of the set.
//
//        The union method performs the union operation by merging the sets containing the given nodes. It uses the rank array to optimize the union operation.
//
//        The compressPaths method applies path compression by recursively compressing the paths from the given node to its root.
//
//        In the main method, a sample graph is created with eight nodes and several edges. Union-find operations are performed, and then path compression is applied to compress the paths in the graph. The resulting parent array is printed to verify the path compression.
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

    public void compressPaths(int[] parent, int node) {
        int root = find(parent, node);
        for (int neighbor : adjList.get(node)) {
            int neighborRoot = find(parent, neighbor);
            if (neighborRoot != root) {
                parent[neighborRoot] = root;
                compressPaths(parent, neighborRoot);
            }
        }
    }
}

public class PathCompressionAlgorithm {
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
        }

        // Perform union-find operations
        graph.union(parent, rank, 0, 2);
        graph.union(parent, rank, 4, 6);
        graph.union(parent, rank, 1, 3);
        graph.union(parent, rank, 5, 7);

        // Compress paths
        for (int i = 0; i < numNodes; i++) {
            graph.compressPaths(parent, i);
        }

        // Print parent array after path compression
        System.out.println("Parent Array:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + parent[i]);
        }
    }
}
