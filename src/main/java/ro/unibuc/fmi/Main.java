package ro.unibuc.fmi;


import ro.unibuc.fmi.graph.Edge;
import ro.unibuc.fmi.graph.Graph;
import ro.unibuc.fmi.graph.Vertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Main {

    public static void main(String[] args) {


        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Facultate\\UNIBUC\\Materii\\Anul II\\Semestrul II\\Tehnici Avansate de Programare\\Examen\\graphs\\src\\main\\resources\\input.txt"))) {

            Graph graph = new Graph();
            graph.setWeighted(false);
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
//                for (int vertex : aStarPath) {
//                    System.out.println(vertex);
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

//            Vertex sourceVertex = new Vertex(0); // Choose the source vertex
//            Vertex targetVertex = new Vertex(4); // Choose the target vertex
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

//            Vertex sourceVertex = new Vertex(0); // Choose the source vertex
//            Vertex targetVertex = new Vertex(4); // Choose the target vertex
//            List<Vertex> path = graph.hashDistributedAStar(sourceVertex, targetVertex);
//
//            if (path.isEmpty()) {
//                System.out.println("No path found.");
//            } else {
//                System.out.println("Path found!");
//                for (Vertex vertex : path) {
//                    System.out.println("Visited Vertex: " + vertex.label);
//                }
//            }

            //lexBFS**********************************************************************


//            Vertex sourceVertex = new Vertex(0); // Choose the source vertex
//            List<Vertex> ordering = graph.lexBFS(sourceVertex);
//
//            System.out.println("Lexicographic BFS Ordering:");
//            for (Vertex vertex : ordering) {
//                System.out.println(vertex.label);
//            }

            //lexDFS**********************************************************************

//            Vertex sourceVertex = new Vertex(0); // Choose the source vertex
//            List<Vertex> ordering = graph.lexDFS(sourceVertex);
//
//            System.out.println("Lexicographic DFS Ordering:");
//            for (Vertex vertex : ordering) {
//                System.out.println(vertex.label);
//            }

            //Tarjan**********************************************************************

//
//            List<List<Vertex>> sccList = graph.findSCCs(numberOfVertices);
//
//            System.out.println("Strongly Connected Components:");
//            for (List<Vertex> scc : sccList) {
//                System.out.println("List is: " + scc);
//                for (Vertex vertex : scc) {
//                    System.out.print(vertex.label + " ");
//                }
//                System.out.println('\n');
//            }

            //Spanning Tree Prim's algo**********************************************************************


//            List<Edge> mst = graph.findMinimumSpanningTree(0);
//
//            System.out.println("Minimum Spanning Tree:");
//            for (Edge edge : mst) {
//                System.out.println(edge.getSourceVertex() + " -- " + edge.getDestinationVertex() + " : " + graph.getWeights().get(edge.getSourceVertex()).get(edge.getDestinationVertex()));
//            }

            //PBFS 1-D**********************************************************************


//            graph.pbfsOneDimensional(numberOfVertices);

            //PDFS 1-D**********************************************************************


//            graph.pdfsOneDimensional(numberOfVertices);


            //Bitmap BFS**********************************************************************

//            graph.bitmapBFS(numberOfVertices, 0);

            //UFSCC**********************************************************************


//            List<List<Integer>> scc = graph.findStronglyConnectedComponents(numberOfVertices);
//
//            System.out.println("Strongly Connected Components:");
//            for (List<Integer> component : scc) {
//                System.out.println(component);
//            }

            //Coloring**********************************************************************


//            int numColors = 3; // Number of colors available
//
//            boolean isColoringPossible = graph.colorGraph(numColors);
//
//            if (isColoringPossible) {
//                System.out.println("Graph can be colored with " + numColors + " colors.");
//                System.out.println("Coloring result:");
//                for (Vertex vertex : graph.getAdjVertices().keySet()) {
//                    System.out.println("Vertex " + vertex.label + ": Color " + vertex.getColor());
//                }
//            } else {
//                System.out.println("Graph cannot be colored with " + numColors + " colors.");
//            }

            //Euler Tour**********************************************************************

//            List<Vertex> eulerTour = graph.findEulerTour(new Vertex(0));
//
//            System.out.println("Euler Tour:");
//            for (Vertex vertex : eulerTour) {
//                System.out.print(vertex.label + " ");
//            }

            //Doubling Euler Tour**********************************************************************


            List<Vertex> eulerTour = graph.findEulerTourWithDoubling();

            System.out.println("Euler Tour:");

            for (Vertex vertex : eulerTour) {
                System.out.print(vertex.label + " ");
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}