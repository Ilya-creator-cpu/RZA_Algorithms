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


    public MV IphsAInst = new MV();
    public MV IphsBInst = new MV();
    public MV IphsCInst = new MV();




    private HWYE HA = new HWYE(); //Последовательность тока гармоник
    private ArrayList<HFFT> fftA = new ArrayList<>();
    private ArrayList<HFFT> fftB = new ArrayList<>();
    private ArrayList<HFFT> fftC = new ArrayList<>();



    @Override
    public void process() {


        for (int h = 0; h < 5; h++) {
            fftA.add(new HFFT(h+1));
            fftB.add(new HFFT(h+1));
            fftC.add(new HFFT(h+1));
            fftA.get(h).process(IphsAInst, HA.getPhsAHar().get(h));
            fftB.get(h).process(IphsBInst, HA.getPhsBHar().get(h));
            fftC.get(h).process(IphsCInst, HA.getPhsCHar().get(h));

        }

    }
}