package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;

import java.util.Iterator;

public class genshinNextTurnPyroPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("NextTurnPyroPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinNextTurnPyroPower(AbstractCreature owner, int armorAmt, String newName) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = armorAmt;
        this.updateDescription();
        String path128 = "GenshinModResources/img/powers/84/NextTurnPyro84.png";
        String path48 = "GenshinModResources/img/powers/32/NextTurnPyro32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public genshinNextTurnPyroPower(AbstractCreature owner, int armorAmt) {
        this(owner, armorAmt, NAME);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var3.hasNext()) {
            AbstractMonster mo = var3.next();
            this.addToBot(new ApplyPowerAction(mo, this.owner, new genshinPyroPower(mo, this.owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ElementalReactionAction(mo, this.owner));
        }
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

}
