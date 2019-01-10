/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Queue;

import LinkedList.SinglyLinkedList;

/**
 *Realization of FIFO queue as an adaption of a SinglyLinkedList.
 * @author MIPC
 * @param <E> any type.
 */
public class LinkedQueue<E> implements Queue<E> {
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedQueue(){}
    @Override
    public int size(){return list.size();}
    @Override
    public boolean isEmpty(){return list.isEmpty();}
    @Override
    public void enqueue(E element){list.addLast(element);}
    @Override
    public E first(){return list.first();}
    @Override
    public E dequeue(){return list.removeFirst();}
}
