package ma.ensat;

import ma.ensat.core.Graph;
import ma.ensat.algo.UCS;
import ma.ensat.utils.GraphIO;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Création d'un graphe
            Graph<String> graph = new Graph<>();
            graph.addEdge("A", "B", 4.0);
            graph.addEdge("A", "C", 2.0);
            graph.addEdge("B", "C", 5.0);
            graph.addEdge("B", "D", 10.0);
            graph.addEdge("C", "D", 3.0);
            graph.addEdge("D", "E", 7.0);
            graph.addEdge("C", "E", 4.0);

            // Sauvegarde du graphe
            GraphIO.saveGraph(graph, "data/saved/graph1.graph");

            // Chargement du graphe
            Graph<String> loadedGraph = GraphIO.loadGraph("data/saved/graph1.graph");

            // Exécution de UCS
            UCS<String> ucs = new UCS<>();
            var distances = ucs.search(loadedGraph, loadedGraph.getVertex("A"));

            // Affichage des résultats
            System.out.println("Distances depuis A:");
            distances.forEach((vertex, distance) ->
                    System.out.println(vertex.getData() + ": " + distance));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}