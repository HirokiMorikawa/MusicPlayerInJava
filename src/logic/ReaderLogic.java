package logic;

//import dataStore.DataStore;
import dataStore.DataStore;
import logic.remove.Remove;
import logic.search.DefaultSearch;
import logic.search.Search;

/**
 * 基本的な読み込みを定義する
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public class ReaderLogic implements Logic {
	/**
	 * 削除するものについて
	 */
	private Remove remove;
	/**
	 * ここでファイルの処理を行う
	 */
	private Search search;
	/**
	 * コーデック
	 */
	private Codec[] codec;

	private DataStore dataStore;

	/**
	 * コンストラクタ
	 * 
	 * @param codec
	 *            コーデック
	 */
	public ReaderLogic(DataStore dataStore, Codec... codec) {
		this(dataStore, null, codec);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param list
	 *            削除するリスト
	 * @param codec
	 *            コーデック
	 */
	public ReaderLogic(DataStore dataStore, String[] list, Codec... codec) {
		this.dataStore = dataStore;
		this.codec = codec;
		updateRemove(false, list, codec);
	}

	/**
	 * 取り除くディレクトリ
	 */
	@Override
	public void setRemoveDirectory(boolean flag, String... list) {
		updateRemove(flag, list, codec);
	}

	/**
	 * 検索
	 */
	@Override
	public void find() {
		search.find();
	}

	@Override
	public void show() {
		search.show();
	}

	/**
	 * Removeの取得
	 * 
	 * @param flag
	 *            マニュアルにするかどうか
	 * 
	 * @param str
	 *            削除するリスト
	 * @param codec
	 *            コーデック
	 */
	private void updateRemove(boolean flag, String[] str, Codec... codec) {
		this.remove = FACTORY.getRemove(flag, str);
		this.search = new DefaultSearch(remove.generateString(), dataStore,
				codec);

	}

}
