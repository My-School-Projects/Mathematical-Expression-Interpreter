package com.mdorst.container.list;

/*
 * Iterator
 * - node : Node
 *
 * + next() : T
 * + hasNext() : Boolean
 * # constructor(Node)
 */

/**
 * This class extends java.util.Iterator, and provides facilities
 * for iterating over a linked list.
 *
 * @param <E>
 */
class Iterator<E> implements java.util.Iterator<E> {
    private Node<E> node;
    private Node<E> head;

    /**
     * Move the iterator forward one element, and get that element
     *
     * @return the next element in the collection
     */
    @Override
    public E next() {
        return hasNext() ? (node = node.next).data : null;
    }

    /**
     * Check weather the iterator has reached the end of the collection
     *
     * @return {@code true} if the the iterator points to the end of the
     * collection, otherwise {@code false}
     */
    @Override
    public boolean hasNext() {
        return node.next != head;
    }

    /**
     * Remove the last element returned by {@code next()} from the collection
     */
    @Override
    public void remove() {
        node.delete();
    }

    /**
     * Create a new Iterator
     * <p>
     * This constructor expects it's parameter's {@code next} attribute to
     * reference the first element in the collection
     *
     * @param node a {@code Node} who's {@code next} attribute references
     *             the list's head node
     */
    protected Iterator(Node<E> node) {
        this.node = head = node;
    }
}
