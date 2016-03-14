package com.mdorst.container.list;

/*
 * Queue is a SortableList
 *
 * T : Generic
 *
 * Queue
 * + enqueue(T)
 * + dequeue() : T
 */

/**
 * This class extends SortableList, and provides methods to support
 * a FIFO queue pattern.
 *
 * @param <E> the type of the elements held in this collection
 */
public class Queue<E extends Comparable<? super E>> extends LinkedList<E> {

    /**
     * Adds an element to the back of the queue
     *
     * @param obj the element to be added
     */
    public void enqueue(E obj) {
        super.add(obj);
    }

    /**
     * Removes an element from the front of the queue, returning it to the caller
     *
     * @return the removed element
     */
    public E dequeue() {
        E value = head.next.data;
        head.next.delete();
        if (size > 0) size--;
        return value;
    }

    /**
     * Shows the element at the front of the queue
     *
     * @return the element at the front of the queue
     */
    public E front() {
        return head.next.data;
    }

    public Queue(LinkedList<E> other) {
        super(other);
    }

    public Queue() {}
}
