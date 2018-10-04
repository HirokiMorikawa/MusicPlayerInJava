package logic.remove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 取り除くディレクトリを指定すると正規表現の文字列として変換する機能を持ちます
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public abstract class Remove {

	private ArrayList<String> strList = new ArrayList<>();
	/**
	 * 出力したくないディレクトリをここに格納
	 */
	protected String[] input;

	/**
	 * コンストラクタ ディレクトリを指定
	 *
	 * @param input
	 *            ディレクトリ
	 */
	protected Remove(String... input) {
		this.input = input;
	}

	/**
	 * コンストラクタ ディレクトリを指定
	 *
	 * @param input
	 *            ディレクトリ
	 */
	public Remove() {
		setString();
	}

	/**
	 * コンストラクタで与えられた情報から正規表現で表された文字列を返します。 
	 * 
	 * @return 空文字もしくは処理された文字列
	 */
	public final String generateString() {
		if (input == null)
			throw new IllegalStateException("ぬるぬるです");
		StringBuilder sb = new StringBuilder();
		if (input.length == 1)
			return "(" + input[0] + ")";
		else {
			int count = 1;
			for (String str : input) {
				sb.append(str);
				if (count % 2 != 0)
					sb.append("|");
			}
			if (sb.charAt(sb.length() - 1) == '|')
				sb.deleteCharAt(sb.length() - 1);
			return "(" +sb.toString() + ")";
		}

	}
	/**
	 * デフォルトプラス任意のディレクトリ
	 * 
	 * @param input
	 */
	protected void setString(String... input) {
		Stream<String> concatenated = Stream.concat(Arrays.stream(this.input),
				Arrays.stream(input));
		concatenated.forEach(x -> buffString(x));
		this.input = getBuff();
	}

	abstract protected void setString();

	private void buffString(String str) {
		strList.add(str);
	}

	private String[] getBuff() {
		String[] list = new String[strList.size()];
		int i = 0;
		for(String str : strList){
			list[i++] = str;
		}
		strList = new ArrayList<>();
		return list;
	}
	public void show(){
		for(String str : input){
			System.out.println(str);
		}
		System.out.println();
	}
}
