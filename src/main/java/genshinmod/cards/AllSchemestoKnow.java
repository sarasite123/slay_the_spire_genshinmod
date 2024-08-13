package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDentroPower;
import genshinmod.element.MagicNumberUtil;

import java.util.Iterator;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class AllSchemestoKnow extends CustomCard {
    public static final String ID = ModHelper.makePath("AllSchemestoKnow");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/AllSchemestoKnow.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private int truebase;

    public AllSchemestoKnow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.truebase = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:AllSchemestoKnow"));
        Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while (var3.hasNext()) {
            AbstractMonster mo = var3.next();
            this.addToBot(new ApplyElementalPowerAction(mo,p,ModHelper.makePath("DentroPower"),this.magicNumber));
            this.addToBot(new ElementalReactionAction(mo, p));
        }
        this.addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));
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
