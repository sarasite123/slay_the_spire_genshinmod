package genshinmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import genshinmod.helper.ModHelper;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
public class genshinExplosivePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("ExplosivePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final int DAMAGE_AMOUNT = 18;

    public genshinExplosivePower(AbstractCreature owner, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damage;
        this.updateDescription();
        this.loadRegion("explosive");
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = String.format(DESCRIPTIONS[0], DAMAGE_AMOUNT);
        } else {
            this.description = String.format(DESCRIPTIONS[1], this.amount, DAMAGE_AMOUNT);
        }

    }

    public void duringTurn() {
        if (this.amount == 1 && !this.owner.isDying) {
            this.addToBot(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
            this.addToBot(new SuicideAction((AbstractMonster)this.owner));
            this.addToBot((new ApplyPowerAction(AbstractDungeon.player,this.owner,new ConstrictedPower(AbstractDungeon.player,this.owner,DAMAGE_AMOUNT),DAMAGE_AMOUNT)));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, ID, 1));
            this.updateDescription();
        }

    }
}
