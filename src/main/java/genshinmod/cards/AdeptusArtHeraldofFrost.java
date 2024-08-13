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
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinAttackGainTempHPPower;
import genshinmod.powers.genshinCryoPower;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class AdeptusArtHeraldofFrost extends CustomCard {
    public static final String ID = ModHelper.makePath("AdeptusArtHeraldofFrost");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/AdeptusArtHeraldofFrost.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int truebase;
    public int M2;
    public int baseM2;
    public boolean isM2Modified;
    public boolean upgradedM2;
    public int truebaseM2;

    public AdeptusArtHeraldofFrost() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.truebase = this.baseMagicNumber;
        this.truebaseM2 = 1;
        this.baseM2 = this.truebaseM2;
        this.M2 = this.truebaseM2;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.baseM2 = this.truebaseM2 + 1;
            this.M2 = this.baseM2;
            this.truebase += 2 ;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:AdeptusArtHeraldofFrost"));
        this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("CryoPower"),this.magicNumber));
        this.addToBot(new ElementalReactionAction(m, p));
        this.addToBot(new ApplyPowerAction(m, p, new genshinAttackGainTempHPPower(m, this.M2), this.M2));
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
