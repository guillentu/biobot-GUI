package prueba2;

public class main1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hola");
		XbeeFrame frame1 = new XbeeFrame();
		//Checksum dd = new Checksum();
		
		
		frame1.type = frame1.TRANSMIT_REQUEST;
		frame1.setAddress64("00 13 A2 00 40 32 DF 8E");
		frame1.setAddress16("FF FE");
		//frame1.setData("00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04 00 01 02 03 04");
		frame1.setData("00 01 02 03 04");
		//frame1.calculeLength();
	
		//int datos[] = new int[5];
		//frame1.setData(datos);
		//frame1.setData("FF FF FF 23 23 23 45 45 56 78 90 43");
		//frame1.setDataASCII("01234");
		
		frame1.makeFrame();		
		
	}

}
