// **********************************************************************************
// Title: BestProduct
// Author: Carly Shearer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: MyHashMap.java
// Description: Program creates a HashMap using hashing and hash tables.
// **********************************************************************************
package com.example.bestproduct;
import java.util.*;
public class MyHashMap<K, V> {
    private final static int DEFAULT_INITIAL_CAPACITY = 4;
    private final static int MAXIMUM_CAPACITY = 1 << 30;
    private int capacity;
    private final static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;
    private float loadFactorThreshold;
    private int size = 0;
    LinkedList<MyHashMap.Entry<K,V>>[] table;

    //contstructors
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactorThreshold) {
        if (initialCapacity > MAXIMUM_CAPACITY){
            this.capacity = MAXIMUM_CAPACITY;
        }
        else{
            this.capacity = trimToPowerOf2(initialCapacity);
        }

        this.loadFactorThreshold = loadFactorThreshold;
        table = new LinkedList[capacity];
    }

    //inner class to create an entry with key and value
    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    //methods
    public void clear() {
        size = 0;
        removeEntries();
    }

    public boolean containsKey(K key) {
        if (get(key) != null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry: bucket){
                    if (entry.getValue().equals(value)){ //if the value exists in map
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //return set of all entries in map
    public java.util.Set<MyHashMap.Entry<K,V>> entrySet() {
        java.util.Set<MyHashMap.Entry<K, V>> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry: bucket){
                    set.add(entry); //add the entry to the set
                }
            }
        }
        return set;
    }

    //get a value from a key
    public V get(K key) {
        int bucketIndex = hash(key.hashCode()); //find index in hashtable using hashcode
        if (table[bucketIndex] != null) {
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry: bucket){
                if (entry.getKey().equals(key)){ //if it is the matching key
                    return entry.getValue(); //get the entry
                }
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //return set of all keys in map
    public java.util.Set<K> keySet() {
        java.util.Set<K> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry: bucket){
                    set.add(entry.getKey()); //add each key in hash table
                }
            }
        }
        return set;
    }

    //add entry to map
    public V put(K key, V value) {
        if (get(key) != null) { //if key in map
            int bucketIndex = hash(key.hashCode());
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry: bucket)
                if (entry.getKey().equals(key)) { //if it is the key
                    V oldValue = entry.getValue();
                    entry.value = value; //replace
                    return oldValue; //return old value
                }
        }

        //check load factor
        if (size >= capacity * loadFactorThreshold) {
            if (capacity == MAXIMUM_CAPACITY)
                throw new RuntimeException("Exceeding maximum capacity");

            rehash();
        }

        int bucketIndex = hash(key.hashCode());

        //if no bucket, create one from a linkedlist
        if (table[bucketIndex] == null) {
            table[bucketIndex] = new LinkedList<Entry<K, V>>();
        }

        //add new entry to hashtable
        table[bucketIndex].add(new MyHashMap.Entry<K, V>(key, value));

        size++; //increase size

        return value;
    }

    //remove an entry using key
    public void remove(K key) {
        int bucketIndex = hash(key.hashCode());

        //remove entry which matches with key
        if (table[bucketIndex] != null) {
            LinkedList<Entry<K, V>> bucket = table[bucketIndex];
            for (Entry<K, V> entry: bucket)
                if (entry.getKey().equals(key)) {
                    bucket.remove(entry); //remove the entry
                    size--; //decrease size
                    break;
                }
        }
    }

    //return size of map
    public int size() {
        return size;
    }

    //return a set of all values
    public java.util.Set<V> values() {
        java.util.Set<V> set = new java.util.HashSet<>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<K, V>> bucket = table[i];
                for (Entry<K, V> entry: bucket){
                    set.add(entry.getValue());
                }
            }
        }
        return set;
    }

    //hash function
    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    //ensure hashing equally distrubuted
    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    //ensure it is power of 2
    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }
        return capacity;
    }

    //remove entries from bucket
    private void removeEntries() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
    }

    //rehash map
    private void rehash() {
        java.util.Set<Entry<K, V>> set = entrySet(); //get entries
        capacity <<= 1; //double capacity
        table = new LinkedList[capacity]; //create new hash table
        size = 0; //set size to zero

        for (Entry<K, V> entry: set) {
            put(entry.getKey(), entry.getValue()); //store in new table
        }
    }

    //toString() method
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].size() > 0)
                for (Entry<K, V> entry: table[i])
                    builder.append(entry);
        }

        builder.append("]");
        return builder.toString();
    }
}
