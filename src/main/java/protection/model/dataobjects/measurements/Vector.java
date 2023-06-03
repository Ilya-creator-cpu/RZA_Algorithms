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

    @Getter @Setter
    private AnalogueValue Vectx = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue Vecty = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue rad = new AnalogueValue();

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

    public void ToVector(double ampl, double ugl) {

        Vectx.getF().setValue(ampl);
        Vecty.getF().setValue(ugl);
        rad.getF().setValue(Math.atan2(ugl,ampl));
        mag.getF().setValue(Math.sqrt(ampl*ampl + ugl*ugl));
        ang.getF().setValue(Math.toDegrees(rad.getF().getValue()));


    }

    public void SetVectors(double ampl, double ugl) {

        mag.getF().setValue(ampl);
        ang.getF().setValue(ugl);
        rad.getF().setValue(Math.toRadians(ang.getF().getValue()));
        Vectx.getF().setValue((ampl * Math.cos(rad.getF().getValue())));
        Vecty.getF().setValue(ampl * Math.sin(rad.getF().getValue()));



    }



    public void Subtract(Vector a, Vector b, Vector c) {

        a.SetVectors(a.getMag().getF().getValue(),a.getAng().getF().getValue());
        b.SetVectors(b.getMag().getF().getValue(),b.getAng().getF().getValue());
        c.SetVectors(c.getMag().getF().getValue(),c.getAng().getF().getValue());
        Vectx.getF().setValue(a.getVectx().getF().getValue() + b.getVectx().getF().getValue() + c.getVectx().getF().getValue());
        Vecty.getF().setValue(a.getVecty().getF().getValue() + b.getVecty().getF().getValue() + c.getVecty().getF().getValue());

    }

    public void SubtractTwo(Vector a, Vector b) {
        a.SetVectors(a.getMag().getF().getValue(),a.getAng().getF().getValue());
        b.SetVectors(b.getMag().getF().getValue(),b.getAng().getF().getValue());
        Vectx.getF().setValue(a.getVectx().getF().getValue() + b.getVectx().getF().getValue());
        Vecty.getF().setValue(a.getVecty().getF().getValue() + b.getVecty().getF().getValue());
    }



}
