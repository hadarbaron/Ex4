package objectOfThegame;
import Geom.Point3D;
import Gui.map;

public class ghost {
	private Point3D point;
	/**
	 * Constractor of the ghost
	 * @param g - get an array string
	 */
	public ghost(String [] g)
	{
		point=new Point3D(Double.parseDouble(g[2]),Double.parseDouble(g[3]),Double.parseDouble(g[4]));
	}
	  
	public Point3D getGpsPoint()
	{
		return this.point;
	}
	
}
