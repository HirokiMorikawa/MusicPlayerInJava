package logic;

/**
 * コーデックに関する定義をここでします。これは、おもにファイルを検索する際に利用されます
 * 
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public enum Codec {
	MP3("mp3"), MP4("mp4"), WAVE("wav"), M4A("m4a"),/* , FLAC("flac") */;

	private String name;

	private Codec(final String name) {
		this.name = name;
	}

	public String getCodec() {
		return name;
	}

}
