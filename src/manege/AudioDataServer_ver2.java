package manege;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import logic.ReaderLogic;
import dataStore.DataStore;

/**
 * 様々な処理を行う(笑)オーディオデータ集中管理システムの基本的機能を提供します(微笑)．
 * 
 * @author morikawahiroki
 *
 *         2016/11/27
 */
public class AudioDataServer_ver2 extends DataServer {

	private int count = 0;

	/**
	 * コンストラクタ
	 */
	public AudioDataServer_ver2() {
		// SAVEDATA.tmpにサーチされたデータを保存
		ds = new DataStore();
		// とりあえず読み込み
		dlb = ds.getOutPutBuffer();
		// ディレクトリサーチを設定
		logic = new ReaderLogic(ds);
	}

	@Override
	protected void rejectDirectory(Path... paths) {
		String[] pathList = new String[paths.length];
		for (int i = 0; i < pathList.length; i++) {
			pathList[i] = paths[i].getFileName().toString();
		}
		logic.setRemoveDirectory(true, pathList);
	}

	@Override
	public List<String> getPathList() {
		if (count == 0) {
			return dlb.getList();
		} else {
			ds.deserializeToData();
			dlb = ds.getOutPutBuffer();
			return dlb.getList();
		}
	}

	@Override
	public void loadDirectory(Path... paths) {
		count++;
		rejectDirectory(paths);
		logic.find();
	}

	@Override
	public void loadDirectory(boolean flag, Path... path) {
		if (flag)
			loadDirectory(path);
		else {
			count++;
			if (path != null) {
				String[] pathList = new String[path.length];
				for (int i = 0; i < pathList.length; i++) {
					pathList[i] = path[i].getFileName().toString();
				}
				logic.setRemoveDirectory(flag, pathList);
			} else {
				logic.setRemoveDirectory(flag);
			}
			logic.find();
		}
	}
}
