package huluwa;
import huluwa.pic.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.util.*;
import huluwa.pic.space;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;

public class Main extends Application {
	private Canvas canvas;
	private GraphicsContext gc;
	private tree mine;
	private int[][] curmap;
	private int win;
	private int mytime=0;
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		win=0;
		mytime=0;
		
		curmap=new int[Config.row][Config.col];
		mine=new tree(Config.row,Config.col);
		mine.start();
		mine.updateMap(curmap);
		
		primaryStage.setTitle(Config.ApplicationName);
		
		BorderPane root=new BorderPane();
		//root.setPadding(new Insets(10,20,10,20));
		BorderPane gp=new BorderPane();
		Scene scene = new Scene(gp, Config.map_width, Config.map_height);
		scene.setFill(null);//透明
		
		
		MenuBar mbar=new MenuBar();
		mbar.prefWidthProperty().bind(primaryStage.widthProperty());
		
		
		Menu m1=new Menu("游戏");
		MenuItem m11=new MenuItem("开始");
		MenuItem m12=new MenuItem("结束");
		m12.setOnAction(actionEvent->Platform.exit());
		
		m1.getItems().addAll(m11,new SeparatorMenuItem(),m12);
		mbar.getMenus().add(m1);
		//root.setRight(mbar);
		root.setTop(mbar);
		
	    Label lbd=new Label();
	    lbd.setGraphic(new ImageView(Config.board));
	    gp.setCenter(lbd);
	    
	   
	    
	    ArrayList<String> input = new ArrayList<String>();
	    scene.setOnKeyPressed(
	            new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    String code = e.getCode().toString();
	 
	                    // only add once... prevent duplicates
	                    if ( !input.contains(code) )
	                        input.add( code );
	                }
	            });
	 
	        scene.setOnKeyReleased(
	            new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    //String code = e.getCode().toString();
	                    //input.remove( code );
	                }
	            });
	    
		canvas=new Canvas(Config.map_width,Config.map_height);
		
		gc = canvas.getGraphicsContext2D();
	    root.setCenter(canvas);	
	    gp.getChildren().add(root);
	    
	    //gp.getChildren().add(canvas);
	    
	    final long startNanoTime = System.nanoTime();
	    
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	
	        	double dt=(currentNanoTime - startNanoTime)/1000000000.0;//1s 
	        	int dt1=(int) dt;
	        	if(dt1==mytime )
	        	{
	        		mytime=dt1;
	        		return;
	        	}
	        	
	        	if(input.contains("SPACE")) {
	        		drawMap();
	        		if(win==-1)
		        	{
	        			Alert information=new Alert(Alert.AlertType.INFORMATION,"妖怪胜利！");
	        			information.setTitle("战斗结束"); 
	        			information.show();
		        		System.out.println("妖怪胜利！");		        		
		        		input.remove("SPACE");
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
		        		System.out.println("葫芦娃胜利！");
		        		input.remove("SPACE");
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
		        
		    }

}