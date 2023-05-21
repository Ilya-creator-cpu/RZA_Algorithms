package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;
import utils.filters.*;

import java.util.ArrayList;


@Getter @Setter
public class MMXU extends LN {

    /*
     * Входы
     */
    public MV IphsAInst = new MV();
    public MV IphsBInst = new MV();
    public MV IphsCInst = new MV();


    public MV UphsBinst = new MV();

    public MV UphsCinst = new MV();

    public MV UphsAInst = new MV();

    /*
     * Выходы
     */
    public final WYE A = new WYE();

    public final WYE PhV = new WYE();

    public final WYE W = new WYE();

    public final WYE PF = new WYE();

    public final WYE VA = new WYE();

    public final WYE Z = new WYE();

    public MV Freq = new MV();

    private ArrayList<Double> freqList = new ArrayList<>();

    private ArrayList<Double> pointList = new ArrayList<>();

    private double buffrequency;

    private double frequency;

    private double k = Math.exp(-1.8);



    private double bufferValue;





    private final Filter phsACurrent = new Furier();
    private final Filter phsBCurrent = new Furier();
    private final Filter phsCCurrent = new Furier();

    private final Filter phsAVoltage = new Furier();

    private final Filter phsBVoltage = new Furier();

    private final Filter phsCVoltage = new Furier();

    private double i1x,i2x,i3x,i1y,i2y,i3y,u1x,u2x,u3x,u1y,u2y,u3y;

    private ArrayList <Double> bufferList = new ArrayList<>();


    @Override
    public void process() {

        getFrequency(IphsAInst.getInstMag().getF().getValue());

        phsACurrent.process(IphsAInst, A.getPhsA());
        phsBCurrent.process(IphsBInst, A.getPhsB());
        phsCCurrent.process(IphsCInst, A.getPhsC());


        phsAVoltage.process(UphsAInst,PhV.getPhsA());
        phsBVoltage.process(UphsBinst,PhV.getPhsB());
        phsCVoltage.process(UphsCinst,PhV.getPhsC());

        getResist(A.getPhsA(), A.getPhsB(), A.getPhsC(), PhV.getPhsA(),
                PhV.getPhsB(),PhV.getPhsC());






        // Коэффициент мощности

        PF.getPhsA().getCVal().getMag().getF().setValue(
                  Math.cos(PhV.getPhsA().getCVal().getAng().getF().getValue()-
                          A.getPhsA().getCVal().getAng().getF().getValue()));

        PF.getPhsB().getCVal().getMag().getF().setValue(
                Math.cos(PhV.getPhsB().getCVal().getAng().getF().getValue()-
                        A.getPhsB().getCVal().getAng().getF().getValue()));
        PF.getPhsC().getCVal().getMag().getF().setValue(
                Math.cos(PhV.getPhsC().getCVal().getAng().getF().getValue()-
                        A.getPhsC().getCVal().getAng().getF().getValue()));

        // Полная мощность

        VA.getPhsA().getCVal().getMag().getF().setValue(
                PhV.getPhsA().getCVal().getMag().getF().getValue()*
                        A.getPhsA().getCVal().getMag().getF().getValue());
        VA.getPhsB().getCVal().getMag().getF().setValue(
                PhV.getPhsB().getCVal().getMag().getF().getValue()*
                        A.getPhsB().getCVal().getMag().getF().getValue());
        VA.getPhsC().getCVal().getMag().getF().setValue(
                PhV.getPhsC().getCVal().getMag().getF().getValue()*
                        A.getPhsC().getCVal().getMag().getF().getValue());

        // Активная мощность

        W.getPhsA().getCVal().getMag().getF().setValue(VA.getPhsA().getCVal().getMag().getF().getValue() *
                PF.getPhsA().getCVal().getMag().getF().getValue());
        W.getPhsB().getCVal().getMag().getF().setValue(VA.getPhsB().getCVal().getMag().getF().getValue() *
                PF.getPhsB().getCVal().getMag().getF().getValue());
        W.getPhsC().getCVal().getMag().getF().setValue(VA.getPhsC().getCVal().getMag().getF().getValue() *
                PF.getPhsC().getCVal().getMag().getF().getValue());
    }

    public void getResist(CMV i1, CMV i2, CMV i3, CMV u1, CMV u2, CMV u3) {


        u1x = u1.getCVal().getOrtX(u1.getCVal().getMag().getF().getValue(),u1.getCVal().getAng().getF().getValue());
        i1x = i1.getCVal().getOrtX(i1.getCVal().getMag().getF().getValue(),i1.getCVal().getAng().getF().getValue());
        u1y = u1.getCVal().getOrtY(u1.getCVal().getMag().getF().getValue(),u1.getCVal().getAng().getF().getValue());
        i1y = i1.getCVal().getOrtY(i1.getCVal().getMag().getF().getValue(),i1.getCVal().getAng().getF().getValue());


        u2x = u2.getCVal().getOrtX(u2.getCVal().getMag().getF().getValue(),u2.getCVal().getAng().getF().getValue());
        i2x = i2.getCVal().getOrtX(i2.getCVal().getMag().getF().getValue(),i2.getCVal().getAng().getF().getValue());
        u2y = u2.getCVal().getOrtY(u2.getCVal().getMag().getF().getValue(),u2.getCVal().getAng().getF().getValue());
        i2y = i2.getCVal().getOrtY(i2.getCVal().getMag().getF().getValue(),i2.getCVal().getAng().getF().getValue());


        u3x = u3.getCVal().getOrtX(u3.getCVal().getMag().getF().getValue(),u3.getCVal().getAng().getF().getValue());
        i3x = i3.getCVal().getOrtX(i3.getCVal().getMag().getF().getValue(),i3.getCVal().getAng().getF().getValue());
        u3y = u3.getCVal().getOrtY(u3.getCVal().getMag().getF().getValue(),u3.getCVal().getAng().getF().getValue());
        i3y = i3.getCVal().getOrtY(i3.getCVal().getMag().getF().getValue(),i3.getCVal().getAng().getF().getValue());


        Z.getPhsA().getCVal().getR().getF().setValue(
                (((u1x-u2x)*(i1x-i2x)+(u1y-u2y)*(i1y-i2y))/(Math.pow((i1x-i2x),2)+Math.pow((i1y-i2y),2)))
        );

        Z.getPhsA().getCVal().getX().getF().setValue(
                -(((u1y-u2y)*(i1x-i2x)-(u1x-u2x)*(i1y-i2y))/(Math.pow((i1x-i2x),2)+Math.pow((i1y-i2y),2)))
        );

        Z.getPhsB().getCVal().getR().getF().setValue(
               (((u2x-u3x)*(i2x-i3x)+(u2y-u3y)*(i2y-i3y))/(Math.pow((i2x-i3x),2)+Math.pow((i2y-i3y),2)))
        );
        Z.getPhsB().getCVal().getX().getF().setValue(
                -(((u2y-u3y)*(i2x-i3x)-(u2x-u3x)*(i2y-i3y))/(Math.pow((i2x-i3x),2)+Math.pow((i2y-i3y),2)))
        );
        Z.getPhsC().getCVal().getR().getF().setValue(
                (((u3x-u1x)*(i3x-i1x)+(u3y-u1y)*(i3y-i1y))/(Math.pow((i3x-i1x),2)+Math.pow((i3y-i1y),2)))
        );
        Z.getPhsC().getCVal().getX().getF().setValue(
                -(((u3y-u1y)*(i3x-i1x)-(u3x-u1x)*(i3y-i1y))/(Math.pow((i3x-i1x),2)+Math.pow((i3y-i1y),2)))
        );





    }

    public void getFrequency(double measurements) {
        pointList.add(measurements);
        Freq.getFrequency().getF().setValue(frequency);

        if ((measurements > 0 && bufferValue <0)
                || (measurements < 0 && bufferValue >0)) {
            freqList.add(measurements);
            if (freqList.size() % 2 == 0) {
                frequency = (k*1/(pointList.size()*0.00025) + (1 - k) * buffrequency);
                if (Math.abs(frequency - buffrequency) > 1)
                    frequency = 1/(pointList.size()*0.00025);
                buffrequency = frequency;
                pointList.clear();
            }

            bufferValue = measurements;

        } else if (bufferValue == 0) {
            bufferValue = measurements;
        }
    }



}
