/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PriorityQueue;

import java.util.Comparator;

/**
 *
 * @author MIPC
 * @param <K> data type of key.
 * @param <V> data type of value.
 */
public class HeapAdaptablePriorityQueue<K,V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K,V>{
    //<editor-fold defaultstate="collapsed" desc="AdaptablePQEntry Class.">
    /**
     * Extension of the PQEntry to include location ind¿formation.
     * @param <K>
     * @param <V> 
     */
    protected static class AdaptablePQEntry<K,V> extends PQEntry<K, V>{
        /**
         * Entry's current index within the heap.
         */
        private int index;
        public AdaptablePQEntry(K key, V value, int j){
            super(key, value);
            index = j;
        }
        public int getIndex(){return index;}
        public void setIndex(int j){index = j;}
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructors methods.">
    /**
     * Creates an empty adaptable priority queue using natural ordering of keys.
     */
    public HeapAdaptablePriorityQueue(){super();}
    /**
     * Creates an empty adaptable priority queue using the given comparator.
     * @param comp 
     */
    public HeapAdaptablePriorityQueue(Comparator<K> comp){ super(comp);}
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Protected utilities.">
    /**
     * Validates an entry to ensure it is location-aware.
     * @param entry
     * @return
     * @throws IllegalArgumentException 
     */
    protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException{
        if(!(entry instanceof AdaptablePQEntry))
            throw new IllegalArgumentException("Invalid entry");
        AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K, V>) entry;
        int j = locator.getIndex();
        if(j >= heap.size() || heap.get(j) != locator)
            throw new IllegalArgumentException("Invalid entry.");
        return locator;
    }
    /**
     * Exchanges the entries at indices i and j of the array list.
     * @param i
     * @param j 
     */
    @Override
    protected void swap(int i, int j){
        super.swap(i, j);                               //perform the swap.
        ((AdaptablePQEntry)heap.get(i)).setIndex(i);    //reset entry's index.
        ((AdaptablePQEntry)heap.get(j)).setIndex(j);    //reset entry's index.
    }
    /**
     * Restores the heap property by moving the entry at index j upward/downward.
     * @param j 
     */
    protected void bubble(int j){
        if(j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
            upheap(j);
        else
            downheap(j);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Public utilities.">
    /**
     * Inserts a key-value pair and returns the entry created.
     * @param key
     * @param value
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public Entry<K,V> insert(K key, V value) throws  IllegalArgumentException{
        checkKey(key);
        Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }
    /**
     * Removes the given entry from the priority queue.
     * @param entry
     * @throws IllegalArgumentException 
     */
    @Override
    public void remove(Entry<K,V> entry) throws IllegalArgumentException{
        AdaptablePQEntry<K,V>  locator = validate(entry);
        int j = locator.getIndex();
        if(j == heap.size() - 1)
            heap.remove(heap.size() - 1);
        else{
            swap(j, heap.size() - 1);
            heap.remove(heap.size() - 1);
            bubble(j);
        }
    }
    /**
     * Replaces the key of an entry.
     * @param entry
     * @param key
     * @throws IllegalArgumentException 
     */
    @Override
    public void replaceKey(Entry<K,V> entry, K key) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        checkKey(key);
        locator.setKey(key);
        bubble(locator.getIndex());
    }
    /**
     * Replaces the value of an entry.
     * @param entry
     * @param value
     * @throws IllegalArgumentException 
     */
    @Override
    public void replaceValue(Entry<K,V> entry, V value) throws  IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        locator.setValue(value);        //method inherited from PQEntry.
    }
    //</editor-fold>
}
