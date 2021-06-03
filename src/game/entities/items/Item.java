package game.entities.items;

import java.awt.Graphics;

import game.Handler;
import game.entities.Entity;
import game.gfx.Animation;

public abstract class Item extends Entity{
	protected static final int ITEM_SIZE = 64;
	protected Animation animation;
	
	protected abstract void makeEffect();
	
	public Item(Animation animation, Handler handler, float x, float y) {
		// TODO Auto-generated constructor stub
		super(handler, x, y, ITEM_SIZE, ITEM_SIZE);
		this.animation = animation;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animation.tick();
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(0f, 0f))) {
			this.hurt(1);
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		makeEffect();
	}

}
