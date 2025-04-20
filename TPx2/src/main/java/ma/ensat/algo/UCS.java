package ma.ensat.algo;

import ma.ensat.core.Graph;
import ma.ensat.core.Vertex;
import ma.ensat.core.Edge;
import ma.ensat.utils.GraphPriorityQueue;

import java.util.HashMap;
import java.util.Map;

public class UCS<T> {
    public Map<Vertex<T>, Double> search(Graph<T> graph, Vertex<T> start) {
        Map<Vertex<T>, Double> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> predecessors = new HashMap<>();
        GraphPriorityQueue<Vertex<T>> priorityQueue = new GraphPriorityQueue<>();

        // Initialisation
        for (Vertex<T> vertex : graph.getVertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        priorityQueue.enqueue(start, 0.0);

        // Algorithme UCS
        while (!priorityQueue.isEmpty()) {
            Vertex<T> current = priorityQueue.dequeue();

            for (Edge<T> edge : current.getEdges()) {
                Vertex<T> neighbor = edge.getDestination();
                double newDistance = distances.get(current) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, current);
                    priorityQueue.enqueue(neighbor, newDistance);
                }
            }
        }

        return distances;
    }
}