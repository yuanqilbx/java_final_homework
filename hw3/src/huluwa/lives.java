package huluwa;


import javax.swing.*;

public abstract class lives implements Runnable {
	
	private where pos;
	//private JLabel thing;
	private int xspeed=1;
	private int yspeed=1;
	private where[][] map;
	private int vsight;
	private boolean in=true;
	private int hp=1;
	private int xg=0;
	private int yg=0;
	
	
	public int getHp() {return hp;}
	public void setHp(int a) {hp=a;}
	public void death() {
		hp--;
		if(hp<0)
			hp=0;
	}
	public boolean ifdeath() {return hp==0;}
	
	public void setF(int a,int b)
	{xg=a;yg=b;}
	public int getFx() {return xg;}
	public int getFy()	{return yg;}
	
	public int getX() {
		if(pos==null)	return -1;
		return this.pos.getX();}
	public int getY() {
		if(pos==null)	return -1;
		return this.pos.getY();}
	public int getXV() {return xspeed;}
	public int getYV() {return yspeed;}
	public int getVsight() {return vsight;}
	public void setPos(int a,int b)
	{
		this.pos=map[a][b];
	}
	public where getPos() {return this.pos;}
	//public JLabel getThing() {return this.thing;}
	public void cleanPos() {this.pos=null;}//changeLoc();}
	public void InitPos(where tpos) {this.pos=tpos;}
	//public void InitThing(JLabel tl) {this.thing=tl;}
	public where[][] getmap() {return this.map;}
	public where getAmap(int a,int b) {return map[a][b];}
	
	public void setIn(boolean a) {in=a;}
	public boolean getIn() {return in;}
	
	public void setSpeed(int a,int b)
	{
		xspeed=a;
		yspeed=b;
	}
	
	public void setVsight(int a) {vsight=a;}
	
	public void setMap(where[][] space)
	{
		
		map=space.clone();
	}
	public void remove() {
		if(pos==null)
			return;
		else
			pos.cleanOn();
	}

	
	public lives seek()
	{
		lives near=null;
		int mil=10000;
		for(int i=0;i<Config.row;i++)
		{
			for(int j=0;j<Config.col;j++)
			{
				if(map[i][j].getLives()!=null && !map[i][j].getLives().ifdeath()  && map[i][j].getLives().getIn() != this.in)
				{
					int t=Math.abs(i-getX())+Math.abs(j-getY());
					if(t<mil) {
						mil=t;
						near=map[i][j].getLives();
					}
				}
			}
		}
		return near;		
	}
	
	public abstract void go();
	
	
	public int Juli(int p,int s)
	{
		p=Math.abs(p);
		int pn=p/s;
		int pm=p&s;
		if(pn==0)
			return pm;
		else if(pm==0)
			return s;
		else
			return pm;
	}
	
	public abstract void tell();
	public abstract String name();
	public abstract void print();
	public abstract int who();
	
	
	
}
