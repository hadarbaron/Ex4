package Graph;

import Geom.Point3D;
import objectOfThegame.Box;

public class Line {
	double m;
	double n;
	public Line (Point3D a, Point3D b)
	{
		m=(a.x()-b.x())/(a.y()-b.y());
		n=-1*m*a.y()+a.x();
	}
	public double functionGety (int y)
	{
		return( m*y+n);
	}
	public double functionGetx (int x)
	{
		return ((x-n)/m);
	}
	
public boolean TherIsALine(Box b) {
	System.out.println("box"+b);
		Point3D p1= new Point3D(b.getLimXleft(),functionGetx(b.getLimXleft()));
		System.out.println("p1"+p1);
		if(b.inTheBoxP(p1)==false)return false;
		Point3D p2= new Point3D(b.getLimXright(),functionGetx(b.getLimXright()));
		System.out.println("p2"+p2);
		if(b.inTheBoxP(p2)==false)return false;
		Point3D p3= new Point3D(functionGety(b.getLimYdown()),b.getLimYdown());
		System.out.println("p3"+p3);
		if(b.inTheBoxP(p3)==false)return false;
		Point3D p4= new Point3D(functionGety(b.getLimYup()),b.getLimYup());
		System.out.println("p4"+p4);
		if(b.inTheBoxP(p4)==false)return false;
		return true;
	}
}

