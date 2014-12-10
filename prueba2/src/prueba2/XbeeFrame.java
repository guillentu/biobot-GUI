package prueba2;

import java.nio.charset.Charset;
import java.util.StringTokenizer;
import java.util.Arrays;

import sun.io.Converters;
import sun.security.util.Length;

public class XbeeFrame {
	
	final int TRANSMIT_REQUEST= 0x10;
	final int LENGTH_START=		0x01;
	final int LENGTH_LENGTH=	0x02;
	final int LENGTH_TYPE=		0x01;
	final int LENGTH_ID=		0x01;
	final int LENGTH_ADDRESS16= 0x02;
	final int LENGTH_ADDRESS64=	0x08;
	final int LENGTH_BROADCAST=	0x01;
	final int LENGTH_OPTION=	0x01;
	final int LENGTH_CRC=		0x01;
	
	
	public final int start = 	0x7e;
	public int length[]= 		new int[] {0x00, 0x00};
	public int type=			TRANSMIT_REQUEST; // Transmit request : PUT TYPE EN HEX
	public int id=				0x01; // Any ID that it isn't important parameter 
	XBeeAddress16 address16=	new XBeeAddress16(0xff, 0xfe);// PUT ADDRESS EN HEX
	XBeeAddress64 address64=	new XBeeAddress64(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00);// PUT ADDRESS EN HEX
	public int broadcast=		0x00;// Numbers of Hops to get the receiver
	public int option=			0x00;
	public int dataFrame[]=		new int[]{}; // Data to send
	public Checksum crc=		new Checksum(); // crc = 
	
	public int bigDataFrame[]=	new int[]{}; // all bytes to send, it's a complete data frame
	public byte bigDataBytes[]=	new byte[]{};
	
	public XbeeFrame(){
		
	}
	
	public void setAddress64(String adr){
		address64.setAddress64(adr);
	}
	
	public void setAddress16(String adr){
		address16.setAddress16(adr);
	}
	
	public void setData(int[] data){
		dataFrame = new int[data.length];
		
		for (int i=0; i<dataFrame.length;i++){
			dataFrame[i]= i;
			System.out.println("\n lenght datos:" + dataFrame.length +" datos:" + ByteUtils.toBase16(dataFrame[i]));
		}
	}
	
	/**
	 * Parses a data from a string representation Must be in the
	 * HEX format "## ## ## ## ... ##" (i.e. don't use 0x prefix)
	 * 
	 * @param DataStr
	 */
	public void setData(String dataStr) {
		StringTokenizer st = new StringTokenizer(dataStr, " ");
		
		int dataNum = st.countTokens();
		System.out.println("\n NUMERO DE DATOS:" + dataNum);

		dataFrame = new int[dataNum];
		dataFrame[1]=12;
		for (int i=0 ; i< dataFrame.length ; i++){
			String byteStr = st.nextToken();
			dataFrame[i] = Integer.parseInt(byteStr, 16);	
			System.out.println("\n String HEX datos:" + ByteUtils.toBase16(dataFrame[i]));
		}
	}
	/**
	 * Parses a data from a string representation Must be in the
	 * ASCII format.
	 * 
	 * @param DataStr
	 */
	public void setDataASCII(String dataStr) {
		byte dataCHAR[] = dataStr.getBytes();
		dataFrame = new int[dataCHAR.length];
		
		for (int i=0; i<dataFrame.length;i++){
			dataFrame[i] = dataCHAR[i];
		}
		System.out.println("\n String ASCII datos:" + ByteUtils.toBase10(dataFrame));
		
	}
	
	/**
	 * Fill all data into the big frame string
	 */
	public void makeFrame(){
		int lengthBigFrame;
		int index=0;
		
		calculeLength();
		makeCRC();
		
		lengthBigFrame = LENGTH_START + LENGTH_LENGTH + LENGTH_TYPE + LENGTH_ID + LENGTH_ADDRESS64 + 
				 LENGTH_ADDRESS16 + LENGTH_BROADCAST + LENGTH_OPTION + dataFrame.length + LENGTH_CRC;
		System.out.println("\n Longitud TOTAL de la Trama: " + lengthBigFrame);
		
		bigDataFrame = new int[lengthBigFrame];
		
		bigDataFrame[index] = start;
		index++;
		bigDataFrame[index] = length[0];
		index++;
		bigDataFrame[index] = length[1];
		index++;
		bigDataFrame[index] = type;
		index++;
		bigDataFrame[index] = id;
		index++;
		int aux[] = address64.getAddress(); 
		for (int i=0;i<aux.length;i++){
			bigDataFrame[index] = aux[i];
			index++;
		}
		bigDataFrame[index] = address16.getMsb();
		index++;
		bigDataFrame[index] = address16.getLsb();
		index++;
		bigDataFrame[index] = broadcast;
		index++;
		bigDataFrame[index] = option;
		index++;
		for (int i=0;i<dataFrame.length;i++){
			bigDataFrame[index] = dataFrame[i];
			index++;
		}
		bigDataFrame[index] = crc.getChecksum();
		// Fill array bytes bigDataBytes
		bigDataBytes = new byte[bigDataFrame.length];
		for (int i=0;i<bigDataFrame.length;i++){
			bigDataBytes[i] = (byte) bigDataFrame[i];
		}
		//(bigDataFrame[1]);
		System.out.println("\n bigData convert " + ByteUtils.toBase10(bigDataFrame));
		System.out.println("\n bigData " + bigDataFrame.toString().getBytes() + " index: " + index);
	}
	
	/**
	 * Calcule data length
	 */
	public void calculeLength(){
		int auxLength = 0;
		
		auxLength = LENGTH_TYPE + LENGTH_ID + LENGTH_ADDRESS64 + 
				 LENGTH_ADDRESS16 + LENGTH_BROADCAST + LENGTH_OPTION + dataFrame.length;
		
		length[1] = auxLength & 0xff;
		length[0] = (auxLength & 0xff00)>>8;
		
		System.out.println("\n Longitud TOTAL de la Trama: " + ByteUtils.toBase10(length));
		System.out.println("\n LSB: " + length[0] + " MSB: " + length[1] + " integar "+ auxLength);
	}
	
	public void makeCRC(){
		
		crc.checksum = 0;
		crc.addByte(type);
		crc.addByte(id);
		crc.addByte(address16.getLsb());
		crc.addByte(address16.getMsb());
		int dir[] = address64.getAddress();
		for (int i=0; i < address64.getAddress().length; i++)
			crc.addByte(dir[i]);
		crc.addByte(broadcast);
		crc.addByte(option);
		
		for (int i=0; i < dataFrame.length; i++)
			crc.addByte(dataFrame[i]);
		
		crc.compute();
		//crc.verify();
	}
}
