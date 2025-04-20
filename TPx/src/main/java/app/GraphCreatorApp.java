package app;

import ma.ensat.io.GraphHTMLExporter;
import ma.ensat.model.Graph;
import ma.ensat.visualization.GraphVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Application graphique pour créer et visualiser des graphes
 */
public class GraphCreatorApp extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> graphTypeCombo;
    private JButton createGraphButton;
    private JButton visualizeButton;
    private JButton openEditorButton;
    private JTextArea logArea;

    private Graph<String> stringGraph;
    private Graph<Integer> integerGraph;
    private String currentGraphType = "string";

    public GraphCreatorApp() {
        setTitle("Créateur de Graphes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        layoutComponents();
        addListeners();

        stringGraph = new Graph<>();
        integerGraph = new Graph<>();
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        graphTypeCombo = new JComboBox<>(new String[]{"string", "integer"});
        createGraphButton = new JButton("Créer Graphe");
        visualizeButton = new JButton("Visualiser");
        openEditorButton = new JButton("Ouvrir Éditeur Web");
        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Type de graphe:"));
        controlPanel.add(graphTypeCombo);
        controlPanel.add(createGraphButton);
        controlPanel.add(visualizeButton);
        controlPanel.add(openEditorButton);

        JScrollPane logScroll = new JScrollPane(logArea);

        add(controlPanel, BorderLayout.NORTH);
        add(logScroll, BorderLayout.CENTER);
    }

    private void addListeners() {
        graphTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGraphType = (String) graphTypeCombo.getSelectedItem();
                log("Type de graphe changé pour: " + currentGraphType);
            }
        });

        createGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("string".equals(currentGraphType)) {
                    createStringGraph();
                } else {
                    createIntegerGraph();
                }
            }
        });

        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if ("string".equals(currentGraphType)) {
                        if (stringGraph.getNodeCount() > 0) {
                            GraphVisualizer.visualizeGraph(stringGraph);
                            log("Graphe de chaînes visualisé avec succès.");
                        } else {
                            log("Veuillez d'abord créer un graphe de chaînes.");
                        }
                    } else {
                        if (integerGraph.getNodeCount() > 0) {
                            GraphVisualizer.visualizeGraph(integerGraph);
                            log("Graphe d'entiers visualisé avec succès.");
                        } else {
                            log("Veuillez d'abord créer un graphe d'entiers.");
                        }
                    }
                } catch (IOException ex) {
                    log("Erreur lors de la visualisation: " + ex.getMessage());
                }
            }
        });

        openEditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ouvrir l'éditeur web
                    File editorFile = new File("web/index.html");
                    if (editorFile.exists()) {
                        Desktop.getDesktop().browse(editorFile.toURI());
                        log("Éditeur web ouvert avec succès.");
                    } else {
                        log("Fichier d'éditeur web non trouvé: " + editorFile.getAbsolutePath());
                    }
                } catch (IOException ex) {
                    log("Erreur lors de l'ouverture de l'éditeur: " + ex.getMessage());
                }
            }
        });
    }

    private void createStringGraph() {
        stringGraph = new Graph<>();
        stringGraph.addEdge("A", "B", 5);
        stringGraph.addEdge("A", "C", 3);
        stringGraph.addEdge("B", "C", 2);
        stringGraph.addEdge("B", "D", 4);
        stringGraph.addEdge("C", "D", 6);
        stringGraph.addEdge("C", "E", 7);
        stringGraph.addEdge("D", "E", 1);

        log("Graphe de chaînes créé:");
        stringGraph.getNodes().forEach(node -> log(node.toString()));
    }

    private void createIntegerGraph() {
        integerGraph = new Graph<>();
        integerGraph.addEdge(0, 1, 4);
        integerGraph.addEdge(0, 4, 8);
        integerGraph.addEdge(1, 2, 8);
        integerGraph.addEdge(1, 3, 11);
        integerGraph.addEdge(1, 4, 2);
        integerGraph.addEdge(2, 3, 7);
        integerGraph.addEdge(3, 4, 9);

        log("Graphe d'entiers créé:");
        integerGraph.getNodes().forEach(node -> log(node.toString()));
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphCreatorApp app = new GraphCreatorApp();
            app.setVisible(true);
        });
    }
}