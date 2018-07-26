package com.hit.algorithm;

import java.util.HashMap;
import java.util.Map;

public class SecondChanceStructure<K,V> {

	private HashMap<K,V> cache = null;
	
	private HashMap<K,Boolean> flagsHashMap = null;
	
	private int capacity;
	
	public SecondChanceStructure(int capacity)
	{
		this.capacity = capacity;
		cache = new HashMap<K,V>();
		flagsHashMap = new HashMap<K,Boolean>();
	}
	
	private boolean IsFull()
	{
		return (cache.size() == capacity);
	}
	
	public V AddElement(K key, V value)
	{
		V valueToReturn = null;
		
		if (IsFull() == true)
		{
			 K keyToRemove = clearSpaceInCache();
			 valueToReturn = cache.remove(keyToRemove);
			 flagsHashMap.remove(keyToRemove);
		}
		
		cache.put(key, value);
		flagsHashMap.put(key, false);
		
		return valueToReturn;
	}
	
	private K clearSpaceInCache()
	{
		K keyToReturn = null;
		
		for (Map.Entry<K, Boolean> item : flagsHashMap.entrySet()) 
		{
			if(item.getValue() == false)
			{
				keyToReturn = item.getKey();
				break;
			}
			else
			{
				item.setValue(false);
			}
		}
		
		if(keyToReturn == null)
		{
			keyToReturn = clearSpaceInCache();
		}
		
		return keyToReturn;
	}
	
	public void removeElement(K key)
	{
		cache.remove(key);
	}
	
	public boolean containsKey(K key)
	{
		return cache.containsKey(key);
	}
	
	public V getValue(K key)
	{
		flagsHashMap.put(key, true);
		
		return cache.get(key);
	}
}
