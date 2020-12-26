package resources;

public abstract class ControlCommand {

	public static final int FILE_TRANSFER_OPEN_FOLDER = 1;
	public static final int FILE_TRANSFER_BACK_FOLDER = 4;
	public static final int FILE_TRANSFER_SEND_LOCAL_TO_REMOTE = 2;
	public static final int FILE_TRANSFER_SEND_REMOTE_TO_LOCAL = 3;
	public static final int FILE_TRANSFER_GET_ICON = 5;
	public static final int FILE_TRANSFER_GET_ICON_BACK = 6;

	public static final int CHAT_LOCAL = 1;
	public static final int CHAT_REMOTE = 2;
}
