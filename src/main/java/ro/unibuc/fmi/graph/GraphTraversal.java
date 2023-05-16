package ro.unibuc.fmi.graph;


import java.util.*;


public class GraphTraversal {

    public static Set<Integer> depthFirstTraversal(Graph graph, Integer root) {

        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {

            Integer vertex = stack.pop();

            if (!visited.contains(vertex)) {

                visited.add(vertex);

                for (Graph.Vertex v : graph.getAdjVertices(vertex)) {
                    stack.push(v.label);
                }

            }

        }

        return visited;

    }

    public static Set<Integer> breadthFirstTraversal(Graph graph, Integer root) {

        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {

            Integer vertex = queue.poll();

            for (Graph.Vertex v : graph.getAdjVertices(vertex)) {

                if (!visited.contains(v.label)) {
                    visited.add(v.label);
                    queue.add(v.label);
                }

            }

        }

        return visited;

    }

}