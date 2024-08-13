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
import genshinmod.actions.ModifyMagicAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinElectroPower;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class YakanEvocationSesshouSakura extends CustomCard {
    public static final String ID = ModHelper.makePath("YakanEvocationSesshouSakura");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/YakanEvocationSesshouSakura.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public int truebase;
    public int M2;
    public int baseM2;
    public boolean isM2Modified;
    public boolean upgradedM2;
    public int truebaseM2;

    public YakanEvocationSesshouSakura() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.baseM2 = this.baseMagicNumber;
        this.M2 = this.baseMagicNumber;
        this.truebase = this.baseMagicNumber;
        this.truebaseM2 = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:YakanEvocationSesshouSakura"));
        this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("ElectroPower"),this.magicNumber));
        this.addToBot(new ElementalReactionAction(m, p));
        this.addToBot(new ModifyMagicAction(this.uuid, this.M2));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.baseM2 += 2;
            this.truebase +=2;
            this.M2 =this.baseM2;
            this.truebaseM2 = this.baseM2;
            this.initializeDescription();
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
