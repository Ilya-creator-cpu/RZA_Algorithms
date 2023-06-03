package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;

import java.util.ArrayList;


@Getter @Setter
public class RMXU extends LN {

    private ArrayList<WYE> inputA = new ArrayList<>();
    private WYE dif = new WYE();
    private ArrayList<WYE> Difoutput = new ArrayList<>();

    private ArrayList<WYE> newDifOutput = new ArrayList<>();

    private CSD TmASt = new CSD();
    private Vector rstCurrent = new Vector();

    private double breakCurr;

    private WYE RstA = new WYE();
    private WYE TripPoint = new WYE();          //Тормозная характеристика

    private Vector Idifa = new Vector();
    private Vector Idifb = new Vector();
    private Vector Idifc = new Vector();

    private Vector newIdifa = new Vector();

    private Vector newIdifb = new Vector();

    private Vector newIdifc = new Vector();

    private double dif0;
    private double k;
    private double rst0;

    private WYE maxA = new WYE();


    double BasicPower = 500*(Math.pow(10,3));


    private double uVn = 500;

    private double uSn = 220;

    private double uNN = 10;

    private double kVn = 160;

    private double kSn = 400;

    private double kNn = 8000;


    @Override
    public void process() {



        for (WYE w: inputA) {
            newDifOutput.add(w);

        }

        double IVnBasic = BasicPower/(uVn * kVn * Math.sqrt(3));

        double ISnBasic = BasicPower/ (uSn * kSn * Math.sqrt(3));

        double INnBasic = BasicPower/ (uNN * kNn * Math.sqrt(3));



        rstCurrent.ToVector(0,0);



        for (int i=0;i<=2;i++){
            inputA.get(i).getPhsA().getCVal().getMag().getF().setValue(inputA.get(i).getPhsA().getCVal().getMag().getF().getValue()/IVnBasic);
            inputA.get(i).getPhsB().getCVal().getMag().getF().setValue(inputA.get(i).getPhsB().getCVal().getMag().getF().getValue()/ISnBasic);
            inputA.get(i).getPhsC().getCVal().getMag().getF().setValue(inputA.get(i).getPhsC().getCVal().getMag().getF().getValue()/INnBasic);
        }



        Idifa.Subtract(inputA.get(0).getPhsA().getCVal(), inputA.get(1).getPhsA().getCVal(), inputA.get(2).getPhsA().getCVal());
//


        Idifa.ToVector(Idifa.getVectx().getF().getValue(),Idifa.getVecty().getF().getValue());

        Idifb.Subtract(inputA.get(0).getPhsB().getCVal(), inputA.get(1).getPhsB().getCVal(), inputA.get(2).getPhsB().getCVal());
        Idifb.ToVector(Idifb.getVectx().getF().getValue(),Idifb.getVecty().getF().getValue());

        Idifc.Subtract(inputA.get(0).getPhsC().getCVal(), inputA.get(1).getPhsC().getCVal(),inputA.get(2).getPhsC().getCVal());
        Idifc.ToVector(Idifc.getVectx().getF().getValue(),Idifc.getVecty().getF().getValue());



        dif.getPhsA().setCVal(Idifa);
        dif.getPhsB().setCVal(Idifb);
        dif.getPhsC().setCVal(Idifc);

        Difoutput.add(dif);

        maxA = defineMaxRst(inputA);

        newDifOutput.remove(maxA);

        newIdifa.SubtractTwo(newDifOutput.get(0).getPhsA().getCVal(), newDifOutput.get(1).getPhsA().getCVal());
//


        newIdifa.ToVector(newIdifa.getVectx().getF().getValue(),newIdifa.getVecty().getF().getValue());

        newIdifb.SubtractTwo(newDifOutput.get(0).getPhsB().getCVal(), newDifOutput.get(1).getPhsB().getCVal());
        newIdifb.ToVector(newIdifb.getVectx().getF().getValue(),newIdifb.getVecty().getF().getValue());

        newIdifc.SubtractTwo(newDifOutput.get(0).getPhsC().getCVal(), newDifOutput.get(1).getPhsC().getCVal());
        newIdifc.ToVector(newIdifc.getVectx().getF().getValue(),newIdifc.getVecty().getF().getValue());




        dif0 = TmASt.getCrvPvs().get(1).getYVal().getValue();
        rst0 = TmASt.getCrvPvs().get(1).getXVal().getValue();
        k = (TmASt.getCrvPvs().get(2).getYVal().getValue() - TmASt.getCrvPvs().get(1).getYVal().getValue())/
                (TmASt.getCrvPvs().get(2).getXVal().getValue() - TmASt.getCrvPvs().get(1).getXVal().getValue());


       RstA.getPhsA().getCVal().getMag().getF().setValue(
                Math.sqrt(maxA.getPhsA().getCVal().getOrtX(maxA.getPhsA().getCVal().getMag().getF().getValue(), maxA.getPhsA().getCVal().getAng().getF().getValue())
                * newIdifa.getOrtX(newIdifa.getMag().getF().getValue(),newIdifa.getAng().getF().getValue())));
        RstA.getPhsB().getCVal().getMag().getF().setValue(
                Math.sqrt(maxA.getPhsB().getCVal().getOrtX(maxA.getPhsB().getCVal().getMag().getF().getValue(), maxA.getPhsB().getCVal().getAng().getF().getValue())
                        * newIdifb.getOrtX(newIdifb.getMag().getF().getValue(),newIdifb.getAng().getF().getValue())));
        RstA.getPhsC().getCVal().getMag().getF().setValue(
                Math.sqrt(maxA.getPhsC().getCVal().getOrtX(maxA.getPhsC().getCVal().getMag().getF().getValue(), maxA.getPhsC().getCVal().getAng().getF().getValue())
                        * newIdifc.getOrtX(newIdifc.getMag().getF().getValue(),newIdifc.getAng().getF().getValue())));



        for (WYE w:inputA){


            if(w.getPhsA().getCVal().getMag().getF().getValue() > RstA.getPhsA().getCVal().getMag().getF().getValue()){
                rstCurrent.SetVectors(w.getPhsA().getCVal().getVectx().getF().getValue(),w.getPhsA().getCVal().getVecty().getF().getValue());
            }
            if(w.getPhsB().getCVal().getMag().getF().getValue() > RstA.getPhsA().getCVal().getMag().getF().getValue()){
                rstCurrent.SetVectors(w.getPhsB().getCVal().getVectx().getF().getValue(),w.getPhsB().getCVal().getVecty().getF().getValue());
            }
            if(w.getPhsC().getCVal().getMag().getF().getValue() > RstA.getPhsA().getCVal().getMag().getF().getValue()){
                rstCurrent.SetVectors(w.getPhsC().getCVal().getVectx().getF().getValue(),w.getPhsC().getCVal().getVecty().getF().getValue());
            }

        }



        if (rstCurrent.getMag().getF().getValue() < rst0) {
            TripPoint.getPhsA().getCVal().ToVector(dif0,0);
            TripPoint.getPhsB().getCVal().ToVector(dif0,0);
            TripPoint.getPhsC().getCVal().ToVector(dif0,0);

        }
        else{
            TripPoint.getPhsA().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsB().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsC().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
        }
    }

    public WYE defineMaxRst(ArrayList<WYE> inputA) {

        WYE maxA = inputA.get(0);

        double PhsAmax = inputA.get(0).getPhsA().getCVal().getMag().getF().getValue();
        double PhsBmax = inputA.get(0).getPhsB().getCVal().getMag().getF().getValue();
        double PhsCmax = inputA.get(0).getPhsC().getCVal().getMag().getF().getValue();

        for (WYE A: inputA) {

          if (A.getPhsA().getCVal().getMag().getF().getValue() > PhsAmax
          && A.getPhsB().getCVal().getMag().getF().getValue() > PhsBmax
          && A.getPhsC().getCVal().getMag().getF().getValue() > PhsCmax) {
              maxA = A;
          }
        }

        return maxA;
    }
}
