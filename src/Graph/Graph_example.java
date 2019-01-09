package Graph;

import java.util.ArrayList;
import java.util.Arrays;



import graph.Graph;
import graph.Node;
import Geom.Point3D;
import Gui.map;
import objectOfThegame.Box;
import objectOfThegame.Fruit;
import objectOfThegame.Me;

public class Graph_example {
	ArrayList <Box> box;
	Graph g=new Graph();
	String source="a";
	String target="b";
	private int counter;
	public Graph_example (ArrayList boxlist)
	{
		this.box=boxlist;
	}
	public Point3D [] tachat (Fruit fruit, Me me)
	{
		Point3D [] pp=new Point3D [box.size()*4];
		System.out.println(box.size());
		System.out.println(pp.length);
		int j=0;
		for (int i=0;i<pp.length;i=i+4)
		{
			pp[i]=new Point3D (box.get(j).getDownRight());
			pp[i+1]=new Point3D (box.get(j).getPointDown());
			pp[i+2]=new Point3D (box.get(j).getPointStart());
			pp[i+3]=new Point3D (box.get(j).getPointTop());
			System.out.println(i);
			j++;
		}

		Point3D [] arr=limitOfBOX (this.box,pp);
		Graph G = new Graph(); 
		arr[0]=new Point3D (map.gpsToPix(1433, 642,me.getMe()));
		arr[arr.length-1]=new Point3D (map.gpsToPix(1433, 642,fruit.getPoint()));
		System.out.println(Arrays.toString(arr));
		System.out.println("length"+arr.length);
		G.add(new Node(source)); // Node "a" (0)
		for(int i=1;i<arr.length-1;i++) {
			Node d = new Node(""+i);
			G.add(d);
			//		pp[i] = new Point3D(xx[i], yy[i]);

		}
		int count=0;
		G.add(new Node(target)); // Node "b" (15)
		Point3D p1=new Point3D (876.0,507.0,0.0);
		Point3D p2=new Point3D (829.0,25.0,0.0);
		Line line=new Line (p1,p2);
		System.out.println("hadar hahabla"+line.TherIsALine(box.get(0)));
//		for (int i=0;i<arr.length;i++)
//		{
//			System.out.println("I"+i);
//			for (int w=i+1;w<arr.length;w++)
//			{
//				System.out.println("W"+w);
//				for (int z=0;z<box.size();z++)
//				{
//					System.out.println("Z"+z);
//					Point3D pI=new Point3D (arr[i].y(),arr[i].x());
//					Point3D pW=new Point3D (arr[w].y(),arr[w].x());
//					Line line=new Line (pI,pW);
//					if (line.TherIsALine(box.get(z))==true)//that means that the line going throw this box
//					{
//						G.addEdge(""+i,""+w,pI.distance2D(pW));
//						count++;
//					}
//					else 
//						System.out.println("False");
//				}
//			}
//		}
		System.out.println("count"+count);
		return arr;
	}
	public Point3D [] limitOfBOX (ArrayList <Box> boxs,Point3D [] arr)
	{int counter=0;
	boolean flag=true;
	for (int i=0; i<arr.length;i++)
	{
		flag=true;
		for (int j=0;j<boxs.size();j++)
		{
			if (boxs.get(j).inTheBox(arr[i])==false)
				flag=false;

		}
		if (flag==true)
			counter++;
	}
	Point3D [] arr2=new Point3D [counter+2];
	counter=1
			;
	for (int i=0; i<arr.length;i++)
	{
		flag=true;
		for (int j=0;j<boxs.size();j++)
		{
			if (boxs.get(j).inTheBox(arr[i])==false)
				flag=false;

		}
		if (flag==true)
		{
			arr2[counter]=map.gpsToPix(1433, 642,arr[i]);
			counter++;
		}
	}
	return arr2;
	}
}
