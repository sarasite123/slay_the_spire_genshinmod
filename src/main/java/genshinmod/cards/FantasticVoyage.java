package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class FantasticVoyage extends CustomCard {
    public static final String ID = ModHelper.makePath("FantasticVoyage");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/FantasticVoyage.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FantasticVoyage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:FantasticVoyage"));
        int currentStrength = p.hasPower(StrengthPower.POWER_ID) ? p.getPower(StrengthPower.POWER_ID).amount : 0;
        int maxStrengthValue = GameActionManagerPatches.ExtraVariableField.maxstrength.get(AbstractDungeon.actionManager);

        // Update the maxStrengthValue based on the current strength
        int totalStrength = maxStrengthValue;

        // Apply total strength as temporary strength
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, totalStrength), totalStrength));
        // Apply the lose strength power to remove the temporary strength at end of turn
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, totalStrength), totalStrength));

        // Update the maxStrengthValue
        if (currentStrength + totalStrength > maxStrengthValue) {
            GameActionManagerPatches.ExtraVariableField.maxstrength.set(AbstractDungeon.actionManager, currentStrength + totalStrength);
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();

        int currentStrength = AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) ? AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount : 0;
        int maxStrengthValue = GameActionManagerPatches.ExtraVariableField.maxstrength.get(AbstractDungeon.actionManager);

        // Update the maxStrengthValue if the current strength is higher
        if (currentStrength > maxStrengthValue) {
            maxStrengthValue = currentStrength;
            GameActionManagerPatches.ExtraVariableField.maxstrength.set(AbstractDungeon.actionManager, maxStrengthValue);
        }

        // Set magicNumber to the maxStrengthValue
        this.magicNumber = maxStrengthValue;
        this.isMagicNumberModified = true;
    }
}
