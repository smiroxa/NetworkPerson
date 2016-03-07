package dal;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import org.apache.log4j.Logger;

import model.Person;


public class XMLImpl implements DataStorageInterface
{
	File file = new File("/Users/cAp/IdeaProjects/NetworkPerson/src/main/resources/persons.xml");
	private static final Logger log = Logger.getLogger( XMLImpl.class.getName() );
	
	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> ret = new ArrayList<Person>();
		try 
		{
			if(file.exists() && file.length() > 0)
			{
				XMLDecoder de = new XMLDecoder(new FileInputStream(file));
				ret =  (ArrayList<Person>) de.readObject();
				de.close();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void create(Person x) 
	{
		ArrayList<Person> p = new ArrayList<Person>();
		try 
		{
			if(file.exists() && file.length() > 0)
			{
				XMLDecoder de = new XMLDecoder(new FileInputStream(file));
				p =  (ArrayList<Person>) de.readObject();
				de.close();
			}
			
			if(hasId(p, x))
			{
				log.error("Error executing sql query for create new person, reason - duplicate field ID");
				return;
			}
			
			p.add(x);
			
			FileWriter fw = new FileWriter(file);
			XMLEncoder en = new XMLEncoder(new FileOutputStream(file));
			en.writeObject(p);
			en.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(Person x) 
	{
		ArrayList<Person> p = new ArrayList<Person>();
		try 
		{
			if(file.exists() && file.length() > 0)
			{
				XMLDecoder de = new XMLDecoder(new FileInputStream(file));
				p =  (ArrayList<Person>) de.readObject();
				de.close();
			}
			
			if(!hasId(p,x))
			{
				create(x);
				return;
			}
			
			for(Person pers : p)
			{
				if(pers.id == x.id)
				{
					p.set(p.indexOf(pers), x);
				}
			}
			FileWriter fw = new FileWriter(file);
			XMLEncoder en = new XMLEncoder(new FileOutputStream(file));
			en.writeObject(p);
			en.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Person x) 
	{
		ArrayList<Person> p = new ArrayList<Person>();
		try 
		{
			if(file.exists() && file.length() > 0)
			{
				XMLDecoder de = new XMLDecoder(new FileInputStream(file));
				p =  (ArrayList<Person>) de.readObject();
				de.close();
			}
			
			ListIterator<Person> iter = p.listIterator();
			while(iter.hasNext())
			{
				if(iter.next().id == x.id)
				{
					iter.remove();
					break;
				}
			}
			
			FileWriter fw = new FileWriter(file);
			XMLEncoder en = new XMLEncoder(new FileOutputStream(file));
			en.writeObject(p);
			en.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	private boolean hasId(ArrayList<Person> pp, Person pers)
	{
		ListIterator<Person> iter = pp.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().id == pers.id)
			{
				return true;
			}
		}
		return false;
	}
}
