package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	private String host;
	private int porta;
	
	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}
	
	public void executa() throws UnknownHostException, IOException {
		Socket cliente = new Socket(this.host, this.porta);
		System.out.println("Conexão estabelecida com o servidor.");
		
		Recebedor r = new Recebedor(new DataInputStream(cliente.getInputStream()));
		new Thread(r).start();
		
		EnviaDados envia = new EnviaDados(cliente);
		new Thread(envia).start();
		
		//DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
		
		//byte[] valor = {10, 22, 44};
		//saida.write(valor);
		
		//cliente.close();
	}
}
