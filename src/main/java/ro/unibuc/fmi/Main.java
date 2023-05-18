package ro.unibuc.fmi;


import ro.unibuc.fmi.graph.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Main {

    public static void main(String[] args) {


        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\TAP\\graphs\\src\\main\\resources\\input.txt"))) {

            Graph graph = new Graph();
            graph.setWeighted(true);
            graph.setDirected(false);

            String[] graphProperties = reader.readLine().trim().split("\\s+");

            int numberOfVertices = Integer.parseInt(graphProperties[0]);
            int numberOfEdges = Integer.parseInt(String.valueOf(graphProperties[1]));

            for (int i = 0; i < numberOfVertices; i++) {
                graph.addVertex(i);
            }

            for (int i = 0; i < numberOfEdges; i++) {

                String[] edge = reader.readLine().trim().split("\\s+");

                if (graph.isWeighted()) {
                    graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Integer.parseInt(String.valueOf(edge[2])));
                } else {
                    graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                }

            }

            System.out.println(graph.printGraph());

            // Specify the start and goal vertices
            int startVertex = 0;
            int goalVertex = 4;

            // Specify the number of processors
            int numProcessors = 4;

            // Find the shortest path using Hash Distributed A* algorithm
            List<Integer> shortestPath = graph.findShortestPath(startVertex, goalVertex, numProcessors, numberOfVertices);

            // Print the shortest path
            if (!shortestPath.isEmpty()) {
                System.out.println("Shortest Path: " + shortestPath);
            } else {
                System.out.println("No path found.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
