package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import model.Person;
import model.TableModel;

public class RootPanel extends JPanel
{
	public RootPanel()
	{
			
		setLayout(null);
		TableModel tableModel = new TableModel();
		JTable jTable = new JTable(tableModel);
		JScrollPane jScrollPane = new JScrollPane(jTable);
		
		jTable.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if ( e.getKeyCode() == KeyEvent.VK_DELETE )
				{ 
					int delindex = jTable.getSelectedRow();
					if(delindex != -1)
					{
						Person p = tableModel.getPersons().get(delindex);
						tableModel.getCurrentDBImpl().delete(p);
						tableModel.getPersons().remove(delindex);
						tableModel.fireTableRowsDeleted(delindex, delindex);
					}
				}
			}
		});
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		jTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		jScrollPane.setBounds(50, 10, 300, 400);
		WorkPanel wp = new WorkPanel(tableModel);
		wp.tbl = jTable;
		add(jScrollPane);
		add(wp);
		
	}
}
