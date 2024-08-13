package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.*;

public class ApplyElementalPowerAction extends AbstractGameAction {
    private String elementType;
    private int baseApply;

    public ApplyElementalPowerAction(AbstractCreature target, AbstractCreature source, String elementType, int baseApply) {
        this.target = target;
        this.source = source;
        this.elementType = elementType;
        this.baseApply = baseApply;
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        AbstractPower genshinCryoFreePower = target.getPower(ModHelper.makePath("CryoFreePower"));
        AbstractPower genshinElectroFreePower = target.getPower(ModHelper.makePath("ElectroFreePower"));
        AbstractPower genshinHydroFreePower = target.getPower(ModHelper.makePath("HydroFreePower"));
        AbstractPower genshinPyroFreePower = target.getPower(ModHelper.makePath("PyroFreePower"));
        AbstractPower genshinDentroFreePower = target.getPower(ModHelper.makePath("DentroFreePower"));
        if(this.baseApply>0) {
            switch (this.elementType) {
                case "genshin:HydroPower":
                    if(genshinHydroFreePower==null) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new genshinHydroPower(target, source, baseApply), baseApply));
                    }
                    break;
                case "genshin:ElectroPower":
                    if(genshinElectroFreePower==null) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new genshinElectroPower(target, source, baseApply), baseApply));
                    }
                    break;
                case "genshin:CryoPower":
                    if(genshinCryoFreePower==null) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new genshinCryoPower(target, source, baseApply), baseApply));
                    }
                    break;
                case "genshin:DentroPower":
                    if(genshinDentroFreePower==null) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new genshinDentroPower(target, source, baseApply), baseApply));
                    }
                    break;
                case "genshin:PyroPower":
                    if(genshinPyroFreePower==null) {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new genshinPyroPower(target, source, baseApply), baseApply));
                    }
                    break;
                default:
                    System.out.println("Unknown element type: " + this.elementType);
            }
        }
        this.isDone = true;
    }
}
