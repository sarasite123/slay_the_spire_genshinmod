package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;

public class Dendrogranum extends CustomCard {
    public static final String ID = ModHelper.makePath("Dendrogranum");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/attack/Dendrogranum.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Dendrogranum() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 6;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.selfRetain = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public void setX(int amount) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower danceofHaftkarsvarPower = p.getPower(ModHelper.makePath("DanceofHaftkarsvarPower"));
        if(danceofHaftkarsvarPower!=null) {
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.upgradeDamage(danceofHaftkarsvarPower.amount*4);
        }
        this.magicNumber = amount;
        this.baseMagicNumber = this.magicNumber;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        System.out.println("Using Dendrogranum");
        AbstractPower danceofHaftkarsvarPower = p.getPower(ModHelper.makePath("DanceofHaftkarsvarPower"));
        for (int i = 0; i < this.magicNumber; ++i) {
            System.out.println("Adding LoseHPAction for iteration: " + i);

            System.out.println("Adding DamageAction for iteration: " + i);
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        if(danceofHaftkarsvarPower==null){
            this.addToBot(new LoseHPAction(p, p, this.magicNumber));
        }
    }
}
