package graph;

import ma.ensat.io.GraphFileHandler;
import ma.ensat.model.Graph;
import ma.ensat.model.Edge;
import ma.ensat.model.Node;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Tests pour la classe Graph et ses fonctionnalités associées
 */
public class GraphTest {

    private Graph<String> stringGraph;
    private Graph<Integer> integerGraph;
    private static final String TEST_FILE_STRING = "test_string_graph.txt";
    private static final String TEST_FILE_INTEGER = "test_integer_graph.txt";

    @Before
    public void setUp() {
        // Initialiser un graphe de chaînes
        stringGraph = new Graph<>();
        stringGraph.addEdge("A", "B", 5);
        stringGraph.addEdge("A", "C", 3);
        stringGraph.addEdge("B", "C", 2);
        stringGraph.addEdge("B", "D", 4);
        stringGraph.addEdge("C", "D", 6);

        // Initialiser un graphe d'entiers
        integerGraph = new Graph<>();
        integerGraph.addEdge(0, 1, 4);
        integerGraph.addEdge(0, 4, 8);
        integerGraph.addEdge(1, 2, 8);
        integerGraph.addEdge(1, 3, 11);
        integerGraph.addEdge(1, 4, 2);
    }

    @After
    public void tearDown() {
        // Supprimer les fichiers de test créés
        new File(TEST_FILE_STRING).delete();
        new File(TEST_FILE_INTEGER).delete();
    }

    @Test
    public void testAddNode() {
        Graph<String> graph = new Graph<>();
        graph.addNode("X");
        assertNotNull("Le nœud devrait exister", graph.getNode("X"));
    }

    @Test
    public void testAddEdge() {
        // Vérifier que les arêtes ont été ajoutées correctement
        Node<String> nodeA = stringGraph.getNode("A");
        List<Edge<String>> edges = nodeA.getEdges();

        boolean hasEdgeToB = false;
        boolean hasEdgeToC = false;

        for (Edge<String> edge : edges) {
            if (edge.getDestination().equals("B") && edge.getWeight() == 5) {
                hasEdgeToB = true;
            }
            if (edge.getDestination().equals("C") && edge.getWeight() == 3) {
                hasEdgeToC = true;
            }
        }

        assertTrue("Node A devrait avoir une arête vers B", hasEdgeToB);
        assertTrue("Node A devrait avoir une arête vers C", hasEdgeToC);
    }

    @Test
    public void testGetNode() {
        Node<String> nodeA = stringGraph.getNode("A");
        Node<String> nodeZ = stringGraph.getNode("Z");

        assertNotNull("Node A devrait exister", nodeA);
        assertNull("Node Z ne devrait pas exister", nodeZ);
    }

    @Test
    public void testGetNodeCount() {
        assertEquals("Le graphe de chaînes devrait avoir 4 nœuds", 4, stringGraph.getNodeCount());
        assertEquals("Le graphe d'entiers devrait avoir 5 nœuds", 5, integerGraph.getNodeCount());
    }

    @Test
    public void testSaveAndLoadStringGraph() throws IOException {
        // Sauvegarder le graphe de chaînes
        GraphFileHandler.saveGraph(stringGraph, TEST_FILE_STRING);

        // Charger le graphe
        Graph<String> loadedGraph = GraphFileHandler.loadStringGraph(TEST_FILE_STRING);

        // Vérifier que le graphe chargé est identique au graphe original
        assertEquals("Le nombre de nœuds devrait être le même",
                stringGraph.getNodeCount(), loadedGraph.getNodeCount());

        // Vérifier que les nœuds existent
        assertNotNull("Node A devrait exister", loadedGraph.getNode("A"));
        assertNotNull("Node B devrait exister", loadedGraph.getNode("B"));
        assertNotNull("Node C devrait exister", loadedGraph.getNode("C"));
        assertNotNull("Node D devrait exister", loadedGraph.getNode("D"));

        // Vérifier quelques arêtes
        Node<String> nodeA = loadedGraph.getNode("A");
        boolean hasEdgeToB = false;

        for (Edge<String> edge : nodeA.getEdges()) {
            if (edge.getDestination().equals("B") && edge.getWeight() == 5) {
                hasEdgeToB = true;
                break;
            }
        }

        assertTrue("Node A devrait avoir une arête vers B avec un poids de 5", hasEdgeToB);
    }

    @Test
    public void testSaveAndLoadIntegerGraph() throws IOException {
        // Sauvegarder le graphe d'entiers
        GraphFileHandler.saveGraph(integerGraph, TEST_FILE_INTEGER);

        // Charger le graphe
        Graph<Integer> loadedGraph = GraphFileHandler.loadIntegerGraph(TEST_FILE_INTEGER);

        // Vérifier que le graphe chargé est identique au graphe original
        assertEquals("Le nombre de nœuds devrait être le même",
                integerGraph.getNodeCount(), loadedGraph.getNodeCount());

        // Vérifier que les nœuds existent
        assertNotNull("Node 0 devrait exister", loadedGraph.getNode(0));
        assertNotNull("Node 1 devrait exister", loadedGraph.getNode(1));
        assertNotNull("Node 2 devrait exister", loadedGraph.getNode(2));
        assertNotNull("Node 3 devrait exister", loadedGraph.getNode(3));
        assertNotNull("Node 4 devrait exister", loadedGraph.getNode(4));

        // Vérifier quelques arêtes
        Node<Integer> node0 = loadedGraph.getNode(0);
        boolean hasEdgeToOne = false;

        for (Edge<Integer> edge : node0.getEdges()) {
            if (edge.getDestination().equals(1) && edge.getWeight() == 4) {
                hasEdgeToOne = true;
                break;
            }
        }

        assertTrue("Node 0 devrait avoir une arête vers 1 avec un poids de 4", hasEdgeToOne);
    }
}