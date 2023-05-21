package protection.model.Direction;
import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.SEQ;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;



@Setter @Getter
public class MSQI extends LN {



    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();
    private SEQ SeqW = new SEQ();


    private WYE A = new WYE();

    private WYE PhV = new WYE();

    private WYE W = new WYE();

    private CMV a = new CMV();
    private CMV b = new CMV();
    private CMV c = new CMV();

    @Override
    public void process() {

        setSeq(A,SeqA);


    }
    private void setSeq(WYE w, SEQ s){



        /** Нулевая последовательность */
        s.getC3().getCVal().setOrt((w.getPhsA().getCVal().getOrtX(w.getPhsA().getCVal().getMag().getF().getValue(),w.getPhsA().getCVal().getAng().getF().getValue())
                        +w.getPhsB().getCVal().getOrtX(w.getPhsB().getCVal().getMag().getF().getValue(), w.getPhsB().getCVal().getAng().getF().getValue())
                        +w.getPhsC().getCVal().getOrtX(w.getPhsC().getCVal().getMag().getF().getValue(), w.getPhsC().getCVal().getAng().getF().getValue()))/3,
                (w.getPhsA().getCVal().getOrtY(A.getPhsA().getCVal().getMag().getF().getValue(),w.getPhsA().getCVal().getAng().getF().getValue())
                        +w.getPhsB().getCVal().getOrtY(A.getPhsB().getCVal().getMag().getF().getValue(), w.getPhsB().getCVal().getAng().getF().getValue())
                        +w.getPhsC().getCVal().getOrtY(A.getPhsC().getCVal().getMag().getF().getValue(), w.getPhsC().getCVal().getAng().getF().getValue()))/3);
    }





}