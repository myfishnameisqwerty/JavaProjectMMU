package com.hit.algorithm;

import java.util.LinkedList;

public class LRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V>{

	private PriorityHashMap<K,V> cache = null;
	
	public LRUAlgoCacheImpl(int capacity) 
	{
		super(capacity);
		cache = new PriorityHashMap<K,V>(capacity); 
	}

	@Override
	public V getElement(K key) 
	{
		V valueToReturn = null;
		
		if(cache.containsKey(key))
			valueToReturn = cache.getValue(key);
		
		return valueToReturn;
	}

	@Override
	public V putElement(K key, V value) 
	{	
		V replacementVal = null;
		
		if (cache.isFull()) 
			replacementVal = clearSpaceForElement();
		
		cache.AddElement(key, value);
	 	return replacementVal;
	}
	
	public V clearSpaceForElement() 
	{
		K replacementKey = null;
		V replacementVal = null;
		LinkedList<K> priorityList =  cache.getPriorityList();
		
		replacementKey = priorityList.getLast();
		replacementVal = cache.removeElement(replacementKey);
		
		return replacementVal;
	}
	
	public void removeElement(K key) 
	{
		cache.removeElement(key);
	}
}