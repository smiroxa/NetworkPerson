package dal;

import java.util.ArrayList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.Person;

public class MongoDBImpl implements DataStorageInterface
{
	MongoClient mongoClient = null;
	DB db = null;
	DBCollection cc = null;

	public MongoDBImpl()
	{
		mongoClient = new MongoClient( "localhost" , 27017 );
		db = mongoClient.getDB( "mcpp" );
		cc = db.getCollection("Person");
	}

	@Override
	public ArrayList<Person> read() 
	{
		return new ArrayList<Person>();
	}

	@Override
	public void create(Person model) 
	{
		BasicDBObject document = new BasicDBObject();
		document.put("id", model.id);
		document.put("fname", model.fname);
		document.put("lname", model.lname);
		document.put("age", model.age);
		cc.insert(document);
	}

	@Override
	public void update(Person p) 
	{

	}

	@Override
	public void delete(Person p) 
	{

	}
}
