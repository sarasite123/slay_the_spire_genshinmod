package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.NereidsAscensionAction;
import genshinmod.element.MagicNumberUtil;
import genshinmod.helper.ModHelper;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class NereidsAscension extends CustomCard {
    public static final String ID = ModHelper.makePath("NereidsAscension");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/NereidsAscension.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int truebase;
    private int M2;

    public NereidsAscension() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.M2=7;
        this.magicNumber=this.baseMagicNumber=6;
        this.truebase=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:NereidsAscension"));
        this.addToBot(new NereidsAscensionAction(this.magicNumber,this.M2,p,m));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.M2 += 2;
            this.upgradeMagicNumber(2);
            this.truebase += 2;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void applyPowers() {
        this.baseMagicNumber = MagicNumberUtil.calculateMagicNumber(this.truebase);
        this.magicNumber = this.baseMagicNumber;
        if(!this.upgraded) {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }else {
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        }
        initializeDescription();
        super.applyPowers();
    }

}