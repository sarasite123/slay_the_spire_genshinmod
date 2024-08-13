package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class Traveller extends CustomCard {
    public static final String ID = ModHelper.makePath("Traveller");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/attack/Traveller.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int truebase;

    public Traveller() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 9;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.truebase = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new Cryo(m, this.magicNumber));
            stanceChoices.add(new Hydro(m, this.magicNumber));
            stanceChoices.add(new Pyro(m, this.magicNumber));
            stanceChoices.add(new Dentro(m, this.magicNumber));
            stanceChoices.add(new Electro(m, this.magicNumber));
            this.addToBot(new ChooseOneAction(stanceChoices));
        } else {
            AbstractCard randomElementCard = getRandomElementCard(m, this.magicNumber);
            applyRandomElementPower(p, m, randomElementCard);
            this.addToBot(new ElementalReactionAction(m, p));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    private AbstractCard getRandomElementCard(AbstractMonster m, int magicNumber) {
        List<AbstractCard> elementCards = Arrays.asList(
                new Cryo(m, magicNumber),
                new Hydro(m, magicNumber),
                new Pyro(m, magicNumber),
                new Dentro(m, magicNumber),
                new Electro(m, magicNumber)
        );
        Random random = new Random();
        return elementCards.get(random.nextInt(elementCards.size()));
    }

    private void applyRandomElementPower(AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        if (card instanceof Cryo) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("CryoPower"),this.magicNumber));
        } else if (card instanceof Hydro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("HydroPower"),this.magicNumber));
        } else if (card instanceof Pyro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("PyroPower"),this.magicNumber));
        } else if (card instanceof Dentro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("DentroPower"),this.magicNumber));
        } else if (card instanceof Electro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("ElectroPower"),this.magicNumber));
        }
    }

    public void applyPowers() {
        this.baseMagicNumber = MagicNumberUtil.calculateMagicNumber(this.truebase);
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        if(this.upgraded){
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        }
        else{
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        super.applyPowers();
    }

    public void calculateCardDamage(AbstractMonster mo) {
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}