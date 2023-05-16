package ro.unibuc.fmi;


import ro.unibuc.fmi.graph.Graph;
import ro.unibuc.fmi.graph.GraphTraversal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {


        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\\\Facultate\\\\UNIBUC\\\\Materii\\\\Anul II\\\\Semestrul II\\\\Tehnici Avansate de Programare\\\\Examen\\\\tutorials\\\\Graphs\\\\src\\\\main\\\\resources\\\\input.txt"))) {

            Graph graph = new Graph();


            String[] graphProperties = reader.readLine().trim().split("\\s+");

            int numberOfVertices = Integer.parseInt(graphProperties[0]);
            int numberOfEdges = Integer.parseInt(String.valueOf(graphProperties[1]));

            for (int i = 1; i <= numberOfVertices; i++) {
                graph.addVertex(Integer.valueOf(i));
            }

            for (int i = 1; i <= numberOfEdges; i++) {
                String[] edge = reader.readLine().trim().split("\\s+");
                graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Double.parseDouble(String.valueOf(edge[2])));
            }

            System.out.println(graph.printGraph());
            System.out.println('\n');
            System.out.println(graph.printEdges());

            System.out.println("DFS: " + GraphTraversal.depthFirstTraversal(graph, 2));
            System.out.println("BFS: " + GraphTraversal.breadthFirstTraversal(graph, 2));

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}