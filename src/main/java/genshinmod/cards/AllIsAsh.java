package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.actions.ModifyMagicAction;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinPyroPower;
import genshinmod.element.MagicNumberUtil;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class AllIsAsh extends CustomCard {
    public static final String ID = ModHelper.makePath("AllIsAsh");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/AllIsAsh.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public int truebase;

    public AllIsAsh() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.truebase = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("PyroPower"),this.magicNumber));
        this.addToBot(new ElementalReactionAction(m, p));
        this.addToBot(new ModifyMagicAction(this.uuid, -1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.truebase += 2;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = MagicNumberUtil.calculateMagicNumber(this.truebase);
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
        super.applyPowers();
    }
}
