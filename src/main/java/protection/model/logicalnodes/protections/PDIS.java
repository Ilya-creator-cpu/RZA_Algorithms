package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ASG;
import protection.model.logicalnodes.Setter.ING;
import protection.model.logicalnodes.commands.SPS;
import protection.model.logicalnodes.common.LN;

@Getter @Setter
public class PDIS extends LN {

    private ACD Str = new ACD();

    private WYE Z = new WYE();

    private WYE PF = new WYE();

    private SPS Blk = new SPS();

    private ACT Op = new ACT();

    private ASG Ro = new ASG();

    private boolean phsA = false;
    private boolean phsB = false;
    private boolean phsC = false;

    private double delay;

    private double x0;

    private double y0;

    public ING OpDlTmms = new ING();

    public boolean inChr;

    public boolean line1, line2, line3, line4, line5;

    public double x1, x2, x3, x4, x5;
    public double r1, r2, r3, r4, r5;



    @Override
    public void process() {

        if (Blk.getСtVal().getValue()) {


            phsA = inCharacteristic(Z.getPhsA());

            phsB = inCharacteristic(Z.getPhsB());

            phsC = inCharacteristic(Z.getPhsC());



            Str.getGeneral().setValue(phsA || phsB || phsC);

            Str.getPhsA().setValue(phsA);
            Str.getPhsB().setValue(phsB);
            Str.getPhsC().setValue(phsC);


            if (Str.getGeneral().getValue()) {


                delay++;

            } else
                delay = 0;

            Op.getGeneral().setValue(Str.getGeneral().getValue() && delay > OpDlTmms.delay);

            Op.getPhsA().setValue(Str.getPhsA().getValue() && delay > OpDlTmms.delay);
            Op.getPhsB().setValue(Str.getPhsB().getValue() && delay > OpDlTmms.delay);
            Op.getPhsC().setValue(Str.getPhsC().getValue() && delay > OpDlTmms.delay);
        }
    }


    public boolean inCharacteristic(CMV MV) {

        double k12 = (x1 - x2) / (r1 - r2);
        double k23 = (x2 - x3) / (r2 - r3);
        double k34 = (x3 - x4) / (r3 - r4);
        double k41 = (x4 - x1) / (r4 - r1);

        double b12 = x1 - k12*r1;
        double b23 = x2 - k23*r2;
        double b34 = x3 - k34*r3;
        double b41 = x4 - k41*r4;



        line1 = MV.getCVal().getX().getF().getValue() <= k12*MV.getCVal().getR().getF().getValue() + b12;
        line2 = MV.getCVal().getX().getF().getValue() >= k23*MV.getCVal().getR().getF().getValue() + b23;
        line3 = MV.getCVal().getX().getF().getValue() >= k34*MV.getCVal().getR().getF().getValue() + b34;
        line4 = MV.getCVal().getX().getF().getValue() >= k41*MV.getCVal().getR().getF().getValue() + b41;

        return (line1 && line2 && line3 && line4);
    }




}
