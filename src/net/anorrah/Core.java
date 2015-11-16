package net.anorrah;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Core extends Applet implements Runnable 
{
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static final int res = 1;//pixel to frame
	public static double offset_Y = 0, offset_X = 0;
	public static int dir = 0;
	public static boolean moving = false;
	public static boolean running = false;
	public static boolean inGame = true;
	
	public final int TARGET_FPS = 60;
	public final long OPTIMAL_TIME = 1000000000/TARGET_FPS;
	public static long lastFPSTIME = 0;
	public static int fps = 0;
	public static int renderFPS = 0;
	
	public static boolean bW, bS, bA, bD, bE, bESC, bP;
	
	private Image screen;
	public static Dimension screenSize = new Dimension(672,544);
	public static Dimension pixel = new Dimension(screenSize.width,screenSize.height);
	public static Dimension Size;
	
	public static String name = "Prisoner of Anorrah";
	
	public static Level level;
	
	public static EntityPlayer player;
	public static Core t;
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> removethese = new ArrayList<Entity>();
	
	//Constructor
	public Core()
	{
		//InputManager inpt = new InputManager();
		setPreferredSize(screenSize);
		addKeyListener(new InputManager());
		//addKeyListener(inpt);
		//addMouseListener(inpt);
	}
	
	public static void main(String[] args) 
	{
		t = new Core();
		
		frame = new JFrame();
		frame.add(t);
		frame.pack();
		
		Size = new Dimension(frame.getWidth(),frame.getHeight());
		
		frame.setTitle(name);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);//centered
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		t.start();
	}
	
	public void start()
	{
		requestFocus();
		
		//Class declarations here
		level = new Level(1);
		new Tile();
		initEntities();
		trackplayer(player,level);
		running = true;
		new Thread(this).start();
	}
	
	public void stop()
	{
		running = false;
	}
	
	public void initEntities()
	{
		player = new EntityPlayer(t, 
				(screenSize.width / 2) - (Tile.size / 2) + offset_X, 
				(screenSize.height / 2) - (Tile.size / 2) + offset_Y,Tile.size,Tile.size);
		entities.add(player);
	}
	
	public void remove(Entity entity)
	{
		removethese.add(entity);
	}
	
	public void trackplayer(EntityPlayer p, Level l)//confirmed it works
	{
		for(int i = 0; i < l.width; i++)
		{
			for(int j = 0; j < l.height; j++)
			{
				if(l.solid[i][j].x == p.Rx && l.solid[i][j].y == p.Ry)
				{
					System.out.println(i + " " + j);
					p.setTilePosition(i, j);
					break;
				}
			}
		}
	}
	
	public void tick(double delta)
	{
		frame.pack();
		
		if(inGame)
		{
			for(int i = 0; i < entities.size(); i++)
			{
				Entity ent = entities.get(i);
				ent.move(delta);
			}
			
			for(int i = 0; i < entities.size(); i++)
			{
				Entity thisobj = entities.get(i);
				for(int j = 0; j < entities.size(); j++)
				{
					Entity otherobj = entities.get(j);
					
					if(thisobj.collides(otherobj))
					{
						thisobj.on_collided(otherobj);
						otherobj.on_collided(thisobj);
					}
				}
			}
		}
		
		entities.remove(removethese);
		removethese.clear();
	}
	
	
	public void render()
	{
		Graphics g = screen.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, screenSize.width, screenSize.height);
		level.render(g, (int)offset_X, (int)offset_Y, (pixel.width/Tile.size), (pixel.height/Tile.size));
		
		for(int i = 0; i < entities.size(); i++)
		{
			Entity ent = entities.get(i);
			ent.render(g);
		}
		
		g.setColor(Color.orange);
		g.drawString("offset_X: " + (int)offset_X , 590, 515);
		g.drawString("offset_Y: " + (int)offset_Y , 590, 530);
		g.drawString("FPS: " + renderFPS, 600, 545);
		
		g = this.getGraphics();
		g.drawImage(screen, 0, 0, screenSize.width, screenSize.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();//reset the image each tick
	}
	
	public void run() 
	{
		screen = createVolatileImage(pixel.width,pixel.height);
		long last_loop_time = System.nanoTime();
		while(running)
		{
			long now = System.nanoTime();
			long update_length = now - last_loop_time;
			last_loop_time = now;
			
			double delta = update_length / (double)OPTIMAL_TIME;
			lastFPSTIME += update_length;
			fps++;
			if(lastFPSTIME >= 1000000000)
			{
				renderFPS = fps;
				fps = 0;
				lastFPSTIME = 0;
			}
			
			tick(delta);
			render();
			
			try//give the thread some time to calculate
			{
				Thread.sleep((last_loop_time - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage() + " lol");
			}
		}
	}
	

}
