package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;
import genshinmod.powers.genshinElectroGainEnergyPower;

public class ElectroReactionGainPowerAction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractCreature player;
    private int num;

    public ElectroReactionGainPowerAction(AbstractCreature owner, AbstractCreature player, int ReactionNum) {
        this.owner = owner;
        this.player = player;
        this.num = ReactionNum;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update() {
        AbstractPower elementPowers = this.owner.getPower(ModHelper.makePath("ElectroPower"));
        AbstractPower electroGainEnergyPower = this.player.getPower(ModHelper.makePath("ElectroGainEnergyPower"));

        if (elementPowers != null && electroGainEnergyPower != null) {
            // 读取变量值
            int value = GameActionManagerPatches.ExtraVariableField.elctrogainenergy.get(AbstractDungeon.actionManager);
            System.out.println("Original value: " + value); // 打印原始变量值

            // 对变量值进行操作
            value += this.num;
            System.out.println("New value after adding : " + value); // 打印操作后的变量值
            if (value >= 10) {
                //this.addToBot(new GainEnergyAction(electroGainEnergyPower.amount));
                value -= 10;
            }
            this.addToTop(new GainEnergyAction(electroGainEnergyPower.amount));
            // 重新设置变量值
            GameActionManagerPatches.ExtraVariableField.elctrogainenergy.set(AbstractDungeon.actionManager, value);
            System.out.println("Value set in ExtraVariableField: " + value); // 打印设置后的变量值

            // 更新描述
            if (electroGainEnergyPower instanceof genshinElectroGainEnergyPower) {
                ((genshinElectroGainEnergyPower) electroGainEnergyPower).onElectroReactionTrigger();
            }
        }

        this.isDone = true;
    }
}
