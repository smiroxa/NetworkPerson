package dal;

import java.util.ArrayList;
import com.mongodb.*;
import model.Person;
import org.apache.log4j.Logger;
//http://www.mkyong.com/mongodb/java-mongodb-hello-world-example/
//http://mongodb.github.io/mongo-java-driver/2.13/getting-started/quick-tour/

public class MongoDBImpl implements DataStorageInterface
{
	MongoClient mongoClient = null;
	DB db = null;
	DBCollection collection = null;
	private static final Logger log = Logger.getLogger( MongoDBImpl.class.getName() );

	public MongoDBImpl()
	{
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("mcpp");
		collection = db.getCollection("persons");
	}

	@Override
	public ArrayList<Person> read() 
	{
		DBCursor cursor = collection.find();
		ArrayList<Person> persons =  new ArrayList<>();
		try
		{
			while (cursor.hasNext())
			{
				DBObject dbObject = cursor.next();
				System.out.println(dbObject);
				persons.add(
						new Person(
								(int) dbObject.get("id"),
								(String) dbObject.get("fname"),
								(String) dbObject.get("lname"),
								(int) dbObject.get("age")
						)
				);
			}
		}
		finally
		{
			cursor.close();
		}
		return persons;
	}

	@Override
	public void create(Person model) 
	{
		BasicDBObject document = new BasicDBObject();
		document.put("id", model.id);
		document.put("fname", model.fname);
		document.put("lname", model.lname);
		document.put("age", model.age);
		collection.insert(document);
	}

	@Override
	public void update(Person p) 
	{
		BasicDBObject query = new BasicDBObject();
		query.put("id", p.getId());

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("fname", p.getFname());
		newDocument.put("lname", p.getLname());
		newDocument.put("age",   p.getAge());

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		collection.update(query, updateObj);

	}

	@Override
	public void delete(Person p) 
	{
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", p.id);
		collection.remove(searchQuery);
	}
}
