package unitTest;

import b2b.robot.controller.VerificationController;

public class DaemonFruitTest {
	public static void main(String[] args) {
		String imgFile = "C:\\Users\\LINI8\\Desktop\\unit\\7w5m4.png";
		
		VerificationController vc = new VerificationController();
		String charts = vc.getCharts(imgFile);
		System.out.println(charts);
		
	}
}
