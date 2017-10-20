package tansfer;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class TransferClient extends Thread {
	private TransferClientSocket cs = null;
	private String ip = "127.0.0.1";
	private int port = TransferServer.PORT;
	private String sendMessage = "Windwos";
	// �����·���������ļ���
	private String des_path;

	public TransferClient(String des_path) {
		this.des_path = des_path;
	}

	public void startClient() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean createConnection() {
		cs = new TransferClientSocket(ip, port);
		try {
			cs.CreateConnection();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private void sendMessage() {
		if (cs == null)
			return;
		try {
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�������ݴ���\n");
		}
	}

	private void getMessage() {
		if (cs == null)
			return;
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			System.out.print("������ݴ���!\n");
			return;
		}
		DataOutputStream fileOut = null;
		try {
			String savePath = des_path;
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;
			// JAVASE7�Ժ������Чtry with source
			// String file_name=inputStream.readUTF();
			// System.out.println();
			// inputStream.readUTF();
			fileOut = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(savePath)));
			// len = inputStream.readLong();
			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				// System.out.println("�ļ�������" + (passedlen * 100/ len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			// System.out.println("������ɣ�" + savePath + "\n");

			// System.out.println("������ɣ�" + savePath + "\n");
		} catch (Exception e) {
			// System.out.println("����ʧ�ܣ�����" + "\n");
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		this.startClient();
	}
}