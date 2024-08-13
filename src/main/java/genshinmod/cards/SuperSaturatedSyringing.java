package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.helper.ModHelper;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class SuperSaturatedSyringing extends CustomCard {
    public static final String ID = ModHelper.makePath("SuperSaturatedSyringing");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/attack/SuperSaturatedSyringing.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public SuperSaturatedSyringing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 0; // 初始基础伤害值
        this.baseMagicNumber = 0; // 临时生命值的初始值
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player != null) {
            int tempHP = TempHPField.tempHp.get(AbstractDungeon.player);
            if(!upgraded){
                this.baseMagicNumber = tempHP; // 更新临时生命值
                this.magicNumber = this.baseMagicNumber;
                this.rawDescription = DESCRIPTION;
            }
            else {
                this.baseMagicNumber = tempHP+3; // 更新临时生命值
                this.magicNumber = this.baseMagicNumber;
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            this.baseDamage=this.magicNumber;
            // 更新描述
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage=this.baseDamage=this.magicNumber;
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
