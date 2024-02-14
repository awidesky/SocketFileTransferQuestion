package sec04_Filexchange.EX01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
	public static void run() {
		System.out.println("[Client]   CLIENT  ");

//    Socket socket = null;
		File infile = new File("D:\\IMG_2312.MOV");

		try (Socket socket = new Socket("localhost", 10000);
				// new Socket(InetAddress.getByAddress(new
				// byte[]{(byte)192,(byte)168,(byte)55,(byte)217}),10000);
				DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(infile));) {

			System.out.println("[Client]  server conntion complete  ");
			System.out
					.println(socket.getInetAddress() + "." + socket.getPort() + "   " + socket.getLocalSocketAddress());

			System.out.println("[Client] file transmission " + infile.getName());

			dos.writeUTF(infile.getName());
			byte[] data = new byte[1024];
			int len, cnt = 1;
			while ((len = bis.read(data)) != -1) {
				System.out.println("[Client] packet #" + cnt++ + " : " + len + "byte");
				dos.writeInt(len);
				dos.write(data, 0, len);
				dos.flush();
			}
			dos.writeInt(-1);
			dos.flush();

			String str = dis.readUTF();
			System.out.println("[Client] from server ::  " + str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
