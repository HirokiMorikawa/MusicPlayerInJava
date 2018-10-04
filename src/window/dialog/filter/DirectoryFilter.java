package window.dialog.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * フォルダのみのフィルタを定義
 * 
 * @author morikawahiroki
 * @version 2016/12/29
 */
public class DirectoryFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folder Only";
	}

}
