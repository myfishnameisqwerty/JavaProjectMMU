package com.hit.algorithm;

public class SecondChanceAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V>{

	private SecondChanceStructure<K, V> cache = null;
	
	public SecondChanceAlgoCacheImpl(int capacity) 
	{
		super(capacity);
		cache = new SecondChanceStructure<K,V>(capacity);
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
		V replacementVal = cache.AddElement(key, value);
	 	return replacementVal;
	}

	@Override
	public void removeElement(K key) 
	{
		if(cache.containsKey(key))
			cache.removeElement(key);
	}

}