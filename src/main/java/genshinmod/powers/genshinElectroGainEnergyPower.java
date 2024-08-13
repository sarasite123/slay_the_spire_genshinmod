package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

public class genshinElectroGainEnergyPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("ElectroGainEnergyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinElectroGainEnergyPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        String path128 = "GenshinModResources/img/powers/84/ElectroGainEnergy84.png";
        String path48 = "GenshinModResources/img/powers/32/ElectroGainEnergy32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    @Override
    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    @Override
    public void updateDescription() {
        int value = GameActionManagerPatches.ExtraVariableField.elctrogainenergy.get(AbstractDungeon.actionManager);
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ModHelper.makePath("ElectroGainEnergyPower")));
        }
        this.updateDescription();  // Update description whenever the power is stacked
    }

    // Override other methods where description might need to be updated
    @Override
    public void atEndOfRound() {
        this.updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(POWER_ID)) {
            this.updateDescription();
        }
    }

    // Ensure description is updated on reaction trigger
    public void onElectroReactionTrigger() {
        this.updateDescription();
    }

    // Other methods where value might change and require description update
}
