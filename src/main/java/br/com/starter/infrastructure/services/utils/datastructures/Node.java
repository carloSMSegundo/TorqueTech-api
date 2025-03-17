package br.com.starter.infrastructure.services.utils.datastructures;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
