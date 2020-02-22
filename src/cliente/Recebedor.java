package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import util.Util;

public class Recebedor implements Runnable {
	private DataInputStream servidor;
	private static Map<String, String> dados;
	
	public Recebedor(DataInputStream servidor) {
		this.servidor = servidor;
		dados = new HashMap<String, String>();
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
				
				System.out.print(Arrays.toString(transacao));
				System.out.print(tamanho);
				System.out.println(Arrays.toString(valor));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void salvaHashMap(byte[] protocolo) {
		byte[] bId = Arrays.copyOfRange(protocolo, 0, 8);
		String id = Util.bytesToString(bId);
		byte tipo = protocolo[8];
		byte[] valor = Arrays.copyOfRange(protocolo, 9, 13);
		dados.put(id, trataResultado(tipo, valor));
	}
	
	public String trataResultado(int tipo, byte[] dado) {
		if (tipo == Util.WORD) {
			return Integer.toString(convertByteToInt(dado));
		} else if (tipo == Util.FLOAT) {
			return String.format("%.2f", convertByteToFloat(dado));
		}
		
		return null;
	}
	
	public float convertByteToFloat(byte[] b) {
		return ByteBuffer.wrap(b).getFloat();
	}
	
	public int convertByteToInt(byte[] b) {
		return (short) convertByteToUInt(b);
	}
	
	public int convertByteToUInt(byte[] b) {
		return ByteBuffer.wrap(b).getInt();
	}
	
	public static Map<String, String> getDados() {
		return dados;
	}
}
