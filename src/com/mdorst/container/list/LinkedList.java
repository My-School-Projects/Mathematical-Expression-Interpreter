package com.mdorst.container.list;


import java.lang.reflect.Array;
import java.util.*;

public class LinkedList<E> implements Collection<E> {
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<E> n = head.next; n != head; n = n.next) {
            if (Objects.equals(o, n.data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this collection contains all of the elements in the specified collection.
     *
     * @param c collection to be checked for containment in this collection
     * @return {@code true} if this collection contains all of the elements in the specified collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new Iterator<>(head);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> n = head.next; n != head; n = n.next)
            result[i++] = n.data;
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Node<E> n = head.next; n != head; n = n.next) {
            a[i++] = (T) n.data;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Appends the specified element to the end of the list
     *
     * @param e the element to be appended
     * @return {@code true}
     */
    @Override
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
     * Appends all of the elements in the specified collection to the list
     *
     * @param c the collection to be appended
     * @return {@code true} if {@code c} is non-empty
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) add(e);
        return !c.isEmpty();
    }

    /**
     * Removes a single instance of the specified element from the list
     * if it is present.
     *
     * @param o the element to be removed, if present
     * @return {@code true} if an element was removed as a result of this call
     */
    @Override
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
     * Removes all elements that are also contained in the specified collection.
     * After this call returns, this collection will contain no elements
     * in common with the specified collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return {@code true} if this collection changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            for (Node<E> n = head.next; n != head; n = n.next) {
                /**
                 * If o == null, checks for null elements.
                 * Otherwise, checks for o.equals(element)
                 */
                if (o == null ? n.data == null : o.equals(n.data)) {
                    n.delete();
                    changed = true;
                }
            }
        }
        return changed;
    }

    /**
     * Retains only the elements that are contained in the specified collection.
     * In other words, removes all elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained
     * @return {@code true} if this collection changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (Node<E> n = head.next; n != head; n = n.next) {
            boolean found = false;
            for (Object o : c) {
                if (o == null ? n.data == null : o.equals(n.data)) {
                    found = true;
                }
            }
            if (!found) {
                n.delete();
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Removes all of the elements from this collection.
     * The collection will be empty after this method returns.
     */
    @Override
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
