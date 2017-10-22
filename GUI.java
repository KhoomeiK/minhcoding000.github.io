import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {

	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel emptyLabel = new JLabel("");
		emptyLabel.setPreferredSize(new Dimension(200, 100));
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);

		frame.setTitle("Receiver Viewer");

		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setBackground(Color.WHITE);
		
		JLabel lab = new JLabel();
		lab.setText("Start or Stop logging IDs");

		JButton stop = new JButton("Stop");
		JButton start = new JButton("Start");
		
		panel.add(start);
		panel.add(stop);
		panel.add(lab);
		frame.add(panel);
		
		receiver r = new receiver();
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					r.b = true;
					class MyThread extends Thread {
						public void run() {
							try {
								r.run();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					MyThread t = new MyThread();
					t.start();
					lab.setText("Now logging IDs");
			}
		});
		
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.b = false;
				lab.setText("Paused logging IDs");
			}
		});
		
	}
}
