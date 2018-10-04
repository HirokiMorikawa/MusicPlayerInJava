package dataStore;

import java.nio.file.Path;

/**
 * 取得したデータをDataListBufferに渡す機能
 * 
 * @author morikawahiroki
 *
 *         2016/11/27
 */
public class DataStructure implements Subject {
	/**
	 * オブザーバ
	 */
	private DataListBuffer observer;
	/**
	 *コンストラクタ オブザーバのセッティングを行う
	 *
	 * @param observer データをためるためのオブザーバ
	 */
	public DataStructure(DataListBuffer observer) {
		this.observer = observer;
	}

	@Override
	public  synchronized void setDataPath(Path path) {
		notifyObserver(path.toString());
	}
	
	/**
	 * オブザーバにデータの更新を通知する
	 */
	private  synchronized void notifyObserver(String path) {
		observer.update(path);
	}

}
