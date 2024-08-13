package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockPerStatusAction extends AbstractGameAction {
    private int blockPerCard;

    public BlockPerStatusAction(int blockAmount) {
        this.blockPerCard = blockAmount;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();

        // Collect all status cards from hand, draw pile, and discard pile
        collectStatusCards(cardsToExhaust, AbstractDungeon.player.hand.group.iterator());
        collectStatusCards(cardsToExhaust, AbstractDungeon.player.drawPile.group.iterator());
        collectStatusCards(cardsToExhaust, AbstractDungeon.player.discardPile.group.iterator());

        // Gain block for each status card
        for (AbstractCard c : cardsToExhaust) {
            this.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockPerCard));
        }

        // Exhaust each status card
        for (AbstractCard c : cardsToExhaust) {
            if (AbstractDungeon.player.hand.group.contains(c)) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            } else if (AbstractDungeon.player.drawPile.group.contains(c)) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            } else if (AbstractDungeon.player.discardPile.group.contains(c)) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
            }
        }

        this.isDone = true;
    }

    private void collectStatusCards(ArrayList<AbstractCard> cardsToExhaust, Iterator<AbstractCard> iterator) {
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.type == AbstractCard.CardType.STATUS) {
                cardsToExhaust.add(c);
            }
        }
    }
}
