package ma.ensat.visualization;

import ma.ensat.io.GraphHTMLExporter;
import ma.ensat.model.Graph;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe utilitaire pour visualiser un graphe
 */
public class GraphVisualizer {

    /**
     * Visualise un graphe en créant un fichier HTML temporaire et en l'ouvrant dans le navigateur par défaut
     * @param <T> Type de données des sommets
     * @param graph Le graphe à visualiser
     * @throws IOException En cas d'erreur lors de la création ou de l'ouverture du fichier
     */
    public static <T> void visualizeGraph(Graph<T> graph) throws IOException {
        // Création d'un fichier temporaire pour l'HTML
        Path tempFile = Files.createTempFile("graph_visualization_", ".html");
        String filename = tempFile.toString();

        // Export du graphe vers HTML
        GraphHTMLExporter.exportToHTML(graph, filename);

        // Ouverture du fichier dans le navigateur par défaut
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new File(filename).toURI());
        } else {
            System.out.println("Le bureau n'est pas supporté. Ouvrez manuellement le fichier: " + filename);
        }
    }
}