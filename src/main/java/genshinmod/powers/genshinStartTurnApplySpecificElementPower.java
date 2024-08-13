package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.cards.*;
import genshinmod.helper.ModHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class genshinStartTurnApplySpecificElementPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("StartTurnApplySpecificElementPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCard chosenElement = null;

    public genshinStartTurnApplySpecificElementPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        String path128 = "GenshinModResources/img/powers/84/StartTurnApplySpecificElement84.png";
        String path48 = "GenshinModResources/img/powers/32/StartTurnApplySpecificElement32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ModHelper.makePath("StartTurnApplySpecificElementPower")));
        }
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            boolean applyToAllEnemies = true;  // 每个回合都对所有怪物施加效果
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new Cryo(null, this.amount, applyToAllEnemies));
            stanceChoices.add(new Hydro(null, this.amount, applyToAllEnemies));
            stanceChoices.add(new Pyro(null, this.amount, applyToAllEnemies));
            stanceChoices.add(new Dentro(null, this.amount, applyToAllEnemies));
            stanceChoices.add(new Electro(null, this.amount, applyToAllEnemies));
            this.addToBot(new ChooseOneAction(stanceChoices));
        }
    }

}
