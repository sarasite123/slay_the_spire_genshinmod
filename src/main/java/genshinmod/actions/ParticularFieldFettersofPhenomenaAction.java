package genshinmod.actions;

import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDentroPower;


public class ParticularFieldFettersofPhenomenaAction extends AbstractGameAction {
    private AbstractMonster m;
    private AbstractPlayer p;
    private int block;

    public ParticularFieldFettersofPhenomenaAction(int DentroAmt,int block, AbstractPlayer p, AbstractMonster m) {
        this.actionType = ActionType.WAIT;
        this.amount = DentroAmt;
        this.block=block;
        this.m = m;
        this.p=p;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }
        else if(this.m != null && this.m.getIntentBaseDmg() < 0) {
            this.addToBot(new ApplyElementalPowerAction(this.m,this.p, ModHelper.makePath("DentroPower"),this.amount));
            this.addToBot(new ElementalReactionAction(m, p));
        }

        this.isDone = true;
    }
}
