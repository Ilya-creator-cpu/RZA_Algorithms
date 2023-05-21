package org.example;

import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Breaker.XCBR;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PTOC;

import java.util.ArrayList;
import java.util.List;


public class LR1 {

    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {

        LCOM lcom = new LCOM();
        lnList.add(lcom);
//        lcom.setFilePath(
//                "C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\Начало линии\\",
//                "PhA80"
//        );

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\Конец линии\\PhABC80");

        MMXU mmxu = new MMXU();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();
        lnList.add(mmxu);
        lnList.add(cswi);
        lnList.add(xcbr);
        mmxu.IphsAInst = lcom.OUT.get(0);
        mmxu.IphsBInst = lcom.OUT.get(1);
        mmxu.IphsCInst = lcom.OUT.get(2);


        // Две ступени защит
        PTOC ptoc1 = new PTOC();
        PTOC ptoc2 = new PTOC();
        lnList.add(ptoc1);
        lnList.add(ptoc2);
        ptoc1.StrVal.getSetMag().getF().setValue(800d);
        ptoc1.OpDlTmms.setDelay(500);
        ptoc1.A = mmxu.A;
        ptoc2.StrVal.getSetMag().getF().setValue(500d);
        ptoc2.OpDlTmms.setDelay(2000);
        ptoc2.A = mmxu.A;

        // Выдача команды на управление
        cswi.setOpOpn1(ptoc1.getOp());
        cswi.setOpOpn2(ptoc2.getOp());

        // Положение выключателя
        xcbr.setPos(cswi.getPos());

        NHMI nhmi = new NHMI();
        lnList.add(nhmi);
        nhmi.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(0).getInstMag().getF()),
                new NHMISignal("FurieValuePhsA", mmxu.A.getPhsA().getCVal().getMag().getF()),
                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF()) );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(1).getInstMag().getF()),
                new NHMISignal("FurieValuePhsB", mmxu.A.getPhsA().getCVal().getMag().getF()),
                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
                );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(2).getInstMag().getF()),
                new NHMISignal("FurieValuePhsC", mmxu.A.getPhsA().getCVal().getMag().getF()),
                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF()));
        nhmi.addSignals(
                new NHMISignal("Trip1", ptoc1.Op.getGeneral()));
        nhmi.addSignals(
                new NHMISignal("PTOC1",ptoc1.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Trip2",ptoc2.Op.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PTOC2",ptoc2.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("XCBR",xcbr.getPos().getCtVal())

        );
        System.out.println(xcbr.getPos().getCtVal().getValue());

        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
        }
    }
}