package org.example;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.measurements.MMXU;
import utils.filters.Filter;
import utils.filters.FrequencyFurier;
import utils.filters.Furier;
import utils.filters.HFFT;

import javax.swing.*;

public class LR2_Protect {

    public static void main(String[] args) {

        MV instMag = new MV();

        CMV cmv = new CMV();

        MV instMag2 = new MV();

        MMXU mmxu = new MMXU();

        CMV cmv2 = new CMV();

        double FFTout = 155;

        double signal;

        double frequency = 55;

        Filter FF1 = new Furier();



       FrequencyFurier FF2 = new FrequencyFurier(frequency);

        NHMI nhmi = new NHMI();

        nhmi.addSignals(
               new NHMISignal("С подстройкой", cmv2.getCVal().getMag().getF())
       //        new NHMISignal("Без подстройки", cmv.getCVal().getMag().getF())


        );

 //       for (double f =0;f < 300; f++) {

            FF2.setFrequency(10);

            for (double t=0; t< 40;t+=0.00025) {

                signal = FFTout*Math.sin(2*Math.PI*10*t);

                instMag.getInstMag().getF().setValue(signal);
                mmxu.IphsAInst.getInstMag().getF().setValue(signal);
                instMag2.getInstMag().getF().setValue(signal);
                mmxu.process();
                FF1.process(instMag,cmv);
                FF2.process(instMag2,cmv2);
                nhmi.process();

            }
            System.out.println(cmv2.getCVal().getMag().getF().getValue());








    //          }

    }















}
