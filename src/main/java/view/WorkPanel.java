package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.Person;
import model.TableModel;

public class WorkPanel extends JPanel
{
	TableModel tableModel = null;
	public JTable tbl = null;
	
	public WorkPanel(TableModel tableModel)
	{
		this.tableModel = tableModel;
		setLayout(null);
		setBounds(370, 10, 170, 400);
		
		JButton create = new JButton("Create");
		create.setActionCommand("1");
		create.setBounds(0, 120, 100, 20);
		create.addActionListener(new ActListener());
		add(create);
		
		JButton read = new JButton("Read");
		read.setActionCommand("2");
		read.setBounds(0, 150, 100, 20);
		read.addActionListener(new ActListener());
		add(read);
		
		JButton update = new JButton("Update");
		update.setActionCommand("3");
		update.setBounds(0, 180, 100, 20);
		update.addActionListener(new ActListener());
		add(update);
		
		JButton delete = new JButton("Delete");
		delete.setActionCommand("4");
		delete.setBounds(0, 210, 100, 20);
		delete.addActionListener(new ActListener());
		add(delete);
	}
	
	public class ActListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			switch (e.getActionCommand())
			{
			case "1":
				WindowDialog windowDialog = new WindowDialog();
				windowDialog.setVisible(true);
				if(windowDialog.isOk)
				{
					Person p = windowDialog.getPerson();
					tableModel.getPersons().add(p);
					tableModel.getCurrentDBImpl().create(p);
					tableModel.fireTableDataChanged();
				}
				break;
			case "2":
				tableModel.setPersons(tableModel.getCurrentDBImpl().read());
				tableModel.fireTableDataChanged();
				break;
			case "3":
				WindowDialog udialog = new WindowDialog();
				int index = tbl.getSelectedRow();
				if(index != -1)
				{
					udialog.setPerson(tableModel.getPersons().get(index));
				}
				udialog.setVisible(true);
				if(udialog.isOk == true)
				{
					Person p = udialog.getPerson();
					int count = 0;
					for(int i = 0; i< tableModel.getPersons().size(); i++)
					{
						if(tableModel.getPersons().get(i).id == p.id)
						{
							tableModel.getPersons().set(i, p);
							tableModel.getCurrentDBImpl().update(p);
							count++;
						}
					}
					if(count == 0)
					{
						tableModel.getPersons().add(p);
					}
					tableModel.fireTableDataChanged();
				}
				break;
			case "4":
				if(removeSelectedRow(tbl))
				{
					break;
				}
				WindowDialog ddialog = new WindowDialog();
				ddialog.setVisible(true);
				if(ddialog.isOk == true)
				{
					Person p = ddialog.getPerson();
					ListIterator<Person> iter = tableModel.getPersons().listIterator();
					while(iter.hasNext())
					{
						if(iter.next().id == p.id)
						{
							iter.remove();
							break;
						}
					}
					tableModel.getCurrentDBImpl().delete(p);
					tableModel.fireTableDataChanged();
				}
				break;
			}
		}

		
	}
	
	private boolean removeSelectedRow(JTable tbl) 
	{
		int delindex = tbl.getSelectedRow();
		if(delindex != -1)
		{
			Person p = tableModel.getPersons().get(delindex);
			tableModel.getCurrentDBImpl().delete(p);
			tableModel.getPersons().remove(delindex);
			tableModel.fireTableRowsDeleted(delindex, delindex);
			return true;
		}
		return false;
	}
	
//	class DeleteListener implements KeyListener
//	{
//
//		@Override
//		public void keyTyped(KeyEvent e) 
//		{}
//
//		@Override
//		public void keyPressed(KeyEvent e) 
//		{
//			if(e.getKeyCode() == KeyEvent.VK_DELETE)
//			{
//				removeSelectedRow(tbl);
//			}
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {}
//		
//	}
}
