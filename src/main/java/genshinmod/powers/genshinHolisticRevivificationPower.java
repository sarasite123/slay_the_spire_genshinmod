package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class genshinHolisticRevivificationPower extends AbstractPower {
    private static final Logger logger = LogManager.getLogger(FlameBarrierPower.class.getName());
    public static final String POWER_ID = ModHelper.makePath("HolisticRevivificationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinHolisticRevivificationPower(AbstractCreature owner, int thornsDamage) {
        this.name = NAME;
        this.owner = owner;
        this.amount = thornsDamage;
        this.updateDescription();
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        String path128 = "GenshinModResources/img/powers/84/HolisticRevivification84.png";
        String path48 = "GenshinModResources/img/powers/32/HolisticRevivification32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
            logger.info(this.name + " does not stack");
        } else {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            this.updateDescription();
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.type != DamageInfo.DamageType.HP_LOSS) {
            this.flash();
            if (this.owner.isPlayer) {
                this.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
            } else {
                this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            }

            this.updateDescription();
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        AbstractPower elementalMasteryPower = AbstractDungeon.player.getPower(ModHelper.makePath("ElementalMasteryPower"));
        if (elementalMasteryPower != null) {
            this.updateDescription();
        }
    }
}
