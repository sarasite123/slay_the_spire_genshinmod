package genshinmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinTenacityPower;

import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;

public class MoltenInferno extends CustomCard {
    public static final String ID = ModHelper.makePath("MoltenInferno");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "GenshinModResources/img/cards/skill/MoltenInferno.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Genshin_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MoltenInferno() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.selfRetain = false;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("genshin:MoltenInferno"));
        AbstractPower TenacityPower = p.getPower(genshinTenacityPower.POWER_ID);
        AbstractPower VigorPower = p.getPower(com.megacrit.cardcrawl.powers.watcher.VigorPower.POWER_ID);
        if(TenacityPower!=null && TenacityPower.amount > 0)
        {
            this.addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,TenacityPower.amount),TenacityPower.amount));
            this.addToBot(new RemoveSpecificPowerAction(p, p, TenacityPower));
        }
        if(VigorPower!=null && VigorPower.amount > 0)
        {
            this.addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,VigorPower.amount),VigorPower.amount));
            this.addToBot(new RemoveSpecificPowerAction(p, p, VigorPower));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
