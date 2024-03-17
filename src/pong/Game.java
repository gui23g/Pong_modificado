package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends Canvas implements Runnable, KeyListener {
	
	//Game variables
	public static JFrame frame;
	private Thread thread;
	public boolean running;
	public static int WIDTH = 324, HEIGHT = 180, SCALE = 4;
	public BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	public static boolean dp=false;
	public static boolean lp=false;
	
	//Game State
	public static int gameState;
	public final static int titleState=0;
	public final static int playStatePVP=1;
	public final static int playStateCPU=2;
	public int cNumber=1;
	public boolean cNumberC= false, cNumberB=false, bm = false;
	
	
	
	//Class
	public static PlayerL player1;
	public static PlayerR player2;
	public static Ball ball;
	public static Enemy enemy;
	public static Sound sound;
	
	
	
	
	
	public Game() {
		//constructor
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addKeyListener(this);
		player1 = new PlayerL(0,(HEIGHT/2)-15);
		player2 = new PlayerR(WIDTH-12,(HEIGHT/2)-15);
		enemy = new Enemy(WIDTH-12,(HEIGHT/2)-15);
		ball = new Ball((WIDTH/2)-3,(HEIGHT/2)-3);
		sound = new Sound();

		

	}

	
	public void intFrame() { //Janela do jogo
		frame = new JFrame("Pong do BG"/*nome da janela*/);
		frame.add(this);
		frame.setResizable(false);//não permite que o usuario modifique o tamanho da janela
		frame.pack();//para calcular as dimensões
		frame.setLocationRelativeTo(null);//quando a abrir a janela abre no meio
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//quando clicar para fechar, o programa encerra junto
		frame.setVisible(true);//para abrir a janela
	}
	
	
	
	
	public synchronized void start(){
		intFrame();
		thread = new Thread(this);
		running = true;
		thread.start();
		gameState = titleState;
	}
	
	public synchronized void stop() { //caso o loop falha, ele vai encerrar o jogo
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) {
	Game game = new Game();
	
	game.start();
	
	}

	public void tick() {
		if(gameState == playStateCPU) {
			player1.tick();
			enemy.tick();
			ball.tick();
		}
		
		if(gameState == playStatePVP) {
			player1.tick();
			player2.tick();
			ball.tick();
		}

		if(bm == true && gameState != titleState){
			gameState = titleState;
			new Game();
			return;
		}

	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);//técnica de renderização para melhorar perfomance
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		
		//TELA DE SELECAO
		if(gameState == titleState) {
			int esp = 40;
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
			g.setFont(new Font("Arial",Font.BOLD,40));
			g.setColor(Color.GRAY);
			g.drawString("PONG", (((WIDTH/2)-10)*SCALE)+3, (((HEIGHT/2)-20)*SCALE)+3);
			g.setFont(new Font("Arial",Font.BOLD,40));
			g.setColor(Color.WHITE);
			g.drawString("PONG", ((WIDTH/2)-10)*SCALE, ((HEIGHT/2)-20)*SCALE);
			if(cNumber ==1) {
				g.setFont(new Font("Arial",Font.BOLD,20));
				g.setColor( new Color(32,178,170));
				g.drawString("Player VS Player", ((WIDTH/2)-10)*SCALE, (((HEIGHT/2)-20)*SCALE)+80);
			}else {
				g.setFont(new Font("Arial",Font.BOLD,20));
				g.setColor(Color.WHITE);
				g.drawString("Player VS Player", ((WIDTH/2)-10)*SCALE, (((HEIGHT/2)-20)*SCALE)+80);
			}
			
			if(cNumber ==2) {
				g.setFont(new Font("Arial",Font.BOLD,20));
				g.setColor( new Color(178,34,34));
				g.drawString("Player VS CPU", ((WIDTH/2)-10)*SCALE, (((HEIGHT/2)-20)*SCALE)+80+esp);
			}else {
				g.setFont(new Font("Arial",Font.BOLD,20));
				g.setColor(Color.WHITE);
				g.drawString("Player VS CPU", ((WIDTH/2)-10)*SCALE, (((HEIGHT/2)-20)*SCALE)+80+esp);
			}
			if(cNumberC == true) {
		
					cNumber--;
					if(cNumber<=1) {
						cNumber = 1;
					}
			}
			if(cNumberB == true) {
				cNumber++;
				if(cNumber>=2) {
					cNumber = 2;
				}
			}
			g.dispose();
		}
		
		if(gameState == playStateCPU) {
			
			player1.render(g);
			enemy.render(g);
			ball.render(g);
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
			g.dispose();
		}
		
		if(gameState == playStatePVP) {
			
			player1.render(g);
			player2.render(g);
			ball.render(g);
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
			g.dispose();
		}		
		

		 

		bs.show();

	}
	
	public void playS(int i) {
		
		sound.setFile(i);
		sound.play();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 10000.0; //teste
		double ns = 1000000000/amountOfTicks;
		double delta = 0; 
		while(running) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1){
				tick();
				render();
				delta--;
			}
	}
		stop();
		
	}


	
	@Override
	public void keyTyped(KeyEvent e) {
if(e.getKeyChar() == 'w') {
	cNumberC = true;
	player1.up=true;

}
if(e.getKeyChar() == 's'){
	cNumberB = true;
	player1.down=true;
}
if(e.getKeyChar() == 'd'){
	
	dp = true;
	
}
if(e.getKeyChar() == 'a'){
	
	player1.ac = true;
	
}
	}
		



	@Override
	public void keyPressed(KeyEvent e) {
		if(gameState == titleState) {
			if(e.getKeyCode() == 38) {
				cNumberC = true;
			}
			if(e.getKeyCode() == 40) {
				cNumberB = true;
			}
		}
		if(e.getKeyCode() == 38) {
			player2.up=true;
		}
		if(e.getKeyCode() == 40) {
		
			player2.down=true;
		}
		if(e.getKeyCode() == 37) {
			
			lp = true;
		
		}
		if(e.getKeyCode() == 39) {
			player2.ac = true;
		}
		
		if(e.getKeyCode() == 27) {
			bm = true;
		}
		
if(gameState == titleState) {
	if(e.getKeyCode() == 10 && cNumber == 1) {
		gameState = playStatePVP;
	}
	if(e.getKeyCode() == 10 && cNumber == 2) {
		gameState = playStateCPU;
	}
}

		
		}
	


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'w') {
			cNumberC = false;
			player1.up=false;

		}
		if(e.getKeyChar() == 's'){
			cNumberB = false;
			player1.down=false;
		}
		if(e.getKeyCode() == 38) {
			cNumberC = false;
			player2.up=false;
		}
		if(e.getKeyCode() == 40) {
			cNumberB = false;
			player2.down=false;
		}
		if(e.getKeyCode() == 37) {
			lp=false;
			ball.cont2=0;
		}
		if(e.getKeyChar() == 'd') {
			dp=false;
			ball.cont1=0;
		}
		if(e.getKeyChar() == 'a') {
			player1.ac = false;
		}
		if(e.getKeyCode() == 39) {
			player2.ac = false;
		}
		if(e.getKeyCode() == 27) {
			bm = false;
		}
		
}
}
