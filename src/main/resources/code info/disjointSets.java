//The disjoint-set data structure, also known as the union-find data structure, is commonly used to track a partition of a set into disjoint subsets. Here's an implementation of the disjoint-set algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the DisjointSet class represents the disjoint-set data structure. The parent array holds the parent/representative of each element, and the rank array is used to optimize the union operation by attaching the shorter tree to the taller tree.
//
//        The find method recursively finds the representative of an element while applying path compression to optimize subsequent find operations.
//
//        The union method performs the union operation by merging the sets containing the given elements. It compares the ranks of the sets and attaches the shorter tree to the taller tree. The rank array is updated accordingly.
//
//        In the main method, a sample set of union-find operations is performed, and the representative of each element is printed.
//
//        Feel free to modify the code to suit your specific use case or graph structure.

import java.util.*;

class DisjointSet {
    private int[] parent;
    private int[] rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
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

public class DisjointSetAlgorithm {
    public static void main(String[] args) {
        int numNodes = 8;
        DisjointSet disjointSet = new DisjointSet(numNodes);

        // Perform union-find operations
        disjointSet.union(0, 1);
        disjointSet.union(2, 3);
        disjointSet.union(4, 5);
        disjointSet.union(6, 7);
        disjointSet.union(1, 3);
        disjointSet.union(5, 7);

        // Find the representative of each element
        System.out.println("Representatives:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Node " + i + ": " + disjointSet.find(i));
        }
    }
}
