package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import genshinmod.helper.ModHelper;

public class genshinCryoPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("CryoPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AbstractCreature player; // Add this line to store the player

    public genshinCryoPower(AbstractCreature m, AbstractCreature p, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.player = p; // Store the player
        this.type = PowerType.DEBUFF;
        this.amount = amount;
        this.updateDescription();
        // 添加一大一小两张能力图
        String path128 = "GenshinModResources/img/powers/84/Cryo84.png";
        String path48 = "GenshinModResources/img/powers/32/Cryo32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
            if (this.amount <= 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ModHelper.makePath("CryoPower")));
            }
    }
}
