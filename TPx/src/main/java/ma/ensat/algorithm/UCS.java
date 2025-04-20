package ma.ensat.algorithm;

import ma.ensat.model.*;
import ma.ensat.model.Node;

import java.util.*;

/**
 * Implémentation de l'algorithme Uniform Cost Search (UCS)
 * @param <T> Type de données des sommets
 */
public class UCS<T> {
    private Graph<T> graph;

    public UCS(Graph<T> graph) {
        this.graph = graph;
    }

    /**
     * Trouve le chemin le plus court entre deux sommets en utilisant UCS
     * @param start Sommet de départ
     * @param goal Sommet d'arrivée
     * @return Liste ordonnée des sommets formant le chemin le plus court, ou liste vide si aucun chemin n'existe
     */
    public List<T> findShortestPath(T start, T goal) {
        if (start.equals(goal)) {
            return Collections.singletonList(start);
        }

        // File de priorité pour les nœuds à explorer
        PriorityQueue<PriorityQueueItem<T>> frontier = new PriorityQueue<>();
        frontier.add(new PriorityQueueItem<>(start, 0));

        // Pour chaque nœud, stocke le nœud précédent dans le chemin le plus court
        Map<T, T> cameFrom = new HashMap<>();

        // Pour chaque nœud, stocke le coût cumulé depuis le départ
        Map<T, Integer> costSoFar = new HashMap<>();
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            PriorityQueueItem<T> current = frontier.poll();
            T currentNode = current.getNode();

            if (currentNode.equals(goal)) {
                break;
            }

            Node<T> node = graph.getNode(currentNode);
            if (node == null) continue;

            for (Edge<T> edge : node.getEdges()) {
                T next = edge.getDestination();
                int newCost = costSoFar.get(currentNode) + edge.getWeight();

                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(new PriorityQueueItem<>(next, newCost));
                    cameFrom.put(next, currentNode);
                }
            }
        }

        // Reconstruction du chemin
        if (!cameFrom.containsKey(goal)) {
            return Collections.emptyList(); // Pas de chemin trouvé
        }

        List<T> path = new ArrayList<>();
        T current = goal;
        path.add(current);

        while (!current.equals(start)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }

        return path;
    }

    /**
     * Calcule le coût total d'un chemin
     * @param path Le chemin à évaluer
     * @return Le coût total du chemin
     */
    public int getPathCost(List<T> path) {
        int cost = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            T current = path.get(i);
            T next = path.get(i + 1);

            Node<T> node = graph.getNode(current);
            for (Edge<T> edge : node.getEdges()) {
                if (edge.getDestination().equals(next)) {
                    cost += edge.getWeight();
                    break;
                }
            }
        }

        return cost;
    }
}