package main.java.com.hit.dao;

public interface IDao<ID extends java.io.Serializable,T> 
{
	public void delete(T entity) throws java.lang.IllegalArgumentException;
	
	public T find(ID id) throws java.lang.IllegalArgumentException;
	
	public void save (T entity);
}