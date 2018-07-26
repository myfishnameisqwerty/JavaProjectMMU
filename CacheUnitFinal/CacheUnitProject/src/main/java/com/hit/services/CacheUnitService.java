package main.java.com.hit.services;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.MRUAlgoCacheImpl;
import com.hit.algorithm.RandomAlgoCacheImpl;
import com.hit.algorithm.SecondChanceAlgoCacheImpl;

import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

public class CacheUnitService<T> extends Object {

	private CacheUnit<T> cacheUnit = null;

	private int capacity = 32;
	
	private String algorithm = "LRU";
	
	private int swaps = 0;
	
	public CacheUnit<T> getCacheUnit() {
		return cacheUnit;
	}
	
	public int getSwaps()
	{
		return swaps;
	}

	private IAlgoCache<Long, DataModel<T>> algo = null;

	public CacheUnitService() {
		IDao<Long, DataModel<T>> memoryUnit = new DaoFileImpl<T>("example.txt");
		InitAlgorithm();
		
		cacheUnit = new CacheUnit<T>(algo, memoryUnit);
	}
	
	private void InitAlgorithm()
	{
		switch(algorithm)
		{
		case "LRU":
			algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(capacity); 
			break;
		case "MRU" :
			algo = new MRUAlgoCacheImpl<Long, DataModel<T>>(capacity);
			break;
		case "SECOND_CHANCE":
			algo = new SecondChanceAlgoCacheImpl<Long, DataModel<T>>(capacity);
			break;
		case "RANDOM":
			algo = new RandomAlgoCacheImpl<Long, DataModel<T>>(capacity);
			break;
		}
	}

	public boolean delete(DataModel<T>[] dataModels) {
		boolean checkDelete = true;
		try {
			for (DataModel<T> dataModelItem : dataModels) {
				cacheUnit.getDataObject().delete(dataModelItem);
			}
		} catch (Exception e) {
			checkDelete = false;
			e.printStackTrace();
		}

		return checkDelete;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {

		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++)
			ids[i] = dataModels[i].getDataModelId();

		try {
			cacheUnit.getDataModels(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataModels;

	}

	public boolean update(DataModel<T>[] dataModels) {

		boolean checkUpdate = true;

		try {
			for (DataModel<T> dataModelItem : dataModels)
				
				if ((cacheUnit.getAlgoCache().putElement(dataModelItem.getDataModelId(), dataModelItem)) != null)
					swaps++;
				
		} catch (Exception e) {
			checkUpdate = false;
			e.printStackTrace();
		}
		return checkUpdate;
	}
	
	public String getAlgorithm()
	{
		return algorithm;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
}
