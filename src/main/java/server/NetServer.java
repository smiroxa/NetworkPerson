package server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.Timer;

import dal.DataStorageInterface;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import dal.H2Impl;
import model.Person;

public class NetServer 
{
	private static final Logger log = Logger.getLogger( NetServer.class.getName() );
	ArrayList<NetClient> clients = null;
	
	public NetServer() throws IOException
	{
		ServerSocket ss = new ServerSocket(7777);
		log.info("[Server] Server started");
		clients = new ArrayList<NetClient>();
		Timer tm = new Timer(50, new ActionRead());
		tm.start();
		
		while(true)
		{
			Socket cs = ss.accept();
			log.info("[Server] Client connected");
			clients.add(new NetClient(cs));
		}
	}
	
	class ActionRead implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			try 
			{
				ListIterator<NetClient> litr = clients.listIterator();
				
				while(litr.hasNext())
				{
					NetClient nc = litr.next();
					if(nc.in.available() > 0)
						{
							String str = nc.in.readUTF();
							log.info("[Server] in: " + str);
							String cmd = str.substring(0, str.indexOf(":"));
							String inf = str.substring(str.indexOf(":")+1);
							
							//create appropriate string for different cases
							log.info("[Server] cmd: " + cmd);
							switch(cmd)
							{
								case "Create": 
									nc.db.create(nc.gs.fromJson(inf, Person.class));
									break;
								case "Read": 
									ArrayList<Person> pp;
									pp = nc.db.read();
									log.info("[Server] sent to client " + nc.gs.toJson(pp));
									nc.out.writeUTF(nc.gs.toJson(pp));
									break;
								case "Update" :
									nc.db.update(nc.gs.fromJson(inf, Person.class));
									break;
								case "Delete" :
									nc.db.delete(nc.gs.fromJson(inf, Person.class));
									break;
							}
						}
				}
			} 
			catch (IOException ee) 
			{
				ee.printStackTrace();
			}
		}
	}
	
	class NetClient
	{
		Socket cs = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		Gson gs;
		DataStorageInterface db;
		
		public NetClient(Socket cs) throws IOException
		{
			this.cs = cs;
			out = new DataOutputStream(cs.getOutputStream());
			in = new DataInputStream(cs.getInputStream());
			gs = new Gson();
			db = new H2Impl();
		}
	}
}
