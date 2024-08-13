package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.actions.AddCardToHandAction;
import genshinmod.actions.AnemoDiffusionReactionAction;
import genshinmod.helper.ModHelper;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class AstableAnemohypostasisCreation6308 extends CustomCard {
    public static final String ID = ModHelper.makePath("AstableAnemohypostasisCreation6308");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/AstableAnemohypostasisCreation6308.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int truebase;


    public AstableAnemohypostasisCreation6308() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new AnemoSeed();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:AstableAnemohypostasisCreation6308"));
        if(!upgraded) {
            this.addToBot(new AnemoDiffusionReactionAction(m, p));//选择触发扩散
        }
        else {
            this.addToBot(new AnemoDiffusionReactionAction(m, p));
            this.addToBot(new AnemoDiffusionReactionAction(m, p));
        }
        AnemoSeed c= new AnemoSeed();
        AbstractCard card = c; // 将 WildStar 转换为 AbstractCard 类型
        this.addToBot(new AddCardToHandAction(card));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }
}
