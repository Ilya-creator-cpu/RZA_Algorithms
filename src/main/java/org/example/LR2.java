package org.example;

import protection.model.Direction.MSQI;
import protection.model.Direction.RDIR;
import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Breaker.XCBR;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PTOC;

import java.util.ArrayList;
import java.util.List;

public class LR2 {

    private final static List<LN> lnList = new ArrayList<>();


    public static void main(String[] args) {

        LCOM lcom = new LCOM();
        lnList.add(lcom);
//        lcom.setFilePath(
//                "C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\Начало линии\\",
//                "PhA80"
//        );

        lcom.readComtrade("C:\\Users\\илья\\Desktop\\10 семестр\\Алгоритмы\\Опыты\\KZ1");

        MMXU mmxu = new MMXU();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();
        MSQI msqi = new MSQI();


        RDIR rdir1 = new RDIR();
        RDIR rdir2 = new RDIR();
        RDIR rdir3 = new RDIR();


        lnList.add(mmxu);
        lnList.add(cswi);
        lnList.add(xcbr);
        lnList.add(msqi);
        lnList.add(rdir1);
        lnList.add(rdir2);
        lnList.add(rdir3);


        mmxu.UphsAInst = lcom.OUT.get(0);
        mmxu.UphsBinst = lcom.OUT.get(1);
        mmxu.UphsCinst = lcom.OUT.get(2);

        mmxu.IphsAInst = lcom.OUT.get(3);
        mmxu.IphsBInst = lcom.OUT.get(4);
        mmxu.IphsCInst = lcom.OUT.get(5);

        msqi.setA(mmxu.A);
        msqi.setPhV(mmxu.PhV);
        msqi.setW(mmxu.W);


        rdir1.setW(mmxu.W);
        rdir2.setW(mmxu.W);
        rdir3.setW(mmxu.W);


        // 6 ступеней защит
        PTOC ptoc1 = new PTOC();
        PTOC ptoc2 = new PTOC();
        PTOC ptoc3 = new PTOC();
        PTOC ptoc4n = new PTOC();
        PTOC ptoc5n = new PTOC();
        PTOC ptoc6n = new PTOC();

        lnList.add(ptoc1);
        lnList.add(ptoc2);
        lnList.add(ptoc3);
        lnList.add(ptoc4n);
        lnList.add(ptoc5n);
        lnList.add(ptoc6n);

        ptoc1.setName("Ptoc1");
        ptoc2.setName("Ptoc2");
        ptoc3.setName("Ptoc3");
        ptoc4n.setName("Ptoc4");
        ptoc5n.setName("Ptoc5");
        ptoc6n.setName("Ptoc6");


        //Наличие ОНМ
        ptoc4n.getDirMod().getSetVal().setValue(1);
        ptoc5n.getDirMod().getSetVal().setValue(1);
        ptoc6n.getDirMod().getSetVal().setValue(1);

        ptoc4n.setDir(rdir1.getDir());
        ptoc5n.setDir(rdir2.getDir());
        ptoc6n.setDir(rdir3.getDir());

        ptoc1.StrVal.getSetMag().getF().setValue(1200d);
        ptoc1.OpDlTmms.setDelay(500);
        ptoc1.A = mmxu.A;
        ptoc2.StrVal.getSetMag().getF().setValue(1320d);
        ptoc2.OpDlTmms.setDelay(1000);
        ptoc2.A = mmxu.A;
        ptoc3.StrVal.getSetMag().getF().setValue(360d);
        ptoc3.A = mmxu.A;
        ptoc3.OpDlTmms.setDelay(500);
        ptoc4n.StrVal.getSetMag().getF().setValue(2210d);
        ptoc4n.A = mmxu.A;
        ptoc4n.OpDlTmms.setDelay(500);
        ptoc5n.StrVal.getSetMag().getF().setValue(1320d);
        ptoc5n.A = mmxu.A;
        ptoc5n.OpDlTmms.setDelay(1000);
        ptoc6n.StrVal.getSetMag().getF().setValue(87.5d);
        ptoc6n.A = mmxu.A;
        ptoc6n.OpDlTmms.setDelay(1200);

        ptoc1.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc1.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc1.getA().setPhsC(msqi.getSeqA().getC3());
        ptoc2.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc2.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc2.getA().setPhsC(msqi.getSeqA().getC3());
        ptoc3.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc3.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc3.getA().setPhsC(msqi.getSeqA().getC3());
        ptoc4n.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc4n.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc4n.getA().setPhsC(msqi.getSeqA().getC3());
        ptoc5n.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc5n.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc5n.getA().setPhsC(msqi.getSeqA().getC3());
        ptoc6n.getA().setPhsA(msqi.getSeqA().getC3());
        ptoc6n.getA().setPhsB(msqi.getSeqA().getC3());
        ptoc6n.getA().setPhsC(msqi.getSeqA().getC3());

        // Выдача команды на управление
        cswi.setOpOpn1(ptoc1.getOp());
        cswi.setOpOpn2(ptoc2.getOp());
        cswi.setOpOpn3(ptoc3.getOp());
        cswi.setOpOpn4(ptoc4n.getOp());
        cswi.setOpOpn5(ptoc5n.getOp());
        cswi.setOpOpn6(ptoc6n.getOp());
        // Положение выключателя
        xcbr.setPos(cswi.getPos());
        // Автоматическое ускорение

        ptoc1.setWork_times(2);
        ptoc2.setWork_times(2);
        ptoc3.setWork_times(2);


        NHMI nhmi = new NHMI();
        lnList.add(nhmi);
        nhmi.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(3).getInstMag().getF()),
                new NHMISignal("FurieValuePhsA", mmxu.A.getPhsA().getCVal().getMag().getF())
//                new NHMISignal("PowerPhsA",mmxu.W.getPhsA().getCVal().getMag().getF())
//                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
//                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(4).getInstMag().getF()),
                new NHMISignal("FurieValuePhsB", mmxu.A.getPhsB().getCVal().getMag().getF())
////                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
////                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(5).getInstMag().getF()),
                new NHMISignal("FurieValuePhsC", mmxu.A.getPhsC().getCVal().getMag().getF())
////                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
////                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
                );
//        nhmi.addSignals(
//                new NHMISignal("UPhsA", lcom.OUT.get(1).getInstMag().getF())
////                new NHMISignal("PowerPhsA",mmxu.W.getPhsA().getCVal().getMag().getF())
////                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
////                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("UPhsB", lcom.OUT.get(0).getInstMag().getF())
////                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
////                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("UPhsC", lcom.OUT.get(2).getInstMag().getF())
////                new NHMISignal("SettingPTOC1", ptoc1.getStrVal().getSetMag().getF()),
////                new NHMISignal("SettingPTOC2", ptoc2.getStrVal().getSetMag().getF())
//        );

        nhmi.addSignals(
                new NHMISignal("PTOC1", ptoc1.Str.getGeneral())
        );
//
        nhmi.addSignals(
                new NHMISignal("PTOC2", ptoc2.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PTOC3", ptoc3.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PTOC4", ptoc4n.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PTOC5", ptoc5n.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PTOC6", ptoc6n.Str.getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("XCBR", xcbr.getPos().getCtVal())

        );

        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);


        }


    }
}
