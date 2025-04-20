package ma.ensat;

import ma.ensat.core.Graph;
import ma.ensat.algo.UCS;
import ma.ensat.utils.GraphIO;
import ma.ensat.view.GraphViewer;

import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;
import java.io.File;

public class App {
    public static void main(String[] args) {
        try {
            // Chemin relatif basé sur la racine du projet
            String projectRoot = System.getProperty("user.dir");

            // 1. Charger le scénario 1
            Graph<String> graph1 = GraphIO.loadGraph(projectRoot + "/src/main/resources/graphs/scenario1.graph");

            // 2. Exécuter UCS sur le scénario 1
            UCS<String> ucs = new UCS<>();
            var distances = ucs.search(graph1, graph1.getVertex("A"));
            System.out.println("Résultats UCS pour scénario 1:");
            distances.forEach((v, d) -> System.out.println(v.getData() + ": " + d));

            // 3. Sauvegarder le graphe du scénario 1
            GraphIO.saveGraph(graph1, projectRoot + "/data/saved/saved_graph1.graph");

            // 4. Générer la visualisation pour le scénario 1
            String visualization1Path = projectRoot + "/data/exports/visualization1.html";
            GraphViewer.generateHTML(graph1, visualization1Path);

            // Charger le scénario 2
            Graph<String> graph2 = GraphIO.loadGraph(projectRoot + "/src/main/resources/graphs/scenario2.graph");

            // Exécuter UCS sur le scénario 2
            distances = ucs.search(graph2, graph2.getVertex("Paris"));
            System.out.println("\nRésultats UCS pour scénario 2:");
            distances.forEach((v, d) -> System.out.println(v.getData() + ": " + d));

            // Sauvegarder le graphe du scénario 2
            GraphIO.saveGraph(graph2, projectRoot + "/data/saved/saved_graph2.graph");

            // Générer la visualisation pour le scénario 2
            String visualization2Path = projectRoot + "/data/exports/visualization2.html";
            GraphViewer.generateHTML(graph2, visualization2Path);

            String graphEditorPath = projectRoot + "/web/graph-editor.html";

            System.out.println("Toutes les opérations ont réussi!");

            // 5. Ouvrir les fichiers HTML dans le navigateur par défaut
            openInBrowser(new File(visualization1Path).toURI());
            openInBrowser(new File(visualization2Path).toURI());
            openInBrowser(new File(graphEditorPath).toURI());

        } catch (IOException e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ouverture du navigateur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ouvre une URI dans le navigateur par défaut
     * @param uri L'URI à ouvrir
     */
    private static void openInBrowser(URI uri) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            } else {
                // Tentative d'ouverture spécifique à l'OS si Desktop n'est pas supporté
                String os = System.getProperty("os.name").toLowerCase();

                ProcessBuilder builder;
                if (os.contains("win")) {
                    // Windows
                    builder = new ProcessBuilder("cmd", "/c", "start", uri.toString());
                } else if (os.contains("mac")) {
                    // macOS
                    builder = new ProcessBuilder("open", uri.toString());
                } else if (os.contains("nix") || os.contains("nux")) {
                    // Linux/Unix
                    builder = new ProcessBuilder("xdg-open", uri.toString());
                } else {
                    System.out.println("Impossible d'ouvrir automatiquement le navigateur sur votre OS.");
                    System.out.println("Veuillez ouvrir manuellement : " + uri.toString());
                    return;
                }

                builder.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du navigateur: " + e.getMessage());
        }
    }
}