package app;

import ma.ensat.algorithm.UCS;
import ma.ensat.io.GraphFileHandler;
import ma.ensat.io.GraphHTMLExporter;
import ma.ensat.model.Graph;
import ma.ensat.visualization.GraphVisualizer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Création des scénarios de test
            createTestScenarios();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Système de gestion de graphes ===");
                System.out.println("1. Créer et afficher un graphe d'entiers");
                System.out.println("2. Créer et afficher un graphe de chaînes");
                System.out.println("3. Charger un graphe depuis un fichier");
                System.out.println("4. Exécuter l'algorithme UCS");
                System.out.println("5. Visualiser un graphe en HTML");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                switch (choice) {
                    case 0:
                        System.out.println("Au revoir !");
                        return;
                    case 1:
                        testIntegerGraph();
                        break;
                    case 2:
                        testStringGraph();
                        break;
                    case 3:
                        System.out.print("Nom du fichier à charger (1, 2 ou 3) : ");
                        String file = "scenario" + scanner.nextLine().trim() + ".txt";
                        loadGraphFromFile(file);
                        break;
                    case 4:
                        runUCSTest();
                        break;
                    case 5:
                        visualizeGraphs();
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            }
        } catch (Exception e) {
            System.out.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testIntegerGraph() {
        System.out.println("\n=== Test avec un graphe d'entiers ===");
        Graph<Integer> intGraph = new Graph<>();

        // Ajout des sommets et arêtes
        intGraph.addEdge(0, 1, 4);
        intGraph.addEdge(0, 4, 8);
        intGraph.addEdge(1, 2, 8);
        intGraph.addEdge(1, 3, 11);
        intGraph.addEdge(1, 4, 2);
        intGraph.addEdge(2, 3, 7);
        intGraph.addEdge(3, 4, 9);

        // Affichage du graphe
        System.out.println("Structure du graphe :");
        intGraph.printGraph();

        // Sauvegarde du graphe
        try {
            GraphFileHandler.saveGraph(intGraph, "integer_graph.txt");
            System.out.println("Graphe sauvegardé dans integer_graph.txt");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du graphe : " + e.getMessage());
        }
    }

    private static void testStringGraph() {
        System.out.println("\n=== Test avec un graphe de chaînes ===");
        Graph<String> stringGraph = new Graph<>();

        // Ajout des sommets et arêtes
        stringGraph.addEdge("A", "B", 5);
        stringGraph.addEdge("A", "C", 3);
        stringGraph.addEdge("B", "C", 2);
        stringGraph.addEdge("B", "D", 4);
        stringGraph.addEdge("C", "D", 6);
        stringGraph.addEdge("C", "E", 7);
        stringGraph.addEdge("D", "E", 1);

        // Affichage du graphe
        System.out.println("Structure du graphe :");
        stringGraph.printGraph();

        // Sauvegarde du graphe
        try {
            GraphFileHandler.saveGraph(stringGraph, "string_graph.txt");
            System.out.println("Graphe sauvegardé dans string_graph.txt");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du graphe : " + e.getMessage());
        }
    }

    private static void loadGraphFromFile(String filename) {
        System.out.println("\n=== Chargement d'un graphe depuis un fichier ===");

        try {
            // Essayons d'abord avec un graphe d'entiers
            try {
                Graph<Integer> intGraph = GraphFileHandler.loadIntegerGraph(filename);
                System.out.println("Graphe d'entiers chargé avec succès :");
                intGraph.printGraph();
            } catch (NumberFormatException e) {
                // Si échec, essayons avec des chaînes
                Graph<String> stringGraph = GraphFileHandler.loadStringGraph(filename);
                System.out.println("Graphe de chaînes chargé avec succès :");
                stringGraph.printGraph();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du graphe : " + e.getMessage());
        }
    }

    private static void runUCSTest() {
        System.out.println("\n=== Test de l'algorithme UCS ===");

        // Créer un graphe pour le test
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 3);
        graph.addEdge("B", "C", 1);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 8);
        graph.addEdge("C", "E", 2);
        graph.addEdge("D", "E", 3);
        graph.addEdge("D", "F", 2);
        graph.addEdge("E", "F", 5);

        System.out.println("Structure du graphe :");
        graph.printGraph();

        UCS<String> ucs = new UCS<>(graph);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Sommet de départ : ");
        String start = scanner.nextLine().trim().toUpperCase();
        System.out.print("Sommet d'arrivée : ");
        String goal = scanner.nextLine().trim().toUpperCase();

        List<String> path = ucs.findShortestPath(start, goal);

        if (path.isEmpty()) {
            System.out.println("Pas de chemin trouvé entre " + start + " et " + goal);
        } else {
            System.out.println("Chemin le plus court de " + start + " à " + goal + " :");
            System.out.println(String.join(" -> ", path));
            System.out.println("Coût total : " + ucs.getPathCost(path));
        }
    }

    private static void visualizeGraphs() {
        System.out.println("\n=== Visualisation de graphes ===");

        try {
            // Créer un graphe pour la visualisation
            Graph<String> graph = new Graph<>();
            graph.addEdge("Paris", "Lyon", 465);
            graph.addEdge("Paris", "Bordeaux", 580);
            graph.addEdge("Paris", "Lille", 220);
            graph.addEdge("Lyon", "Marseille", 315);
            graph.addEdge("Lyon", "Nice", 340);
            graph.addEdge("Bordeaux", "Toulouse", 250);
            graph.addEdge("Marseille", "Nice", 190);
            graph.addEdge("Marseille", "Toulouse", 400);

            // Génération du fichier HTML statique
            GraphHTMLExporter.exportToHTML(graph, "graph_visualization.html");
            System.out.println("Graphe exporté vers graph_visualization.html");

            // Visualiser le graphe dans le navigateur
            GraphVisualizer.visualizeGraph(graph);

        } catch (IOException e) {
            System.out.println("Erreur lors de la visualisation du graphe : " + e.getMessage());
        }
    }

    /**
     * Crée des scénarios de test pour démontrer les fonctionnalités
     */
    private static void createTestScenarios() {
        try {
            // Scénario 1 : Un graphe de réseau routier
            Graph<String> roadNetwork = new Graph<>();
            roadNetwork.addEdge("Paris", "Lyon", 465);
            roadNetwork.addEdge("Paris", "Bordeaux", 580);
            roadNetwork.addEdge("Paris", "Lille", 220);
            roadNetwork.addEdge("Lyon", "Marseille", 315);
            roadNetwork.addEdge("Lyon", "Nice", 340);
            roadNetwork.addEdge("Bordeaux", "Toulouse", 250);
            roadNetwork.addEdge("Marseille", "Nice", 190);
            roadNetwork.addEdge("Marseille", "Toulouse", 400);
            GraphFileHandler.saveGraph(roadNetwork, "scenario1.txt");

            // Scénario 2 : Un graphe d'un réseau social
            Graph<String> socialNetwork = new Graph<>();
            socialNetwork.addEdge("Alice", "Bob", 5);
            socialNetwork.addEdge("Alice", "Charlie", 3);
            socialNetwork.addEdge("Bob", "Charlie", 2);
            socialNetwork.addEdge("Bob", "David", 4);
            socialNetwork.addEdge("Charlie", "David", 6);
            socialNetwork.addEdge("Charlie", "Eve", 7);
            socialNetwork.addEdge("David", "Eve", 1);
            socialNetwork.addEdge("Eve", "Alice", 9);
            GraphFileHandler.saveGraph(socialNetwork, "scenario2.txt");

            // Scénario 3 : Un graphe numérique avec différents poids
            Graph<Integer> numberGraph = new Graph<>();
            numberGraph.addEdge(1, 2, 10);
            numberGraph.addEdge(1, 3, 15);
            numberGraph.addEdge(1, 4, 30);
            numberGraph.addEdge(2, 3, 12);
            numberGraph.addEdge(2, 5, 25);
            numberGraph.addEdge(3, 4, 8);
            numberGraph.addEdge(3, 5, 18);
            numberGraph.addEdge(4, 5, 14);
            numberGraph.addEdge(4, 6, 20);
            numberGraph.addEdge(5, 6, 16);
            GraphFileHandler.saveGraph(numberGraph, "scenario3.txt");

            System.out.println("Scénarios de test créés avec succès !");

        } catch (IOException e) {
            System.out.println("Erreur lors de la création des scénarios de test : " + e.getMessage());
        }
    }
}