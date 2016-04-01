package com.mdorst.container.list;

import java.util.Objects;

public class LinkedList<E> implements Iterable<E> {
    protected Node<E> head;
    protected int size;

    public LinkedList() {
        head = new Node<>();
        head.next = head.prev = head;
    }

    public LinkedList(LinkedList<E> c) {
        this();
        for(E e : c) {
            add(e);
        }
    }

    public E get(int index) {
        Node<E> n = head.next;
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return n.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (Node<E> n = head.next; n != head; n = n.next) {
            if (Objects.equals(o, n.data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>(head);
    }

    /**
     * Appends the specified element to the end of the list
     *
     * @param e the element to be appended
     * @return {@code true}
     */
    public boolean add(E e) {
        Node<E> n = new Node<>(e);
        head.prev.next = n;
        n.prev = head.prev;
        head.prev = n;
        n.next = head;
        size++;
        return true;
    }

    /**
     * Removes a single instance of the specified element from the list
     * if it is present.
     *
     * @param o the element to be removed, if present
     * @return {@code true} if an element was removed
     */
    public boolean remove(Object o) {
        for (Node<E> n = head.next; n != head; n = n.next) {
            /**
             * If o == null, checks for null elements.
             * Otherwise, checks for o.equals(element)
             */
            if (o == null ? n.data == null : o.equals(n.data)) {
                n.delete();
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all instances of the specified element from the list.
     *
     * @param o the element to be removed
     * @return {@code true} if one or more elements were removed
     */
    public boolean removeAll(Object o) {
        boolean modified = false;
        if (remove(o)) modified = true;
        while (remove(o));
        return modified;
    }

    /**
     * Removes all of the elements from this collection.
     * The collection will be empty after this method returns.
     */
    public void clear() {
        head.next = head;
        head.prev = head;
    }

    @Override
    public String toString() {
        String s = "[";
        for (Node<E> n = head.next; n != head; n = n.next) {
            if (!Objects.equals(s, "[")) s += ", ";
            s += n.data.toString();
        }
        return s + "]";
    }
}
