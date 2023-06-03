package protection.model.logicalnodes.commands;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.MV;
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

    private List<Double> currentList = new ArrayList<>();

    private List<WYE> voltageList = new ArrayList<>();

    private MV currentA = new MV();

    private MV currentB = new MV();

    private MV currentC = new MV();

    private MV voltageA = new MV();

    private MV voltageB = new MV();

    private MV voltageC = new MV();

    private WYE voltage = new WYE();


    private boolean isBlocked;

    private int size = 80;


    private int count = 0;

    private double bufferCurrentA;

    private double bufferCurrentB;

    private double bufferCurrentC;

    private double bufferVoltageA;

    private double bufferVoltageB;

    private double bufferVoltageC;

    private double BlkValue;

    private double currCurrentA;
    private double currCurrentB;
    private double currCurrentC;

    private double currVoltageB;
    private double currVoltageC;



    @Override
    public void process() {






        BlkZn.getСtVal().setValue((Math.abs(currentA.getInstMag().getF().getValue() - bufferCurrentA) > BlkValue) ||
                (Math.abs(currentB.getInstMag().getF().getValue() - bufferCurrentB)) > BlkValue ||
                (Math.abs(currentC.getInstMag().getF().getValue() - bufferCurrentC) > BlkValue) &&
                (Math.abs(bufferVoltageA - voltageA.getInstMag().getF().getValue()) > BlkValue ||
                Math.abs(bufferVoltageB - voltageB.getInstMag().getF().getValue()) > BlkValue ||
                Math.abs(bufferVoltageC - voltageC.getInstMag().getF().getValue()) > BlkValue));

           bufferCurrentA = currentA.getInstMag().getF().getValue();
           bufferVoltageA = voltageA.getInstMag().getF().getValue();

           bufferCurrentB = currentA.getInstMag().getF().getValue();
           bufferVoltageB = voltageB.getInstMag().getF().getValue();

           bufferCurrentC = currentA.getInstMag().getF().getValue();
           bufferVoltageC = voltageC.getInstMag().getF().getValue();

        }

    }



