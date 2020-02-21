package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import tipos.Tipo;
import util.Util;

public class EnviaDados implements Runnable {
	private DataOutputStream saida;
	private DataInputStream entrada;
	
	public EnviaDados(Socket cliente) throws IOException {
		this.saida = new DataOutputStream(cliente.getOutputStream());
		this.entrada = new DataInputStream(cliente.getInputStream());
	}
	@Override
	public void run() {
		while(true) {
			try {
				//byte[] dado = Util.criaPalavraComunicacao("Inteiro", Util.WORD, (short) 1, (short) 0);
				//byte[] dado1 = Util.criaPalavraComunicacao("Real", Util.FLOAT, (short) 1, (short) 2);
				
				//dados.put("receita", b);
				//dados.put("Teste", b);
				
				//saida.writeObject(dados);
				//saida.write(dado);
				//saida.flush();
				//saida.write(dado1);
				//saida.flush();
				
				byte[] dado = Util.protocoloLeitura(27, Tipo.ENVIA_BYTE, (short) 36, (short) 68, (byte) 0);
				System.out.println(Arrays.toString(dado));
				
				short valor = (short) ((dado[0] << 4) + ((dado[1] >> 4) & 0x0F));
				byte tipo = (byte) (dado[1] & 0x0F);
				short db = (short) ((dado[3] << 8) + dado[4]);
				short offsetDb = (short) ((dado[5] << 5) + ((dado[6] >> 3) & 31));
				byte nBit = (byte) (dado[6] & 07);
				
				System.out.println(valor);
				System.out.println(tipo);
				System.out.println(db);
				System.out.println(offsetDb);
				System.out.println(nBit);
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
