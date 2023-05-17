package ro.unibuc.fmi;


import ro.unibuc.fmi.graph.Graph;
import ro.unibuc.fmi.graph.Vertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;


public class Main {

    public static void main(String[] args) {


        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Facultate\\UNIBUC\\Materii\\Anul II\\Semestrul II\\Tehnici Avansate de Programare\\Examen\\graphs\\src\\main\\resources\\input.txt"))) {

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
                    graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Double.parseDouble(String.valueOf(edge[2])));
                } else {
                    graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                }

            }

            System.out.println(graph.printGraph());
            System.out.println('\n');

            //Dijkstra*********************************************************************

//            graph.dijkstra(numberOfVertices, 0);
//            System.out.println('\n');

            //DFS/BFS*********************************************************************

//            System.out.println("DFS: " + graph.depthFirstTraversal(graph, 1));
//            System.out.println("BFS: " + graph.breadthFirstTraversal(graph, 1));

            //A star*********************************************************************

//            List<Integer> aStarPath = graph.aStar(0, 4);
//
//            if (aStarPath.isEmpty()) {
//                System.out.println("No path found.");
//            } else {
//                System.out.println("Path found!");
//                for (int node : aStarPath) {
//                    System.out.println(node);
//                }
//            }

            //Bidirectional Search*********************************************************************


//            Vertex sourceVertex = new Vertex(0);
//            Vertex targetVertex = new Vertex(4);
//
//            List<Vertex> bidirectionalSearchPath = graph.bidirectionalSearch(sourceVertex, targetVertex);
//
//            if (bidirectionalSearchPath.isEmpty()) {
//                System.out.println("No path found.");
//            } else {
//                System.out.println("Path found!");
//                for (Vertex vertex : bidirectionalSearchPath) {
//                    System.out.println("Visited Vertex: " + vertex.label);
//                }
//            }

            //B Heuristic*********************************************************************

//            Vertex sourceVertex = new Vertex(0); // Choose the source node
//            Vertex targetVertex = new Vertex(4); // Choose the target node
//            List<Vertex> bHeuristicPath = graph.bHeuristicSearch(sourceVertex, targetVertex);
//
//            if (bHeuristicPath.isEmpty()) {
//                System.out.println("No path found.");
//            } else {
//                System.out.println("Path found!");
//                for (Vertex vertex : bHeuristicPath) {
//                    System.out.println("Visited Vertex: " + vertex.label);
//                }
//            }

            //Hash A**********************************************************************

            Vertex sourceVertex = new Vertex(0); // Choose the source node
            Vertex targetVertex = new Vertex(4); // Choose the target node
            List<Vertex> path = graph.hashDistributedAStar(sourceVertex, targetVertex);

            if (path.isEmpty()) {
                System.out.println("No path found.");
            } else {
                System.out.println("Path found!");
                for (Vertex vertex : path) {
                    System.out.println("Visited Vertex: " + vertex.label);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}