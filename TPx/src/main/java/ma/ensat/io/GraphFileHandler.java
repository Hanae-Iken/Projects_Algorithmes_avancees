package ma.ensat.io;

import ma.ensat.model.Graph;
import java.io.*;
import java.util.*;

/**
 * Classe de gestion des opérations de chargement et sauvegarde de graphes
 */
public class GraphFileHandler {

    /**
     * Sauvegarde un graphe dans un fichier
     * @param <T> Type de données des sommets
     * @param graph Le graphe à sauvegarder
     * @param filename Nom du fichier
     * @throws IOException En cas d'erreur lors de l'écriture
     */
    public static <T> void saveGraph(Graph<T> graph, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Écriture du nombre de sommets
            writer.write(graph.getNodeCount() + "\n");

            // Liste des valeurs des sommets
            List<T> nodeValues = new ArrayList<>(graph.getNodeValues());

            // Écriture des sommets
            for (T value : nodeValues) {
                writer.write(value.toString() + "\n");
            }

            // Écriture des arêtes
            for (int i = 0; i < nodeValues.size(); i++) {
                T source = nodeValues.get(i);
                for (var edge : graph.getNode(source).getEdges()) {
                    T dest = edge.getDestination();
                    // Évite de dupliquer les arêtes (non orienté)
                    int destIndex = nodeValues.indexOf(dest);
                    if (i < destIndex) {
                        writer.write(source + " " + dest + " " + edge.getWeight() + "\n");
                    }
                }
            }
        }
    }

    /**
     * Charge un graphe à partir d'un fichier pour des sommets de type entier
     * @param filename Nom du fichier
     * @return Le graphe chargé
     * @throws IOException En cas d'erreur lors de la lecture
     */
    public static Graph<Integer> loadIntegerGraph(String filename) throws IOException {
        Graph<Integer> graph = new Graph<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Lecture du nombre de sommets
            int nodeCount = Integer.parseInt(reader.readLine().trim());

            // Lecture des sommets
            for (int i = 0; i < nodeCount; i++) {
                int nodeValue = Integer.parseInt(reader.readLine().trim());
                graph.addNode(nodeValue);
            }

            // Lecture des arêtes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 3) {
                    int src = Integer.parseInt(parts[0]);
                    int dest = Integer.parseInt(parts[1]);
                    int weight = Integer.parseInt(parts[2]);
                    graph.addEdge(src, dest, weight);
                }
            }
        }

        return graph;
    }

    /**
     * Charge un graphe à partir d'un fichier pour des sommets de type chaîne
     * @param filename Nom du fichier
     * @return Le graphe chargé
     * @throws IOException En cas d'erreur lors de la lecture
     */
    public static Graph<String> loadStringGraph(String filename) throws IOException {
        Graph<String> graph = new Graph<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Lecture du nombre de sommets
            int nodeCount = Integer.parseInt(reader.readLine().trim());

            // Lecture des sommets
            for (int i = 0; i < nodeCount; i++) {
                String nodeValue = reader.readLine().trim();
                graph.addNode(nodeValue);
            }

            // Lecture des arêtes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 3) {
                    String src = parts[0];
                    String dest = parts[1];
                    int weight = Integer.parseInt(parts[2]);
                    graph.addEdge(src, dest, weight);
                }
            }
        }

        return graph;
    }
}