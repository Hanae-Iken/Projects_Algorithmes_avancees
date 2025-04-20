package ma.ensat.model;

import java.util.*;

/**
 * Classe représentant un graphe non orienté
 * @param <T> Type de données des sommets
 */
public class Graph<T> {
    private Map<T, Node<T>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    /**
     * Ajoute un sommet au graphe
     * @param data Valeur du sommet
     */
    public void addNode(T data) {
        if (!nodes.containsKey(data)) {
            nodes.put(data, new Node<>(data));
        }
    }

    /**
     * Ajoute une arête entre deux sommets
     * @param source Sommet source
     * @param destination Sommet destination
     * @param weight Poids de l'arête
     */
    public void addEdge(T source, T destination, int weight) {
        // Ajoute les sommets s'ils n'existent pas
        addNode(source);
        addNode(destination);

        // Ajoute l'arête dans les deux sens (graphe non orienté)
        nodes.get(source).addEdge(destination, weight);
        nodes.get(destination).addEdge(source, weight);
    }

    /**
     * Récupère un sommet par sa valeur
     * @param data Valeur du sommet
     * @return Le sommet correspondant ou null s'il n'existe pas
     */
    public Node<T> getNode(T data) {
        return nodes.get(data);
    }

    /**
     * Retourne tous les sommets du graphe
     * @return Collection de tous les sommets
     */
    public Collection<Node<T>> getNodes() {
        return nodes.values();
    }

    /**
     * Retourne toutes les valeurs des sommets du graphe
     * @return Ensemble des valeurs des sommets
     */
    public Set<T> getNodeValues() {
        return nodes.keySet();
    }

    /**
     * Affiche le graphe
     */
    public void printGraph() {
        for (Node<T> node : nodes.values()) {
            System.out.println(node);
        }
    }

    /**
     * Retourne le nombre de sommets dans le graphe
     * @return Nombre de sommets
     */
    public int getNodeCount() {
        return nodes.size();
    }
}