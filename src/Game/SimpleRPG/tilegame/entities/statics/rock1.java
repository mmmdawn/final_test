package Game.SimpleRPG.tilegame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import Game.SimpleRPG.tilegame.Handler;
import Game.SimpleRPG.tilegame.gfx.Assets;
import Game.SimpleRPG.tilegame.tiles.Tile;

public class rock1 extends StaticEntity {

	public rock1(Handler handler, float x, float y) {
		super(handler, x, y, 200, 200 );
		
		bounds.x =3;
		bounds.y =(int)(height/2f);
		bounds.width= width -6;
		bounds.height =(int)(height - height/2f);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock1,(int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.red);
		g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
				(int)(y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);
	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
