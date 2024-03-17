package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PlayerR {
	
public int x,y,widht,height;
public boolean up,down,ac;
	
	public PlayerR(int x,int y) {
		this.x = x;
		this.y = y;
		this.widht = 3*Game.SCALE;
		this.height = 10*Game.SCALE;
		
	}
	


	public void tick() {
		if(up == true){
			y--;
		}
		
		if(down == true) {
			y++;
		}
		if(ac == true && up == true) {
			y-=5;
			
			
			
		}else if (ac == true && down == true) {
			y+=5;
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
		g.fillRect(x, y, widht, height);
	}
}
