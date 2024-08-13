package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinElectroPower;

import java.util.Iterator;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class SubjugationKoukouSendou extends CustomCard {
    public static final String ID = ModHelper.makePath("SubjugationKoukouSendou");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/SubjugationKoukouSendou.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private int truebase;

    public SubjugationKoukouSendou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 7;
        this.truebase = this.baseMagicNumber;
        this.cardsToPreview = new VoidCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.truebase += 3;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:SubjugationKoukouSendou"));
        Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var3.hasNext()) {
            AbstractMonster mo = var3.next();
            this.addToBot(new ApplyElementalPowerAction(mo,p,ModHelper.makePath("ElectroPower"),this.magicNumber));
            this.addToBot(new ElementalReactionAction(mo, p));
        }
        this.addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }


    public void applyPowers() {
        this.baseMagicNumber = MagicNumberUtil.calculateMagicNumber(this.truebase);
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
        super.applyPowers();
    }
}
