package TCP;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class TCPClient {
	public static void main(String args[]) {
		//args[0] <- hostname of destination
		Socket socket = null;
		try {
			if(args.length == 2)
				socket = new Socket(args[0], Integer.parseInt(args[1]));
			else {
				Scanner scanner = new Scanner(System.in);
				System.out.print("IP: ");
				String ip = scanner.next();
				System.out.print("PORT: ");
				int port = scanner.nextInt();
				socket = new Socket(ip, port);
			}
			// 1o passo
			System.out.println("SOCKET=" + socket);

			// 2o passo
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			String texto = "";
			InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(input);
			String data= in.readUTF();
			System.out.println("Bem Vindo ao Terminal de Voto\n");
			System.out.println("Mesa de Voto: "+ data);
			// 3o passo
			while (true) {
				// READ STRING FROM KEYBOARD
				try {
					texto = reader.readLine();
				} catch (Exception ignored) {
				}
				// WRITE INTO THE SOCKET
				out.writeUTF(texto);
				// READ FROM SOCKET
				data = in.readUTF();
				if (data.equals("EXIT")){
					out.close();
					socket.close();
					exit(0);
				}
				// DISPLAY WHAT WAS READ
				System.out.println("Resposta da Mesa de Voto: " + data);
			}

		} catch (UnknownHostException e) {
			System.out.println("Sock:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (socket != null)
				try {
					socket.close();
					exit(0);
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
					exit(0);
				}
		}
	}
}