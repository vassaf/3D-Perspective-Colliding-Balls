package package_3D;

import java.awt.Color;
/**
 * @author burakerken
 *
 */
public class Point3D {

	public double x, y, z,vx,vy,vz,radius;
	Color c;

	Point3D(double x, double y, double z, Color c) {
		this.x = x; 
		this.y = y; 
		this.z = z;
		this.c = c; 
	} 
	
	Point3D(double x, double y, double z) {
		this.x = x; 
		this.y = y;
		this.z = z;
	} 
	
	Point3D(double x, double y, double z, Color c,double radius) {
		this.x = 400*(Math.random()-0.5);
		this.y = 400*(Math.random()-0.5);
		this.z = 400*(Math.random()-0.5);
		this.vx = Math.random()-0.5;
		this.vy = Math.random()-0.5;
		this.vz = Math.random()-0.5; 
		this.c = c;
		this.radius = radius;
	}
	
	Point3D(double radius) {
		this.x = 400*(Math.random()-0.5);
		this.y = 400*(Math.random()-0.5);
		this.z = 400*(Math.random()-0.5);
		this.vx = 5*(Math.random()-0.5);
		this.vy = 5*Math.random()-0.5;
		this.vz = 5*Math.random()-0.5; 
		this.radius = radius;
	}
	 
	public double distanceTo(Point3D k) {
	    return Math.sqrt(Math.pow(this.x - k.x, 2) + Math.pow(this.y - k.y, 2) + Math.pow(this.z-k.z, 2));
	}

	public double[] getMatris() {
		double[] k = { this.x, this.y, this.z };
		return k;
	}

	public void setMatris(double[] k) {
		this.x = k[0];
		this.y = k[1];
		this.z = k[2];
	}
	
	
}
