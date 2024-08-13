package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinHydroPower;
import genshinmod.powers.genshinElectroPower;
import genshinmod.powers.genshinCryoPower;
import genshinmod.powers.genshinDentroPower;
import genshinmod.powers.genshinPyroPower;

public class MultiElementAction extends AbstractGameAction {
    private int multi;

    public MultiElementAction(AbstractCreature target, AbstractCreature source, int multi) {
        this.target = target;
        this.source = source;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.FIRE;
        this.multi = multi;
    }
//将目标身上的元素变为multi倍
    public void update() {
        System.out.println("MultiElementAction multi value: " + this.multi);  // Debugging output
        AbstractPower hydroPower = this.target.getPower(ModHelper.makePath("HydroPower"));
        AbstractPower electroPower = this.target.getPower(ModHelper.makePath("ElectroPower"));
        AbstractPower cryoPower = this.target.getPower(ModHelper.makePath("CryoPower"));
        AbstractPower dentroPower = this.target.getPower(ModHelper.makePath("DentroPower"));
        AbstractPower pyroPower = this.target.getPower(ModHelper.makePath("PyroPower"));
        AbstractPower[] powers = {hydroPower, electroPower, cryoPower, dentroPower, pyroPower};
        String[] powerIDs = {"HydroPower", "ElectroPower", "CryoPower", "DentroPower", "PyroPower"};
        AbstractPower firstPower = null;
        for (AbstractPower power : powers) {
            if (power != null) {
                if (firstPower == null) {
                    firstPower = power;
                }
            }
        }
        if (firstPower != null) {
            switch (firstPower.ID) {
                case "genshin:HydroPower":
                    this.addToBot(new ApplyElementalPowerAction(this.target,this.source,ModHelper.makePath("HydroPower"),firstPower.amount * (multi + 1)));
                    break;
                case "genshin:ElectroPower":
                    this.addToBot(new ApplyElementalPowerAction(this.target,this.source,ModHelper.makePath("ElectroPower"),firstPower.amount * (multi + 1)));
                    break;
                case "genshin:CryoPower":
                    this.addToBot(new ApplyElementalPowerAction(this.target,this.source,ModHelper.makePath("CryoPower"),firstPower.amount * (multi + 1)));
                    break;
                case "genshin:DentroPower":
                    this.addToBot(new ApplyElementalPowerAction(this.target,this.source,ModHelper.makePath("DentroPower"),firstPower.amount * (multi + 1)));
                    break;
                case "genshin:PyroPower":
                    this.addToBot(new ApplyElementalPowerAction(this.target,this.source,ModHelper.makePath("PyroPower"),firstPower.amount * (multi + 1)));
                    break;
            }
        }

        this.isDone = true;
    }
}
