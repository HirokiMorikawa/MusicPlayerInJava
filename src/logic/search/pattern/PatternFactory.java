package logic.search.pattern;

import java.util.regex.Pattern;

import logic.Codec;

/**
 * コーデックに対応した正規表現のリストを生成するのに使います
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public class PatternFactory {

	/**
	 * 正規表現のリストを取得
	 * 
	 * @param codec
	 *            　コーデック
	 * @return　正規表現のリスト
	 */
	public Pattern[] getPattern(Codec... codec) {
		Codec[] c = codec;
		if (codec.length == 0 || codec == null) {
			c = Codec.values();

		}
		Pattern[] ptn = new Pattern[c.length];
		for (int i = 0; i < c.length; i++) {
			ptn[i] = Pattern.compile(packString(c[i].getCodec()));
		}
		return ptn;
	}

	/**
	 * いい感じの文字列を生成
	 * 
	 * @param str
	 *            文字列
	 * @return 処理された文字列
	 */
	private String packString(String str) {
		return ".*\\." + str;
	}

}
