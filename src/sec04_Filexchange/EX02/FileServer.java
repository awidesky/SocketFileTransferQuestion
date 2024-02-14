package sec04_Filexchange.EX02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileServer {
	public static void run() {

		System.out.println("  SERVER ");
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(10000);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Server]  port not avaliable ");
		}

		System.out.println("[Server]  stand by Server ");
		try {
			Socket socket = ssocket.accept();
			System.out.println("[Server]  Server connection complete  ");
			System.out.println("[Server] Server  ::  " + socket.getInetAddress() + "." + socket.getPort() + "  "
					+ socket.getLocalSocketAddress());

			DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			String filename = dis.readUTF();
			System.out.println("[Server]  receive file  " + filename);

			File outfile = new File("D:\\JAVA_Coding_Study\\file\\" + filename);
			outfile.getParentFile().mkdirs();
			outfile.delete(); outfile.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outfile));

			byte[] data = new byte[1024];
			int len, cnt = 1;
			while ((len = dis.readInt()) != -1) {
				int sent = dis.read(data, 0, len);
				System.out.println("[Server] packet #" + cnt++ + " : " + sent + "byte");
				bos.write(data, 0, len);
				bos.flush();
			}
			System.out.println("[Server]  file receiver complete ");
			dos.writeUTF("[Server]  file receive complete ");
			dos.flush();

			dis.close();
			dos.close();
			bos.flush();
			bos.close();
			if (socket != null)
				socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
