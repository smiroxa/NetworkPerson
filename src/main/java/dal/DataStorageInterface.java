package dal;
import java.util.ArrayList;

import model.Person;

public interface DataStorageInterface
{
	public ArrayList<Person>  read();
	public void create(Person p);
	public void update(Person p);
	public void delete(Person p);
}
