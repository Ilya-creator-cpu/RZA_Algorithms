package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;

@Getter @Setter
public class RMXU extends LN {
    private WYE ALoc = new WYE();
    private WYE RstA = new WYE();
    private WYE A1 = new WYE();
    private WYE A2 = new WYE();
    private WYE A3 = new WYE();
    private WYE A4 = new WYE();
    private WYE A5 = new WYE();

    @Override
    public void process() {
        Vector a1 = A1.getPhsA().getCVal();
        Vector a2 = A2.getPhsA().getCVal();
        Vector a3 = A3.getPhsA().getCVal();
        Vector a4 = A4.getPhsA().getCVal();
        Vector a5 = A5.getPhsA().getCVal();

        Vector b1 = A1.getPhsB().getCVal();
        Vector b2 = A2.getPhsB().getCVal();
        Vector b3 = A3.getPhsB().getCVal();
        Vector b4 = A4.getPhsB().getCVal();
        Vector b5 = A5.getPhsB().getCVal();

        Vector c1 = A1.getPhsC().getCVal();
        Vector c2 = A2.getPhsC().getCVal();
        Vector c3 = A3.getPhsC().getCVal();
        Vector c4 = A4.getPhsC().getCVal();
        Vector c5 = A5.getPhsC().getCVal();


        /** Дифференциальный ток */
        ALoc.getPhsA().getCVal().setOrt(a1.getOrtX(a1.getMag().getF().getValue(),   a1.getAng().getF().getValue())+
                        a2.getOrtX(a2.getMag().getF().getValue(), a2.getAng().getF().getValue())+
                        a3.getOrtX(a3.getMag().getF().getValue(), a3.getAng().getF().getValue())+
                        a4.getOrtX(a4.getMag().getF().getValue(), a4.getAng().getF().getValue())+
                        a5.getOrtX(a5.getMag().getF().getValue(), a5.getAng().getF().getValue()),
                a1.getOrtY(a1.getMag().getF().getValue(), a1.getAng().getF().getValue())+
                        a2.getOrtY(a2.getMag().getF().getValue(),    a2.getAng().getF().getValue())+
                        a3.getOrtY(a3.getMag().getF().getValue(),    a3.getAng().getF().getValue())+
                        a4.getOrtY(a4.getMag().getF().getValue(),    a4.getAng().getF().getValue())+
                        a5.getOrtY(a5.getMag().getF().getValue(),    a5.getAng().getF().getValue()));

        ALoc.getPhsB().getCVal().setOrt(b1.getOrtX(b1.getMag().getF().getValue(),    b1.getAng().getF().getValue())+
                        b2.getOrtX(b2.getMag().getF().getValue(),  b2.getAng().getF().getValue())+
                        b3.getOrtX(b3.getMag().getF().getValue(),  b3.getAng().getF().getValue())+
                        b4.getOrtX(b4.getMag().getF().getValue(),  b4.getAng().getF().getValue())+
                        b5.getOrtX(b5.getMag().getF().getValue(),  b5.getAng().getF().getValue()),
                b1.getOrtY(b1.getMag().getF().getValue(),       b1.getAng().getF().getValue())+
                        b2.getOrtY(b2.getMag().getF().getValue(),  b2.getAng().getF().getValue())+
                        b3.getOrtY(b3.getMag().getF().getValue(),  b3.getAng().getF().getValue())+
                        b4.getOrtY(b4.getMag().getF().getValue(),  b4.getAng().getF().getValue())+
                        b5.getOrtY(b5.getMag().getF().getValue(),  b5.getAng().getF().getValue()));

        ALoc.getPhsC().getCVal().setOrt(c1.getOrtX(c1.getMag().getF().getValue(),  c1.getAng().getF().getValue())+
                        c2.getOrtX(c2.getMag().getF().getValue(),c2.getAng().getF().getValue())+
                        c3.getOrtX(c3.getMag().getF().getValue(),c3.getAng().getF().getValue())+
                        c4.getOrtX(c4.getMag().getF().getValue(),c4.getAng().getF().getValue())+
                        c5.getOrtX(c5.getMag().getF().getValue(),c5.getAng().getF().getValue()),
                a1.getOrtY(c1.getMag().getF().getValue(),     c1.getAng().getF().getValue())+
                        c2.getOrtY(a2.getMag().getF().getValue(),c2.getAng().getF().getValue())+
                        c3.getOrtY(a3.getMag().getF().getValue(),c3.getAng().getF().getValue())+
                        c4.getOrtY(a4.getMag().getF().getValue(),c4.getAng().getF().getValue())+
                        c5.getOrtY(a5.getMag().getF().getValue(),c5.getAng().getF().getValue()));

        /** Тормозной ток */
        RstA.getPhsA().getCVal().getMag().getF().setValue(b1.getMag().getF().getValue() +
                a2.getMag().getF().getValue()+
                a3.getMag().getF().getValue()+
                a4.getMag().getF().getValue()+
                a5.getMag().getF().getValue());
        RstA.getPhsB().getCVal().getMag().getF().setValue(b1.getMag().getF().getValue()+
                b2.getMag().getF().getValue()+
                b3.getMag().getF().getValue()+
                b4.getMag().getF().getValue()+
                b5.getMag().getF().getValue());
        RstA.getPhsC().getCVal().getMag().getF().setValue (c1.getMag().getF().getValue()+
                c2.getMag().getF().getValue()+
                c3.getMag().getF().getValue()+
                c4.getMag().getF().getValue()+
                c5.getMag().getF().getValue());

    }
}
