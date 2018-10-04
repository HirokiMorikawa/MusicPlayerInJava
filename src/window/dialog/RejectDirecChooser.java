package window.dialog;


import window.Window;
import window.dialog.filter.DirectoryFilter;
import logic.Logic;
import logic.search.Search;
import manege.DataServer;

/**
 * ディレクトリを取り除くためのダイアログ
 * 
 * @author morikawahiroki
 * @version 2016/12/28
 */
public class RejectDirecChooser extends Dialog {

	/**
	 *
	 * @param logic 探索用プログラム
	 */
	public RejectDirecChooser(DataServer server, Window window) {
		super(server, window);
	}

	@Override
	protected void setPath() {
		path = Search.dir.toAbsolutePath().toString();
	}

	@Override
	protected void setTitle() {
		title = "読みこみたくないフォルダの選択";
	}

	@Override
	protected void setFilter() {
		filter = new DirectoryFilter();
	}

}
