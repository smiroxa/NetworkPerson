package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Person;
import org.apache.log4j.Logger;

//SQL
//drop table person;
//CREATE TABLE persons(ID int, LNAME varchar(255), FNAME varchar(255), AGE int);
//SELECT ID, LNAME, FNAME, AGE FROM persons
//INSERT INTO persons (ID, FNAME, LNAME, AGE) VALUES (2, 'Alena', 'Uglenko', 26);
public class H2Impl implements DataStorageInterface
{
	private static final Logger log = Logger.getLogger( H2Impl.class );
	private Statement stmt;
	
	public H2Impl()
	{
		try 
		{
			Class.forName("org.h2.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			log.info("Connected to H2 database, ready executing query..");
			stmt = con.createStatement();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> pp = new ArrayList<Person>();
		try
		{
			ResultSet rs = stmt.executeQuery("SELECT ID, FNAME, LNAME, AGE FROM persons");
			while (rs.next())
			{
				pp.add(new Person(rs.getInt(1), rs.getString("FNAME"),
						rs.getString("LNAME"), rs.getInt(4)));
			}
			rs.close();
			
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for read all persons");
			e.printStackTrace();
		}
		return pp;
	}
	
	@Override
	public void create(Person p) 
	{
		if(hasId(p))
		{
			return;
		}
		try 
		{
			stmt.executeUpdate("INSERT INTO persons (ID, FNAME, LNAME, AGE) values (\'" + p.id + "\',\'" + p.fname + "\',\'" + p.lname + "\',\'" + p.age + "\')");
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for create new person");
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Person p) 
	{
		if(!hasId(p))
		{
			create(p);
			return;
		}
		
		try 
		{
			stmt.executeUpdate("UPDATE persons SET FNAME=\'" + p.fname + "\', LNAME=\'" + p.lname + "\', AGE=\'" + p.age +"\' WHERE ID=\'" + p.id + "\'");
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for update person");
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Person p) 
	{
		try 
		{
			stmt.execute("DELETE FROM persons WHERE ID=\'" + p.id + "\'");
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for delete person");
			e.printStackTrace();
		}
	}
	
	private boolean hasId (Person p)
	{
		try 
		{
			ResultSet rs = stmt.executeQuery("SELECT ID FROM persons");
			while (rs.next())
			{
				if(rs.getInt(1) == p.id)
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for hasId");
			e.printStackTrace();
		}
		return false;
	}
}
