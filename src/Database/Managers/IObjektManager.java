package Managers;

import objectTypes.Event;



public interface IObjektManager<ObjectType> {
	
	public Integer insertObject(ObjectType o);
	public void updateObject(ObjectType o);
	public void deleteObject(ObjectType o);
	public ObjectType getObject (Object primaryKey);
	public void deleteObject(Integer id);
}
