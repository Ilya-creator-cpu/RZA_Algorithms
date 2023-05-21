package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;
import utils.filters.FFT;
import utils.filters.HFFT;

import java.util.ArrayList;

@Getter @Setter
public class MHAI extends LN {

    /**
     * Токи фаз
     */
    public MV IphsAInst = new MV();
    public MV IphsBInst = new MV();
    public MV IphsCInst = new MV();
    private HWYE HA = new HWYE(); //Последовательность тока гармоник
    private ArrayList<HFFT> fftA = new ArrayList<>();
    private ArrayList<HFFT> fftB = new ArrayList<>();
    private ArrayList<HFFT> fftC = new ArrayList<>();


    public MHAI() {
        for (int h = 0; h <= HA.getNumberOfHarm(); h++) {
            fftA.add(new HFFT(h));
            fftB.add(new HFFT(h));
            fftC.add(new HFFT(h));
        }

    }



    @Override
    public void process() {


        for (int h = 0; h < HA.getNumHar().getValue(); h++) {
            fftA.get(h).process(IphsAInst, HA.getPhsAHar().get(h));
            fftB.get(h).process(IphsBInst, HA.getPhsBHar().get(h));
            fftC.get(h).process(IphsCInst, HA.getPhsCHar().get(h));
        }

    }
}