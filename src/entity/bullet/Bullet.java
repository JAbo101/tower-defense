package entity.bullet;

import static gui.Game.TILE_SIZE;

import entity.Entity;
import entity.enemy.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public abstract class Bullet extends Entity {
	
	int speed, damage;
	Enemy enemy;
	
	double rotationAngle = 0;
	
	Image img;
	Image flames;
	
	public Bullet(double x, double y, Enemy enemy, int speed, int damage) {
		super(x, y);
		this.enemy = enemy;
		this.speed = speed;
		this.damage = damage;
	}

	@Override
	public void draw(GraphicsContext gc) {
		
		double xi = getX() - TILE_SIZE / 2;
		double yi = getY() - TILE_SIZE / 2;
		
		double dy = (double) TILE_SIZE * .5 + Math.sin(Math.toRadians(rotationAngle + 180));
		
		Rotate r;
		
		if (flames != null) {
			r = new Rotate(rotationAngle + 180, getX(), getY());
			gc.save();
			gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			gc.drawImage(flames, xi, yi - dy, TILE_SIZE, TILE_SIZE);
			gc.restore();
		}
			
		r = new Rotate(rotationAngle, getX(), getY());
		gc.save();
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(img, xi, yi, TILE_SIZE, TILE_SIZE);
		gc.restore();
	}
	
	public void move() {
		
		double dx = enemy.getX() - getX();
		double dy = enemy.getY() - getY();
		
		double angle = Math.atan2(dy, dx);
		
		rotationAngle = Math.toDegrees(angle) + 90;
		
		setX(getX() + speed * Math.cos(angle));
		setY(getY() + speed * Math.sin(angle));
		
	}
	
	public void doDamage() {
		enemy.takeDamage(damage);
	}
	
	public boolean collided() {
		return distance(enemy) <= 5;
	}
	
}
