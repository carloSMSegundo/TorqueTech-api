package br.com.starter.infrastructure.services.utils.datastructures;

import lombok.Getter;

import java.util.*;

@Getter
public class LinkedList<T> implements Queue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(T data) { // Adiciona ao final da fila
        add(data);
        return true;
    }

    @Override
    public T poll() { // Remove e retorna o primeiro elemento da fila
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T peek() { // Retorna o primeiro elemento sem remover
        if (isEmpty()) {
            return null;
        }
        return head.getData();
    }

    // Implementação dos métodos restantes da Queue<T>
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.getData());
            current = current.getNext();
        }
        return list;
    }


    @Override
    public boolean remove(Object data) {
        if (head == null) {
            return false;
        }
        if (head.getData().equals(data)) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }
        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                if (current.getNext() == null) {
                    tail = current;
                }
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("A lista está vazia");
        }
        T data = head.getData();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }

    // Métodos não utilizados na fila, apenas lançam exceções
    @Override
    public T remove() { throw new UnsupportedOperationException(); }

    @Override
    public T element() { throw new UnsupportedOperationException(); }

    @Override
    public boolean contains(Object o) { throw new UnsupportedOperationException(); }

    @Override
    public Object[] toArray() { throw new UnsupportedOperationException(); }

    @Override
    public <T1> T1[] toArray(T1[] a) { throw new UnsupportedOperationException(); }

    @Override
    public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean addAll(Collection<? extends T> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
}
