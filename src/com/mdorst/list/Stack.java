package com.mdorst.container.list;

/*
 * Stack is a BasicList
 *
 * T : Generic
 *
 * Stack
 * + push(T)
 * + pop() : T
 * + top() : T
 */

/**
 * This class extends BasicList, and provides methods to support
 * a LIFO stack pattern.
 *
 * @param <E> the type of the elements held in this collection
 */
public class Stack<E> extends LinkedList<E> {
    /**
     * Adds an element to the top of the stack
     *
     * @param obj the element to be added
     */
    public void push(E obj) {
        super.add(obj);
    }

    /**
     * Removes an element from the top of the stack, returning it to the caller
     *
     * @return the removed element
     */
    public E pop() {
        E value = head.prev.data;
        head.prev.delete();
        if (size > 0) size--;
        return value;
    }

    /**
     * Peeks at the element at the top of the stack
     *
     * @return the element at the top of the stack
     */
    public E top() {
        return head.prev.data;
    }

    public Stack(LinkedList<E> other) {
        super(other);
    }

    public Stack() {}
}
