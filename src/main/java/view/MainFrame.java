package view;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	public MainFrame()
	{
		setTitle("Person CRUD");
		setBounds(200, 200, 550, 600);
		add(new RootPanel());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
