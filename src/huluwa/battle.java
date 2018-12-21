package huluwa;

public class battle{
	
	public static int vs(lives a,lives b)
	{
		int r=(int) Math.random();
		if(a.who()<10 && b.who()==12)
			r=0;
		else if(a.who()<10 && b.who()==11)
			r=r/3;
		else if(b.who()<10 && a.who()==12)
			r=0;
		else if(b.who()<10 && a.who()==11)
			r=r/3;
		if(r==0)
			return 1;
		else if(r==1)
			return -1;
		else
			return 0;
		
	}
	
	public static boolean inSpace(int x, int y) {
	    if (x >= 0 && x < Config.row && y >= 0 && y < Config.col)
	      return true;
	    else return false;
	  }
	
	public static boolean inSpace(where tpos) {
	    int x=tpos.getX(),y=tpos.getY();
	    if (x >= 0 && x < Config.row && y >= 0 && y < Config.col)
		      return true;
		    else return false;
	  }
	
	/*判断死亡者*/
	public static void death(tree all)
	{
		for(int i=0;i<all.getBoys().size();i++)
		{
			if(all.getBoys().elementAt(i).ifdeath()) {
				System.out.println(all.getBoys().elementAt(i).name()+" 战死");
				//all.getBoys().remove(i);
				}
		}
		for(int i=0;i<all.getMons().size();i++)
		{
		
			if(all.getMons().elementAt(i).ifdeath()) {
				System.out.println(all.getMons().elementAt(i).name()+" 战死");
				//all.getMons().remove(i);
				}
		}
	}
	
	/*判断胜负*/
	public static int win(tree all)
	{
		/*for(int i=0;i<all.getMons().size();i++)
		{
			lives t=all.getMons().elementAt(i);
			if(t.who()==21) {
				if(t.ifdeath())
					return 1;
				}
		}
		for(int i=0;i<all.getBoys().size();i++)
		{
			if(! all.getBoys().elementAt(i).ifdeath()) {
				return 0;
				}
		}
		return -1;*/
		if(all.getYeye().ifdeath())
			return -1;
		else if(all.getSnake().ifdeath())
			return 1;
		return 0;
	}
	
	public static int F(int a)
	{
		if(a>0)
			return 1;
		else if(a==0)
			return 0;
		else return -1;
	}

}
