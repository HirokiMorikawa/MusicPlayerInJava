package manege;

//import java.nio.file.Path;

//import dataStore.DataStore;
import dataStore.DataStore;
import logic.Logic;
import logic.ReaderLogic;

/**
 * オーディオを管理するための機能を提供します
 * 
 * 
 * @author morikawahiroki
 *
 *         2016/11/24
 */
public class AudioDataServer {

	/**
	 * ファイル検索のロジック
	 */
	private Logic logic;

	/**
	 * コンストラクタ
	 *
	 * @param parallel
	 */
	public AudioDataServer() {
		this.logic = new ReaderLogic(new DataStore());
	}

	/**
	 * ユーザディレクトリの一括読み込み
	 */
	public void readUnderUserDirectory() {
		logic.find();
	}

}
