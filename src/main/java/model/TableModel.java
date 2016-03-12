package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import dal.*;

public class TableModel extends AbstractTableModel
{
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger( TableModel.class );
	private final String[] colNameArr = {"Id", "FName", "LName", "Age"};
	private ArrayList<Person> persons = new ArrayList<>();
	private DataStorageInterface dataStorageInterface;
	
	public TableModel()
	{
//		this.dataStorageInterface = new StubImpl(); //Stub (костюмчик)
//		this.dataStorageInterface = new MockImpl();
//		this.dataStorageInterface = new H2Impl();
//		this.dataStorageInterface = new MySQLImpl();
//		this.dataStorageInterface = new XMLImpl();
//		this.dataStorageInterface = new JsonImpl();
//		this.dataStorageInterface = new H2HibernateImpl();
//			Hibernate
//			NetConnect
//			CSV
//			Yaml
//		this.dataStorageInterface = new MongoDBImpl();
		this.dataStorageInterface = new	RedisImpl();
//			Casandra ( column )
//			Neo4j ( graph )
		log.debug("create instance connection to DB");
	}

	@Override
	public String getColumnName(int col) 
	{
		return this.colNameArr[col];
	}
	@Override
	public int getColumnCount() 
	{
		return this.colNameArr.length;
	}

	@Override
	public int getRowCount()
	{
		return this.persons.size();
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		Object  o = null;
		Person person = persons.get(row);
		switch (col) 
		{
			case 0:
				o = person.id;
				break;
			case 1:
				o = person.fname;
				break;
			case 2:
				o = person.lname;
				break;
			case 3:
				o = person.age;
				break;
		}
		return o;
	}
	
	@Override
	public void setValueAt(Object val, int row, int col) 
	{
		Person p = persons.get(row);
		switch (col) 
		{
			case 0:
				p.id = Integer.parseInt((String) val);
				break;
			case 1:
				p.fname = (String) val;
				break;
			case 2:
				p.lname = (String) val;
				break;
			case 3:
				p.age = Integer.parseInt((String) val);
				break;
		}
		dataStorageInterface.update(p);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) 
	{
		return true;
	}

	public ArrayList<Person> getPersons()
	{
		return persons;
	}

	public void setPersons(ArrayList<Person> persons)
	{
		this.persons = persons;
	}
	
	public DataStorageInterface getCurrentDBImpl()
	{
		return this.dataStorageInterface;
	}

	public void setDb(DataStorageInterface db)
	{
		this.dataStorageInterface = db;
	}
}
