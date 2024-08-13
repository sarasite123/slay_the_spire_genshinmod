package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.helper.ModHelper;

import java.util.Iterator;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class OTidesIHaveReturned extends CustomCard {
    public static final String ID = ModHelper.makePath("OTidesIHaveReturned");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/attack/OTidesIHaveReturned.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public OTidesIHaveReturned() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 3; // 初始基础伤害值
        this.baseMagicNumber = 0; // 临时生命值的初始值
        this.magicNumber = this.baseMagicNumber;
        this.damage=this.baseDamage;
        this.isMultiDamage = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player != null) {
            int tempHP = TempHPField.tempHp.get(AbstractDungeon.player);
            this.baseMagicNumber = tempHP; // 更新临时生命值
            this.magicNumber = this.baseMagicNumber;
            this.isMultiDamage = true;
            // 更新描述
            this.rawDescription = (this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tempHP = TempHPField.tempHp.get(p);
        this.damage = (this.baseDamage + tempHP);
        if(upgraded) {
            for(int i=0;i<4;i++) {
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));// 清除所有临时生命值
            }
            TempHPField.tempHp.set(p, 0);
            this.baseMagicNumber = 0;
            this.damage=this.baseDamage;
        }
        else {
            for(int i=0;i<3;i++) {
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));// 清除所有临时生命值
            }
            // 清除所有临时生命值
            TempHPField.tempHp.set(p, 0);
            this.baseMagicNumber = 0;
            this.damage=this.baseDamage;
        }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
