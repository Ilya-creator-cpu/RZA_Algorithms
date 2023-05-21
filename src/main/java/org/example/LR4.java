package org.example;

import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Breaker.XCBR;
import protection.model.logicalnodes.commands.RPSB;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MHAI;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.measurements.RMXU;
import protection.model.logicalnodes.protections.PDIF;
import protection.model.logicalnodes.protections.PDIS;

import java.util.ArrayList;
import java.util.List;

public class LR4 {

    public static final List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {

        LCOM lcom = new LCOM();
        lnList.add(lcom);

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\DPT\\Trans3Obm\\Trans3ObmVnutAB");


        MMXU mmxu1VN = new MMXU();
        MMXU mmxu2SN = new MMXU();
        MMXU mmxu3NN = new MMXU();
        MHAI mhai1VN = new MHAI();
        MHAI mhai2SN = new MHAI();
        MHAI mhai3NN = new MHAI();
        RMXU rmxu = new RMXU();
        CSWI cswi = new CSWI();
        XCBR xcbr1 = new XCBR();
        XCBR xcbr2 = new XCBR();
        XCBR xcbr3 = new XCBR();

        PDIF pdif = new PDIF();


        lnList.add(mmxu1VN);
        lnList.add(mmxu2SN);
        lnList.add(mmxu3NN);
        lnList.add(mhai1VN);
        lnList.add(mhai2SN);
        lnList.add(mhai3NN);
        lnList.add(cswi);
        lnList.add(xcbr1);
        lnList.add(pdif);
        lnList.add(rmxu);


        // Задаем сигналы

        mmxu1VN.IphsAInst = lcom.OUT.get(0);
        mmxu1VN.IphsBInst = lcom.OUT.get(1);
        mmxu1VN.IphsCInst = lcom.OUT.get(2);


        mmxu2SN.IphsAInst = lcom.OUT.get(3);
        mmxu2SN.IphsBInst = lcom.OUT.get(4);
        mmxu2SN.IphsCInst = lcom.OUT.get(5);

        mmxu3NN.IphsAInst = lcom.OUT.get(6);
        mmxu3NN.IphsBInst = lcom.OUT.get(7);
        mmxu3NN.IphsCInst = lcom.OUT.get(8);

        mhai1VN.IphsAInst = lcom.OUT.get(0);
        mhai1VN.IphsBInst = lcom.OUT.get(1);
        mhai1VN.IphsCInst = lcom.OUT.get(3);

        mhai2SN.IphsAInst = lcom.OUT.get(3);
        mhai2SN.IphsBInst = lcom.OUT.get(4);
        mhai2SN.IphsCInst = lcom.OUT.get(5);

        mhai3NN.IphsAInst = lcom.OUT.get(6);
        mhai3NN.IphsBInst = lcom.OUT.get(7);
        mhai3NN.IphsCInst = lcom.OUT.get(8);

        NHMI nhmi = new NHMI();
        lnList.add(nhmi);

//        mhai1VN.getHA().getNumHar().setValue(5);

        pdif.setDifAClc(rmxu.getALoc());
        pdif.setHarm(mhai1VN.getHA());
        pdif.setHarm(mhai2SN.getHA());
        pdif.setHarm(mhai3NN.getHA());
        pdif.setRstA(rmxu.getRstA());
        pdif.setK(0.45);
        pdif.getOpDiTmms().setDelay(15);
        pdif.setRst0(300);


        cswi.setOpOpn1(pdif.getOp());
        cswi.setOpOpn2(pdif.getOp());
        cswi.setOpOpn3(pdif.getOp());

        xcbr1.setPos(cswi.getPos());
        xcbr2.setPos(cswi.getPos());
        xcbr3.setPos(cswi.getPos());


        nhmi.addSignals(
                new NHMISignal("IaVN", mmxu1VN.IphsAInst.getInstMag().getF()),
                new NHMISignal("IaSN", mmxu2SN.IphsAInst.getInstMag().getF()),
                new NHMISignal("IaNN",mmxu3NN.IphsAInst.getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Срабатывание дифф.защиты", pdif.getOp().getGeneral())
        );


        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);

        }


    }
}



