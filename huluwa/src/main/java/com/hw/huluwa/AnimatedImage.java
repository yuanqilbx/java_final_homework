package com.hw.huluwa;

import javafx.scene.image.Image;


public class AnimatedImage {
	public Image[] frames;
    public double duration;
    public int now;
    private int max;
     
    public AnimatedImage(int n)
    {
    	now=0;
    	max=n;
    	duration=0.100;
    	frames=new Image[n];
    	for(int i=0;i<n;i++)
  		{
  			frames[i] = new Image("file:src/main/resource/replay/pic/"+i+".png");
  			
  		}
  	   
    	
    }
   
    
    public Image getImg()
    {
    	return frames[now];
    }
    public void next()
    {
    	now++;
    	if(now>=max)
    		now=0;
    }
}

