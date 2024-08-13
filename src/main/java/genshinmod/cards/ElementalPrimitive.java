package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class ElementalPrimitive extends CustomCard {
    public static final String ID = ModHelper.makePath("ElementalPrimitive");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/ElementalPrimitive.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ElementalPrimitive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 0;
        this.magicNumber=this.baseMagicNumber;
    }

    public void updateCostBasedOnReactions() {
        int reactionTypes = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        this.baseMagicNumber = Math.max(reactionTypes, 0);
        this.magicNumber=this.baseMagicNumber;
        String hexColor = "#00ff00";
        int vaporizeValue = GameActionManagerPatches.ExtraVariableField.vaporize.get(AbstractDungeon.actionManager);
        int burningValue = GameActionManagerPatches.ExtraVariableField.burning.get(AbstractDungeon.actionManager);
        int overloadedValue = GameActionManagerPatches.ExtraVariableField.overloaded.get(AbstractDungeon.actionManager);
        int meltValue = GameActionManagerPatches.ExtraVariableField.melt.get(AbstractDungeon.actionManager);
        int electroChargedValue = GameActionManagerPatches.ExtraVariableField.electroCharged.get(AbstractDungeon.actionManager);
        int frozenValue = GameActionManagerPatches.ExtraVariableField.frozen.get(AbstractDungeon.actionManager);
        int bloomValue = GameActionManagerPatches.ExtraVariableField.bloom.get(AbstractDungeon.actionManager);
        int superconductValue = GameActionManagerPatches.ExtraVariableField.superconduct.get(AbstractDungeon.actionManager);
        int catalyzeValue = GameActionManagerPatches.ExtraVariableField.catalyze.get(AbstractDungeon.actionManager);
        int decayValue = GameActionManagerPatches.ExtraVariableField.decay.get(AbstractDungeon.actionManager);
        int crystallizationValue = GameActionManagerPatches.ExtraVariableField.crystallization.get(AbstractDungeon.actionManager);
        int diffusionValue = GameActionManagerPatches.ExtraVariableField.diffusion.get(AbstractDungeon.actionManager);
        if (Settings.language == Settings.GameLanguage.ZHS) {
            if (vaporizeValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("水火", " [" + hexColor + "]水 [" + hexColor + "]火 ");
            }
            if (burningValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("火草", " [" + hexColor + "]火 [" + hexColor + "]草 ");
            }
            if (overloadedValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("火雷", " [" + hexColor + "]火 [" + hexColor + "]雷 ");
            }
            if (meltValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("火冰", " [" + hexColor + "]火 [" + hexColor + "]冰 ");
            }
            if (electroChargedValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("水雷", " [" + hexColor + "]水 [" + hexColor + "]雷 ");
            }
            if (frozenValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("水冰", " [" + hexColor + "]水 [" + hexColor + "]冰 ");
            }
            if (bloomValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("水草", " [" + hexColor + "]水 [" + hexColor + "]草 ");
            }
            if (superconductValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("雷冰", " [" + hexColor + "]雷 [" + hexColor + "]冰 ");
            }
            if (catalyzeValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("雷草", " [" + hexColor + "]雷 [" + hexColor + "]草 ");
            }
            if (decayValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("冰草", " [" + hexColor + "]冰 [" + hexColor + "]草 ");
            }
            if (crystallizationValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("结晶", " [" + hexColor + "]结 [" + hexColor + "]结 ");
            }
            if (diffusionValue > 0) {
                this.rawDescription = this.rawDescription.replaceAll("扩散", " [" + hexColor + "]扩 [" + hexColor + "]散 ");
            }
        }
        else{
            if (vaporizeValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Hydro-Pyro,", "[" + hexColor + "]Hydro-Pyro[],");
            }
            if (burningValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Pyro-Dentro,", " [" + hexColor + "]Pyro-Dentro[],");
            }
            if (overloadedValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Pyro-Electro,", " [" + hexColor + "]Pyro-Electro[],");
            }
            if (meltValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Pyro-Cryo,", " [" + hexColor + "]Pyro-Cryo[],");
            }
            if (electroChargedValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Hydro-Electro,", " [" + hexColor + "]Hydro-Electro[],");
            }
            if (frozenValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Hydro-Cryo,", " [" + hexColor + "]Hydro-Cryo[],");
            }
            if (bloomValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Hydro-Dentro,", " [" + hexColor + "]Hydro-Dentro[],");
            }
            if (superconductValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Electro-Cryo,", " [" + hexColor + "]Electro-Cryo[],");
            }
            if (catalyzeValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Electro-Dentro,", " [" + hexColor + "]Electro-Dentro[],");
            }
            if (decayValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Cryo-Dentro,", " [" + hexColor + "]Cryo-Dentro[],");
            }
            if (crystallizationValue  > 0 ) {
                this.rawDescription = this.rawDescription.replaceAll("Crystallization,", " [" + hexColor + "]Crystallization[],");
            }
            if (diffusionValue > 0) {
                this.rawDescription = this.rawDescription.replaceAll("Diffusion,", " [" + hexColor + "]Diffusion[],");
            }
        }
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainEnergyAction(this.magicNumber));
    }

    public void applyPowers() {
        super.applyPowers();
        this.updateCostBasedOnReactions();
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }
}
