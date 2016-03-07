package dal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Person;

public class JsonImpl implements DataStorageInterface
{
	File file = new File("/Users/cAp/IdeaProjects/NetworkPerson/src/main/resources/persons.json");
	
	private static final Logger log = Logger.getLogger( JsonImpl.class.getName() );
	
	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> persons = new ArrayList<Person>();
		
		if(file.exists() && file.length() > 0)
		{
			try 
			{
				Scanner sc = new Scanner(file);
				String str = sc.nextLine();
				Gson gs = new Gson();
				persons = gs.fromJson(str, (new TypeToken<ArrayList<Person>>(){}).getType());
			} 
			catch (FileNotFoundException e)
			{
				log.error("Error executing read persons from file persons.json, reason " + e.getMessage());
				e.printStackTrace();
			}
		}
		return persons;
	}

	@Override
	public void create(Person x) 
	{
		ArrayList<Person> p = new ArrayList<Person>();
		if(file.exists() && file.length() > 0)
		{
			try
			{
				Scanner sc = new Scanner(file);
				String str = sc.nextLine();
				Gson gs = new Gson();
				p = gs.fromJson(str, (new TypeToken<ArrayList<Person>>(){}).getType());
			}
			catch (FileNotFoundException e) 
			{
				log.error("Error executing create new person, reason " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		if(hasId(p, x))
		{
			return;
		}
		
		p.add(x);
		
		try 
		{
			Gson gs = new Gson();
			String str = gs.toJson(p);
			FileWriter fw = new FileWriter(file);
			fw.write(str);
			fw.flush();
			fw.close();
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
		
		if(file.exists() && file.length() > 0)
		{
			try
			{
				Scanner sc = new Scanner(file);
				String str = sc.nextLine();
				Gson gs = new Gson();
				p = gs.fromJson(str, (new TypeToken<ArrayList<Person>>(){}).getType());
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			if(!hasId(p,x))
			{
				create(x);
				return;
			}
		}
		
		for(Person pers : p)
		{
			if(pers.id == x.id)
			{
				p.set(p.indexOf(pers), x);
			}
		}
		
		try 
		{
			Gson gs = new Gson();
			String str = gs.toJson(p);
			FileWriter fw = new FileWriter(file);
			fw.write(str);
			fw.flush();
			fw.close();
		} 
		catch (IOException e) 
		{
			log.error("Error executing update person from file persons.json, reason " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Person x) 
	{
		ArrayList<Person> p = new ArrayList<Person>();
		
		if(file.exists() && file.length() > 0)
		{
			try
			{
				Scanner sc = new Scanner(file);
				String str = sc.nextLine();
				Gson gs = new Gson();
				p = gs.fromJson(str, (new TypeToken<ArrayList<Person>>(){}).getType());
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
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
		
		try 
		{
			Gson gs = new Gson();
			String str = gs.toJson(p);
			FileWriter fw = new FileWriter(file);
			fw.write(str);
			fw.flush();
			fw.close();
		} 
		catch (IOException e) 
		{
			log.error("Error executing delete person from file persons.json, reason " + e.getMessage());
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
