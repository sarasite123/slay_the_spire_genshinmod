package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinHydroPower;

import java.util.Iterator;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class LettheShowBegin extends CustomCard {
    public static final String ID = ModHelper.makePath("LettheShowBegin");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/LettheShowBegin.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private int truebase;

    public LettheShowBegin() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 3;
        this.truebase = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.truebase += 1;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:LettheShowBegin"));
        Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var3.hasNext()) {
            AbstractMonster mo = var3.next();
            this.addToBot(new ApplyElementalPowerAction(mo,p,ModHelper.makePath("HydroPower"),this.magicNumber));
            this.addToBot(new ElementalReactionAction(mo, p));
        }
        if(upgraded){
            this.addToTop(new AddTemporaryHPAction(p, p, 7));
        }
        else {
            this.addToTop(new AddTemporaryHPAction(p, p, 5));
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
