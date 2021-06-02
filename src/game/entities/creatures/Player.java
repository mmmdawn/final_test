package game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Handler;
import game.entities.Entity;
import game.gfx.Animation;
import game.gfx.Assets;

public class Player extends Creature {
	
	private Animation animationDown, animationUp, animationLeft, animationRight, animationIdle;
	private Animation attack_left, attack_right, attack_down;
	
	//attack timer
	
	private long  lastAttackTimer, attackCooldown = 100, attackTimer = attackCooldown;
	
	//inventory
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		bounds.x = 25;
		bounds.y = 37;
		bounds.width = 20;
		bounds.height = 15;
		
		//animation
		animationDown = new Animation(100, Assets.playerDown);
		animationUp = new Animation(100, Assets.playerUp);
		animationLeft = new Animation(100, Assets.playerLeft);
		animationRight = new Animation(100, Assets.playerRight);
		animationIdle = new Animation(100, Assets.playerIdleRight);
		attack_left = new Animation(500, Assets.attack_left);
		attack_right = new Animation(500, Assets.attack_right);
		attack_down = new Animation(500, Assets.attack_down);
		
		
		
	
	}

	@Override
	public void tick() {
		
		// Animations
		animationDown.tick();
		animationUp.tick();
		animationLeft.tick();
		animationRight.tick();
		animationIdle.tick();
		
		attack_left.tick();
		attack_right.tick();
		attack_down.tick();
		//movement
		getInput();
		move();
		handler.getGameCamera().setCameraPosition(this);
		//attack
		checkAttacks();
		
		
	}
	
	public void checkAttacks() {
		
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp) {
			ar.x = cb.x + cb.width/2 - arSize/2;
			ar.y = cb.y -arSize;
			attack = 0;
		}
		else if(handler.getKeyManager().aDown) {
			ar.x = cb.x + cb.width/2 - arSize/2;
			ar.y = cb.y + cb.height;
			attack = 3;
		}else if(handler.getKeyManager().aLeft) {
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height/2 -arSize/2 ;
			attack = 1;
		}else if(handler.getKeyManager().aRight) {
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height/2 -arSize/2 ;
			attack = 2;
		}else {
			attack = 0;
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return;
			}
		}
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		
		if(handler.getKeyManager().up)
			yMove -= speed;
		if(handler.getKeyManager().down)
			yMove += speed;
		if(handler.getKeyManager().left)
			xMove -= speed;
		if(handler.getKeyManager().right)
			xMove += speed;	
	}
	public void die() {
		System.out.println("you lose!!");
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(),(int)(x - handler.getGameCamera().getxOffset()),
				(int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		
		// dev mode :D 
		g.setColor(Color.red);
		g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
				(int)(y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			return animationLeft.getCurrentFrame();
		}else if(xMove > 0) {
			return animationRight.getCurrentFrame();
		}else if(yMove < 0) {
			return animationUp.getCurrentFrame();
		}else if(yMove > 0){
			return animationDown.getCurrentFrame();
		}else if(attack ==1 ) {
			return attack_left.getCurrentFrame();
		}else if(attack == 2) {
			return attack_right.getCurrentFrame();
		}else if(attack == 3) {
			return attack_down.getCurrentFrame();
		}else if(xMove == 0 && yMove == 0 && attack == 0){
			return animationIdle.getCurrentFrame();
		}
		else return null;
	}

	
	
}
