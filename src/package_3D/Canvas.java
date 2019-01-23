package package_3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author burakerken
 *
 */

public class Canvas extends JPanel {

	Ball[] balls = new Ball[30];
	Point3D[] cube = new Point3D[8];
	Point mouse_point = new Point(0, 0);
	Point3D origin = new Point3D(400, 400, 0);
	Point3D projected_2D = new Point3D(0, 0, 0);
	Point3D[] projected_points = new Point3D[8];
	Point3D rotated = new Point3D(0, 0, 0);

	BufferedImage bi;
	double angle = 45;
	double distance = 5;
	int wheel_count;
	Double ppoint_x[] = new Double[10];
	Double ppoint_y[] = new Double[10];

	Canvas() {

		init();
		setOpaque(true);
		addMouseWheelListener(new MouseWheelListener() {
			@Override 
			public void mouseWheelMoved(MouseWheelEvent e) {
				distance = distance + e.getWheelRotation();
				if (distance < 1.5) {
					distance = 1.5;
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouse_point = e.getPoint();
			} 
		});
		
	}
	
	private void init() {
		for (int i = 0; i < 30; i++) {
			balls[i] = new Ball(50);
		}
		for (int i = 0; i < 30; i++) {
			balls[i].getBalls(balls);
		}
		
		cube[0] = new Point3D(400, 400, 400);
		cube[1] = new Point3D(400, 400, -400);
		cube[2] = new Point3D(400, -400, 400);
		cube[3] = new Point3D(400, -400, -400);
		cube[4] = new Point3D(-400, 400, 400);
		cube[5] = new Point3D(-400, 400, -400);
		cube[6] = new Point3D(-400, -400, 400);
		cube[7] = new Point3D(-400, -400, -400);
		
		bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics= bi.createGraphics();
		graphics.setPaint(Color.white);
	}

	private double[][] projection(double z) {
		double[][] k = { { z, 0, 0 }, { 0, z, 0 }, { 0, 0, 0 } };
		return k;
	}

	private double[][] rotationX(double angle) {
		double[][] k = { { 1, 0, 0 }, { 0, Math.cos(angle), -Math.sin(angle) },
				{ 0, Math.sin(angle), Math.cos(angle) } };
		return k;
	}

	private double[][] rotationY(double angle) {
		double[][] k = { { Math.cos(angle), 0, Math.sin(angle) }, { 0, 1, 0 },
				{ -Math.sin(angle), 0, Math.cos(angle) } };
		return k;
	}

	private double[][] rotationZ (double angle) {
		double[][] k = { { Math.cos(angle), -Math.sin(angle), 0 }, { Math.sin(angle), Math.cos(angle), 0 },
				{ 0, 0, 1 } };
		return k;
	}
	
	private void connect_points(int k, int l, Double x[], Double y[], Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) (origin.x + x[k].intValue()), (int) origin.y + y[k].intValue(),
				(int) origin.x + x[l].intValue(), (int) origin.y + y[l].intValue());
	}

	private void draw_balls(Graphics g) {

		for (Point3D ball : balls) {
			g.setColor(ball.c);
			ball.getMatris();
			rotated.setMatris(Matrix.multiply(rotationX(-Math.toRadians(mouse_point.getY() % 360)), ball.getMatris()));
			rotated.setMatris(Matrix.multiply(rotationY(Math.toRadians(mouse_point.getX() % 360)), rotated.getMatris()));
			projected_2D.setMatris(Matrix.multiply(projection((100 / (100 * distance - rotated.z / 4))), rotated.getMatris()));
			
			g.fillOval((int) (origin.x + projected_2D.x), (int) (origin.y + projected_2D.y),
					(int) (ball.radius * (100 / (100 * distance - rotated.z / 4))),
					(int) (ball.radius * (100 / (100 * distance - rotated.z / 4))));
		}
	}

	private void draw_cube(Graphics g) {
		g.clearRect(0, 0, 800, 800);
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 800);
		int i = 0;
		for (Point3D cube : cube) {
			cube.getMatris();
			rotated.setMatris(Matrix.multiply(rotationX(-Math.toRadians(mouse_point.getY() % 360)), cube.getMatris()));
			rotated.setMatris(Matrix.multiply(rotationY(Math.toRadians(mouse_point.getX() % 360)), rotated.getMatris()));
			projected_2D.setMatris(Matrix.multiply(projection((100 / (100 * distance - rotated.z / 4))), rotated.getMatris()));

			ppoint_x[i] = projected_2D.x;
			ppoint_y[i] = projected_2D.y;
			i++;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		draw_cube(bi.getGraphics()); 
		Ball.update_all();
		draw_balls(bi.getGraphics());

		connect_points(0, 1, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(1, 3, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(2, 3, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(0, 2, ppoint_x, ppoint_y, bi.getGraphics());

		connect_points(4, 5, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(5, 7, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(6, 4, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(6, 7, ppoint_x, ppoint_y, bi.getGraphics());

		connect_points(0, 4, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(1, 5, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(2, 6, ppoint_x, ppoint_y, bi.getGraphics());
		connect_points(3, 7, ppoint_x, ppoint_y, bi.getGraphics());
		g.drawImage(bi, 0, 0, null);
		
	}

}
