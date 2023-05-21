import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.measurements.MMXU;
import utils.filters.Filter;
import utils.filters.FrequencyFurier;
import utils.filters.Furier;

public class FrequencyTest {



    @Test
    public void FrequencyMeterTest1() {


        MV instMag = new MV();



        MMXU mmxu = new MMXU();

        double FFTout = 40d;

        double signal;

        double frequency = 55;

            for (double t=0; t< 40;t+=0.00025) {

                signal = FFTout*Math.sin(2*Math.PI*frequency*t);

                instMag.getInstMag().getF().setValue(signal);
                mmxu.IphsAInst.getInstMag().getF().setValue(signal);
                mmxu.process();
            }


        System.out.println("Заданная частота: " + frequency + ", Измеренная частота: " + mmxu.getFrequency());

        Assertions.assertEquals(mmxu.getFrequency(),55,0.04);

    }


    @Test
    public void FrequencyFurieTest1() {

        MV instMag = new MV();

        CMV cmv = new CMV();
        MV instMag2 = new MV();

        CMV cmv2 = new CMV();

        MMXU mmxu = new MMXU();

        double FFTout = 220d;

        double signal;

        double frequency = 15;

        NHMI nhmi = new NHMI();

        nhmi.addSignals(
                new NHMISignal("С подстройкой",cmv.getCVal().getMag().getF()),
                new NHMISignal("Обычный Фурье", cmv2.getCVal().getMag().getF())

        );

        FrequencyFurier FF1 = new FrequencyFurier(frequency);

        Filter FF2 = new Furier();

        for (double t=0; t< 1;t+=0.00025) {


            signal = FFTout*Math.sin(2*Math.PI*frequency*t);

            instMag.getInstMag().getF().setValue(signal);
            instMag2.getInstMag().getF().setValue(signal);
            mmxu.IphsAInst.getInstMag().getF().setValue(signal);
            mmxu.process();
            FF1.setFrequency(mmxu.getFrequency());
            FF1.process(instMag,cmv);
            FF2.process(instMag2,cmv2);
            nhmi.process();



        }



        Assertions.assertEquals(cmv.getCVal().getMag().getF().getValue(),FFTout/Math.sqrt(2),0.2);


    }

    @Test
    public void FrequencyFurieTest2() {
        MV instMag = new MV();

        CMV cmv = new CMV();
        MV instMag2 = new MV();

        CMV cmv2 = new CMV();

        MMXU mmxu = new MMXU();

        double FFTout = 40d;

        double signal;

        double frequency = 20;

        NHMI nhmi = new NHMI();

        nhmi.addSignals(
                new NHMISignal("C подстройкой",cmv.getCVal().getMag().getF()),
                new NHMISignal("Обычный Фурье", cmv2.getCVal().getMag().getF())

        );

        FrequencyFurier FF1 = new FrequencyFurier(frequency);

        Filter FF2 = new Furier();

        for (double t=0; t< 40;t+=0.00025) {

            signal = FFTout*Math.sin(2*Math.PI*frequency*t);

            instMag.getInstMag().getF().setValue(signal);
            instMag2.getInstMag().getF().setValue(signal);
            mmxu.IphsAInst.getInstMag().getF().setValue(signal);
            mmxu.process();
            FF1.setFrequency(mmxu.getFrequency());
            FF1.process(instMag,cmv);
            FF2.process(instMag2,cmv2);
            nhmi.process();

        }
        Assertions.assertEquals(cmv.getCVal().getMag().getF().getValue(),FFTout/Math.sqrt(2),0.5);

    }

    @Test
    public void FrequencyFurieTest4() {
        MV instMag = new MV();

        CMV cmv = new CMV();
        MV instMag2 = new MV();

        CMV cmv2 = new CMV();

        MMXU mmxu = new MMXU();

        double FFTout = 40d;

        double signal;

        double frequency = 44;

        NHMI nhmi = new NHMI();

        nhmi.addSignals(
                new NHMISignal("С подстройкой",cmv.getCVal().getMag().getF()),
                new NHMISignal("Обычный Фурье", cmv2.getCVal().getMag().getF())

        );

        FrequencyFurier FF1 = new FrequencyFurier(frequency);

        Filter FF2 = new Furier();


        for (double f = 50; f<=55; f++) {

            System.out.println("Частота: " + f);


            for (double t = 0; t < 1; t += 0.00025) {


                signal = FFTout * Math.sin(2 * Math.PI * f * t);

                instMag.getInstMag().getF().setValue(signal);
                instMag2.getInstMag().getF().setValue(signal);
                mmxu.IphsAInst.getInstMag().getF().setValue(signal);
                mmxu.process();
                FF1.setFrequency(mmxu.getFrequency());
                FF1.process(instMag, cmv);
                FF2.process(instMag2, cmv2);
            nhmi.process();


            }
        }
        System.out.println(cmv.getCVal().getMag().getF().getValue() + " Значение, полученное фильтром с подстройкой");


        System.out.println(cmv2.getCVal().getMag().getF().getValue() + "Значение, полученное фильтром без подстройки");


        Assertions.assertEquals(cmv.getCVal().getMag().getF().getValue(),FFTout/Math.sqrt(2),0.2);

    }

    @Test
    public void FrequencyFurieTest5() {

        MV instMag = new MV();

        CMV cmv = new CMV();
        MV instMag2 = new MV();

        CMV cmv2 = new CMV();

        MMXU mmxu = new MMXU();

        double FFTout = 40d;

        double signal;

        double frequency = 44;

        NHMI nhmi = new NHMI();

        nhmi.addSignals(
                new NHMISignal("С подстройкой",cmv.getCVal().getMag().getF()),
                new NHMISignal("Обычный Фурье", cmv2.getCVal().getMag().getF())

        );

        FrequencyFurier FF1 = new FrequencyFurier(frequency);

        Filter FF2 = new Furier();


        for (double f = 50; f>=45; f--) {

            System.out.println("Частота: " + f);


            for (double t = 0; t < 1; t += 0.00025) {


                signal = FFTout * Math.sin(2 * Math.PI * f * t);

                instMag.getInstMag().getF().setValue(signal);
                instMag2.getInstMag().getF().setValue(signal);
                mmxu.IphsAInst.getInstMag().getF().setValue(signal);
                mmxu.process();
                FF1.setFrequency(mmxu.getFrequency());
                FF1.process(instMag, cmv);
                FF2.process(instMag2, cmv2);
                nhmi.process();


            }
        }
        System.out.println(cmv.getCVal().getMag().getF().getValue() + " Значение, полученное фильтром с подстройкой");


        System.out.println(cmv2.getCVal().getMag().getF().getValue() + "Значение, полученное фильтром без подстройки");


        Assertions.assertEquals(cmv.getCVal().getMag().getF().getValue(),FFTout/Math.sqrt(2),0.2);

    }


}
