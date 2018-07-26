package main.java.com.hit.dm;

public class DataModel<T> extends java.lang.Object implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;

	private T content;
	
	private Long id;

	public DataModel(Long id, T content) 
	{
		this.content = content;
		this.id = id;
	}
	
	public Long getDataModelId() 
	{
		return this.id;
	}
	
	public void setDataModelId(java.lang.Long id)
	{
		this.id = id;
	}
	
	public T getContent() 
	{
		return content;
	}

	public void setContent(T content) 
	{
		this.content = content;
	}
	
	public boolean equals(Object obj) 
	{
		return this.equals(obj);
	}
	
	public int hashCode()
	{
		return this.hashCode();
	}
	
	@Override
	public String toString()
	{
		String strToReturn = this.getDataModelId().toString() +" " + this.getContent().toString();
		return strToReturn;
	}
	
}
