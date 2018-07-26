package main.java.com.hit.services;

import main.java.com.hit.dm.DataModel;

public class CacheUnitController<T> extends Object {

	private CacheUnitService<T> cacheUnitService = null;

	public CacheUnitService<T> getCacheUnitService() {
		return cacheUnitService;
	}

	public CacheUnitController() {
		cacheUnitService = new CacheUnitService<T>();
	}

	public boolean delete(DataModel<T>[] dataModels) {
		return cacheUnitService.delete(dataModels);
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		return cacheUnitService.get(dataModels);
	}

	public boolean update(DataModel<T>[] dataModels) {
		return cacheUnitService.update(dataModels);
	}
}
