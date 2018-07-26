package com.hit.algorithm;

import java.util.LinkedList;

public class MRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V>
{
	private PriorityHashMap<K,V> cache = null;
	
	public MRUAlgoCacheImpl(int capacity)
	{
		super(capacity);
		cache = new PriorityHashMap<K,V>(capacity); 
	}
	
	@Override
	public V putElement(K key, V value) 
	{	
		V replacementVal = null;
		if (cache.isFull()) 
		{
			replacementVal = clearSpaceForElement();
		}
		cache.AddElement(key, value);
	 	return replacementVal;
	}
	
	@Override
	public V getElement(K key) 
	{
		V valueToReturn = null;
		
		if(cache.containsKey(key))
		{
			valueToReturn = cache.getValue(key);
		}
		return valueToReturn;
	}

	@Override
	public void removeElement(K key) 
	{
		if(cache.containsKey(key))
			cache.removeElement(key);
	}
	
	public V clearSpaceForElement() {
		K replacementKey = null;
		V replacementVal = null;
		LinkedList<K> priorityList =  cache.getPriorityList();
		
		replacementKey = priorityList.getFirst();
		priorityList.remove(replacementKey);
		replacementVal = cache.removeElement(replacementKey);
		
		return replacementVal;
	}

}
