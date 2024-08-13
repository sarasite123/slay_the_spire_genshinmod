package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class AddCardToHandAction extends AbstractGameAction {
    private AbstractCard card;

    public AddCardToHandAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (this.card != null) {
            if (AbstractDungeon.player.hand.group.size() < 10) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.card));
                this.card.initializeDescription();
            } else {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.card));
            }
        }
        this.isDone = true;
    }
}
