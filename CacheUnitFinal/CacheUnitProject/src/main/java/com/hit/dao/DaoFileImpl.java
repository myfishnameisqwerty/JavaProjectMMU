package main.java.com.hit.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;
import java.util.Map;

import main.java.com.hit.dm.DataModel;



public class DaoFileImpl<T> extends Object implements IDao<java.lang.Long, DataModel<T>> {
	
	private String filePath = "";
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private HashMap<Long, DataModel<T>> tempHashMap = null;

	public DaoFileImpl(String filePath) {
		this.filePath = filePath;
		tempHashMap = new HashMap<>();
	}

	@Override
	public void delete(DataModel<T> entity) {
		inputUpdateMap();

		for (Map.Entry<Long, DataModel<T>> item : tempHashMap.entrySet())
			if (item.getKey() == entity.getDataModelId())
				this.save(new DataModel<T>(entity.getDataModelId(),null));

		outputUpdateMap();
	}
	
	@SuppressWarnings("unchecked")
	private void inputUpdateMap() {
		try {
			File file = new File(filePath);

			if (file.exists() == false)
				file.createNewFile();

			input = new ObjectInputStream(new FileInputStream(file));

			tempHashMap.clear();
			tempHashMap = (HashMap<Long, DataModel<T>>) input.readObject();

		} catch (EOFException e) {

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void outputUpdateMap() {
		try {
			File file = new File(filePath);

			output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(tempHashMap);

			output.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public DataModel<T> find(Long id) {

		DataModel<T> itemToReturn = null;

		inputUpdateMap();

		for (Map.Entry<Long, DataModel<T>> item : tempHashMap.entrySet())
			if (item.getKey().longValue() == id.longValue()) {
				itemToReturn = item.getValue();
				break;
			}

		return itemToReturn;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void save(DataModel<T> entity) {
		inputUpdateMap();

		tempHashMap.put(entity.getDataModelId(), entity);

		outputUpdateMap();
	}
}
