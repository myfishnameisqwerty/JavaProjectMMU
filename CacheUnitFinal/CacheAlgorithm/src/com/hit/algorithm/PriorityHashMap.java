package com.hit.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

public class PriorityHashMap<K, V> {
	
	private HashMap<K, V> cache = null;
	
	private LinkedList<K> priorityList = null;
	
	private int capacity;
	
	public PriorityHashMap(int capacity) 
	{
		cache = new HashMap<K, V>(capacity);
		priorityList = new LinkedList<K>();
		this.capacity = capacity;
	}

	public boolean isFull()
	{
		return (cache.size() == capacity);
	}
	
	public void AddElement(K key, V value)
	{
		cache.put(key, value);
		priorityList.addFirst(key);
	}
	
	public boolean containsKey(K key)
	{
		return cache.containsKey(key);
	}
	
	public V getValue(K key)
	{	
		V valueToReturn = cache.get(key);
		
		priorityList.remove(key);
		priorityList.addFirst(key);
		
		return valueToReturn;
	}
	
	public V removeElement(K key) {
		priorityList.remove(key);
		return cache.remove(key);
	}
	
	public LinkedList<K> getPriorityList() {
		return priorityList;
	}
}
