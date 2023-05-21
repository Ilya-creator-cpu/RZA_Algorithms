package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ASG;
import protection.model.logicalnodes.Breaker.CSWI;
import protection.model.logicalnodes.Setter.ING;
import protection.model.logicalnodes.common.LN;

import static protection.model.Direction.Direction.BACKWARD;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */

@Getter @Setter
public class PTOC extends LN {

    public double delay;

    public boolean worked_out;

    /*
     * Входы
     */
    public int accelerateCounter;

    public boolean accelerate;
    public WYE A = new WYE();

    public ING DirMod = new ING();

    public ACD Dir = new ACD();

    public int work_times;

    public ASG StrVal = new ASG();
    public ING OpDlTmms = new ING();

    public CSWI brk = new CSWI();

    /*
     * Выходы
     */
    public final ACD Str = new ACD();
    public final ACT Op = new ACT();

    public String name;


    /*
     * Переменные
     */

    @Override
    public void process() {


        Str.getPhsA().setValue(A.getPhsA().getCVal().getMag().getF().getValue() >
                StrVal.getSetMag().getF().getValue());
        Str.getPhsB().setValue(A.getPhsB().getCVal().getMag().getF().getValue() >
                StrVal.getSetMag().getF().getValue());
        Str.getPhsC().setValue(A.getPhsC().getCVal().getMag().getF().getValue() >
                StrVal.getSetMag().getF().getValue());

        Str.getGeneral().setValue(
                Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue()
        );
        worked_out = Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue();

      // Delay();

        if (Str.getGeneral().getValue()) {


            if (worked_out && delay == 0) {
                accelerateCounter++;
                worked_out = false;
            }
            delay +=1;

        } else {
            delay = 0;
        }

        if (accelerateCounter>=work_times) {
            accelerate = true;
            OpDlTmms.setDelay(0);
        }

        if (Dir.getDirGeneral().getValue().equals(BACKWARD)) {
            Str.getGeneral().setValue(false);
            System.out.println("Blocked  " + name);
        }




        if (worked_out){
            brk.getOpcls().getGeneral().setValue(worked_out);
            brk.getOpcls().getPhsA().setValue(worked_out);
            brk.getOpcls().getPhsB().setValue(worked_out);
            brk.getOpcls().getPhsC().setValue(worked_out);
        }
            Op.getGeneral().setValue(Str.getGeneral().getValue()&& (delay > OpDlTmms.delay));
            Op.getPhsA().setValue(Str.getPhsA().getValue()&& (delay > OpDlTmms.delay));
            Op.getPhsB().setValue(Str.getPhsB().getValue()&& (delay > OpDlTmms.delay));
            Op.getPhsC().setValue(Str.getPhsC().getValue()&& (delay > OpDlTmms.delay));

    }




}
