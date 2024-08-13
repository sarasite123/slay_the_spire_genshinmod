package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinStartTurnApplyRandomElementPower;
import genshinmod.powers.genshinStartTurnApplySpecificElementPower;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class ElementalRainbow extends CustomCard {
    public static final String ID = ModHelper.makePath("ElementalRainbow");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/power/ElementalRainbow.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardColor COLOR = Genshin_CARD;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private int truebase;

    public ElementalRainbow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.truebase = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            this.addToBot(new ApplyPowerAction(p, p, new genshinStartTurnApplySpecificElementPower(p, this.magicNumber), this.magicNumber));
        }
        else {
            this.addToBot(new ApplyPowerAction(p, p, new genshinStartTurnApplyRandomElementPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }

    }

    public void applyPowers() {
        this.baseMagicNumber = MagicNumberUtil.calculateMagicNumber(this.truebase);
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
        super.applyPowers();
    }
}
