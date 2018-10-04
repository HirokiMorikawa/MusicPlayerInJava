package audioPlayer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.*;

import com.tagtraum.casampledsp.CAAudioFileReader;
import com.tagtraum.casampledsp.CAAudioFormat;
import com.tagtraum.casampledsp.CAFormatConversionProvider;

/**
 * 音楽を再生するためのクラス
 * 
 * @author morikawahiroki
 * @version 2016/12/27
 */
public class Player implements Runnable, LineListener {

	protected Path path;
	protected String fileName;

	private long time = 0;

	/**
	 * 再生時間
	 */
	private float durationSecond = 0;

	private boolean playing;

	private boolean notYetEOF;

	private int frameSize;

	private byte[] buffer = new byte[32 * 1024];

	private int readPoint = 0;

	private int bytesRead = 0;

	/**
	 * ファイル読み込み用オブジェクト
	 */
	protected final CAAudioFileReader fileReader = new CAAudioFileReader();
	/**
	 * オーディオフォーマット
	 */
	protected AudioFileFormat audioFileFormat = null;
	/**
	 * オーディオストリーム
	 */
	protected AudioInputStream audioInputStream = null;
	/**
	 * 変換後のオーディオストリーム
	 */
	protected AudioInputStream result = null;
	/**
	 * 変換後のオーディオフォーマット
	 */
	protected AudioFormat resultFormat = null;

	/**
	 * 再生用ライン
	 */
	private SourceDataLine line;

	public Player(String path) {
		setPath(path);
		line.addLineListener(this);
		playing = false;
		notYetEOF = true;
	}

	/**
	 * パスの設定
	 * 
	 * @param path
	 */
	private void setPath(String path) {
		this.path = Paths.get(path);
		this.fileName = path;
		try {
			update();
			convert();
			setDataLine();
			setDurationSecond();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public double DurationSecond() {
		return durationSecond;
	}

	/**
	 * ファイルが更新された時アップデート
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private void update() throws UnsupportedAudioFileException, IOException {
		this.audioFileFormat = fileReader.getAudioFileFormat(path.toFile());
		this.audioInputStream = fileReader.getAudioInputStream(path.toFile());
	}

	/**
	 * オーディオファイルの形式をリニアPCMに変換します 変換結果はresuleFormat, resultに格納されます。
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	private void convert() throws UnsupportedAudioFileException, IOException {
		CAFormatConversionProvider provider = new CAFormatConversionProvider();
		result = provider.getAudioInputStream(
				CAAudioFormat.CAEncoding.PCM_SIGNED, audioInputStream);
		resultFormat = result.getFormat();
		frameSize = resultFormat.getFrameSize();
	}

	/**
	 * 再生時間の取得
	 */
	private void setDurationSecond() {
		durationSecond = result.getFrameLength() / resultFormat.getSampleRate();
	}

	/**
	 * データを再生するためのラインを確立する
	 * 
	 * @throws LineUnavailableException
	 */
	private void setDataLine() throws LineUnavailableException {
		line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(
				SourceDataLine.class, resultFormat));
		line.open();
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		readPoint = 0;
		bytesRead = 0;

		while (true) {
			try {
				while (notYetEOF) {
					if (playing) {
						bytesRead = result.read(buffer, readPoint,
								buffer.length - readPoint);
						if (bytesRead == -1) {
							notYetEOF = false;
							break;
						}
						int leftover = bytesRead % frameSize;
						long end = System.currentTimeMillis();
						time = end - start;
						line.write(buffer, readPoint, bytesRead - leftover);
						System.arraycopy(buffer, bytesRead, buffer, 0, leftover);
						readPoint = leftover;
					} else {
						try {
							Thread.sleep(10);
						} catch (InterruptedException ie) {
						}

					}
				}
				line.drain();
				line.stop();
				playing = false;
				notYetEOF = true;
				lineReset();
				// スレッドを休止
				suspend();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {

			}
		}
	}

	public void start() {
		playing = true;
		line.start();
		try {
			// スレッド復帰
			resume();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void contStart() {
		if (playing) {
			line.stop();
			line.flush();
			lineReset();
			line.start();
		} else {
			start();
		}
	}

	public void stop() {
		playing = false;
		line.stop();
		line.flush();
		lineReset();
	}

	/**
	 * 　Lineの音量を調整する
	 * 
	 * @param linearScalar
	 *            0-1までの小数
	 */
	public void volume(double linearScalar) {
		try {
			FloatControl control = (FloatControl) line
					.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue((float) Math.log10(linearScalar) * 20);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 　Lineをミュートする
	 * 
	 * @param state
	 *            true or false
	 */
	public void mute(boolean state) {
		BooleanControl control = (BooleanControl) line
				.getControl(BooleanControl.Type.MUTE);
		if (control.getValue() != state) {
			control.setValue(state);
		}
	}

	public boolean isUsed() {
		return playing;
	}

	/**
	 * 　Lineを再取得する。
	 * 
	 */
	public void lineReset() {
		setPath(fileName);
	}

	/**
	 * 　スレッドを休止。
	 * 
	 */
	public synchronized void suspend() throws InterruptedException {
		this.wait();
	}

	/**
	 * 　スレッドを復帰。
	 * 
	 */
	public synchronized void resume() throws InterruptedException {
		this.notify();
	}

	public void exit() {
		line.stop();
		line.close();
	}

	public long getTime() {
		return time;
	}

	@Override
	public void update(LineEvent event) {
	}
}
