package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import genshinmod.helper.ModHelper;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;


public class GuidetoAfterlife extends CustomCard {
        public static final String ID = ModHelper.makePath("GuidetoAfterlife");
        private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
        private static final String NAME = CARD_STRINGS.NAME;
        private static final String IMG_PATH = "GenshinModResources/img/cards/skill/GuidetoAfterlife.png";
        private static final int COST = 1;
        private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
        private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
        private static final CardType TYPE = CardType.SKILL;
        private static final CardColor COLOR = Genshin_CARD;
        private static final CardRarity RARITY = CardRarity.UNCOMMON;
        private static final CardTarget TARGET = CardTarget.SELF;

        public GuidetoAfterlife() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
            this.baseMagicNumber = 3;
            this.magicNumber = this.baseMagicNumber;
        }

        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:GuidetoAfterlife"));
            this.addToBot(new LoseHPAction(p, p, 5));
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }

        public void upgrade() {
            if (!this.upgraded) {
                this.upgradeName();
                this.upgradeMagicNumber(1);
                this.initializeDescription();
            }
        }

    }
