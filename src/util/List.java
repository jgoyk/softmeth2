package util;

import clinic.Appointment;

import java.util.Iterator;

public class List<E>  {
    //implements Iterable<E>
    public static final int INITIAL_CAPACITY = 4;
    public static final int INCREASE_CAPACITY = 4;
    public static final int NOT_FOUND = -1;

    private E[] objects;
    private int size;
    public List() {
        objects = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    } //default constructor with an initial capacity of 4.
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + INCREASE_CAPACITY];
        for (int i = 0; i < size; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }
    /**
     * Checks if the list contains the specified object.
     *
     * @param e The object to check for.
     * @return true if the appointment is in the list, false otherwise.
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }
    /**
     * Adds a new object to the list.
     *
     * @param e The object to be added.
     */
    public void add(E e) {
        if (contains(e)) {
            return;
        }
        if (size == objects.length) {
            grow();
        }
        objects[size++] = e;
    }
    /**
     * Removes an object from the list.
     *
     * @param e The object to be removed.
     */
    public void remove(E e) {
        int index = find(e);
        if (index == NOT_FOUND) {
            return;
        }
        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        objects[--size] = null; // Decrement size and set the last element to null
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }

    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return objects[index];
    } //return the object at the index

    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            return;
        }
        objects[index] = e;
    } //put object e at the index

    public int indexOf(E e) {
        return find(e);
    } //return the index of the object or return -1

    private class ListIterator<E> implements Iterator<E> {
        private int currentIndex = 0;
        public boolean hasNext() {
            return currentIndex < size && objects[currentIndex] != null;
        }//return false if it’s empty or end of list
        public E next() {
            return (E) objects[currentIndex++];
        } //return the next object in the list
    }
}
