package protection.model.Direction;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.SEQ;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.logicalnodes.common.LN;

import static protection.model.Direction.Direction.BACKWARD;
import static protection.model.Direction.Direction.FORWARD;


@Getter @Setter
public class RDIR extends LN {

    private ACD Dir = new ACD();
    private WYE W = new WYE();
    private SEQ W0 = new SEQ();

    @Override
    public void process() {

        if (W.getPhsA().getCVal().getMag().getF().getValue()<0) {

            Dir.getDirPhsA().setValue(BACKWARD);
        }
        else {
            Dir.getDirPhsA().setValue(FORWARD);

        }
        if (W.getPhsB().getCVal().getMag().getF().getValue()<0) {
            Dir.getDirPhsB().setValue(BACKWARD);

        }
        else {
            Dir.getDirPhsB().setValue(FORWARD);

        }
        if (W.getPhsC().getCVal().getMag().getF().getValue()<0) {
            Dir.getDirPhsC().setValue(BACKWARD);

        }
        else {
            Dir.getDirPhsC().setValue(FORWARD);


        }

        if (Dir.getDirPhsA().getValue().equals(BACKWARD) && Dir.getPhsB().equals(FORWARD) && Dir.getPhsC().equals(FORWARD)) {

            Dir.getDirGeneral().setValue(FORWARD);
        }
        else {
            Dir.getDirGeneral().setValue(BACKWARD);
        }

        if (W.getPhsA().getCVal().getR().getF().getValue() < 0 || W.getPhsB().getCVal().getR().getF().getValue() < 0 ||
                W.getPhsC().getCVal().getR().getF().getValue() < 0) {
            Dir.getGeneral().setValue(true);
        }
    }
}
