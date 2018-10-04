package manege;

import java.nio.file.Path;
import java.util.List;

import logic.Logic;
import dataStore.DataListBuffer;
import dataStore.DataStore;

/**
 * @author morikawahiroki
 *
 *         2016/12/27
 */
public abstract class DataServer {

	/**
	 * データを溜め込む
	 */
	protected DataStore ds;
	/**
	 * ディレクトリサーチ
	 */
	protected Logic logic;

	protected DataListBuffer dlb;

	/**
	 * 指定されたフォルダを取り除く
	 * 
	 * @param paths
	 *            パス
	 */
	protected abstract void rejectDirectory(Path... paths);

	/**
	 * 見つかった音楽ファイルのパスのリストを返す
	 * 
	 * @return パス
	 */
	public abstract List<String> getPathList();

	/**
	 * ディレクトリから音楽データを探し，リストに入れる
	 */
	public abstract void loadDirectory(Path... paths);
	
	public DataListBuffer getBuffer(){
		return dlb;
	}

	/**
	 * 
	 * @param flag
	 * @param path
	 */
	public void loadDirectory(boolean flag, Path... path) {
	}

}