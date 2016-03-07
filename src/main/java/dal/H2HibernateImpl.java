package dal;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Person;


public class H2HibernateImpl implements DataStorageInterface
{

	private static SessionFactory sessionFactory = null;
	private static Session session = null;
	private static final Logger log = Logger.getLogger( H2HibernateImpl.class.getName() );

	public H2HibernateImpl()
	{
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		session = sessionFactory.openSession();
	}

	public void close()
	{
		session.close();
		sessionFactory.close();
	}

	@Override
	public ArrayList<Person> read()
	{
		session.beginTransaction();
		Query query = session.createQuery("from Person");
		List<Person> pp  = query.list();
		session.getTransaction().commit();
		return (ArrayList<Person>) pp;
	}

	@Override
	public void create(Person p)
	{
		if(hasId(p))
		{
			log.warn("======== ID IS ALREADY EXISTS! =======");
		}
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	@Override
	public void update(Person p)
	{
		if(!hasId(p))
		{
			create(p);
		}
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
	}

	@Override
	public void delete(Person p)
	{
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
	}

	public boolean hasId(Person p)
	{
		session.beginTransaction();
		Query query = session.createQuery("from Person");
		List<Person> pp  = query.list();
		ListIterator<Person> iter = pp.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().id == p.id)
			{
				session.getTransaction().commit();
				return true;
			}
		}
		session.getTransaction().commit();
		return false;
	}
}
