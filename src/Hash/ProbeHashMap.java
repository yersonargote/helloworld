/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hash;

import PriorityQueue.Entry;
import java.util.ArrayList;

/**
 * Concrete ProbeHashMap class that uses linear probing for collision resolution.
 * @author Goodrich
 * @param <K> data type of the key
 * @param <V> data type of the value
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
    private MapEntry<K,V>[] table;
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);
    public ProbeHashMap(){ super();}
    public ProbeHashMap(int cap){ super(cap);}
    public ProbeHashMap(int cap, int p){ super(cap, p);}
    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    protected void createTable(){
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }
    /**
     * Returns true location is either empty or the "defunct" sentinel.
     * @param j
     * @return 
     */
    private boolean isAvailable(int j){
        return (table[j] == null || table[j] == DEFUNCT);
    }
    /**
     * Returns index with key k, or -(a + 1) such that k could be added at index a.
     * @param h
     * @param k
     * @return 
     */
    private int findSlot(int h, K k){
        int avail = -1;                             // no slot available (thus far)
        int j = h;                                  // index while scanning table
        do{
            if(isAvailable(j)){                     // may be either empty or defunct
                if(avail == -1) avail = j;          // this is the ﬁrst available slot!
                if(table[j] == null) break;         // if empty, search fails immediately 
            }else if(table[j].getKey().equals(k))
                return j;                           // successful match
            j = (j + 1)% capacity;                  // keep looking (cyclically)
        }while(j != h);                             // stop if we return to the start
        return -(avail + 1);                        // search has failed
    }
    /**
     * Returns value associates with key k in bucket with hash value h, or else null.
     * @param h
     * @param k
     * @return 
     */
    @Override
    protected V bucketGet(int h, K k){
        int j = findSlot(h, k);
        if(j < 0) return null;      //no match found
        return table[j].getValue();
    }
    /**
     * Associates key k with value in bucket with hash value h; returns old value.
     * @param h
     * @param k
     * @param v
     * @return 
     */
    @Override
    protected V bucketPut(int h, K k,V v){
        int j = findSlot(h, k);
        if(j >= 0)                                  // this key has an existing entry
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);     // convert to proper index
        n++;
        return null;
    }
    /**
     * Removes entry having key k from bucket with hash h (if any).
     * @param h the hash value of the relevant bucket
     * @param k previous value associated with k (or null, if no such is empty)
     * @return 
     */
    @Override
    protected V bucketRemove(int h, K k){
        int j = findSlot(h, k);
        if(j < 0) return null;              // nothing to remove
        V answer = table[j].getValue();
        table[j] = DEFUNCT;                 // mark this slot as deactivated
        n--;
        return answer;
    }
    /**
     * Returns an iterable collection of all key-value entries of the map.
     * @return 
     */
    @Override
    public Iterable<Entry<K,V>> entrySet(){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int h = 0; h < capacity; h++)
            if(!isAvailable(h)) buffer.add(table[h]);
        return buffer;
    }
}
