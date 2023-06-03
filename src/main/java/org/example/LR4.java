package org.example;

import protection.model.dataobjects.measurements.Point;
import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Breaker.XCBR;
import protection.model.logicalnodes.commands.PHAR;
import protection.model.logicalnodes.commands.PTRC;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.NHMIP;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MHAI;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.measurements.RMXU;
import protection.model.logicalnodes.protections.PDIF;
import protection.model.logicalnodes.protections.PIOC;
import protection.model.logicalnodes.protections.PTOC;

import java.util.ArrayList;
import java.util.List;

public class LR4 {

    public static final List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {

        LCOM lcom = new LCOM();
        lnList.add(lcom);

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\DPT\\Trans3Obm\\Trans3ObmVkl");


        MMXU mmxuVN = new MMXU();
        MMXU mmxuSN = new MMXU();
        MMXU mmxuNN = new MMXU();
        MHAI mhaiVN = new MHAI();
        MHAI mhaiSN = new MHAI();
        MHAI mhaiNN = new MHAI();
        PHAR pharVN = new PHAR();
        PHAR pharSN = new PHAR();
        PHAR pharNN = new PHAR();
        RMXU rmxu = new RMXU();
        PTRC ptrc1 = new PTRC();
        PTRC ptrc2 = new PTRC();
        CSWI cswi = new CSWI();
        XCBR xcbr1 = new XCBR();
        XCBR xcbr2 = new XCBR();
        XCBR xcbr3 = new XCBR();

        PDIF pdif = new PDIF();
        PIOC pioc = new PIOC();


        lnList.add(mmxuVN);
        lnList.add(mmxuSN);
        lnList.add(mmxuNN);
        lnList.add(mhaiVN);
        lnList.add(mhaiSN);
        lnList.add(mhaiNN);
        lnList.add(cswi);
        lnList.add(pdif);
        lnList.add(pioc);
        lnList.add(rmxu);
        lnList.add(pharVN);
        lnList.add(pharSN);
        lnList.add(pharNN);
        lnList.add(ptrc1);
        lnList.add(ptrc2);
        lnList.add(xcbr1);
        lnList.add(xcbr2);
        lnList.add(xcbr3);


        // Задаем сигналы

        mmxuVN.IphsAInst = lcom.OUT.get(0);
        mmxuVN.IphsBInst = lcom.OUT.get(1);
        mmxuVN.IphsCInst = lcom.OUT.get(2);


        mmxuSN.IphsAInst = lcom.OUT.get(3);
        mmxuSN.IphsBInst = lcom.OUT.get(4);
        mmxuSN.IphsCInst = lcom.OUT.get(5);

        mmxuNN.IphsAInst = lcom.OUT.get(6);
        mmxuNN.IphsBInst = lcom.OUT.get(7);
        mmxuNN.IphsCInst = lcom.OUT.get(8);

        mhaiVN.IphsAInst = lcom.OUT.get(0);
        mhaiVN.IphsBInst = lcom.OUT.get(1);
        mhaiVN.IphsCInst = lcom.OUT.get(2);

        mhaiSN.IphsAInst = lcom.OUT.get(3);
        mhaiSN.IphsBInst = lcom.OUT.get(4);
        mhaiSN.IphsCInst = lcom.OUT.get(5);

        mhaiNN.IphsAInst = lcom.OUT.get(6);
        mhaiNN.IphsBInst = lcom.OUT.get(7);
        mhaiNN.IphsCInst = lcom.OUT.get(8);

        pharVN.setHA(mhaiVN.getHA());
        pharSN.setHA(mhaiSN.getHA());
        pharNN.setHA(mhaiNN.getHA());

        rmxu.getInputA().add(mmxuVN.getA());
        rmxu.getInputA().add(mmxuSN.getA());
        rmxu.getInputA().add(mmxuNN.getA());
        rmxu.getTmASt().getCrvPvs().add(new Point(0d,1000d));
        rmxu.getTmASt().getCrvPvs().add(new Point(250d,1000d));
        rmxu.getTmASt().getCrvPvs().add(new Point(1000d,2000d));
        rmxu.process();



//        NHMI nhmia = new NHMI();
//        NHMI nhmib = new NHMI();
//        NHMI nhmic = new NHMI();
        NHMI nhmiDif = new NHMI();
        NHMIP nhmip = new NHMIP();
        lnList.add(nhmiDif);


        pdif.getInputHarm().add(mhaiVN.getHA());
        pdif.getInputHarm().add(mhaiSN.getHA());
        pdif.getInputHarm().add(mhaiNN.getHA());
        pdif.setDifAClc(rmxu.getDifoutput().get(0));
        pdif.setTripPoint(rmxu.getTripPoint());
        pdif.getStrVal().getSetMag().getF().setValue(500d);
        pdif.getMinOpTmms().setDelay(5d);
        pdif.getStrHarm().getSetMag().getF().setValue(0.02d);


        ptrc1.setBlk1(pharVN.getBlK());
        ptrc1.setBlk2(pharSN.getBlK());
        ptrc1.setBlk3(pharNN.getBlK());

        pdif.setBlk(ptrc1.getOp().getGeneral());

        pioc.A = rmxu.getDif();
        pioc.getStrVal().getSetMag().getF().setValue(400d);

        ptrc2.setOpCls1(pdif.getOp());
        ptrc2.setOpCls2(pioc.getOp());




        cswi.setOpOpn1(ptrc2.getOpOpn());
        cswi.setOpOpn2(ptrc2.getOpOpn());
        cswi.setOpOpn3(ptrc2.getOpOpn());

        xcbr1.setPos(cswi.getPos());
        xcbr2.setPos(cswi.getPos());
        xcbr3.setPos(cswi.getPos());
        lnList.add(nhmip);



//        nhmip.drawCharacteristic("Тормозная характеристика", NHMIP.getNhmiPoints(0,1000,250,1000,1000,2000,2000,2000));
//        nhmip.addSignals(new NHMISignal("Ток",pdif.getTripPoint().getPhsA().getCVal().getMag().getF(),pdif.getDifAClc().getPhsA().getCVal().getMag().getF()));

        nhmiDif.addSignals(
                new NHMISignal("IADIF",rmxu.getDifoutput().get(0).getPhsA().getCVal().getMag().getF()),
                new NHMISignal("TripPoint",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())

        );
        nhmiDif.addSignals(
                new NHMISignal("IBDIF",rmxu.getDifoutput().get(0).getPhsB().getCVal().getMag().getF())

        );
        nhmiDif.addSignals(
                new NHMISignal("ICDIF",rmxu.getDifoutput().get(0).getPhsC().getCVal().getMag().getF())

        );
        nhmiDif.addSignals(
                new NHMISignal("Protection",pdif.getOp().getGeneral())
        );
        nhmiDif.addSignals(
                new NHMISignal("Block",ptrc1.getOp().getGeneral())
        );

        nhmiDif.addSignals(
                new NHMISignal("Breaker",xcbr1.getPos().getCtVal())
        );

        nhmiDif.addSignals(
                new NHMISignal("PIOC",pioc.Op.getGeneral())
        );


        while (lcom.hasNextData()) {


            lnList.forEach(LN::process);


        }



    }
}



