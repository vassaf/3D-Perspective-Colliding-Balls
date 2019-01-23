package package_3D;

import java.awt.Color;

/**
 * @author burakerken
 *
 */

public class Ball extends Point3D {
	static Ball[] balls = new Ball[20];

	Ball(double radius) {
		super(radius);
		c = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
	}

	public void getBalls(Ball[] balls) { 
		Ball.balls = balls;
	}

	public static void collision_control() {
		double distance;
		double diffX, diffY, diffZ;
		for (Point3D ball1 : balls) {
			for (Point3D ball2 : balls) {
				if (ball1 != ball2) {
					distance = ball1.distanceTo(ball2);
					if (distance <= ball1.radius) {
						if (distance == 0) {
							distance = 1;
							ball1.x = ball1.x + 1;
						}
						// Simply resolving the collision and speeds adjusted according to collision axis.
						// For better result this part  can be easily changed to 3D elastic collision. 
						//2D elastic collision code part also attached as a source.
						diffX = ball1.x - ball2.x;
						diffY = ball1.y - ball2.y;
						diffZ = ball1.z - ball2.z;

						ball1.x = ball1.x + 3 * diffX / distance;
						ball1.y = ball1.y + 3 * diffY / distance;
						ball1.z = ball1.z + 3 * diffZ / distance;

						ball2.x = ball2.x - 3 * diffX / distance;
						ball2.y = ball2.y - 3 * diffY / distance;
						ball2.z = ball2.z - 3 * diffZ / distance;

						ball1.vx = 5 * diffX / distance;
						ball1.vy = 5 * diffY / distance;
						ball1.vz = 5 * diffZ / distance;
 
						ball2.vx = 5 * -diffX / distance;
						ball2.vy = 5 * -diffY / distance;
						ball2.vz = 5 * -diffZ / distance;

					}
				}
			}
		}

	}

	public static void wall_collision() {
		for (Point3D ball : balls) {
			// updating position
			ball.x = ball.x + ball.vx; 
			ball.y = ball.y + ball.vy;
			ball.z = ball.z + ball.vz;
			// updating position end

			if (ball.x - ball.radius / 2 < -400) {
				ball.vx = Math.abs(ball.vx);
				//ball.x =-400+ball.radius ;
			} else if (ball.x + ball.radius > 400) {
				ball.vx = -Math.abs(ball.vx);
				//ball.x =400+ball.radius ;
			}

			if (ball.y - ball.radius / 2 < -400) {
				ball.vy = Math.abs(ball.vy);
				//ball.x =-400+ball.radius ;
			} else if (ball.y + ball.radius / 2 > 400) {
				ball.vy = -Math.abs(ball.vy); 
			}

			if (ball.z - ball.radius / 2 < -400) {
				ball.vz = Math.abs(ball.vz);
			} else if (ball.z + ball.radius / 2 > 400) {
				ball.vz = -Math.abs(ball.vz);
			}

		}
	}

	public static void update_position() {
		for (Point3D ball : balls) {
			ball.x = ball.x + ball.vx;
			ball.y = ball.y + ball.vy;
			ball.z = ball.z + ball.vz;
		}
	}

	public static void update_all() {
		collision_control();
		wall_collision();
		update_position();
	}
}
