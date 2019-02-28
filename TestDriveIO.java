/**
 * HelloWorld
 */

import java.io.File;

public class TestDriveIO {
    private static double [] input = {
        0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,  /* ramp forward up */
        1.0, 1.0, 1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3,  /* ramp forward down*/
        0.2, 0.1, 0.0, 0.0, 0.0, -0.1, -0.2, -0.3, -0.4, -0.5,  /* ramp backwards up */
        -0.6, -0.7, -0.8, -0.9, -1.0, -1.0, -1.0, -1.0, -1.0    /* ramp backwards down */
        -0.95, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0.0,  /* ramp backwards down */
        0.1, 1.0, 1.0, 1.0, -1.0, -1.0 -1.0, 1.0, 1.0, 1.0,  /* crazy stuff */
        
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, 
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 
				 0, 0, 0, 0 
};

public static void TestSmoothMove() {
    double outputSM = 0;
    double outputTF = 0;
    Filters smooth = new Filters();
    Filters tipping= new Filters();
    int size = input.length;
    for (int i = 0;  i < size; i++){
        outputSM = smooth.CheckSmoothMove(input[i]);
        outputTF = tipping.CheckTippingFilter(input[i]);
        //System.out.println(input[i] + " --> " + output);
        DebugLogger.log(i + "   " + input[i] + "     " + outputSM + "     " + outputTF);
    }
    System.out.println("********************");   
}
    public static void main(String[] args) {
        
		File _logDirectory = new File("C:/temp/test");
		if (!_logDirectory.exists()) {
			_logDirectory.mkdir();
		}
        DebugLogger.init("C:/temp/test/Debug_");
        
        System.out.println("Hello World!");
        TestSmoothMove();
    }
}