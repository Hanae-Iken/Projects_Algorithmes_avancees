package ma.ensat.io;

import ma.ensat.model.Edge;
import ma.ensat.model.Graph;
import ma.ensat.model.Node;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour exporter un graphe au format HTML pour visualisation
 */
public class GraphHTMLExporter {

    /**
     * Exporte un graphe vers un fichier HTML pour visualisation
     * @param <T> Type de données des sommets
     * @param graph Le graphe à exporter
     * @param filename Nom du fichier HTML
     * @throws IOException En cas d'erreur lors de l'écriture
     */
    public static <T> void exportToHTML(Graph<T> graph, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html lang=\"fr\">\n");
            writer.write("<head>\n");
            writer.write("    <meta charset=\"UTF-8\">\n");
            writer.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
            writer.write("    <title>Visualisation de Graphe</title>\n");
            writer.write("    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/vis-network/9.1.2/vis-network.min.js\"></script>\n");
            writer.write("    <style>\n");
            writer.write("        #mynetwork {\n");
            writer.write("            width: 100%;\n");
            writer.write("            height: 800px;\n");
            writer.write("            border: 1px solid lightgray;\n");
            writer.write("        }\n");
            writer.write("    </style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("    <h1>Visualisation du Graphe</h1>\n");
            writer.write("    <div id=\"mynetwork\"></div>\n");
            writer.write("    <script>\n");

            // Création des nœuds et arêtes pour vis.js
            writer.write("        const nodes = new vis.DataSet([\n");

            int nodeId = 0;
            Map<T, Integer> nodeIds = new HashMap<>();

            for (T nodeValue : graph.getNodeValues()) {
                nodeIds.put(nodeValue, nodeId);
                writer.write("            { id: " + nodeId + ", label: '" + nodeValue + "' },\n");
                nodeId++;
            }

            writer.write("        ]);\n\n");

            writer.write("        const edges = new vis.DataSet([\n");

            for (T nodeValue : graph.getNodeValues()) {
                Node<T> node = graph.getNode(nodeValue);
                int sourceId = nodeIds.get(nodeValue);

                for (Edge<T> edge : node.getEdges()) {
                    T destValue = edge.getDestination();
                    int destId = nodeIds.get(destValue);

                    // Évite de dupliquer les arêtes (graphe non orienté)
                    if (sourceId < destId) {
                        writer.write("            { from: " + sourceId + ", to: " + destId +
                                ", label: '" + edge.getWeight() + "' },\n");
                    }
                }
            }

            writer.write("        ]);\n\n");

            writer.write("        const container = document.getElementById('mynetwork');\n");
            writer.write("        const data = {\n");
            writer.write("            nodes: nodes,\n");
            writer.write("            edges: edges\n");
            writer.write("        };\n");
            writer.write("        const options = {\n");
            writer.write("            physics: {\n");
            writer.write("                barnesHut: {\n");
            writer.write("                    gravitationalConstant: -80000,\n");
            writer.write("                    springConstant: 0.001,\n");
            writer.write("                    springLength: 200\n");
            writer.write("                }\n");
            writer.write("            },\n");
            writer.write("            edges: {\n");
            writer.write("                font: {\n");
            writer.write("                    size: 12\n");
            writer.write("                },\n");
            writer.write("                widthConstraint: {\n");
            writer.write("                    maximum: 90\n");
            writer.write("                }\n");
            writer.write("            },\n");
            writer.write("            nodes: {\n");
            writer.write("                shape: 'circle',\n");
            writer.write("                size: 25,\n");
            writer.write("                font: {\n");
            writer.write("                    size: 14\n");
            writer.write("                }\n");
            writer.write("            }\n");
            writer.write("        };\n");
            writer.write("        const network = new vis.Network(container, data, options);\n");
            writer.write("    </script>\n");
            writer.write("</body>\n");
            writer.write("</html>");
        }
    }
}
