package ma.ensat.utils;

import ma.ensat.core.Graph;
import ma.ensat.core.Vertex;
import ma.ensat.core.Edge;

import java.io.*;
import java.util.List;

public class GraphIO<T> {
    public static <T> void saveGraph(Graph<T> graph, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Écriture des sommets
            writer.println("# Vertices");
            for (Vertex<T> vertex : graph.getVertices()) {
                writer.println(vertex.getData().toString());
            }

            // Écriture des arêtes
            writer.println("# Edges (source,destination,weight)");
            for (Edge<T> edge : graph.getEdges()) {
                writer.printf("%s,%s,%.2f%n",
                        edge.getSource().getData().toString(),
                        edge.getDestination().getData().toString(),
                        edge.getWeight());
            }
        }
    }

    public static Graph<String> loadGraph(String filePath) throws IOException {
        Graph<String> graph = new Graph<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingVertices = false;
            boolean readingEdges = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")) {
                    if (line.contains("Vertices")) {
                        readingVertices = true;
                        readingEdges = false;
                    } else if (line.contains("Edges")) {
                        readingVertices = false;
                        readingEdges = true;
                    }
                    continue;
                }

                if (readingVertices && !line.isEmpty()) {
                    graph.addVertex(line);
                } else if (readingEdges && !line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String source = parts[0].trim();
                        String dest = parts[1].trim();
                        double weight = Double.parseDouble(parts[2].trim());
                        graph.addEdge(source, dest, weight);
                    }
                }
            }
        }
        return graph;
    }
}