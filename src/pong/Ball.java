package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JLabel;

public class Ball {
	
	
	public double x,y;
	public double dx,dy, speed=0.6;
	public int width, height;
	public boolean atk1 = false,atk2 = false;
	public int cont1 = 0, cont2=0;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;
		int angle = new Random().nextInt(120 - 45) + 45 + 1;
		dx = Math.sin(Math.toRadians(angle));
		dy = Math.cos(Math.toRadians(angle));
	
	}
	
	
	public void tick() {
		
		
		if(y+(speed*dy)+height>Game.HEIGHT || y+(speed*dy)<0) {
			dy = dy*-1;
			Game.sound.playS(0);
		}
		
		if(x>=Game.WIDTH || x<=0) {
			new Game();
			Game.sound.playS(1);
			return;
		}
		
		Rectangle bounds = new Rectangle((int)x,(int)y,width,height);
		Rectangle b_player1 = new Rectangle(Game.player1.x,Game.player1.y,Game.player1.widht,Game.player1.height);
		Rectangle b_player2 = new Rectangle(Game.player2.x,Game.player2.y,Game.player2.widht,Game.player2.height);
		Rectangle b_enemy = new Rectangle((int)Game.enemy.x,(int)Game.enemy.y,Game.enemy.widht,Game.enemy.height);
		
		if(Game.gameState == Game.playStateCPU) {
			if(bounds.intersects(b_player1)||bounds.intersects(b_enemy)) {
				speed+=0.2;
				dx*=-1;
				Game.sound.playS(0);
			}
		}
		if(Game.gameState == Game.playStatePVP) {
			if(bounds.intersects(b_player1) || bounds.intersects(b_player2)) {
				speed+=0.2;
				dx*=-1;
				Game.sound.playS(0);
			}
		}
		x+=dx*speed;
		y+=dy*speed;
		
		if(Game.dp == true) {
			cont1++;
		}
		if(cont1 <=7 && cont1 > 0 && bounds.intersects(b_player1) ) {
			speed*=2;
		}
		
		if(Game.lp == true) {
			cont2++;
		}
		if(cont2 <=7 && cont2 > 0 && bounds.intersects(b_player2) ) {
			speed*=2;
		}
		
		
		
	}

	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval((int)x, (int)y, width, height);
		
		
		
	}
}
