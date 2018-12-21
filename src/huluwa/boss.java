package huluwa;
import java.awt.*;
import javax.swing.*;

public class boss extends lives{
	private String name;
	
	
	public boss()
	{
		this.name="蝎子精";
		this.InitPos(null);
		this.setSpeed(2, 2);
		this.setIn(false);
		this.setF(0, -1);
		
		//ImageIcon ic=new ImageIcon("src/icon/boss.png");
		//ic.setImage(ic.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		//JLabel tl=new JLabel(ic);
		//tl.setSize(ic.getIconWidth(), ic.getIconHeight());
		//this.InitThing(tl);
	}
	
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
		System.out.print("蝎");
	}
	@Override
	public int who()
	{
		if(ifdeath())
			return -1;
		return 11;
	}
	
	@Override
	public void go()
	{
		if(ifdeath())
			return;
		lives em=seek();
		if(em!=null)
		{
			int as=em.getX()-getX(),ad=em.getY()-getY();
			this.setF(battle.F(as), battle.F(ad));
		}
		
		int a=this.getX()+getFx()*this.getXV(), b=this.getY()+getFy()*getYV();
		if(battle.inSpace(a,b))
			this.getmap()[a][b].set(this);
		
	}
	
	
	@Override
	public void run()
	{
		try {
			//Thread.sleep(3000);
			this.go();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
