package dal;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Person;

public class NetClientImpl implements DataStorageInterface
{
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private Gson gs;
	
	public NetClientImpl()
	{
		try 
		{
			Socket cs = new Socket("localhost",7777);
			out = new DataOutputStream(cs.getOutputStream());
			in = new DataInputStream(cs.getInputStream());
			gs = new Gson();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public ArrayList<Person> read() 
	{
		ArrayList<Person> pp = null;
		try 
		{
			out.writeUTF("Read:");
			out.flush();
			pp = gs.fromJson(in.readUTF(), new TypeToken<ArrayList<Person>>(){}.getType());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public void create(Person p) 
	{
		try 
		{
			out.writeUTF("Create:" + gs.toJson(p));
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

	@Override
	public void update(Person p) 
	{
		try 
		{
			out.writeUTF("Update:" + gs.toJson(p));
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Person p) 
	{
		try 
		{
			out.writeUTF("Delete:" + gs.toJson(p));
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
