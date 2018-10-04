package logic.search;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import logic.Codec;

/**
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public interface Search {
	
	/**
	 * 
	 */
	Path dir = Paths.get(System.getProperty("user.home"));

	/**
	 * ディレクトリにある欲しいファイルを見つけます
	 * 
	 * 
	 */
	public void find();
	
	/**
	 * ファイル一覧を表示
	 */
	public void show();
	/**
	 * コーデックを新しく設定する
	 * 
	 * @param codec
	 */
	public void setCodec(Codec... codec);

	
	/**
	 * 便利なメソッドを定義します
	 * @author morikawahiroki
	 *
	 * 2016/11/23
	 */
	class Util {
		static boolean find(Path path, Pattern[] ptn) {
			for (Pattern pp : ptn) {
				if (pp.matcher(path.toString()).matches())
					return true;
			}
			return false;
		}

		static boolean match(Path path, Pattern ptn) {
			return ptn.matcher(path.toString()).matches();
		}
	}

}
