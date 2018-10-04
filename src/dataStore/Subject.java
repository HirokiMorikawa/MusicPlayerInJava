package dataStore;

import java.nio.file.Path;

/**
 * @author morikawahiroki
 *
 * 2016/11/27
 */
public interface Subject {
	/**
	 * データをオブザーバに通知する
	 * 
	 * @param path データ
	 */
	public void setDataPath(Path path);

}
