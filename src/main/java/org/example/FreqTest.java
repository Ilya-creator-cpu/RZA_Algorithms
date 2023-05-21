package org.example;

import protection.model.common.DataAttribute;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MMXU;

import java.util.ArrayList;
import java.util.List;

public class FreqTest {

    public static final List<LN> lnList = new ArrayList<>();

    private static ArrayList<Double> freqList = new ArrayList<>();

    private static int pointCounter = 0;

    private static ArrayList<Double> pointList = new ArrayList<>();

    private static double frequency;
    private static double buffrequency;

    private static double k = Math.exp(-1.8);



    private static double bufferValue;

    public static void main(String[] args) {
        LCOM lcom = new LCOM();
        lnList.add(lcom);

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\KZ1");


        MMXU mmxu = new MMXU();

        lnList.add(mmxu);
        mmxu.IphsAInst = lcom.OUT.get(3);
        mmxu.IphsBInst = lcom.OUT.get(4);
        mmxu.IphsCInst = lcom.OUT.get(5);

        NHMI nhmi = new NHMI();
        lnList.add(nhmi);

        nhmi.addSignals(new NHMISignal(
                "Частота", mmxu.Freq.getFrequency().getF()
        ));


        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
            pointList.add(lcom.OUT.get(3).getInstMag().getF().getValue());
            mmxu.Freq.getFrequency().getF().setValue(frequency);

            if ((lcom.OUT.get(3).getInstMag().getF().getValue() > 0 && bufferValue <0)
                    || (lcom.OUT.get(3).getInstMag().getF().getValue() < 0 && bufferValue >0)) {
                freqList.add(lcom.OUT.get(3).getInstMag().getF().getValue());
                if (freqList.size() % 2 == 0) {
                    frequency =  k*1/(pointList.size()*0.00025) + (1 - k) * buffrequency;
                    buffrequency = frequency;
                    pointList.clear();
                }
                bufferValue = lcom.OUT.get(3).getInstMag().getF().getValue();

            } else if (bufferValue == 0) {
                bufferValue = lcom.OUT.get(3).getInstMag().getF().getValue();
            }



        }



    }
}
