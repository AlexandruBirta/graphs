//The path splitting algorithm is used to split a path between two nodes in a graph into multiple smaller segments. Here's an implementation of the path splitting algorithm using adjacency list-based graphs in Java:
//
//        In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The splitPath method takes the starting and ending nodes of the path as inputs and returns a list of segments that make up the path. It uses depth-first search (DFS) to find all possible paths between the two nodes. Each segment of the path is stored as a separate list in the segments list.
//
//        The dfs method performs the depth-first search starting from a given node. It marks the node as visited, adds it to the current path, and recursively explores its neighbors. When the ending node is reached, the current path is added to the segments list. After exploring all neighbors, the current node is removed from the path, and its visited status is reset.
//
//        In the main method, a sample graph is created with seven nodes and several edges. The splitPath method is called with the starting and ending nodes, and the resulting segments of the path are printed.
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

    public List<List<Integer>> splitPath(int start, int end) {
        List<List<Integer>> segments = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[numNodes];

        dfs(start, end, visited, path, segments);

        return segments;
    }

    private void dfs(int node, int end, boolean[] visited, List<Integer> path, List<List<Integer>> segments) {
        visited[node] = true;
        path.add(node);

        if (node == end) {
            segments.add(new ArrayList<>(path));
        } else {
            for (int neighbor : adjList.get(node)) {
                if (!visited[neighbor]) {
                    dfs(neighbor, end, visited, path, segments);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[node] = false;
    }
}

public class PathSplittingAlgorithm {
    public static void main(String[] args) {
        int numNodes = 7;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 6);

        int startNode = 0;
        int endNode = 6;

        List<List<Integer>> segments = graph.splitPath(startNode, endNode);

        System.out.println("Segments of the path from " + startNode + " to " + endNode + ":");
        for (List<Integer> segment : segments) {
            System.out.println(segment);
        }
    }
}
