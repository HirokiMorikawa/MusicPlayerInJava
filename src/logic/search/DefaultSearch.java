package logic.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import dataStore.DataStore;
import logic.Codec;
import logic.search.pattern.PatternFactory;

/**
 * ファイルを取得する基本的なアルゴリズムを定義します
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public class DefaultSearch implements Search {

	private PatternFactory fac;
	private Pattern wantNot;
	private Pattern[] patterns;

	private DataStore dataStore;

	/**
	 * コンストラクタ
	 *
	 * @param str
	 *            文字列
	 */
	public DefaultSearch(String str, DataStore dataStore, Codec... codec) {
		this.dataStore = dataStore;
		this.fac = new PatternFactory();
		this.wantNot = Pattern.compile(".*/" + str + "/.*");
		updatePattern(codec);
	}

	/**
	 * ファイルの一覧を取得する
	 * 
	 */
	@Override
	public void find() {
		try (Stream<Path> stream = Files.walk(dir)) {
			stream.parallel()
					.filter(s -> !Util.match(s, wantNot))
					.filter(s -> !Files.isDirectory(s))
					.filter(s -> Util.find(s, patterns))
					.forEach(dataStore::setDataPath);
			 dataStore.serializeToData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ファイルの一覧を表示
	 */
	@Override
	public void show() {
		try (Stream<Path> stream = Files.walk(dir)) {
			stream.parallel().filter(s -> !Util.match(s, wantNot))
					.filter(s -> !Files.isDirectory(s))
					.filter(s -> Util.find(s, patterns))
					.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * コーデックを追加
	 */
	@Override
	public void setCodec(Codec... codec) {
		updatePattern(codec);
	}

	/**
	 * パターンのアップデート
	 */
	private void updatePattern(Codec... codec) {
		this.patterns = fac.getPattern(codec);
	}

}
