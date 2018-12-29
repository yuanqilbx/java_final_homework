package huluwa;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/*配置文件*/

public class Config {
	 //程序名称
    public static final String ApplicationName = "葫芦娃大战妖怪";
    //地图大小(maps下的地图大小)
    public static final int map_width = 1300;
    public static final int map_height = 800;
 
    public static final int ox=100;
    public static final int oy=150;
    
    public static String text=System.getProperty("line.separator");
    
    public static final String filePath="file:src/replay/game.txt";
    public static final String replayPath="src/replay/pic";
    public static final String storeDir=replayPath+File.separator+new SimpleDateFormat("yyyy:MM:dd.HH:mm:ss").format(new Date());
    
    public static final Image death = new Image("file:src/icon/death.png");
    public static final Image board = new Image("file:src/icon/map2.jpg");
    public static final Image h1 = new Image("file:src/icon/h1.png");
    public static final Image h2 = new Image("file:src/icon/h2.png");
    public static final Image h3 = new Image("file:src/icon/h3.png");
    public static final Image h4 = new Image("file:src/icon/h4.png");
    public static final Image h5 = new Image("file:src/icon/h5.png");
    public static final Image h6 = new Image("file:src/icon/h6.png");
    public static final Image h7 = new Image("file:src/icon/h7.png");
    public static final Image se = new Image("file:src/icon/se.png");
    public static final Image ye = new Image("file:src/icon/yeye.png");
    public static final Image boss = new Image("file:src/icon/xie.jpg");
    public static final Image mst = new Image("file:src/icon/low.png");
    public static final Image block = new Image("file:src/icon/stone.png");

    //布局大小
    public static final double pwidth = 40;
    public static final double pheight = 40;
    //行和列
    public static final int row=12;
    public static final int col=22;


	
}
