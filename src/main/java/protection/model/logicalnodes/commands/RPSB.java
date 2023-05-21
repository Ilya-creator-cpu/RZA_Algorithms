package protection.model.logicalnodes.commands;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.SEQ;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.common.LN;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
public class RPSB extends LN {

    private ACD Str = new ACD();
    private ACT Op = new ACT();
    private SPS BlkZn = new SPS();
    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private List<Double> currentList = new ArrayList<>();

    private List<WYE> voltageList = new ArrayList<>();

    private WYE current = new WYE();

    private WYE voltage;


    private boolean isBlocked;

    private int size = 80;


    private int count = 0;

    private double bufferCurrentA;

    private double bufferCurrentB;

    private double bufferCurrentC;

    private double bufferVoltageA;

    private double bufferVoltageB;

    private double bufferVoltageC;



    @Override
    public void process() {

        BlkZn.getСtVal().setValue(((current.getPhsA().getCVal().getMag().getF().getValue() - bufferCurrentA) > 40)
        || (current.getPhsB().getCVal().getMag().getF().getValue() - bufferCurrentB) > 40 ||
                (current.getPhsC().getCVal().getMag().getF().getValue() - bufferCurrentC > 40)
        && ((bufferVoltageA - voltage.getPhsA().getCVal().getMag().getF().getValue() > 40) ||
                        (bufferVoltageB - voltage.getPhsB().getCVal().getMag().getF().getValue() > 40) ||
                        bufferVoltageC - voltage.getPhsC().getCVal().getMag().getF().getValue() > 40)) ;



           bufferCurrentA = current.getPhsA().getCVal().getMag().getF().getValue();
           bufferVoltageA = voltage.getPhsA().getCVal().getMag().getF().getValue();

           bufferCurrentB = current.getPhsB().getCVal().getMag().getF().getValue();
           bufferVoltageB = voltage.getPhsB().getCVal().getMag().getF().getValue();

           bufferCurrentC = current.getPhsC().getCVal().getMag().getF().getValue();
           bufferVoltageC = voltage.getPhsC().getCVal().getMag().getF().getValue();

        }

    }



