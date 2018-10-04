package dataStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.*;

/**
 * @author morikawahiroki
 *
 *         2016/11/27
 */
public class DataStore {
	/**
	 * オブジェクトデータのファイル名
	 */
	private String fileName;
	/**
	 * データを溜め込むためのもの
	 */
	private DataListBuffer observer;
	/**
	 * データの受け渡しのための窓口
	 */
	private Subject subject;

	private DataListBuffer output;

	/**
	 * コンストラクタ,データの格納に関する様々なことを実行
	 *
	 * @param fileName
	 *            ファイル名
	 */
	public DataStore(String fileName) {
		this.fileName = fileName;
		if (!Files.exists(Paths.get(fileName))) {
			createFile();
			serializeToData();
		}
		deserializeToData();
		makeObservers();
	}

	/**
	 * コンストラクタ
	 */
	public DataStore() {
		this("SAVEDATA.tmp");
	}

	/**
	 * データの追加
	 * 
	 * @param path
	 *            パス
	 */
	public synchronized void setDataPath(Path path) {
		subject.setDataPath(path);
	}

	/**
	 * データのシリアライズを行う
	 */
	public synchronized void serializeToData() {
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new FileOutputStream(fileName))) {
			objectOutputStream.writeObject(observer);
			observer.clearList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * データのデシリアライズを行う
	 */
	public synchronized void deserializeToData() {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(
				new FileInputStream(fileName))) {
			output = (DataListBuffer) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一つ前に実行した時のデータ
	 * 
	 * @return
	 */
	public DataListBuffer getOutPutBuffer() {
		return output;
	}

	private void makeObservers() {
		this.observer = new DataListBuffer();
		this.subject = new DataStructure(observer);
	}

	private Path createFile() {
		return createFile(fileName);
	}

	private Path createFile(String fileName) {
		try {
			return Files.createFile(Paths.get(fileName));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

}
