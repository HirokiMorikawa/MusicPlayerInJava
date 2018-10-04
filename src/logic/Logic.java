package logic;


import logic.remove.RemoveFactory;

/**
 * 
 * ファイルを読み込むためのロジックの定義を行うインタフェース
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public interface Logic {
	/**
	 * 文字を生成するためのファクトリ
	 */
	static final RemoveFactory FACTORY = new RemoveFactory();

	/**
	 * 具象クラスの定義に従ってファイルを読み込みます
	 * 
	 */
	public void find();

	/**
	 * ファイルの一覧を表示
	 */
	public void show();

	/**
	 * 取り除くディレクトリのセット
	 * 
	 * @param trueをセットするとマニュアルモードとなる
	 * @param list
	 *            取り除き対象ディレクトリ
	 */
	public void setRemoveDirectory(boolean flag, String... list);
}
