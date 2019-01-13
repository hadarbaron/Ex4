package Graph;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;



import Geom.Point3D;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;
import objectOfThegame.Box;
import objectOfThegame.Fruit;
import objectOfThegame.Me;
public class algo {
	ArrayList <Line2D> lineOfBox;
	ArrayList <Point3D> pointOfBox;
	ArrayList <Point3D> Edge;
	ArrayList <pathPoint> lineOfEdge;
	pathPoint pathFruit;
	pathPoint me1;
	/**
	 * the constructor get the arralist of the box and make two new arralist.
	 * line of box it is all the line of the boxes.
	 * pointofboxes all the points in the edge of the box.
	 * the function used the two private function 
	 * @param boxes
	 */
	public algo (ArrayList <Box> boxes)
	{ 

		lineOfBox=new ArrayList <Line2D> ();
		pointOfBox=new ArrayList <Point3D>();
		for (int i=0; i<boxes.size();i++)
		{
			Point3D p1=newmap.gpsToPix(1433,642,boxes.get(i).getDownRight());
			Point3D p2=newmap.gpsToPix(1433,642,boxes.get(i).getPointDown());
			Point3D p3=newmap.gpsToPix(1433,642,boxes.get(i).getPointStart());
			Point3D p4=newmap.gpsToPix(1433,642,boxes.get(i).getPointTop());
			pointOfBox.add(p1);
			pointOfBox.add(p2);
			pointOfBox.add(p3);
			pointOfBox.add(p4);	
			Line2D l1=new Line2D.Double (p1.x(),p1.y(),p4.x(),p4.y());
			Line2D l2=new Line2D.Double (p1.x(),p1.y(),p2.x(),p2.y());
			Line2D l3=new Line2D.Double (p3.x(),p3.y(),p4.x(),p4.y());
			Line2D l4=new Line2D.Double (p3.x(),p3.y(),p2.x(),p2.y());
			lineOfBox.add(l1);
			lineOfBox.add(l2);
			lineOfBox.add(l3);
			lineOfBox.add(l4);
		}
		Points_of_edges(boxes);
		line_of_edges();
	}
	
	public ArrayList<Line2D> getLineOfBox() {
		return lineOfBox;
	}
	
	public ArrayList<Point3D> getPointOfBox() {
		return pointOfBox;
	}
	
	public ArrayList<Point3D> getEdge() {
		return Edge;
	}
	
	public ArrayList<pathPoint> getLineOfEdge() {
		return lineOfEdge;
	}
	
	/**
	 * points that in the edge 
	 * the function make a new arraylist that there is all the points in the boxes that dont in the boxes
	 * @param boxes
	 */
	private void Points_of_edges(ArrayList <Box> boxes) {
		Edge=new ArrayList <Point3D>();
		boolean flag=true;
		for (int i=0;i<pointOfBox.size();i++)
		{
			flag=true;
			for (int j=0;j<boxes.size();j++)
			{
				if (boxes.get(j).inTheBoxP(pointOfBox.get(i))==false)
					flag=false;
			}
			if (flag)
			{
				Edge.add(new Point3D (pointOfBox.get(i)));
			}
		}
	}
	/**
	 * the function create a new arraylist and in it willl be all the line that can be from the points in the edge of the boxes
	 */
	private void line_of_edges()
	{
		boolean flag=false;
		lineOfEdge=new ArrayList <pathPoint>();
		for (int i=0;i<Edge.size();i++)
		{
			pathPoint path=new pathPoint (Edge.get(i));
			for (int j=i+1;j<Edge.size();j++)
			{
				flag=false;
				Line2D line=new Line2D.Double(Edge.get(i).x(),Edge.get(i).y(),Edge.get(j).x(),Edge.get(j).y());
				for (int w=0;w<lineOfBox.size();w++)
				{	
					if (line.intersectsLine(lineOfBox.get(w)))//If both lines cut
					{
						if(line.getP2().equals(lineOfBox.get(w).getP1())||line.getP1().equals(lineOfBox.get(w).getP1())||
								line.getP1().equals(lineOfBox.get(w).getP2())||line.getP2().equals(lineOfBox.get(w).getP2()))//If the cut point is at the edges
						{	
						}
						else
							flag=true;
					}
				}
				if (flag==false)
				{
					path.add(Edge.get(j));
				}
			}
			if(path.getPath().size()!=0)//If it has connections at all to other lines
				lineOfEdge.add(path);
		}
	}
	/**
	 * the function get a me and a fruit 
	 * the function create for both of them a pathpoint and in it will be all the points that conceted to it and can be a line to it
	 * @param me
	 * @param fruit
	 */
	Point3D fruit1;
	public void getPathP2F (Me me, Fruit fruit)
	{
		Point3D p=newmap.gpsToPix(1433, 642, me.getMe());
		me1=new pathPoint (p);//Create all the possible lines between the Pacman and the other edges
		boolean flag=false;
		for (int i=0;i<Edge.size();i++)
		{
			flag=false;
			Line2D line=new Line2D.Double(p.x(),p.y(),Edge.get(i).x(),Edge.get(i).y());
			for (int w=0;w<lineOfBox.size();w++)
			{
				if (line.intersectsLine(lineOfBox.get(w))==true)
				{
					if(line.getP2().equals(lineOfBox.get(w).getP1())||line.getP1().equals(lineOfBox.get(w).getP1())||
							line.getP1().equals(lineOfBox.get(w).getP2())||line.getP2().equals(lineOfBox.get(w).getP2()))//If the cut point is at the edges
					{	
					}
					else
						flag=true;
				}
			}
			if (flag==false)
				me1.add(Edge.get(i));
		}
		fruit1=newmap.gpsToPix(1433, 642,fruit.getPoint());
		Line2D linep2f=new Line2D.Double(fruit1.x(),fruit1.y(),p.x(),p.y());
		flag=false;
		for (int w=0;w<lineOfBox.size();w++)//See if there is a connection between the fruit and the buckman
		{
			if (linep2f.intersectsLine(lineOfBox.get(w))==true)
			{

				if(linep2f.getP2().equals(lineOfBox.get(w).getP1())||linep2f.getP1().equals(lineOfBox.get(w).getP1())||
						linep2f.getP1().equals(lineOfBox.get(w).getP2())||linep2f.getP2().equals(lineOfBox.get(w).getP2()))//If the cut point is at the edges
				{	
				}
				else
					flag=true;
			}
		}
		if (flag==false)
		{
			me1.add(fruit1);
		}
		
		pathFruit=new pathPoint(fruit1);//Create all the possible lines between the Pacman and the other edges
		for (int i=0;i<Edge.size();i++)
		{
			flag=false;
			Line2D line=new Line2D.Double(fruit1.x(),fruit1.y(),Edge.get(i).x(),Edge.get(i).y());
			for (int w=0;w<lineOfBox.size();w++)
			{
				if (line.intersectsLine(lineOfBox.get(w))==true)
				{

					if(line.getP2().equals(lineOfBox.get(w).getP1())||line.getP1().equals(lineOfBox.get(w).getP1())||
					   line.getP1().equals(lineOfBox.get(w).getP2())||line.getP2().equals(lineOfBox.get(w).getP2()))//If the cut point is at the edges
					{
					}
					else
						flag=true;
				}
			}
			if (flag==false)
				pathFruit.add(Edge.get(i));
		}

	}
	/**
	 * this function used the jar that writtion by boaz ben moshe. the algo is used by the Dykstra
	 * @return the point that me need to get to in to get close to the fruit
	 */
	public Point3D  boazALgo() {
		Graph G = new Graph(); 
		String source = "me";
		String target = "fruit";
		G.add(new Node(source)); // Node "me" (0)
		for(int i=0;i<Edge.size();i++) 
		{
			Node d = new Node(""+i);
			G.add(d);	
		}
		G.add(new Node(target));//Node "fruit"
		int i=0;
		while(i<me1.getPath().size())
		{
			int index=Edge.indexOf(me1.getPath().get(i));
			if(index>=0)
				G.addEdge("me",""+index,me1.getP1().distance2D(me1.getPath().get(i)));
			else
			{
				G.addEdge("me","fruit",me1.getP1().distance2D(me1.getPath().get(i)));
			}
			System.out.println("me" + ">>" +(index));
			i++;
		}
		i=0;
		for (int j=0;j<lineOfEdge.size();j++)
		{
			pathPoint Path=lineOfEdge.get(j);
			int index1=Edge.indexOf(Path.getP1());
			int w=0;
			while(w<Path.getPath().size())
			{
				int index=Edge.indexOf(Path.getPath().get(w));
				G.addEdge(""+index1,""+index,Path.getP1().distance2D(Path.getPath().get(w)));
				System.out.println((index1) + ">>" +(index));
				w++;	
			}
		}
		i=0;
		while(i<pathFruit.getPath().size())
		{
			int index=Edge.indexOf(pathFruit.getPath().get(i));
			G.addEdge(""+index,"fruit",pathFruit.getP1().distance2D(pathFruit.getPath().get(i)));
			System.out.println("fruit" + ">>" +(index ) );
			i++;
		}
		Graph_Algo.dijkstra(G, source);
		Node b = G.getNodeByName(target);
		for (int k=0;k<b.getPath().size();k++)
		{
			System.out.println(b.getPath().get(k));
		}
		if ( b.getPath().size()==1)
		{
			System.out.println("shira");
			return fruit1; 	
		}
		else
			return Edge.get(Integer.parseInt(b.getPath().get(1)));
	}
	public static void main(String[] args) 
	{
	}
}


