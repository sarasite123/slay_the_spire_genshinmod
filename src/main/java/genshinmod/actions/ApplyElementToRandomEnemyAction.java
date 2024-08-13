package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyElementToRandomEnemyAction extends AbstractGameAction {
    private AbstractPower powerToApply;
    private boolean isFast;
    private AbstractGameAction.AttackEffect effect;

    public ApplyElementToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
        this.setValues((AbstractCreature)null, source, stackAmount);
        this.powerToApply = powerToApply;
        this.isFast = isFast;
        this.effect = effect;
    }

    public ApplyElementToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast) {
        this(source, powerToApply, stackAmount, isFast, AttackEffect.NONE);
    }

    public ApplyElementToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(source, powerToApply, stackAmount, false);
    }

    public ApplyElementToRandomEnemyAction(AbstractCreature source, AbstractPower powerToApply) {
        this(source, powerToApply, -1);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        this.powerToApply.owner = this.target;
        if (this.target != null) {
            this.addToTop(new ApplyPowerAction(this.target, this.source, this.powerToApply, this.amount, this.isFast, this.effect));
            this.addToBot(new ElementalReactionAction(this.target, AbstractDungeon.player));
        }

        this.isDone = true;
    }
}
