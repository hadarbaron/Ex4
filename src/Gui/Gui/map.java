package Gui;
import Geom.Point3D;

/**
 * The class creates static auxiliary functions 
 * converting GPS point to pixel, and pixel to GPS
 * @author shira AND hadar
 *
 */
public class  map{

	// offsets
	static final double mapLongitudeStart =35.202369, mapLatitudeStart = 32.105728;
	// length of map in long/lat
	static final double mapLongitude = 35.212416-mapLongitudeStart, 
			// invert because it decreases as you go down
			mapLatitude = mapLatitudeStart-32.101898;

	/**
	 * get a gps point and convert it to a pix
	 * @param width
	 * @param height
	 * @param p
	 * @return
	 */
	public static Point3D gpsToPix (double width,double height,Point3D p )
	{
		Point3D pnew=new Point3D (p);
		double y = pnew.y()-mapLongitudeStart;
		// do inverse because the latitude increases as we go up but the y decreases as we go up.
		// if we didn't do the inverse then all the y values would be negative.
		double x = mapLatitudeStart-pnew.x();

		// set x & y using conversion
		int py = (int) (width*(y/mapLongitude));
		int px= (int) (height*(x/mapLatitude));

		return new Point3D(px, py);
	}

	/**
	 * get a pix point and convert it to a gps
	 * @param width
	 * @param height
	 * @param p
	 * @return
	 */
	public static  Point3D pixToGps(double width,double height,Point3D p)
	{
		double y=((p.x()*mapLongitude)/width)+mapLongitudeStart;
		double x=-((p.y()*mapLatitude)/height)+mapLatitudeStart;

		return new Point3D (x,y);
	}
	public static double disBPix(double width,double height,Point3D pix1, Point3D pix2)
	{
		Point3D gps1=new Point3D (map.pixToGps(1443, 642, pix1));
		Point3D gps2=new Point3D (map.pixToGps(1443, 642, pix2));
		Coords c=new Coords();
		double b=c.distance3d(gps1, gps2);
		return b;
	}
	public static double angal(double width,double height, Point3D pix1, Point3D pix2)
	{
		Point3D gps1=new Point3D (map.pixToGps(1443, 642, pix1));
		Point3D gps2=new Point3D (map.pixToGps(1443, 642, pix2));
		Coords c=new Coords();
		double [] b=c.azimuth_elevation_dist(gps1, gps2);
		return b[0];
	}
	public static void main(String[] args) {
		System.out.println(map.disBPix(1433, 642, new Point3D(0,0), new Point3D (1433,642)));
	}

}