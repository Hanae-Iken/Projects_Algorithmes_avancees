package ma.ensat.utils;

import java.util.PriorityQueue;

public class GraphPriorityQueue<T> {
    private PriorityQueue<QueueNode<T>> queue;

    public GraphPriorityQueue() {
        this.queue = new PriorityQueue<>((a, b) -> Double.compare(a.priority, b.priority));
    }

    public void enqueue(T element, double priority) {
        queue.add(new QueueNode<>(element, priority));
    }

    public T dequeue() {
        return queue.poll().element;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    private static class QueueNode<T> {
        T element;
        double priority;

        public QueueNode(T element, double priority) {
            this.element = element;
            this.priority = priority;
        }
    }
}
