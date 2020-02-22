package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import tipos.Tipo;

public class Util {
	public final static byte BYTE = 1, WORD = 2, FLOAT = 4;
	
	public static byte[] criaPalavraComunicacao(String id, byte tipoDado, 
			short db, short offset) {
		byte[] retorno = new byte[13];
		byte[] byteId = id.getBytes();
		
		for (int i = 0 ; i < 8; i++) {
			if (i < byteId.length) {
				retorno[i] = byteId[i];
			} else {
				retorno[i] = 0;
			}
		}
		
		retorno[8] = tipoDado;
		
		retorno[9] = (byte) ((db >>> 8) & 0xff);
		retorno[10] = (byte) (db & 0xff);
		retorno[11] = (byte) ((offset >>> 8) & 0xff);
		retorno[12] = (byte) (offset & 0xff);
		
		return retorno;
	}
	
	public static byte[] protocoloLeitura(int idTransacao, Tipo tipo, short db, short offsetDb, byte nBit) {
		short transacao = (short) (((idTransacao << 4) & 0xFFF0) + tipo.getCode());
		short offset = (short) ((offsetDb << 3) + (nBit & 07));
		byte tamanho = 0x04;
		byte[] protocolo = new byte[7];
		
		protocolo[0] = (byte) ((transacao >> 8) & 0x00FF);
		protocolo[1] = (byte) (transacao & 0x00FF);
		protocolo[2] = tamanho;
		protocolo[3] = (byte) ((db >> 8) & 0x00FF);
		protocolo[4] = (byte) (db & 0x00FF);
		protocolo[5] = (byte) ((offset >> 8) & 0x00FF);
		protocolo[6] = (byte) (offset & 0x00FF);
		
		return protocolo;
	}
	
	public static String bytesToString(byte[] bytes) {
		String resultado = "";
		
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] > 0) {
				resultado += (char) bytes[i];
			}
		}
		
		return resultado;
	}
	
	public static short bytesToShort(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}
	
	
	public static float convertByteToFloat(byte[] b) {
		return ByteBuffer.wrap(b).getFloat();
	}
	
	public static int convertByteToInt(byte[] b) {
		return ByteBuffer.wrap(b).getShort();
	}
	
	public static int convertByteToUInt(byte[] b) {
		return ByteBuffer.wrap(b).getInt();
	}
}
