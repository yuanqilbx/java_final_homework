# 项目详情
---
游戏运行开始，葫芦娃在左侧按照长蛇阵列队，妖怪在右侧按照雁行阵列队，爷爷和蛇精分别站在地图的左上角和右下角。
![](/mdpic/start.png)

- 按下空格键后，游戏开始自动运行。葫芦娃和妖怪将按照编写的寻路方法寻找最近的敌人进行战斗。同时，左侧边栏会显示游戏的战斗信息。
![avatar](/mdpic/ing.png)

- 自动战斗直至一方阵营全部死亡后，宣告游戏结果。用消息框显示。
![avatar](/mdpic/end.png)

- 按下s键，游戏将会暂停，再次按下空格键后，继续运行。
- 按下n键，游戏会重开一局。
- 按下L键，会播放选择的游戏录像。
- 也可以通过顶部菜单栏的开始、结束、暂停来控制游戏进程。

---
## 系统设计
生物：
live是游戏中所有角色的基类
- 定义了属性有：pos（当前位置）、 map（地图）、 hp（生命值）、x(y)speed（某轴上的速度）、x(y)g（某轴上的方向）
- 定义了方法：set...（设置私有变量）get...（得到私有变量的值）ifdeath（判断生物生死）remove（离开当前位置）seek（寻找敌人） go（前进） tell（控制台输出自身位置） name（返回生物名字） print（控制台打印自己标志） who（返回自己身份）

huluwa boss mst cheer 都继承了 lives，但根据设定有些属性初始化值不同
- huluwa 移动速度为1，攻击力为1，生命值为1
- boss 移动速度为2，攻击力为1，生命值为3
- mst 移动速度为1，攻击力为0，生命值为1
- cheer 移动速度为0，攻击力为0，生命值为1，作为各个阵营的吉祥物存在

UML图：
![avatar](/mdpic/uml.png)

空间：
- 在类 where 中，定义坐标x，y，以及存在的角色 on，定义了角色变动的方法set,cleanOn;
- 在类 tree 中，定义了 col,row 限制空间大小、以及二维数组 where[ ][ ] space。
- 在类 list 中，只定义了static 的方法changshe、heyi、yanxing 等阵法。可以直接调用方法。

---
## 功能实现
### 地图绘制
我开始在底层放置了一层背景，在背景上用canvas放置角色图片。但我发现我选择的背景和角色图片容易弄混在一起，显示效果不好。所以我最后决定用地块显示除角色外的地图。

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
		                    /*……*/
		                    default:break;
		                }
		                
		                //每个图片不一样宽 需要在对应地板的中心绘制地图
		                gc.drawImage(image,Config.ox+ W * j,Config.oy+H * i, W, H);

		            }
		        }
		        saveMap();
		    }


### 物体移动
为了实现地图上生物体的移动，需要实现游戏动态化。这意味着游戏状态会随着时间而变化。我实现了一个游戏循环：一个无限循环，它可以更新游戏对象并将场景渲染到屏幕上，我设定的情况下是每秒6次。

我选择使用AnimationTimer类，其中handle()可以编写一个方法（命名），该方法将以每秒60次的速率调用，或尽可能接近该速率。

    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	
	        	double dt=(currentNanoTime - startNanoTime)/1000000000.0;//1s 
	        	int dt1= (int) (dt*2);
	        	if(dt1==mytime )
	        	{
	        		return;
	        	}else
	        	{
	        		mytime=dt1;
	        	}
	        	/*若干代码*/
	        }
	    }.start();



### 复现战斗过程
一开始我打算保存初始的角色站位、生命值等初始设定在一个txt 文件里，复现时从文件中读取，初始化。后来发现复现结果不同。
因为我使用了多线程编程，由于只有一个 CPU，所以是随机分配时间片，导致攻击、移动会出现随机性。所以我选择使用截图保存在以当时系统时间为区分的目录下。读取时，选择直接读取目录，然后判断目录下图片数目，然后每秒播放6张。
主循环在每次刷新屏幕时都截图并且保存：

    public void captureScreen(String folder)throws Exception{
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

播放录像时：

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

---
## 结束语

感谢一学期以来，两位老师认真的上课教学和助教帮助我们审核作业，指出可以改进的地方。经过 Java 课布置的大作业，我对如何使用 GitHub 和 Java 语言编写比较大的代码有了新的理解和体会。



