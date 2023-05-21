package org.example;

import protection.model.dataobjects.measurements.CMV;
import protection.model.logicalnodes.gui.NHMIP;
import protection.model.logicalnodes.gui.other.NHMIPoint;

import java.util.ArrayList;
import java.util.List;

public class LR3Test {

    public boolean line1,line2,line3,line4;

    public static void main(String[] args) {

        double x = -100d;
        double y = -80d;

        NHMIP nhmip = new NHMIP();

        List<NHMIPoint<Double, Double>> pointsList = new ArrayList<>();

        pointsList.add(new NHMIPoint<>(x,y));

        nhmip.drawCharacteristic("Test",pointsList);
        nhmip.drawCharacteristic("Chr1", NHMIP.getNhmiPoints(100d,300d,-200d,300d,
                -50d, -100d, 50d, 0d));

        double x1 = 300d;
        double x2 = 300d;
        double x3 = -100d;
        double x4 = 0d;
        double r1 = 100d;
        double r2 = -200d;
        double r3 = -50d;
        double r4 = 50d;

        double k12 = (x1 - x2) / (r1 - r2);
        double k23 = (x2 - x3) / (r2 - r3);
        double k34 = (x3 - x4) / (r3 - r4);
        double k41 = (x4 - x1) / (r4 - r1);

        double b12 = x1 - k12*r1;
        double b23 = x2 - k23*r2;
        double b34 = x3 - k34*r3;
        double b41 = x4 - k41*r4;



        boolean line1 = y <= k12*x + b12;
        boolean line2 = y >= k23*x + b23;
        boolean line3 = y >= k34*x + b34;
        boolean line4 = y >= k41*x + b41;

        System.out.println(line1);

        System.out.println(line2);
        System.out.println(line3);
        System.out.println(line4);
    }


}
