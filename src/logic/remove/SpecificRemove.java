package logic.remove;


/**
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public final class SpecificRemove extends Remove {

	/**
	 *
	 */
	protected SpecificRemove() {
	}

	@Override
	protected void setString() {
		this.input = new String[] { "Documents", "Pictures" };
	}

	/**
	 * 取り除きたいディレクトリを指定するとデフォルトディレクトリと指定されたディレクトリの正規化された文字列を返します。
	 * 
	 * @param strings
	 *            取り除きたいディレクトリ
	 */
	public void stringList(String... strings) {
		this.setString(strings);
	}
}
