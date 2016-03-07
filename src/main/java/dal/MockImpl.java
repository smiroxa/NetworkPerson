package dal;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import model.Person;

public class MockImpl implements DataStorageInterface
{
	Map<Integer, Person> personTreeMap = new TreeMap<>();

	public MockImpl()
	{
		personTreeMap.put(1, new Person(1,"default","default",26));
		personTreeMap.put(2, new Person(2,"default","default",26));
		personTreeMap.put(3, new Person(3,"default","default",26));
		personTreeMap.put(4, new Person(4,"default","default",26));
		personTreeMap.put(5, new Person(5,"default","default",26));
	}

	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> temp = new ArrayList<>();
		for (Map.Entry e: personTreeMap.entrySet())
		{
			temp.add( (Person) e.getValue() );
		}
		return temp;
	}

	@Override
	public void create(Person p) 
	{
		personTreeMap.put(p.id, p);
	}

	@Override
	public void update(Person p) 
	{
		personTreeMap.put(p.id, p);
	}

	@Override
	public void delete(Person p) 
	{
		personTreeMap.remove(p.id);
	}
}
