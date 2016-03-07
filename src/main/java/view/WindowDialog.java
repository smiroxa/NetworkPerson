package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.Person;

public class WindowDialog extends JDialog
{
	public boolean isOk;
	JTextField fid;
	JTextField ffname;
	JTextField flname;
	JTextField fage;
	

	public WindowDialog()
	{
		setBounds(300, 200, 300, 300);
		setLayout(null);
		
		JLabel id = new JLabel("id");
		id.setBounds(30, 30, 50, 20);
		add(id);
		
		JLabel fname = new JLabel("fname");
		fname.setBounds(30, 60, 50, 20);
		add(fname);
		
		JLabel lname = new JLabel("lname");
		lname.setBounds(30, 90, 50, 20);
		add(lname);
		
		JLabel age = new JLabel("age");
		age.setBounds(30, 120, 50, 20);
		add(age);
		
		fid = new JTextField();
		fid.setBounds(70, 30, 100, 20);
		add(fid);
		
		ffname = new JTextField();
		ffname.setBounds(70, 60, 100, 20);
		add(ffname);
		
		flname = new JTextField();
		flname.setBounds(70, 90, 100, 20);
		add(flname);
		
		fage = new JTextField();
		fage.setBounds(70, 120, 100, 20);
		add(fage);
		
		JButton ok = new JButton("Ok");
		ok.setBounds(30, 170, 100, 20);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				isOk = true;
				setVisible(false);
			}
		});
		add(ok);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(150, 170, 100, 20);
		cancel.addActionListener((ActionEvent e)-> {isOk = false;setVisible(false);});
		add(cancel);
		
		setModalityType(DEFAULT_MODALITY_TYPE);
	}

	public Person getPerson() 
	{
		Person p = new Person();
		p.id = Integer.parseInt(fid.getText());
		p.fname = ffname.getText();
		p.lname = flname.getText();
		p.age = Integer.parseInt(fage.getText());
		
		return p;
	}
	
	public void setPerson(Person p) 
	{
		fid.setText(p.id+"");
		ffname.setText(p.fname);
		flname.setText(p.lname);
		fage.setText(p.age+"");
	}
	
	public void showDialog()
	{
		setVisible(true);
	}
}
