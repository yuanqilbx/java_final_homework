package huluwa;
import java.util.*;

public class list {
	public static void changshe (tree all,Vector<? extends lives> arr,int h,int w)
	{
		if(arr.isEmpty())
			return;
		
		where[][] ts=all.getSpace();
		
		for(int i=0;i<arr.size();i++)
		{
			ts[h+i][w].set(arr.elementAt(i));
			//arr.elementAt(i).newX(h);
			//arr.elementAt(i).newY(w+i);
		}
	}
	
	public static void heyi (tree all,Vector<? extends lives> arr,int h,int w)
	{
		if(arr.isEmpty())
			return;
		where[][] ts=all.getSpace();
		int l;
		l=arr.size();
		int i=0,j=0;
		for( ;i<l/2;i++)
		{
			ts[h+i][w+i].set(arr.elementAt(i));
			//ts[h-i][w+i].set(arr.elementAt(i));
		}
		for(j=0 ;i+j<l;j++)
		{	
			ts[h+i-j][w+i+j].set(arr.elementAt(i+j));
			//ts[h-i-j][w+i+j].set(arr.elementAt(i));
		}
	}
	
	public static void yanxing (tree all,Vector<? extends lives> arr,int h,int w)
	{
		if(arr.isEmpty())
			return;
		where[][] ts=all.getSpace();
		for(int i=0;i<arr.size();i++)
		{
			ts[h-i][w+i].set(arr.elementAt(i));
		}
	}
	
	

}
