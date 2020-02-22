package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import tipos.Tipo;
import tipos.TipoNumero;
import util.Util;

public class Recebedor implements Runnable {
	private DataInputStream servidor;
	private static Map<Integer, String> dados;
	
	public Recebedor(DataInputStream servidor) {
		this.servidor = servidor;
		dados = new HashMap<Integer, String>();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				byte[] transacao = new byte[2];
				servidor.read(transacao);
				byte tamanho = servidor.readByte();
				byte[] valor = new byte[tamanho];
				servidor.read(valor);
				
				//float res = ByteBuffer.wrap(valor).getFloat();
				salvaHashMap(transacao, valor);
				int id = ((transacao[0] << 4) & 0xFFF0) + ((transacao[1] >> 4) & 0x000F);
				System.out.print("Recebido " + id + ": [");
				for (int i = 0; i < tamanho + 3; i++) {
					if (i < 2) {
						if (i > 0) {
							System.out.print(", ");
						}
						System.out.print(transacao[i]);
					} else if (i < 3) {
						System.out.print(", " + tamanho);
					} else {
						System.out.print(", " + valor[i - 3]);
					}
				}
				System.out.println("]");
				
				dados.forEach((key, value) -> {
					System.out.print(key + ": ");
					System.out.println(value);
				}); 
				
				
				//System.out.println(res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void salvaHashMap(byte[] transacao, byte[] valor) {
		int id = ((transacao[0] << 4) & 0xFFF0) + ((transacao[1] >> 4) & 0x000F);
		TipoNumero tipo = TipoNumero.fromId((transacao[1] & 0x000F));
		String s = trataResultado(tipo, valor);
		dados.put(id, s);
		//System.out.println(id);
		//System.out.println(tipo.getCode());
		//byte tipo = protocolo[8];
		//byte[] valor = Arrays.copyOfRange(protocolo, 9, 13);
		//dados.put(id, trataResultado(tipo, valor));
	}
	
	public String trataResultado(TipoNumero tipo, byte[] dado) {
		if (tipo == TipoNumero.BIT) {
			
		} else if (tipo == TipoNumero.BYTE) {
			
		} else if (tipo == TipoNumero.WORD) {
			
		} else if (tipo == TipoNumero.INT) {
			return Integer.toString(Util.convertByteToInt(dado));
		} else if (tipo == TipoNumero.DWORD) {
			
		} else if (tipo == TipoNumero.DINT) {
			
		} else if (tipo == TipoNumero.REAL) {
			return String.format("%.2f", Util.convertByteToFloat(dado));
		}
		
		return null;
	}
	
	public static Map<Integer, String> getDados() {
		return dados;
	}
}
