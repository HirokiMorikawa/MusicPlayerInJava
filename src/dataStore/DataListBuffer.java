package dataStore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ファイルの位置を溜め込む直列化機構の提供
 * 
 * @author morikawahiroki
 *
 *         2016/11/27
 */
public class DataListBuffer implements Serializable, Observer {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7602648159372693736L;
	/**
	 * データを溜め込むためのもの
	 */
	private ArrayList<String> list;

	/**
	 * コンストラクタ,フィールドの初期化
	 */
	public DataListBuffer() {
		this.list = new ArrayList<>();
	}

	/**
	 * リストを返却する
	 * 
	 * @return パスのリスト
	 */
	public ArrayList<String> getList() {
		return list;
	}

	@Override
	public synchronized void update(String path) {
		list.add(path);
	}
	
	synchronized void clearList() {
		list.clear();
	}

}
