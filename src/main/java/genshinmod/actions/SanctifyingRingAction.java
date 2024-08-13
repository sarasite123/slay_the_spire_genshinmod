package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class SanctifyingRingAction extends AbstractGameAction {
    private int energyGain;

    public SanctifyingRingAction(int energyGain) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.energyGain = energyGain;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.SKILL) {
                this.addToBot(new GainEnergyAction(this.energyGain));
                break;
            }
        }

        this.isDone = true;
    }
}
