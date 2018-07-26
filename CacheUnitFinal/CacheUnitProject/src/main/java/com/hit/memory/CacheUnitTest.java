package main.java.com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;

public class CacheUnitTest extends java.lang.Object
{
	private String filePath = "Example.txt";
	
	public CacheUnitTest() {}
	
	public void getDataModelsTest() {
		IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<Long,DataModel<String>>(3);

		IDao<Long, DataModel<String>> dao = new DaoFileImpl<>(filePath);

		CacheUnit<String> cacheUnit = new CacheUnit<>(algo, dao);
		
		try {
			algo.putElement((long) 1000, new DataModel<String>((long)1000,"first"));
			algo.putElement((long) 2000, new DataModel<String>((long)2000,"second"));
			algo.putElement((long) 3000, new DataModel<String>((long)3000,"Third"));
			algo.putElement((long) 4000, new DataModel<String>((long)4000,"fourth"));
			
			dao.save(new DataModel<String>((long)1000,"first"));
			dao.save(new DataModel<String>((long)2000,"second"));
			dao.save(new DataModel<String>((long)3000,"Third"));
			DataModel<String>[] array= cacheUnit.getDataModels(new Long[]{(long) 1000,(long) 2000});
			for (DataModel<String> dataModel : array) {
				System.out.println(dataModel.getContent());
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
