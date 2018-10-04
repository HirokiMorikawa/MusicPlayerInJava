package dataStore;


/**
 * データを受け取った時の動作を提供
 * 
 * @author morikawahiroki
 *
 * 2016/11/27
 */
public interface Observer {
	/**
	 * データを受け取るために利用するもの
	 * 
	 * @param path パス
	 */
	public void update(String path);

}
