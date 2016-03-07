package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import model.Person;

public class MySQLImpl implements DataStorageInterface
{

	private static final Logger log = Logger.getLogger( H2Impl.class.getName() );
	private Statement stmt;
	private ResultSet rs;
	
	public MySQLImpl()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false","root","");
			log.info("Connected to MySQL database, ready executing query..");
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
			rs = stmt.executeQuery("SELECT ID, FNAME, LNAME, AGE FROM persons");
			while (rs.next())
			{
				pp.add(new Person(rs.getInt(1), rs.getString("FNAME"),
						rs.getString("LNAME"), rs.getInt(4)));
			}
			rs.close();
			
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for read all persons, reason " + e.getMessage());
			e.printStackTrace();
		}
		
		return pp;
	}
	
	@Override
	public void create(Person p) 
	{
		if(!hasId(p.id))
		{
			try 
			{
				stmt.executeUpdate("INSERT INTO persons (ID, FNAME, LNAME, AGE) values (\'" + p.id + "\',\'" + p.fname + "\',\'" + p.lname + "\',\'" + p.age + "\')");
			} 
			catch (SQLException e) 
			{
				log.error("Error executing sql query for create new person, reason " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void update(Person p) 
	{
		if(!hasId(p.id))
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
			log.error("Error executing sql query for update person, reason " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Person p) 
	{
		try 
		{
			stmt.execute("DELETE FROM persons WHERE id=\'" + p.id + "\'");
		} 
		catch (SQLException e) 
		{
			log.error("Error executing sql query for delete person, reason " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private boolean hasId (int id)
	{
		try 
		{
			rs = stmt.executeQuery("SELECT id FROM persons");
			while (rs.next())
			{
				if(rs.getInt(1) == id)
				{
					return true;
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

}
