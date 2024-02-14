package sec04_Filexchange.EX01;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
	public static void main(String[] args) {
		System.out.println("   CLIENT  ");

//    Socket socket = null;
		File infile = new File("C:\\JAVA_Coding_Study\\file\\source.jpeg");

		try (Socket socket = new Socket(
				InetAddress.getByAddress(new byte[] { (byte) 192, (byte) 168, (byte) 55, (byte) 217 }), 10000);
				DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(infile));) {

			System.out.println("  server conntion complete  ");
			System.out
					.println(socket.getInetAddress() + "." + socket.getPort() + "   " + socket.getLocalSocketAddress());

			System.out.println(" file transmission " + infile.getName());

			dos.writeUTF(infile.getName());
			byte[] data = new byte[100];
			int len;
			while ((len = bis.read(data)) != -1) {
				dos.writeInt(len);
				dos.write(data, 0, len);
				dos.flush();
			}
			dos.writeInt(-1);
			dos.flush();

			String str = dis.readUTF();
			System.out.println(" from server ::  " + str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
