package Gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//import org.omg.PortableServer.THREAD_POLICY_ID;
import Geom.Point3D;
import Robot.Play;
import objectOfThegame.Box;
import objectOfThegame.Fruit;
import objectOfThegame.Me;
import objectOfThegame.Packman;
import objectOfThegame.ghost;


public class MainWindow extends JFrame implements MouseListener{
	public BufferedImage myImage;
	int x = -1;
	int y = -1;
	Play play1=null;
	BufferedImage img2 = null;
	BufferedImage img1 = null;

	boolean isGamer=true;//to know if press packman or fruit
	boolean entercsv=false;//if the game load a csv
	boolean endgame=false;//if the game is end
	//if the game is alive
	//boolean gameAction=false;
	boolean game=false;
	boolean initgame=false;
	ArrayList<Packman> packmanlist = null;
	ArrayList<Fruit> fruitlist= null;
	ArrayList<ghost> ghostlist= null;
	ArrayList<Box> boxlist= null;
	public MainWindow() 
	{
		initGUI();	
		this.addMouseListener(this); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initGUI() 
	{ 
		//*******puting all the item***********
		MenuBar menuBar = new MenuBar();

		Menu File = new Menu("FILE");
		MenuItem item2 = new MenuItem("IMPORT CSV");
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileDialog();
			}
		});
		Menu gameaction = new Menu("REAL TIME GAME"); 
		MenuItem gameAction = new MenuItem("START");
		gameAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game=true;
				playRefresh();

			}
		});
		MenuItem initplace = new MenuItem("INIT PLACE");
		initplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initgame=true;	
			}
		});
		menuBar.add(File);
		menuBar.add(gameaction);
		File.add(item2);
		setMenuBar(menuBar);
		gameaction.add(gameAction);
		gameaction.add(initplace);
		//******end of items***********

		//*********reading pictures**************
		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//************end of reading pictures*******************
		this.setSize(myImage.getWidth(),myImage.getHeight());
	}
	//******************paint**********************
	public void paint(Graphics g)
	{
		g.drawImage(myImage,0 , 0,getWidth(),getHeight(), this);
		//********painting the game**********************
	}

	public void readFileDialog() {
		entercsv=true;
		FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
		fd.setFile("*.csv");

		fd.setDirectory("C:\\Users\\DELL\\Desktop\\Ex4_OOP\\data");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		System.out.println(fileName);
		play1 = new Play("Data/"+fileName);
		play1.setIDs(208761452, 316148842);
	}

	/**
	 * this function restart every time and restart all the arraylist with the new infomations
	 */
	public void playRefresh()
	{
		ArrayList<String> board_data = play1.getBoard();
		for(int i=0;i<board_data.size();i++) 
		{
			//reading from the csv
			String[] csvLine = board_data.get(i).split(",");
			String obj=csvLine[0];
			switch(obj)
			{
			case "B": 
			{
				Box box=new Box(csvLine);
				boxlist.add(box);
				break;
			}
			case "F": 
			{Fruit fruit=new Fruit (csvLine);
			fruitlist.add(fruit);
			break;
			}
			case "G":
			{
				ghost ghost=new ghost (csvLine);
				ghostlist.add(ghost);
				break;
			}

			case "P": 
			{
				Packman packman=new Packman(csvLine);
				packmanlist.add(packman);
				break;
			}
			}
		}
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		x=e.getX();
		y=e.getY();
		if(initgame)
		{
			Point3D p=map.pixToGps(getWidth(), getHeight(), new Point3D (x,y));
			play1.setInitLocation(p.x(),p.y());
			initgame=false;
		}
		if (game)
		{
			String []arr=play1.getBoard().iterator().next().split(",");//founding the rotate 
			Me me=new Me(arr);
			Point3D p=map.gpsToPix(getWidth(), getHeight(), me.getMe());
			double angal=map.angal(getWidth(), getHeight(),p, new Point3D(x,y));
			play1.rotate(angal);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		MainWindow Window=  new MainWindow();
		Window.setVisible(true);
	}
}