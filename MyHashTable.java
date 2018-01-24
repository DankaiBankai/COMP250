package hashMap;

import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/**
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		buckets = new ArrayList< HashLinkedList<K,V> >();
		this.numBuckets = numBuckets;
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new HashLinkedList<K,V>());
		}

		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {
		return  Math.abs( key.hashCode() ) % numBuckets ;
		
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE
		int hashValue = hashFunction(key);
		HashLinkedList<K,V> hList = this.buckets.get(hashValue);
		
		if (this.containsKey(key)) {
			V oldValue = hList.getListNode(key).getValue();
			System.out.println(oldValue);
			hList.getListNode(key).setValue(value);
			return oldValue;
			
		}else {
			hList.add(key, value);
			this.entryCount++;
			if((((double)entryCount) / numBuckets) > MAX_LOAD_FACTOR) {
				this.rehash();
			}
		}		
		//  ADD YOUR CODE ABOVE HERE
		return null;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		int hashValue = hashFunction(key);
		HashLinkedList<K,V> hList = this.buckets.get(hashValue);
		
		if(hList.getListNode(key) != null) { // not redundant b/c o/w it would be null.getValue() => null pointer exception
			return hList.getListNode(key).getValue();
		}

		//  ADD YOUR CODE ABOVE HERE

		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE
		int hashValue = hashFunction(key);
		HashLinkedList<K,V> hList = this.buckets.get(hashValue);
		
		if(hList.getListNode(key) != null) { // not redundant b/c o/w it would be null.getValue() => null pointer exception
			return hList.remove(key).getValue();
		}

		//  ADD  YOUR CODE ABOVE HERE

		return(null);
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
		int oldNumBuckets = this.numBuckets;
		this.numBuckets = numBuckets *2; 
		ArrayList<K> keyList = this.keys();
		ArrayList<V> valueList = this.values();
		this.clear();
		
		for (int i = oldNumBuckets; i < numBuckets; i++) {
			this.buckets.add(new HashLinkedList<K,V>());
		}
		
		for (int i = 0; i < keyList.size(); i++) {
			this.put(keyList.get(i), valueList.get(i));
		}
		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE BELOW HERE

		Iterator<HashNode<K,V> > hIter = new HashIterator();
		while (hIter.hasNext()) {
			listKeys.add(hIter.next().getKey());
		}
		return listKeys;
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE
		Iterator<HashNode<K,V> > hIter = new HashIterator();
		while (hIter.hasNext()) {
			listValues.add(hIter.next().getValue());
		}
		return listValues;
		//   ADD YOUR CODE ABOVE HERE
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;

		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{
// ADD SIZE COUNTER!!!!
			//  ADD YOUR CODE BELOW HERE
			allEntries = new HashLinkedList<K,V>();
			for (HashLinkedList<K,V> element : buckets) {
				if (element.getHead() != null) {
					HashNode<K,V> tmpNode = element.getHead();
					if(tmpNode.getKey() != null) {
						allEntries.add(tmpNode.getKey(), tmpNode.getValue());
						while(tmpNode.next != null) {
							tmpNode = tmpNode.next;
							allEntries.add(tmpNode.getKey(), tmpNode.getValue());
							
						}
					}
					
				}
				
			}

			//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
