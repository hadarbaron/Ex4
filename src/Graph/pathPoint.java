package Graph;

import java.util.ArrayList;

import Geom.Point3D;

public class pathPoint {
	Point3D P1;
	ArrayList <Point3D> path;
	public pathPoint(Point3D p1)
	{
		path=new ArrayList <Point3D>();
		this.P1=p1;	
	}
	/**
	 * the arraylist that p1 can connected with line and not get in to the boxes
	 * @param p
	 */
	public void add(Point3D p)
	{
		path.add(p);
	}
	public Point3D getP1() {
		return P1;
	}
	public ArrayList<Point3D> getPath() {
		return path;
	}
	
}


