package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.cards.*;
import genshinmod.helper.ModHelper;

import java.util.*;

public class genshinStartTurnApplyRandomElementPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("StartTurnApplyRandomElementPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinStartTurnApplyRandomElementPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        String path128 = "GenshinModResources/img/powers/84/StartTurnApplyRandomElement84.png";
        String path48 = "GenshinModResources/img/powers/32/StartTurnApplyRandomElement32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ModHelper.makePath("StartTurnApplyRandomElementPower")));
        }
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
            AbstractPlayer p = AbstractDungeon.player;
            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDead && !m.isDying) {
                    AbstractCard randomElementCard = getRandomElementCard(m, this.amount);
                    applyRandomElementPower(p, m, randomElementCard);
                    this.addToBot(new ElementalReactionAction(m, p));
                }
            }
        }
    }

    private AbstractCard getRandomElementCard(AbstractMonster m, int magicNumber) {
        List<AbstractCard> elementCards = Arrays.asList(
                new Cryo(m, magicNumber),
                new Hydro(m, magicNumber),
                new Pyro(m, magicNumber),
                new Dentro(m, magicNumber),
                new Electro(m, magicNumber)
        );
        Random random = new Random();
        return elementCards.get(random.nextInt(elementCards.size()));
    }

    private void applyRandomElementPower(AbstractPlayer p, AbstractMonster m, AbstractCard card) {
        if (card instanceof Cryo) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("CryoPower"),this.amount));
        } else if (card instanceof Hydro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("HydroPower"),this.amount));
        } else if (card instanceof Pyro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("PyroPower"),this.amount));
        } else if (card instanceof Dentro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("DentroPower"),this.amount));
        } else if (card instanceof Electro) {
            this.addToBot(new ApplyElementalPowerAction(m,p,ModHelper.makePath("ElectroPower"),this.amount));
        }
    }
}