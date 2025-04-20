package ma.ensat.model;

/**
 * Élément utilisé dans la file de priorité pour l'algorithme UCS
 * @param <T> Type de données du sommet
 */
public class PriorityQueueItem<T> implements Comparable<PriorityQueueItem<T>> {
    private T node;
    private int priority;

    public PriorityQueueItem(T node, int priority) {
        this.node = node;
        this.priority = priority;
    }

    public T getNode() {
        return node;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PriorityQueueItem<T> other) {
        // Priorité plus basse = plus haute dans la file
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PriorityQueueItem)) return false;

        @SuppressWarnings("unchecked")
        PriorityQueueItem<T> other = (PriorityQueueItem<T>) obj;
        return node.equals(other.node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }
}