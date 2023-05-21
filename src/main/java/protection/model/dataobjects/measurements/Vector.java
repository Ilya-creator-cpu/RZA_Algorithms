package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */


public class Vector extends DATA {


    @Getter @Setter
    private AnalogueValue mag = new AnalogueValue();
    @Getter @Setter
    private AnalogueValue ang = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue R = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue X = new AnalogueValue();

    public double getOrtX(double r, double f){
        return  (r*Math.cos(Math.toRadians(f)));
    }

    public double getOrtY(double r, double f){
        return (r*Math.sin(Math.toRadians(f)));
    }

    public void setOrt(double Y, double X){
        mag.getF().setValue(Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2)));
        ang.getF().setValue(Math.toDegrees(Math.atan2(Y, X)));
    }



}
