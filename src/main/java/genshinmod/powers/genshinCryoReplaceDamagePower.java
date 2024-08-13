package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;

public class genshinCryoReplaceDamagePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("CryoReplaceDamagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinCryoReplaceDamagePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        String path128 = "GenshinModResources/img/powers/84/CryoReplaceDamage84.png";
        String path48 = "GenshinModResources/img/powers/32/CryoReplaceDamage32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();

            if (action.target != null) { // If target is not null (single target)
                this.addToBot(new ApplyElementalPowerAction(action.target,this.owner,ModHelper.makePath("CryoPower"),action.target.lastDamageTaken));
                this.addToBot(new ElementalReactionAction(action.target, this.owner));
            } else { // If action.target is null (for all enemies)
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!monster.isDead && !monster.isDying) { // Check if monster is alive
                        this.addToBot(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("CryoPower"),monster.lastDamageTaken));
                        this.addToBot(new ElementalReactionAction(monster, this.owner));
                    }
                }
            }

            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ModHelper.makePath("CryoReplaceDamagePower")));
        }
    }
}
