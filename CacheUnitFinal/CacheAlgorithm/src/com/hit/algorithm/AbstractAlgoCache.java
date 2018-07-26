package com.hit.algorithm;


public abstract class AbstractAlgoCache<K,V> extends java.lang.Object implements IAlgoCache<K, V>{
		
		protected int capacity;
		
		public AbstractAlgoCache(int capacity) 
		{
			this.capacity = capacity;
		}
		
		public void SetCapacity(int capacity) 
		{
			this.capacity = capacity;
		}
		
		public int getCapacity(int capacity) 
		{
			return this.capacity;
		}		
}