package main.java.com.hit.memory;

import java.io.IOException;

import com.hit.algorithm.IAlgoCache;

import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;

public class CacheUnit<T> {

	private IDao<Long, DataModel<T>> dataObject = null;

	private IAlgoCache<Long, DataModel<T>> algoCache = null;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
		dataObject = dao;
		algoCache = algo;
	}

	public IDao<Long, DataModel<T>> getDataObject() {
		return dataObject;
	}

	public void setDataObject(IDao<Long, DataModel<T>> dataObject) {
		this.dataObject = dataObject;
	}

	public IAlgoCache<Long, DataModel<T>> getAlgoCache() {
		return algoCache;
	}

	public void setAlgoCache(IAlgoCache<Long, DataModel<T>> algoCache) {
		this.algoCache = algoCache;
	}
	
	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(Long[] ids) throws ClassNotFoundException, IOException {
		DataModel<T>[] arrayToReturn = new DataModel[ids.length];
		int currentIndex = 0;

		for (Long currentID : ids) {
			DataModel<T> currentDataModel = algoCache.getElement(currentID);
			if (currentDataModel == null) // cache not contains
			{
				DataModel<T> foundDM = dataObject.find(currentID);
				if (foundDM != null) // data object contain
				{
					arrayToReturn[currentIndex] = foundDM; // insert to array of dms
					algoCache.putElement(currentID, arrayToReturn[currentIndex]); // insert to cache
					currentIndex++;
				}
			} else // cache contains
			{
				arrayToReturn[currentIndex] = currentDataModel;
				algoCache.putElement(currentID, arrayToReturn[currentIndex]); // insert to cache
				currentIndex++;
			}
		}

		return arrayToReturn; // return all the dms that were inserted to cache or already in cache
	}
}
