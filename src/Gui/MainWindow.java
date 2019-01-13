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
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import Geom.Point3D;
import Graph.algo;
import Graph.newmap;
import Graph.pathPoint;
import Robot.Play;
import objectOfThegame.Box;
import objectOfThegame.Fruit;
import objectOfThegame.Me;
import objectOfThegame.Packman;
import objectOfThegame.ghost;


public class MainWindow extends JFrame implements MouseListener{
	public BufferedImage myImage;
	Point3D [] pp;
	int x = -1;
	int y = -1;
	algo algo;
	Me me=new Me (32.101898,35.202369);
	Play play1=null;
	BufferedImage img2 = null;
	BufferedImage img1 = null;
	double angal=0;
	boolean isGamer=true;//to know if press packman or fruit
	boolean entercsv=false;//if the game load a csv
	boolean endgame=false;//if the game is end
	//if the game is alive
	//boolean gameAction=false;
	boolean game=false;
	boolean gameAuto=false;
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
		//**puting all the item****
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
				play1.start();
				playRefresh();
			}
		});
		MenuItem startAuto = new MenuItem("STARTAuto");
		startAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameAuto=true;
				play1.start();
				automaticMod();
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
		gameaction.add(startAuto);
		File.add(item2);
		setMenuBar(menuBar);
		gameaction.add(gameAction);
		gameaction.add(initplace);
		//***end of items****

		//****reading pictures*****
		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//*****end of reading pictures******
		this.setSize(myImage.getWidth(),myImage.getHeight());
	}
	//*******paint*******
	public void paint(Graphics g)
	{
		g.drawImage(myImage,0 , 0,getWidth(),getHeight(), this);
		//************draw the box****************************

		if (boxlist!=null) 
		{ 
			Iterator<Box> itB=boxlist.iterator(); 
			while(itB.hasNext())
			{
				Box newB=new Box(itB.next());
				double disx=newB.getWidth(getWidth(), getHeight());
				double disy=newB.gethieht(getWidth(),getHeight());
				Point3D pstart=map.gpsToPix(getWidth(),getHeight(),newB.getPointStart());
				g.setColor(Color.black); g.fillRect((int) pstart.y(), (int)pstart.x(), (int)disy ,(int)disx); 
			}
		}
		//***painting the packmans*******************
		if(packmanlist!=null) 
		{
			for (int i=0;i<packmanlist.size();i++)
			{
				Point3D p=map.gpsToPix(getWidth(), getHeight(), packmanlist.get(i).getPoint());
				g.setColor(Color.YELLOW);
				g.fillOval(p.iy(), p.ix(), 20,20);
			}
		}
		//*****painting the fruits********************
		if(fruitlist!=null) 
		{Point3D p=null;
		if (fruitlist.size()>0)
		{
			p=map.gpsToPix(getWidth(), getHeight(), fruitlist.get(0).getPoint());
			g.setColor(Color.green);
			g.fillOval(p.iy(), p.ix(), 15,15);
		}
		for (int i=1;i<fruitlist.size();i++)
		{
			p=map.gpsToPix(getWidth(), getHeight(), fruitlist.get(i).getPoint());
			g.setColor(Color.pink);
			g.fillOval(p.iy(), p.ix(), 15,15);

		}	
		}
		//*******painting the ghosts****
		if (ghostlist!=null)
		{
			for (int i=0;i<ghostlist.size();i++)
			{

				Point3D p=map.gpsToPix(getWidth(), getHeight(), ghostlist.get(i).getGpsPoint());
				g.setColor(Color.RED);
				g.fillOval(p.iy(), p.ix(), 15,15);

			}
		}
		//********painting the me********************


		if (game||gameAuto) 
		{
			String []arr=play1.getBoard().iterator().next().split(",");//founding the rotate 
			me=new Me(arr);
		}
		Point3D p=map.gpsToPix(getWidth(), getHeight(),me.getMe());
		g.setColor(Color.green);
		g.fillOval(p.iy(), p.ix(), 25,25);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(game||gameAuto)
		{
			if(play1.isRuning()==false)
			{
				game=false;
				gameAuto=false;
				g.setColor(Color.black);
				Graphics2D _paper2D=(Graphics2D)g;
				_paper2D.setStroke(new BasicStroke(6F));
				g.drawString(play1.getStatistics(), 1000, 400);
			}
		}
		if (game)
		{
			play1.rotate(getAngle());
			playRefresh();
		}
		if(gameAuto)
		{
			automaticMod();
		}
	}
	/**
	 * reading from the csv
	 */
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
		repaint();
		playRefresh();

	}

	/**
	 * this function restart every time and restart all the arraylist with the new infomations
	 */
	public void playRefresh()
	{
		packmanlist = new ArrayList<Packman>();
		fruitlist= new ArrayList<Fruit>();
		ghostlist= new ArrayList<ghost>();
		boxlist= new ArrayList<Box> ();
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
			{
				Fruit fruit=new Fruit (csvLine);
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
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		x=e.getX();
		y=e.getY();
		if(initgame)
		{
			Point3D p=map.pixToGps(getWidth(), getHeight(), new Point3D (x,y));
			play1.setInitLocation(p.x(),p.y());
			me.setPoint(p);
			initgame=false;
			repaint();
		}
		if (game)
		{
			String []arr=play1.getBoard().iterator().next().split(",");//founding the rotate 
			me=new Me(arr);
			Point3D p=map.gpsToPix(getWidth(), getHeight(), me.getMe());
			Point3D z=new Point3D (p.y(),p.x());
			System.out.println(z);
			angal=map.angal(getWidth(), getHeight(),z, new Point3D(x,y));
			setAngle(angal);
		}
	}
/**
 * the function used the class algo and calculate the angal and rotate it in the end
 */
	public void automaticMod()
	{
		algo=new algo(boxlist);
		algo.getPathP2F(me, fruitlist.get(0));
		System.out.println("hadar"+me.getMe());
		Point3D st=	algo.boazALgo();
		System.out.println(st);
		//		String []arr=play1.getBoard().iterator().next().split(",");//founding the rotate 
		//		me=new Me(arr);
		Point3D fake=map.gpsToPix(getWidth(), getHeight(), me.getMe());
		Point3D z=new Point3D (fake.y(),fake.x());
		angal=map.angal(getWidth(), getHeight(),z, new Point3D(st));
		play1.rotate(angal);
		playRefresh();
	}
	public double getAngle()
	{
		return angal;
	}

	public void setAngle(double angle)
	{
		this.angal = angle;
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