package genshinmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDentroPower;
import genshinmod.powers.genshinHydroPower;

public class NereidsAscensionAction extends AbstractGameAction {
    private AbstractMonster m;
    private AbstractPlayer p;
    private int TempHPAmt;

    public NereidsAscensionAction(int HydroAmt,int TempHPAmt, AbstractPlayer p, AbstractMonster m) {
        this.actionType = ActionType.WAIT;
        this.amount = HydroAmt;
        this.TempHPAmt=TempHPAmt;
        this.m = m;
        this.p=p;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            this.addToBot(new AddTemporaryHPAction(p, p, this.TempHPAmt));
        }
        else if(this.m != null && this.m.getIntentBaseDmg() < 0) {
            this.addToBot(new ApplyElementalPowerAction(m,p, ModHelper.makePath("HydroPower"),this.amount));
            this.addToBot(new ElementalReactionAction(m, p));
        }

        this.isDone = true;
    }
}
