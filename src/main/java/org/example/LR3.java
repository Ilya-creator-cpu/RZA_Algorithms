package org.example;

import protection.model.Direction.MSQI;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Breaker.XCBR;
import protection.model.logicalnodes.commands.RPSB;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.NHMIP;
import protection.model.logicalnodes.gui.other.NHMIPoint;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PDIS;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LR3 {


    public static final List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {

        LCOM lcom = new LCOM();
        lnList.add(lcom);

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\KZ1");


        MMXU mmxu = new MMXU();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();
        PDIS pdis1 = new PDIS();
        PDIS pdis2 = new PDIS();
        PDIS pdis3 = new PDIS();
        PDIS pdis4 = new PDIS();
        PDIS pdis5 = new PDIS();
        PDIS pdis6 = new PDIS();
        RPSB rpsb = new RPSB();


        lnList.add(mmxu);
        lnList.add(cswi);
        lnList.add(xcbr);
        lnList.add(pdis1);
        lnList.add(pdis2);
        lnList.add(pdis3);
        lnList.add(pdis4);
        lnList.add(pdis5);
        lnList.add(pdis6);
        lnList.add(rpsb);



        // Задаем сигналы

        mmxu.UphsAInst = lcom.OUT.get(0);
        mmxu.UphsBinst = lcom.OUT.get(1);
        mmxu.UphsCinst = lcom.OUT.get(2);

        mmxu.IphsAInst = lcom.OUT.get(3);
        mmxu.IphsBInst = lcom.OUT.get(4);
        mmxu.IphsCInst = lcom.OUT.get(5);

        // Блокирвока при качаниях



        // Уставки

        pdis1.setZ(mmxu.Z);
        pdis2.setZ(mmxu.Z);
        pdis3.setZ(mmxu.Z);

        pdis1.r1 = 100d;
        pdis1.x1 = 300d;
        pdis1.r2 = -200d;
        pdis1.x2 = 300d;
        pdis1.r3 = -50d;
        pdis1.x3 = -100d;
        pdis1.r4 = 50d;
        pdis1.x4 =0d;

        pdis2.r1 = 300d;
        pdis2.x1 = 500d;
        pdis2.r2 = -300d;
        pdis2.x2 = 500d;
        pdis2.r3 = -100d;
        pdis2.x3 = -150d;
        pdis2.r4 = 100d;
        pdis2.x4 = 0d;

        pdis3.r1 = 500d;
        pdis3.x1 = 700d;
        pdis3.r2 = -400d;
        pdis3.x2 = 700d;
        pdis3.r3 = -150d;
        pdis3.x3 = -250d;
        pdis3.r4 = 150d;
        pdis3.x4 = 0d;





        pdis1.getOpDlTmms().getSetVal().setValue(15);
        pdis2.getOpDlTmms().getSetVal().setValue(60);
        pdis3.getOpDlTmms().getSetVal().setValue(110);
        pdis4.getOpDlTmms().getSetVal().setValue(160);
        pdis5.getOpDlTmms().getSetVal().setValue(200);
        pdis6.getOpDlTmms().getSetVal().setValue(230);

       rpsb.setCurrent(mmxu.A);
       rpsb.setVoltage(mmxu.PhV);

       pdis1.setBlk(rpsb.getBlkZn());
       pdis2.setBlk(rpsb.getBlkZn());
       pdis3.setBlk(rpsb.getBlkZn());


        cswi.setOpOpn1(pdis1.getOp());
        cswi.setOpOpn2(pdis2.getOp());

        // Выключатель

        xcbr.setPos(cswi.getPos());



        NHMI nhmi = new NHMI();
        lnList.add(nhmi);

        NHMIP nhmip = new NHMIP();
        lnList.add(nhmip);


        nhmip.drawCharacteristic("Chr1", NHMIP.getNhmiPoints(pdis1.r1,pdis1.x1,pdis1.r2,pdis1.x2,
                pdis1.r3, pdis1.x3, pdis1.r4, pdis1.x4));
        nhmip.drawCharacteristic("Chr2", NHMIP.getNhmiPoints(pdis2.r1,pdis2.x1,
                pdis2.r2,pdis2.x2,pdis2.r3,pdis2.x3,pdis2.r4,pdis2.x4));
        nhmip.drawCharacteristic("Chr3", NHMIP.getNhmiPoints(pdis3.r1,pdis3.x1,
                pdis3.r2,pdis3.x2,pdis3.r3,pdis3.x3,pdis3.r4,pdis3.x4));


        nhmip.addSignals(new NHMISignal("Za", mmxu.getZ().getPhsA().getCVal().getR().getF(), mmxu.getZ().getPhsA().getCVal().getX().getF()));
        nhmip.addSignals(new NHMISignal("Zb", mmxu.getZ().getPhsB().getCVal().getR().getF(), mmxu.getZ().getPhsB().getCVal().getX().getF()));
        nhmip.addSignals(new NHMISignal("Zc", mmxu.getZ().getPhsC().getCVal().getR().getF(), mmxu.getZ().getPhsC().getCVal().getX().getF()));
        nhmi.addSignals(new NHMISignal("Ra",mmxu.getZ().getPhsA().getCVal().getR().getF()));
        nhmi.addSignals(new NHMISignal("Rb",mmxu.getZ().getPhsB().getCVal().getR().getF()));
       nhmi.addSignals(new NHMISignal("Rc",mmxu.getZ().getPhsC().getCVal().getR().getF()));
        nhmi.addSignals(new NHMISignal("Xa",mmxu.getZ().getPhsA().getCVal().getX().getF()));
        nhmi.addSignals(new NHMISignal("Xb",mmxu.getZ().getPhsB().getCVal().getX().getF()));
        nhmi.addSignals(new NHMISignal("Xc",mmxu.getZ().getPhsC().getCVal().getX().getF()));

        nhmi.addSignals(new NHMISignal("PDIS1_Str", pdis1.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("PDIS2_Str", pdis2.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("PDIS3_Str", pdis3.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("PDIS4_Str", pdis4.getOp().getGeneral()));
        nhmi.addSignals(new NHMISignal("PDIS5_Str", pdis5.getOp().getGeneral()));
        nhmi.addSignals(new NHMISignal("PDIS6_Str", pdis6.getOp().getGeneral()));
        nhmi.addSignals(new NHMISignal("CSWI", cswi.getPos().getCtVal()));
        nhmi.addSignals(new NHMISignal("XCBR", xcbr.getPos().getCtVal()));


//        nhmi.addSignals(new NHMISignal(
//                "Частота", mmxu.Freq.getFrequency().getF()
//        ));



        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);

        }






    }



}
