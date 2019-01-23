package package_3D;

import java.awt.Color;
import java.awt.Insets;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
/**
 * @author burakerken
 *
 */
public class Frame extends JFrame {
	Insets p;

	Frame() {
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.white);
		init(canvas);
		Timer myTimer = new Timer(); 
		TimerTask gorev = new TimerTask() {
			@Override
			public void run() {
			canvas.repaint(); 
			} 
		};
		myTimer.schedule(gorev, 100, 20);
	}

	public void init(Canvas canvas) { 
	   this.add(canvas);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(800, 800);
		p = this.getInsets();
	}
}
