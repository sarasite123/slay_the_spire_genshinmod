package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinCryoPower;

import java.util.Iterator;

public class CryoPerAttackPlayedAction extends AbstractGameAction {
    private int magicNumber;
    private AbstractCreature target;
    public CryoPerAttackPlayedAction(AbstractCreature target,int magicnumber){
        this.actionType = ActionType.SPECIAL;
        this.target=target;
        this.magicNumber=magicnumber;
    }
        public void update() {
            this.isDone = true;
            if (this.target != null && this.target.currentHealth > 0) {
                int count = 0;
                Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();

                while(var2.hasNext()) {
                    AbstractCard c = (AbstractCard)var2.next();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        ++count;
                    }
                }

                --count;
                AbstractPlayer p = AbstractDungeon.player;
                this.addToBot(new ApplyElementalPowerAction(this.target,p, ModHelper.makePath("CryoPower"),magicNumber * count));
                this.addToBot(new ElementalReactionAction(this.target, p));
            }


        }
    }

