package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	public int widht,height;
	public double x,y;
	public boolean up,down;
	
	
		
		public Enemy(int x,int y) {
			this.x = x;
			this.y = y;
			this.widht = 3*Game.SCALE;
			this.height = 10*Game.SCALE;
			
		}
		


		public void tick() {
		if(Game.ball.dy>0) {
			y++;
			if(y>Game.ball.y) {
				y--;
			}
		}else
		if(Game.ball.dy<0){
			y--;
			if(y<Game.ball.y) {
				y++;
			}			
		}
			
			if(y+height > Game.HEIGHT){ //colisoes
				y = Game.HEIGHT - height;
		
			}
			if(y<0) {
				y=0;
			}
			
		
		}
		
		public void render(Graphics g) {
			g.setColor(Color.RED);
			g.fillRect((int)x,(int) y, widht, height);
		}
}
