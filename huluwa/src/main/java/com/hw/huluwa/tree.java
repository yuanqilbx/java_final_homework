package com.hw.huluwa;
import java.io.*;
import java.util.*;



public class tree {
	private int row,col;
	private where[][] space;
	private Vector<lives> boys;
	private Vector<lives> mons;
	private cheer yeye;
	private cheer snake;
	private boss Boss;
	
	
	public tree(int row,int col)
	{
		
		
		this.row=Config.row;
		this.col=Config.col;
		
		this.space=new where[row+1][col+1];
		for (int i = 0; i <= row; i++) {
		      for (int j = 0; j <= col; j++) {
		        this.space[i][j] = new where(i, j);
		      }
		   }
		
		this.boys=new Vector<lives>();
		this.mons=new Vector<lives>();
		for(int i=1;i<=7;i++)
		{
			huluwa tmp=new huluwa(i);
			tmp.setMap(space);
			space[1][2+i].set(tmp);	
			this.boys.addElement(tmp);
			//space[6][i].set(boys.elementAt(i-1));
		}
		
		boss t1=new boss();
		t1.setMap(space);
		space[row-3][col-8].set(t1);;	
		this.Boss=t1;
		this.mons.addElement(t1);
		//space[row-8][col-8].set(mons.elementAt(0));
		for(int i=1;i<7;i++)
		{
			mst t2=new mst();
			t2.setMap(space);
			space[row-3][col-8+i].set(t2);
			
			this.mons.addElement(t2);
			//space[row-8][col-8+i].set(mons.elementAt(i));
		}
		
		this.yeye=new cheer(0);
		this.yeye.setMap(space);
		space[1][1].set(yeye);
		
		this.snake=new cheer(1);
		this.snake.setMap(space);
		space[row-1][col-1].set(snake);
		
		
	}
	
	public void clear()
	{
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
			{
				space[i][j].cleanOn();
			}
		
	}
	
	public int getRow() { return this.row; }

	public int getCol() { return this.col; }
	

	  public where[][] getSpace() { return this.space; }

	  public Vector<lives> getBoys() { return this.boys; }

	  public Vector<lives> getMons() { return this.mons; }

	  public boss getBoss() { return this.Boss; }

	  public cheer getSnake() { return this.snake; }

	  public cheer getYeye() { return this.yeye; }

	  public boolean inSpace(int x, int y) {
	    if (x >= 0 && x < this.row && y >= 0 && y < this.col)
	      return true;
	    else return false;
	  }
	
	  public void start()
	  {
		  Collections.shuffle(this.boys);
		  Collections.shuffle(this.mons);
		  
		  try {
			this.Outfile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  list.changshe(this,this.getBoys(),2,3);
		  list.yanxing(this,this.getMons(),10,10);
		  //printBoys();
		  //printMons();	
		  
		  
	  }
	  
	  public void printBoys()
	  {
		  for(int i=0;i<this.boys.size();i++)
		  {
			  this.boys.elementAt(i).print();
		  }
	  }
	  
	  public void printMons()
	  {
		  for(int i=0;i<this.mons.size();i++)
		  {
			  this.mons.elementAt(i).print();
		  }
	  }
	  
	  
	  /*
	public static void main(String[] args)
	{
		tree all=new tree(18,25);
		all.start();
		space t=new space();
		
		for(int i=0;i<all.getBoys().size();i++)
		{
			t.add(all.getBoys().elementAt(i).getThing());
		}
		for(int i=0;i<all.getMons().size();i++)
		{
			t.add(all.getMons().elementAt(i).getThing());
		}
		t.add(all.getSnake().getThing());
		t.add(all.getYeye().getThing());
		
		list.changshe(all,all.getBoys(),1,3);
		list.yanxing(all,all.getMons(),10,10);
		//all.print();
		
		all.yeye.cheers();
		all.snake.cheers();
		
		try {
			Thread.sleep(5000);
		}catch(Exception e)
		{
			System.exit(0);
		}
		
		list.heyi(all,all.getMons(),10,10);
		//all.print();
		
	}
	*/
	  
	public void print()
	{

		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(space[i][j].isEmpty())
				{
					System.out.printf("_ ");
				}
				else {
					space[i][j].getLives().print();
					System.out.printf(" ");
				}
			}
			System.out.print('\n');
		}
		
		System.out.println("*********ENDIF**********");
	}
	
	public void updateMap(int[][] map)
	{

		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(space[i][j].isEmpty())
				{
					map[i][j]=0;
				}
				else {
					map[i][j]=space[i][j].getLives().who();
					
				}
			}
			
		}
		
	}
	
	public void go()
	{
		
		for(int i=0;i<mons.size();i++)
		{
			new Thread(mons.elementAt(i)).start();
		}
		for(int i=0;i<boys.size();i++)
		{
			new Thread(boys.elementAt(i)).start();
		}
		battle.win(this);
		
	}
	
	public void Outfile() throws IOException
	{
		BufferedWriter out=new BufferedWriter(new FileWriter("src/main/resources/replay/game.txt"));
		String s="";
		
		for(int i=0;i<boys.size();i++) {
			int a=boys.elementAt(i).who();
			s+=String.valueOf(a);
			s+=System.getProperty("line.separator");
		}
		
		for(int i=0;i<mons.size();i++) {
			int a=mons.elementAt(i).who();
			s+=String.valueOf(a);
			s+=System.getProperty("line.separator");
		}
		out.write(s);
		out.flush();
		out.close();
		
	}
	
	public void Infile() throws IOException
	{
		BufferedReader in=new BufferedReader(new FileReader("src/main/resources/replay/game.txt"));
		String s=null;
		
		clear();
		this.boys.removeAllElements();
		this.mons.removeAllElements();
		for(int i=1;i<=7;i++)
		{
			s=in.readLine();
			int a=Integer.parseInt(s);
			huluwa tmp=new huluwa(a);
			tmp.setMap(space);
			space[1][2+i].set(tmp);	
			this.boys.addElement(tmp);
			//space[6][i].set(boys.elementAt(i-1));
		}
		
		for(int i=1;i<7;i++)
		{
			s=in.readLine();
			int a=Integer.parseInt(s);
			if(a==11)
			{
				boss t1=new boss();
				t1.setMap(space);
				space[row-3][col-8].set(t1);;	
				this.Boss=t1;
				this.mons.addElement(t1);
			}else {
			mst t2=new mst();
			t2.setMap(space);
			space[row-3][col-8+i].set(t2);
			
			this.mons.addElement(t2);
			}
		}
		this.yeye=new cheer(0);
		this.yeye.setMap(space);
		space[1][1].set(yeye);
		
		this.snake=new cheer(1);
		this.snake.setMap(space);
		space[row-1][col-1].set(snake);
		
		in.close();
	}
	
	public void replay()
	{
		try {
			this.Infile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.changshe(this,this.getBoys(),2,3);
		list.yanxing(this,this.getMons(),10,10);
		
	}

}

