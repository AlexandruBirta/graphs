//In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an edge between two nodes in the graph.
//
//        The calculateWeakComponents method calculates the weak components of the graph using depth-first search (DFS). It maintains a boolean array visited to keep track of visited nodes and a list components to store the weak components. It starts a DFS from each unvisited node, marking all reachable nodes as visited and adding them to the current component. When the DFS finishes, it adds the component to the components list.
//
//        The dfs method performs the depth-first search starting from a given node. It marks the node as visited, adds it to the current component, and recursively visits its neighbors that have not been visited yet.
//
//        In the main method, a sample graph is created with nine nodes and several edges. The calculateWeakComponents method is called, and the resulting weak components are printed.
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
    }

    public List<Set<Integer>> calculateWeakComponents() {
        List<Set<Integer>> components = new ArrayList<>();
        boolean[] visited = new boolean[numNodes];

        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                dfs(i, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private void dfs(int node, boolean[] visited, Set<Integer> component) {
        visited[node] = true;
        component.add(node);

        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited, component);
            }
        }
    }
}

public class WeakComponentsAlgorithm {
    public static void main(String[] args) {
        int numNodes = 9;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 6);

        List<Set<Integer>> components = graph.calculateWeakComponents();

        System.out.println("Weak Components:");
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + i + ": " + components.get(i));
        }
    }
}
