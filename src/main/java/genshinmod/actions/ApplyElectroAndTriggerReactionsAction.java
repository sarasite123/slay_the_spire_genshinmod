package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinElectroPower;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class ApplyElectroAndTriggerReactionsAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int magicNumber;

    public ApplyElectroAndTriggerReactionsAction(AbstractPlayer player, int magicNumber) {
        this.player = player;
        this.magicNumber = magicNumber;
    }

    @Override
    public void update() {
        System.out.println("Starting applyElectroAndTriggerReactions");
        applyElectroAndTriggerReactions();
        this.isDone = true;
    }

    private void applyElectroAndTriggerReactions() {
        AtomicBoolean hasReaction = new AtomicBoolean(false);
        Iterator<AbstractMonster> monsterIterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (monsterIterator.hasNext()) {
            AbstractMonster monster = monsterIterator.next();
            System.out.println("Applying genshinElectroPower to " + monster.name);
            this.addToBot(new ApplyElementalPowerAction(monster,player, ModHelper.makePath("ElectroPower"),this.magicNumber));
            this.addToBot(new ElementalReactionAction(monster, player, reaction -> {
                if (reaction != null) {
                    System.out.println("Reaction occurred: " + reaction + " on " + monster.name);
                    hasReaction.set(true);
                } else {
                    System.out.println("No reaction on " + monster.name);
                }
            }));
        }

        // This will ensure that we wait for all actions to complete before checking hasReaction
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if (hasReaction.get()) {
                    System.out.println("Reactions occurred, reapplying genshinElectroPower");
                    applyElectroAndTriggerReactions();
                } else {
                    System.out.println("No more reactions, ending loop");
                }
                isDone = true;
            }
        });
    }
}
