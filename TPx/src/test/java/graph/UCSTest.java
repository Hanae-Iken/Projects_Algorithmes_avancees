package graph;

import ma.ensat.algorithm.UCS;
import ma.ensat.model.Graph;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Tests pour l'algorithme UCS (Uniform Cost Search)
 */
public class UCSTest {

    private Graph<String> stringGraph;
    private Graph<Integer> integerGraph;
    private UCS<String> stringUCS;
    private UCS<Integer> integerUCS;

    @Before
    public void setUp() {
        // Initialiser un graphe de chaînes
        stringGraph = new Graph<>();
        stringGraph.addEdge("A", "B", 4);
        stringGraph.addEdge("A", "C", 3);
        stringGraph.addEdge("B", "C", 1);
        stringGraph.addEdge("B", "D", 5);
        stringGraph.addEdge("C", "D", 8);
        stringGraph.addEdge("C", "E", 2);
        stringGraph.addEdge("D", "E", 3);
        stringGraph.addEdge("D", "F", 2);
        stringGraph.addEdge("E", "F", 5);

        stringUCS = new UCS<>(stringGraph);

        // Initialiser un graphe d'entiers
        integerGraph = new Graph<>();
        integerGraph.addEdge(0, 1, 4);
        integerGraph.addEdge(0, 2, 3);
        integerGraph.addEdge(1, 2, 1);
        integerGraph.addEdge(1, 3, 5);
        integerGraph.addEdge(2, 3, 8);
        integerGraph.addEdge(2, 4, 2);
        integerGraph.addEdge(3, 4, 3);
        integerGraph.addEdge(3, 5, 2);
        integerGraph.addEdge(4, 5, 5);

        integerUCS = new UCS<>(integerGraph);
    }

    @Test
    public void testFindShortestPathStringGraph() {
        // Test du chemin le plus court de A à F
        List<String> path = stringUCS.findShortestPath("A", "F");
        int cost = stringUCS.getPathCost(path);

        // Vérifier que le chemin est valide
        assertNotNull("Le chemin ne devrait pas être null", path);
        assertFalse("Le chemin ne devrait pas être vide", path.isEmpty());

        // Vérifier le premier et le dernier élément du chemin
        assertEquals("Le chemin devrait commencer par A", "A", path.get(0));
        assertEquals("Le chemin devrait se terminer par F", "F", path.get(path.size() - 1));

        // Vérifier quelques cas spécifiques

        // Chemin A → C → E → D → F (le plus court en coût)
        // Alternative: A → C → E → F
        // Le chemin exact peut varier selon l'implémentation, mais le coût devrait être le même
        System.out.println("Chemin A → F: " + String.join(" → ", path));
        System.out.println("Coût: " + cost);

        // Vérifier un chemin direct
        List<String> directPath = stringUCS.findShortestPath("A", "B");
        assertEquals("Le chemin direct devrait avoir 2 nœuds", 2, directPath.size());
        assertEquals("A", directPath.get(0));
        assertEquals("B", directPath.get(1));
    }

    @Test
    public void testFindShortestPathIntegerGraph() {
        // Test du chemin le plus court de 0 à 5
        List<Integer> path = integerUCS.findShortestPath(0, 5);
        int cost = integerUCS.getPathCost(path);

        // Vérifier que le chemin est valide
        assertNotNull("Le chemin ne devrait pas être null", path);
        assertFalse("Le chemin ne devrait pas être vide", path.isEmpty());

        // Vérifier le premier et le dernier élément du chemin
        assertEquals("Le chemin devrait commencer par 0", Integer.valueOf(0), path.get(0));
        assertEquals("Le chemin devrait se terminer par 5", Integer.valueOf(5), path.get(path.size() - 1));

        // Chemin 0 → 2 → 4 → 3 → 5 (le plus court en coût)
        // Alternative: 0 → 2 → 4 → 5
        // Le chemin exact peut varier selon l'implémentation, mais le coût devrait être le même
        System.out.println("Chemin 0 → 5: " + path);
        System.out.println("Coût: " + cost);
    }

    @Test
    public void testFindShortestPathNoPath() {
        // Créer un graphe avec des composantes déconnectées
        Graph<String> disconnectedGraph = new Graph<>();
        disconnectedGraph.addEdge("A", "B", 1);
        disconnectedGraph.addEdge("C", "D", 1);

        UCS<String> ucs = new UCS<>(disconnectedGraph);
        List<String> path = ucs.findShortestPath("A", "D");

        // Vérifier qu'aucun chemin n'est trouvé
        assertTrue("Il ne devrait pas y avoir de chemin entre A et D", path.isEmpty());
    }

    @Test
    public void testFindShortestPathSameNode() {
        // Test du chemin d'un nœud à lui-même
        List<String> path = stringUCS.findShortestPath("A", "A");

        // Vérifier que le chemin est valide
        assertNotNull("Le chemin ne devrait pas être null", path);
        assertEquals("Le chemin devrait avoir 1 nœud", 1, path.size());
        assertEquals("Le chemin devrait contenir seulement A", "A", path.get(0));
    }

    @Test
    public void testGetPathCost() {
        // Créer un chemin simple
        List<String> path = List.of("A", "C", "E");

        // Calculer le coût du chemin
        int cost = stringUCS.getPathCost(path);

        // Vérifier que le coût est correct (A→C = 3, C→E = 2, total = 5)
        assertEquals("Le coût du chemin A→C→E devrait être 5", 5, cost);
    }
}