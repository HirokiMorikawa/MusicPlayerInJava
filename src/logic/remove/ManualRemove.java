package logic.remove;

/**
 * マニュアルで取り除くファイルを指定します
 * @author morikawahiroki
 *
 * 2016/11/24
 */
public class ManualRemove extends Remove {
	
	@Override
	protected void setString() {
		input = new String[1];
		input[0] = "";
	}

}
