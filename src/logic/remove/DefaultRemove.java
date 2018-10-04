package logic.remove;


/**
 * @author morikawahiroki
 *
 *         2016/11/23
 */
public final class DefaultRemove extends Remove {

	/**
	 *
	 */
	protected DefaultRemove() {
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see logic.remove.Remove#setString()
	 */
	@Override
	protected void setString() {
		input = new String[] { "Documents", "Pictures" };
	}
}
