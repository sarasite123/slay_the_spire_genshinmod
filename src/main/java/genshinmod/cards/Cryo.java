package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinCryoPower;

import java.util.Iterator;

public class Cryo extends CustomCard {

    public static final String ID = ModHelper.makePath("Cryo");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/element/Cryo.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private AbstractMonster targetMonster;
    private int magicNumber;
    private boolean applyToAllEnemies;

    public Cryo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public Cryo(AbstractMonster m) {
        this();
        this.targetMonster = m;
    }

    public Cryo(AbstractMonster m, int magicNumber) {
        this();
        this.targetMonster = m;
        this.magicNumber = magicNumber;
    }

    public Cryo(AbstractMonster m, int magicNumber, boolean applyToAllEnemies) {
        this();
        this.targetMonster = m;
        this.magicNumber = magicNumber;
        this.applyToAllEnemies = applyToAllEnemies;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        if (applyToAllEnemies) {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                applyCryoPowerWithReaction(monster, p, magicNumber);
            }
        } else if (targetMonster != null) {
            if (magicNumber > 0) {
                applyCryoPowerWithReaction(targetMonster, p, magicNumber);
            } else {
                applyCryoPower(targetMonster, p);
            }
        }
    }

    private void applyCryoPowerWithReaction(AbstractCreature target, AbstractPlayer source, int amount) {
        this.addToBot(new ApplyElementalPowerAction(target,source,ModHelper.makePath("CryoPower"),amount));
        this.addToBot(new ElementalReactionAction(target, source));
    }

    private void applyCryoPower(AbstractCreature target, AbstractPlayer source) {
        applyAndRemovePower(target, source, ModHelper.makePath("HydroPower"));
        applyAndRemovePower(target, source, ModHelper.makePath("ElectroPower"));
        applyAndRemovePower(target, source, ModHelper.makePath("PyroPower"));
        applyAndRemovePower(target, source, ModHelper.makePath("DentroPower"));
    }

    private void applyAndRemovePower(AbstractCreature target, AbstractPlayer source, String powerID) {
        AbstractPower power = target.getPower(powerID);
        if (power != null) {
            int amount = power.amount;
            this.addToBot(new ApplyElementalPowerAction(target,source,ModHelper.makePath("CryoPower"),amount));
            this.addToBot(new ReducePowerAction(target, target, powerID, amount));
            this.addToBot(new RemoveSpecificPowerAction(target, target, powerID));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
