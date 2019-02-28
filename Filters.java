/**
 * Filters
 */
public class Filters {
    public  double previousEMAValue = 0.0;
    public  int timePeriodSF =  35;
    public  double smoothFactor = 1.0;
    
public  double CheckSmoothMove( double _throttle) {
    double fThrottle = _throttle;
    //double deltaValue = fThrottle - previousEMAValue;

    if ((fThrottle > 0) && (previousEMAValue < -0.1 /*TeleopConfig.ZERO_DEAD_BAND*/))
    {
        // we're tipping!!
        fThrottle = 0;
        timePeriodSF = 35; //TeleopConfig.kHighSmoothPeriod;
        // System.out.println("Tipping forward");
    }
    else if ((fThrottle < 0) && (previousEMAValue > 0.1 /*TeleopConfig.ZERO_DEAD_BAND*/))
    {// we're tipping!!
        fThrottle = 0;
        timePeriodSF = 35; //TeleopConfig.kHighSmoothPeriod;
        // System.out.println("tipping backward");
    }

    double smoothFactor = 2.0 / (timePeriodSF + 1);
    fThrottle = previousEMAValue + smoothFactor * (fThrottle - previousEMAValue);

    if (Math.abs(previousEMAValue) < 0.1 /*TeleopConfig.ZERO_DEAD_BAND*/)
    {
        timePeriodSF = 5; //TeleopConfig.kLowSmoothPeriod;
    }

    previousEMAValue = fThrottle;
    
    return fThrottle;		
}

public  double CheckTippingFilter(double _value) {
    double value = _value;
    // determine change for last joystick read
    double deltaValue = value - previousEMAValue;
    
    // Check joystick value transition from one side of zero to the other side of zero
    if (Math.signum(value) != Math.signum(previousEMAValue)){
        
        // If joystick change is large enough to cause a wheelie or cause the
        // robot to start to tip - the robot intervenes to see that this does
        // not occur The following limits the change in joystick movement
        if (Math.abs(deltaValue) > 0.2 /*TeleopConfig.kTransitionMaxDelta*/){
            smoothFactor = 0.05556; //TeleopConfig.kTransitionSmoothFactor;
        } else {	
        
            // If driver behaves
            smoothFactor = 0.3333; //TeleopConfig.klowSmoothFactor;
        }
    }
    
    // Determine if the sign of value and oldEMA are the same
    else if (Math.signum(value) == Math.signum(previousEMAValue)){
            
        // Check for large deltaValue that may cause a wheelie or 
        // rotation torque to a high Center of gravity on decel

            if (Math.abs(deltaValue) > 0.2 /*TeleopConfig.kMaxDeltaVelocity*/ ){
                smoothFactor = 35; //TeleopConfig.kHighSmoothFactor;
            } else {	
            
                // If driver behaves
                smoothFactor = 5; //TeleopConfig.klowSmoothFactor;
            }
    }
    
    // Check if the smoothing filter is within the joystick deadband and put filter in high response gain
    if (Math.abs(value) < 0.1) {
        value = 0;  // not previousValue?
        smoothFactor = 5; // TeleopConfig.klowSmoothFactor;
    } 		
    // Run through smoothing filter	
    /* 
    *Exponential Avg Filter (EMA) is a recursive low pass filter that
    * can change it's gain to address filter response
    * 
    * Range of smoothFactor is 0 to 1; where smoothFactor = 0 (no smoothing)
    * smoothFactor = .99999 high smoothing
    * Typical smoothFactor = 1-(2.0 / (timePeriodSF + 1)) where user decides
    * on aprox number of cycles for output = input. Time period on
    * iterative robot class is aprox 20ms
    */		
    
    value = previousEMAValue + smoothFactor * (value - previousEMAValue);
    previousEMAValue = value;
    
    return value;
}

}