package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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

import java.util.concurrent.atomic.AtomicBoolean;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class SecretRiteTwilightShadowpiercer extends CustomCard {
    public static final String ID = ModHelper.makePath("SecretRiteTwilightShadowpiercer");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/SecretRiteTwilightShadowpiercer.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int truebase;

    public SecretRiteTwilightShadowpiercer() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 1;
        this.magicNumber = this.baseMagicNumber = 6;
        this.truebase = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.upgradeBlock(1);
            this.truebase += 2;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AtomicBoolean hasReaction = new AtomicBoolean(false);
        this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("ElectroPower"),this.magicNumber));
        this.addToBot(new ElementalReactionAction(m, p, reaction -> {
            if (reaction != null) {
                System.out.println("Reaction occurred: " + reaction + " on " + m.name);
                hasReaction.set(true);
            } else {
                System.out.println("No reaction on " + m.name);
            }
        }));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if (hasReaction.get()) {
                    System.out.println("Reactions occurred, reapplying genshinElectroPower");
                    this.addToBot(new GainEnergyAction(1));
                } else {
                    System.out.println("No more reactions, ending loop");
                }
                isDone = true;
            }
        });
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
