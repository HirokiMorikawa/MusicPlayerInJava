package controller;

import java.nio.file.Path;

import audioPlayer.Player;
import manege.DataServer;

/**
 * データサーバと音楽プレーヤーを操り， 一つの音楽プレイヤーとして動作する機能を提供する
 * 
 * @author morikawahiroki
 *
 * @version 2016/12/27
 */
public class MarionettePlayer {

	private Thread player;

	/**
	 * 音楽データを管理する
	 */
	private DataServer dataServer;
	/**
	 * 音楽を再生する
	 */
	private Player musicPlayer;

	/**
	 * 音楽を管理するためのデータサーバをセットする
	 *
	 * @param dataServer
	 */
	public MarionettePlayer(DataServer dataServer) {
		this.dataServer = dataServer;
	}

	public DataServer getDataServer() {
		return dataServer;
	}

	/**
	 * 音楽のリストを取得
	 * 
	 * @param paths
	 */
	public void loadDirectory(Path... paths) {
		dataServer.loadDirectory(paths);
	}

	/**
	 * 音楽データを音楽プレイヤーに送信
	 * 
	 * @param path
	 */
	public void sendMusic(String path) {
		musicPlayer = new Player(path);
		player = new Thread(musicPlayer);
	}

	public boolean isPlay() {
		return musicPlayer.isUsed();
	}

	public boolean isExsistPlayer() {
		return (musicPlayer != null) ? true : false;
	}

	/**
	 * 音楽の再生
	 */
	public void play() {
		player.start();
		musicPlayer.contStart();

	}

	/**
	 * 音楽の停止
	 */
	public void stop() {
		musicPlayer.stop();
	}

	public void exit() {
		musicPlayer.exit();
	}

	public void resetLine() {
		musicPlayer.lineReset();
	}

}
