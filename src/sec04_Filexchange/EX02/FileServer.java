package sec04_Filexchange.EX02;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileServer {
	public static void main(String[] args) {

		System.out.println("  SERVER ");
		ServerSocket ssocket = null;
		try {
			ssocket = new ServerSocket(10000);
		} catch (IOException e) {
			System.out.println(" port not avaliable ");
		}

		System.out.println(" stand by client ");
		try {
			Socket socket = ssocket.accept();
			System.out.println(" client connection complete  ");
			System.out.println("client  ::  " + socket.getInetAddress() + "." + socket.getPort() + "  "
					+ socket.getLocalSocketAddress());

			DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			String filename = dis.readUTF();
			System.out.println(" receive fiel  " + filename);

			File outfile = new File("C:\\JAVA_Coding_Study\\file\\" + filename);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outfile));

			byte[] data = new byte[1024];
			int len;
			while ((len = dis.readInt()) != -1) {
				dis.read(data, 0, len);
				bos.write(data, 0, len);
				bos.flush();
			}
			System.out.println(" file receiver complete ");
			dos.writeUTF(" file receive complete ");
			dos.flush();

			dis.close();
			dos.close();
			bos.flush();
			if (socket != null)
				socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
