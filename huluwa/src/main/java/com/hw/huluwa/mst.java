package com.hw.huluwa;


enum MonsterClass {Snake,Scorpions,Other}

public class mst extends lives{
	private int no;
	private String name;
	
	public mst()
	{
		this.name="小喽啰";
		this.setIn(false);
		this.setF(0, -1);
		//this.InitThing(null);
		//ImageIcon ic=new ImageIcon("src/icon/low.png");
		//ic.setImage(ic.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		//JLabel tl=new JLabel(ic);
		//tl.setSize(ic.getIconWidth(), ic.getIconHeight());
		//this.InitThing(tl);
	}
	
	
	public int getNo()
	{return this.no;}
	
	@Override
	public void tell()
	{
		System.out.println(this.name()+" in "+this.getX()+ ","+this.getY());
		
	}
	
	@Override
	public String name()
	{
		return this.name;
	}
	@Override
	public void print()
	{
		System.out.print("怪");
	}
	
	@Override
	public int who()
	{
		if(ifdeath())
			return -1;
		return 12;
	}

	@Override
	public void go()
	{
		if(this.ifdeath())
			return;
		int a=this.getX()+getFx()*this.getXV(), b=this.getY()+getFy()*getYV();
		if(battle.inSpace(a,b))
			this.getmap()[a][b].set(this);
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.go();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
