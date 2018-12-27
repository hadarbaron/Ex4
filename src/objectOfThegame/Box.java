package objectOfThegame;

import Geom.Point3D;
import Gui.map;

public class Box {
	private Point3D pointTop;
	private Point3D pointDown;
	private Point3D pointStart;

	/**
	 * Constractor of the Box
	 */

	public Box(String []arr)
	{
		pointDown=new Point3D(Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]));
		pointTop=new Point3D(Double.parseDouble(arr[5]),Double.parseDouble(arr[6]),Double.parseDouble(arr[7]));
		pointStart=new Point3D(pointTop.x(),pointDown.y());
	}
	public Box(Box b)
	{
		pointDown=b.getPointDown();
		pointTop=b.getPointTop();
		pointStart=b.getPointStart();
	}
	public Point3D getPointTop()
	{
		return this.pointTop;
	}
	public Point3D getPointDown()
	{
		return this.pointDown;
	}
	public Point3D getPointStart() 
	{
		return this.pointStart;
	}
	/**
	 * return the width that get from this rectangle
	 * @param width
	 * @param height
	 * @return
	 */
	public int getWidth(int width, int height)
	{
		Point3D pixPD=map.gpsToPix(width, height, pointDown);
		Point3D pixPT=map.gpsToPix(width, height, pointTop);
		return (int)(pixPD.x()-pixPT.x());
	}
	/**
	 * return the height that get from this rectangle
	 * @param width
	 * @param height
	 * @return
	 */
	public int gethieht(int width, int height)
	{
		Point3D pixPD=map.gpsToPix(width, height, pointDown);
		Point3D pixPT=map.gpsToPix(width, height,pointTop);
		return (int)(pixPT.y()-pixPD.y());
	}
}
