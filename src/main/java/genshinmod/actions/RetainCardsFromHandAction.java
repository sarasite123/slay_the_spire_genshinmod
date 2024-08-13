package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetainCardsFromHandAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int numberOfCards;

    public RetainCardsFromHandAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                if (this.player.hand.size() <= this.numberOfCards) {
                    for (AbstractCard c : this.player.hand.group) {
                        c.retain = true;
                    }
                    this.isDone = true;
                } else {
                    AbstractDungeon.handCardSelectScreen.open("Retain", this.numberOfCards, false, false);
                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    c.retain = true;
                    this.player.hand.addToTop(c);  // 确保选中的卡牌不会从手牌中移除
                }
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            }
            this.tickDuration();
        }
    }
}
