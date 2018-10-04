package window.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import window.Window;
import dataStore.DataStore;
import logic.*;
import manege.AudioDataServer_ver2;
import manege.DataServer;

/**
 * @author morikawahiroki
 * @version 2016/12/28
 */
public abstract class Dialog implements ActionListener {
	/**
	 * 最初に表示されるフレーム
	 */
	private JFrame firstFrame;
	/**
	 * ファイルダイアログ
	 */
	private JFileChooser chooser;
	/**
	 * フィルター
	 */
	protected FileFilter filter;
	/**
	 * パス
	 */
	protected String path;
	/**
	 * タイトル
	 */
	protected String title;
	/**
	 * 選択された時の状態
	 */
	private int selected;
	/**
	 * サーバ
	 */
	private DataServer server;
	
	private Window window;

	/**
	 * コンストラクタ
	 */
	public Dialog(DataServer server, Window window) {
		this.server = server;
		firstSetup();
		setPath();
		setJFileChooser();
		this.window = window;
	}

	private void firstSetup() {
		firstFrame = new JFrame("確認");

		JButton button = new JButton("OK");
		button.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);

		JLabel label = new JLabel();
		label.setText("ディスク内のオーディオファイルを探索します．取り除きたいフォルダを選択してください．");

		JPanel labelPanel = new JPanel();
		labelPanel.add(label);

		firstFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		firstFrame.getContentPane().add(labelPanel, BorderLayout.NORTH);

		firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		firstFrame.setSize(550, 100);
		firstFrame.setLocationRelativeTo(null);
		// firstFrame.pack();
	}

	/**
	 * フィルターの設定，filterにフィルタするためのオブジェクトを定義する
	 */
	protected abstract void setFilter();

	/**
	 * パスの初期化
	 */
	protected abstract void setPath();

	/**
	 * タイトルの設定
	 */
	protected abstract void setTitle();

	/**
	 * パスの取得
	 * 
	 * @return パス
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * ファイルチューザーの初期化
	 */
	private void setJFileChooser() {
		chooser = new JFileChooser(path);
		chooser.setMultiSelectionEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
		setTitle();
		if (title == null)
			title = "選択すべし!!";
		chooser.setDialogTitle(title);
		setFilter();
		if (filter != null)
			chooser.setFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	/**
	 * 見えるようにする
	 */
	public void visible(boolean flag) {
		firstFrame.setVisible(flag);
	}
	
	private void sendList(List<String> list) {
		window.setPathList(list.toArray(new String[0]));
	}
	
	private void openWindow() {
		window.setup();
		//window.setVisible(true);
	}
	
	public Window getWindow() {
		return window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		selected = chooser.showOpenDialog(chooser);
		if (selected == JFileChooser.APPROVE_OPTION) {
			File[] files = chooser.getSelectedFiles();
			Path[] paths = new Path[files.length];
			for (int i = 0; i < files.length; i++) {
				paths[i] = files[i].toPath();
			}
			server.loadDirectory(paths);
			sendList(server.getPathList());
			
			openWindow();
			visible(false);
			//System.exit(0);
		} else if (selected == JFileChooser.CANCEL_OPTION) {
			Path[] path = null;
			server.loadDirectory(false, path);
			sendList(server.getPathList());
			openWindow();
			visible(false);
			//System.exit(0);
		} else if (selected == JFileChooser.ERROR_OPTION) {
			System.out.println("エラーが発生しました");
			System.exit(0);
		}
	}
}
