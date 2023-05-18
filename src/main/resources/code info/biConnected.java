//In this implementation, the Graph class represents the graph using an adjacency list. The addEdge method adds an undirected edge between two nodes in the graph.
//
//        The calculateBiConnectedComponents method calculates the bi-connected components of the graph using depth-first search (DFS) with Tarjan's algorithm. It maintains arrays disc and low to store the discovery time and low time of each node. It also keeps track of the bi-connected components in the biConnectedComponents list. The algorithm starts a DFS from each unvisited node and applies Tarjan's algorithm to identify the bi-connected components.
//
//        The dfs method performs the depth-first search starting from a given node. It marks the node as visited and assigns a discovery time and low time. It explores the neighbors of the node, maintaining the low time values and identifying the bi-connected components. It uses a stack to keep track of the nodes visited in the current DFS path.
//
//        In the main method, a sample graph is created with nine nodes and several edges. The calculateBiConnectedComponents method is called, and the resulting bi-connected components are printed.
//
//        You can modify the main method to create and connect nodes based on your specific graph structure.

import java.util.*;

class Graph {
    private int numNodes;
    private List<List<Integer>> adjList;
    private int[] disc;
    private int[] low;
    private int time;
    private List<List<Integer>> biConnectedComponents;

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

    public List<List<Integer>> calculateBiConnectedComponents() {
        disc = new int[numNodes];
        low = new int[numNodes];
        time = 0;
        biConnectedComponents = new ArrayList<>();

        boolean[] visited = new boolean[numNodes];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                dfs(i, -1, visited, stack);
            }
        }

        return biConnectedComponents;
    }

    private void dfs(int node, int parent, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        disc[node] = low[node] = ++time;
        stack.push(node);

        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, node, visited, stack);
                low[node] = Math.min(low[node], low[neighbor]);

                if (low[neighbor] >= disc[node]) {
                    List<Integer> component = new ArrayList<>();
                    while (!stack.isEmpty()) {
                        int popped = stack.pop();
                        component.add(popped);
                        if (popped == neighbor) {
                            break;
                        }
                    }
                    biConnectedComponents.add(component);
                }
            } else if (neighbor != parent) {
                low[node] = Math.min(low[node], disc[neighbor]);
            }
        }
    }
}

public class BiConnectedComponentsAlgorithm {
    public static void main(String[] args) {
        int numNodes = 9;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 6);

        List<List<Integer>> biConnectedComponents = graph.calculateBiConnectedComponents();

        System.out.println("Bi-Connected Components:");
        for (int i = 0; i < biConnectedComponents.size(); i++) {
            System.out.println("Component " + i + ": " + biConnectedComponents.get(i));
        }
    }
}
