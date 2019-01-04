package com.hw.huluwa;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.DirectoryChooser;


public class Main extends Application {
	private Canvas canvas;
	private GraphicsContext gc;
	private tree mine;
	private int[][] curmap;
	private int win;
	private int mytime=0;
	private int thetime;
	private int picNum=0;
	private BorderPane root;
	private int now;
	String fname="file:src/main/resources/replay/pic";
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		win=0;
		mytime=0;
		now=0;
		thetime=0;
		
		//in=new FileInputStream(Config.filePath);
		 ArrayList<String> input = new ArrayList<String>();
		 
		curmap=new int[Config.row][Config.col];
		mine=new tree(Config.row,Config.col);
		mine.start();
		mine.updateMap(curmap);
		
		primaryStage.setTitle(Config.ApplicationName);
		
		root=new BorderPane();
		//root.setPadding(new Insets(10,20,10,20));
		BorderPane gp=new BorderPane();
		Scene scene = new Scene(gp, Config.map_width, Config.map_height);
		scene.setFill(null);//透明
		
		
		MenuBar mbar=new MenuBar();
		mbar.prefWidthProperty().bind(primaryStage.widthProperty());
		
		
		Menu m1=new Menu("游戏");
		MenuItem m11=new MenuItem("开始");
		m11.setOnAction(actionEvent->{
			input.add("SPACE");			
		});
		MenuItem m12=new MenuItem("结束");
		m12.setOnAction(actionEvent->Platform.exit());
		MenuItem m13=new MenuItem("暂停");
		m13.setOnAction(actionevent->{input.add("S");});
		
		m1.getItems().addAll(m11,new SeparatorMenuItem(),m12,new SeparatorMenuItem(),m13);
		mbar.getMenus().add(m1);
		//root.setRight(mbar);
		root.setTop(mbar);
		
	    Label lbd=new Label();
	    lbd.setGraphic(new ImageView(Config.board));
	    gp.setCenter(lbd);
	    
	    Label  ltt=new Label("回合：0");
	    ltt.setFont(new Font("Arial", 15));
	    ltt.setPrefWidth(150);
	    ltt.setMinWidth(150);
	    ltt.setWrapText(true);
	    ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ltt);
	    
	    root.setLeft(scrollPane);
	   
	    
	    scene.setOnKeyPressed(
	            new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    String code = e.getCode().toString();
	                    if(code=="N")//新建
	                    {
	                    	win=0;
	                		mytime=0;
	                		curmap=new int[Config.row][Config.col];
	                		mine=new tree(Config.row,Config.col);
	                		mine.start();
	                		mine.updateMap(curmap);
	                		picNum=0;
	                		now=0;
	                    	
	                    }
	                    if(code=="L")//回放
	                    {
	                    	
	                    }
	                    
	                    if ( !input.contains(code) )
	                        input.add( code );
	                }
	            });
	 
	        scene.setOnKeyReleased(
	            new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    String code = e.getCode().toString();
	                    if(code!="SPACE" && code !="L" )
	                    	input.remove( code );
	                }
	            });
	    
		canvas=new Canvas(Config.map_width,Config.map_height);
		
		gc = canvas.getGraphicsContext2D();
	    root.setCenter(canvas);	
	    gp.getChildren().add(root);
	    
	    
	   
	    
	    
	    final long startNanoTime = System.nanoTime();
	    
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	
	        	double dt=(currentNanoTime - startNanoTime)/1000000000.0;//1s 
	        	int dt1= (int) (dt*10);
	        	if(input.contains("S"))
                {
                	input.remove("SPACE");
                	input.remove("S");
                }
	        	if(dt1==thetime )
	        	{
	        		return;
	        	}else
	        	{
	        		thetime=dt1;
	        	}
	        	if(input.contains("L"))
	        	{
	        		if(now==0)
	        		{
	        			DirectoryChooser Chooser = new DirectoryChooser();
	        			Chooser.setTitle("Open Resource File");
	        			File myfold=Chooser.showDialog(primaryStage);
	        			//System.out.println(myfold.getPath());
	        			fname=myfold.getAbsoluteFile().toURI().toString();
	        			picNum=myfold.list().length;
	        		}
	        		if(now>=picNum)
	        			input.remove("L");
	        		String ms=Integer.toString(now);
	        		Image imgv=new Image(fname+"/"+ms+".png");
	        		gc.clearRect(0, 0, 1300,800);
	        		gc.drawImage(imgv, 0, 0,860,600);
	        		now++;
	        		return;
	        	}
	        
	        	if(input.contains("SPACE")) {
	        		
	        		mytime++;
	        		ltt.setText("回合："+mytime+Config.text);
	        		drawMap();
	        		if(win==-1)
		        	{
	        			Alert information=new Alert(Alert.AlertType.INFORMATION,"妖怪胜利！");
	        			information.setTitle("战斗结束"); 
	        			information.show();
	        			Config.text=Config.text+"妖怪胜利！"+System.getProperty("line.separator");
	        			ltt.setText("回合："+mytime+Config.text);
	        			
	        			//System.out.println("妖怪胜利！");		        		
		        		input.remove("SPACE");
		        		saveMap();
		        		return;
		        	}
		        	else if(win==1)
		        	{
		        		
		        		
		        		Alert information=new Alert(Alert.AlertType.INFORMATION,"葫芦娃胜利！");
	        			information.setTitle("战斗结束"); 
	        			information.setHeaderText(null);
	        			try {
	        				information.show();
	        			}catch(Exception e)
	        			{
	        				e.printStackTrace();
	        			}
	        			Config.text=Config.text+"葫芦娃胜利！"+System.getProperty("line.separator");
	        			ltt.setText("回合："+mytime+Config.text);
	        			//System.out.println("葫芦娃胜利！");
		        		input.remove("SPACE");
		        		
		        		saveMap();
		        		return;
		        	}
	            	mine.go();
	            	win=battle.win(mine);
	            	//input.remove("SPACE");
	            }
	        }
	    }.start();
	    
	   
	    
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
		
	}
	
	
	//绘制地图
		 public void drawMap() 
		 {

			 	gc.clearRect(0, 0, Config.map_height,Config.map_width);
		        Image image;
		        
		        mine.updateMap(curmap);
		        for (int i = 0; i < Config.row; i++) {//行
		            for (int j = 0; j < Config.col; j++) {//列

		                image = Config.block;//初始图片
		                double W = Config.pwidth;
		    	        double H = Config.pheight;
		                //绘制地板
		    	        gc.drawImage(image,Config.ox+ W * j,Config.oy+H * i, W, H);

		                switch (curmap[i][j]) {
		                	case -1:
		                		image=Config.death;break;
		                	case 0://绘制地板
		                		continue;
		                		//break;
		                	case 1://绘制1
		                        image = Config.h1;
		                        break;
		                	case 2://绘制
		                        image = Config.h2;
		                        break;
		                	case 3://绘制
		                        image = Config.h3;
		                        break;
		                	case 4://绘制
		                        image = Config.h4;
		                        break;
		                	case 5://绘制
		                        image = Config.h5;
		                        break;
		                	case 6://绘制
		                        image = Config.h6;
		                        break;
		                	case 7://绘制
		                        image = Config.h7;
		                        break;
		                    case 22://绘制蛇精
		                        image = Config.se;
		                        break;
		                    case 21://绘制爷爷
		                        image = Config.ye;
		                        break;
		                    case 11://绘制蝎子
		                        image = Config.boss;
		                        break;
		                    case 12://绘制小怪
		                        image = Config.mst;
		                        break;
		                    default:
		                        break;
		                }
		                
		                //每个图片不一样宽 需要在对应地板的中心绘制地图
		                gc.drawImage(image,Config.ox+ W * j,Config.oy+H * i, W, H);

		            }
		        }
		        saveMap();
		    }

		 public void saveMap()
		 {
			 try {
					captureScreen(Config.storeDir);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		 }
		 
		//截图 并保存
		public void captureScreen(String folder)throws Exception{
			 
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd.HH:mm:ss");//可以方便地修改日期格式
//			String fileName = dateFormat.format( new Date() ); 
//			String storeDir=folder+File.separator+fileName;
			
			//获得屏幕大小并创建一个Rectangle(区域)
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);
			//创建包含从屏幕中读取的像素的图像
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			//保存路径
			File screenFile = new File(folder);
			if(!screenFile.exists()) {
				screenFile.mkdir();
			}
			File f = new File(screenFile,Integer.toString(picNum)+".png");
			picNum++;
			//决定了f为文件，将图像1以.png格式写入文件f
			ImageIO.write(image, "png", f);
			
		}
	
		
		
}