package ma.ensat;


import ma.ensat.core.Graph;
import ma.ensat.algo.UCS;
import ma.ensat.utils.GraphIO;
import ma.ensat.view.GraphViewer;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            // Chemin relatif basé sur la racine du projet
            String projectRoot = System.getProperty("user.dir");

            // 1. Charger un scénario
            Graph<String> graph = GraphIO.loadGraph(projectRoot + "/src/main/resources/graphs/scenario1.graph");

            // 2. Exécuter UCS
            UCS<String> ucs = new UCS<>();
            var distances = ucs.search(graph, graph.getVertex("A"));
            System.out.println("Résultats UCS:");
            distances.forEach((v, d) -> System.out.println(v.getData() + ": " + d));

            // 3. Sauvegarder le graphe
            GraphIO.saveGraph(graph, projectRoot + "/data/saved/saved_graph1.graph");

            // 4. Générer la visualisation
            GraphViewer.generateHTML(graph, projectRoot + "/data/exports/visualization1.html");

            System.out.println("Toutes les opérations ont réussi!");

        } catch (IOException e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}