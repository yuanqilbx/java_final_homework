package huluwa;

public class where {
	
	private int x,y;
	private lives on;
	
	public where(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.on=null;
		
	}
	
	public int getX() {
		return this.x;}
	public int getY() {return this.y;}
	public lives getLives() {return this.on;}
	
	public void newX(int x) {this.x=x;}
	public void newY(int y) {this.y=y;}
	
	public void set(lives one)
	{
		
		if(this.on!=null)
		{
			if(this.on.ifdeath() )
			{
				
				int a=x+one.getFx()*one.getXV(), b=y+one.getFy()*one.getYV();
				if(battle.inSpace(a,b))
					this.on.getmap()[a][b].set(one);
				return;
			}
			if( this.on.getIn() == one.getIn())
				return;
			
			int r=battle.vs(one, this.on);
			if(r>0)
			{	
				//this.on.cleanPos();
				this.on.death();
				System.out.println(one.name()+"VS"+this.on.name()+" 结果:"+one.name()+"胜利！");
			}
			else {
				System.out.println(one.name()+"VS"+this.on.name()+" 结果:"+on.name()+"胜利！");
				one.death();
				//death td=new death(this.on);
			}
		}else {
		this.on=one;
		one.remove();
		one.setPos(x, y);
		}
	}
	
	public void cleanOn()
	{
		if(this.on!=null)
			this.on.cleanPos();
		this.on=null;
	}
	
	
	public boolean isEmpty() {return this.on==null;}
	

}
