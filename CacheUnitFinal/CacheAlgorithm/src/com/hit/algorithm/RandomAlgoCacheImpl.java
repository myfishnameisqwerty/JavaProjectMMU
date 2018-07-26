package com.hit.algorithm;

public class RandomAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V>{
	
	private PriorityHashMap<K,V> cache = null;
	
	private int capacity;
	
	public RandomAlgoCacheImpl(int capacity)
	{
		super(capacity);
		cache = new PriorityHashMap<K, V>(capacity);
		this.capacity = capacity;
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
		V valueToReturn = null;
		
		if(cache.isFull())
			valueToReturn = clearSpaceForElement();
		
		cache.AddElement(key, value);
		return valueToReturn;
	}
	
	public V clearSpaceForElement()
	{
		V replacementValue = null;
		K replacementKey = null;
		java.util.Random rnd = new java.util.Random();
		int n = rnd.nextInt(capacity);
		
		replacementKey = cache.getPriorityList().get(n);
		replacementValue = cache.removeElement(replacementKey);
		cache.getPriorityList().addFirst(replacementKey);
		
		return replacementValue;
	}

	@Override
	public void removeElement(K key) 
	{
		if(cache.containsKey(key))
			cache.removeElement(key);
	}

}