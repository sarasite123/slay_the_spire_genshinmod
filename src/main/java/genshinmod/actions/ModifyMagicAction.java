package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import genshinmod.cards.AllIsAsh;
import genshinmod.cards.YakanEvocationSesshouSakura;

import java.util.Iterator;
import java.util.UUID;

public class ModifyMagicAction extends AbstractGameAction {
    private UUID uuid;

    public ModifyMagicAction(UUID targetUUID, int amount) {
        this.setValues(null, null, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator<AbstractCard> var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while (var1.hasNext()) {
            AbstractCard c = var1.next();
            if (c instanceof YakanEvocationSesshouSakura) {
                YakanEvocationSesshouSakura card = (YakanEvocationSesshouSakura) c;
                card.baseMagicNumber += this.amount;
                card.magicNumber = card.baseMagicNumber;
                card.isMagicNumberModified = true;
                card.truebase += this.amount;
                card.applyPowers();
            }
            else if(c instanceof AllIsAsh){
                AllIsAsh card = (AllIsAsh ) c;
                card.baseMagicNumber+=this.amount;
                card.magicNumber=card.baseMagicNumber;
                card.isMagicNumberModified = true;
                card.truebase +=this.amount;
                card.applyPowers();
            }
        }

        this.isDone = true;
    }
}
