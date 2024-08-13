package genshinmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

public class HydroReactionGainTempHPaction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractCreature player;
    private int num;
    public HydroReactionGainTempHPaction(AbstractCreature owner, AbstractCreature player, int ReactionNum) {
        this.owner = owner;
        this.player = player;
        this.num = ReactionNum;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update() {
        AbstractPower elementPowers = this.owner.getPower(ModHelper.makePath("HydroPower"));
        AbstractPower hydroGainTempHPPower = this.player.getPower(ModHelper.makePath("HydroGainTempHPPower"));


        if (elementPowers != null) {
            if(hydroGainTempHPPower != null){
                this.addToTop(new AddTemporaryHPAction(this.player, this.player, this.num * hydroGainTempHPPower.amount));
            }
        }

        this.isDone = true;
    }
}