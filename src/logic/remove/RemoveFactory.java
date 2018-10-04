package logic.remove;

/**
 * 取り除くディレクトリに関するファクトリ
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public class RemoveFactory {

	/**
	 * コンストラ。何もしない
	 */
	public RemoveFactory() {
	}

	/**
	 * Removeクラスを生成し、それを返却します
	 * 
	 * @param flag
	 *            フラグ
	 * 
	 * @param list
	 *            ディレクトリのリスト
	 * @return Remove
	 */
	public Remove getRemove(boolean flag, String... list) {
		if (flag) {
			Remove re = new ManualRemove();
			re.setString(list);
			return re;
		}
		if (list == null) {
			return new DefaultRemove();
		} else {
			Remove re = new SpecificRemove();
			re.setString(list);
			return re;
		}
	}
}
