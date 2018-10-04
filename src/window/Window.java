package window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDesktopPane;
import javax.swing.JList;

import java.awt.Color;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import window.dialog.Dialog;
import window.dialog.RejectDirecChooser;
import manege.AudioDataServer_ver2;
import controller.MarionettePlayer;

/**
 * @author morikawahiroki
 * @version 2016/12/29
 */
public class Window extends JFrame {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6950095204024926670L;
	private JPanel contentPane;
	private final ActionListener start = new StartButtonEvent();
	private final ActionListener stop = new StopButtonEvent();
	private final ListSelectionListener listE = new ListEvent();
	private String[] pathList;
	private JButton startButton;
	private JButton stopButton;
	private JLabel label;
	private JList list;
	private MarionettePlayer player;

	private String origin;

	/**
	 * Create the frame.
	 */
	public Window() {

	}

	public void setup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.desktop);
		contentPane.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 75, 75, 61, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 29, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		startButton = new JButton("再生");
		startButton.addActionListener(start);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel.add(startButton, gbc_btnNewButton);

		stopButton = new JButton("停止");
		stopButton.addActionListener(stop);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		panel.add(stopButton, gbc_btnNewButton_1);

		label = new JLabel();
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 0;
		panel.add(label, gbc_lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_1, BorderLayout.CENTER);

		list = new JList(pathList);
		list.addListSelectionListener(listE);

		JScrollPane sp = new JScrollPane();
		sp.getViewport().setView(list);
		panel_1.add(sp);
	}

	public void setMarionettePlayer(MarionettePlayer player) {
		this.player = player;
	}

	public void setPathList(String[] pathList) {
		this.pathList = pathList;
	}

	private class StartButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (player.isExsistPlayer() && player.isPlay()) {
				player.exit();
			}

			player.sendMusic(origin);
			player.play();
		}
	}

	private class StopButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (player.isPlay())
				player.stop();
		}
	}

	private class ListEvent implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			origin = (String) list.getSelectedValue();
			char f = '/';
			int stack = 0;
			for (int i = 0; i < origin.length(); i++) {
				if (origin.charAt(i) == f) {
					stack = i;
				}
			}
			String str = origin.substring(stack + 1, origin.length());
			label.setText(str);
		}

	}
}
