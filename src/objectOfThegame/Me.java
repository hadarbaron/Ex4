package objectOfThegame;
import Geom.Point3D;
public class Me {
	Point3D me;
	public Me(String []arr)
	{
		me=new Point3D (Double.parseDouble(arr[2]),Double.parseDouble(arr[3]),Double.parseDouble(arr[4]));
	}
	public Point3D getMe ()
	{
		return me;
	}
}
