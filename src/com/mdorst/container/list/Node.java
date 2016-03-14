package com.mdorst.container.list;

/*
 * E : Generic
 *
 * Node
 * + data : E
 * + next : Node
 * + prev : Node
 * + Node()
 * + Node(E)
 * + delete()
 */

/**
 * This class provides the facility to implement a linked list.
 * <p>
 * It holds a generic data member, as well as two references to other {@code Node}'s,
 * which serve as forward and backward references to the adjacent links in the list.
 *
 * @param <E> the type of the elements held in the linked list
 */
class Node<E> {
    public E data;
    public Node<E> next;
    public Node<E> prev;

    public Node() {}
    public Node(E obj) {
        data = obj;
    }

    /**
     * Deletes this node, reorienting the {@code next} and {@code prev} nodes
     * to reference each other, skipping over this one
     */
    public void delete() {
        prev.next = next;
        next.prev = prev;
    }
}
