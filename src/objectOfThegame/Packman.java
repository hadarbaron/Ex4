package objectOfThegame;
import Geom.Point3D;
import Gui.map;

public class Packman {

	private Point3D point;
	/**
	 * Constractor of the Pakcman
	 * @param g - get an array string
	 */
	public Packman(String [] g)
	{
		point=new Point3D(Double.parseDouble(g[2]),Double.parseDouble(g[3]),Double.parseDouble(g[4]));
	}

	public Point3D getPoint()
	{
		return this.point;
	}


}




