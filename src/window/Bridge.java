package window;

import manege.AudioDataServer_ver2;
import window.dialog.Dialog;
import window.dialog.RejectDirecChooser;
import controller.MarionettePlayer;

/**
 * @author morikawahiroki
 * @version 2016/12/29
 */
public class Bridge {
	private Dialog dialog;
	private MarionettePlayer player;
	
	private Window window;
	
	public Bridge() {
		player = new MarionettePlayer(new AudioDataServer_ver2());
		dialog = new RejectDirecChooser(player.getDataServer(), new Window());
		dialog.visible(true);
	}
	
	
	public void start() {
		dialog.getWindow().setMarionettePlayer(player);
		dialog.getWindow().setVisible(true);
	}
	
	public static void main(String[] args) {
		Bridge bridge = new Bridge();
		bridge.start();
	}

}
