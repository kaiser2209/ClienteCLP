package principal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cliente.Cliente;

public class Main {
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Cliente("127.0.0.1", 12345).executa();
	}
}
