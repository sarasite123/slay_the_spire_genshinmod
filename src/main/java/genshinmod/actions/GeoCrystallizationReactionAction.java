package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.cards.Cryo;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

public class GeoCrystallizationReactionAction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractCreature player;
    private boolean flag;

    // 构造方法，接受两个参数
    public GeoCrystallizationReactionAction(AbstractCreature owner, AbstractCreature player) {
        this.owner = owner;
        this.player = player;
        this.actionType = ActionType.SPECIAL;
        this.flag = false;
    }

    // 构造方法，接受三个参数
    public GeoCrystallizationReactionAction(AbstractCreature owner, AbstractCreature player, boolean FromPlanetBefall) {
        this(owner, player); // 调用两个参数的构造方法
        this.flag = FromPlanetBefall;
    }

    @Override
    public void update() {
        AbstractPower[] elementPowers = {
                this.owner.getPower(ModHelper.makePath("HydroPower")),
                this.owner.getPower(ModHelper.makePath("ElectroPower")),
                this.owner.getPower(ModHelper.makePath("CryoPower")),
                this.owner.getPower(ModHelper.makePath("DentroPower")),
                this.owner.getPower(ModHelper.makePath("PyroPower"))
        };

        AbstractPower activePower = null;

        for (AbstractPower power : elementPowers) {
            if (power != null) {
                activePower = power;
                break;
            }
        }

        if (activePower == null) {
            this.isDone = true;
            return;
        }

        AbstractPower anemoGeoCostPower = this.player.getPower(ModHelper.makePath("AnemoGeoCostPower"));
        if (activePower != null) {
            int block = activePower.amount;

            int crystallizationValue = GameActionManagerPatches.ExtraVariableField.crystallization.get(AbstractDungeon.actionManager);
            int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
            if(crystallizationValue==0){ elementreactiontypeValue++;}
            crystallizationValue += activePower.amount;
            GameActionManagerPatches.ExtraVariableField.crystallization.set(AbstractDungeon.actionManager, crystallizationValue);
            GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);


            int crystallizationroundValue = GameActionManagerPatches.ExtraVariableField.crystallizationinround.get(AbstractDungeon.actionManager);
            int elementreactiontyperoundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
            if(crystallizationroundValue==0){ elementreactiontyperoundValue++;}
            crystallizationroundValue += activePower.amount;
            GameActionManagerPatches.ExtraVariableField.crystallizationinround.set(AbstractDungeon.actionManager, crystallizationroundValue);
            GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontyperoundValue);

            if(activePower==this.owner.getPower(ModHelper.makePath("PyroPower"))){
                int crystallizationpyroroundValue = GameActionManagerPatches.ExtraVariableField.crystallizationpyroinround.get(AbstractDungeon.actionManager);
                crystallizationpyroroundValue += activePower.amount;
                GameActionManagerPatches.ExtraVariableField.crystallizationpyroinround.set(AbstractDungeon.actionManager, crystallizationpyroroundValue);
            }
            if(activePower==this.owner.getPower(ModHelper.makePath("CryoPower"))){
                int crystallizationcryoroundValue = GameActionManagerPatches.ExtraVariableField.crystallizationcryoinround.get(AbstractDungeon.actionManager);
                crystallizationcryoroundValue += activePower.amount;
                GameActionManagerPatches.ExtraVariableField.crystallizationcryoinround.set(AbstractDungeon.actionManager, crystallizationcryoroundValue);
            }


            if(activePower==this.owner.getPower(ModHelper.makePath("HydroPower"))) {
                this.addToBot(new HydroReactionGainTempHPaction(this.owner, this.player, block));
            }
            if(activePower==this.owner.getPower(ModHelper.makePath("ElectroPower"))){

                int crystallizationelectroinroundValue = GameActionManagerPatches.ExtraVariableField.crystallizationelectroinround.get(AbstractDungeon.actionManager);
                crystallizationelectroinroundValue += activePower.amount;
                GameActionManagerPatches.ExtraVariableField.crystallizationelectroinround.set(AbstractDungeon.actionManager, crystallizationelectroinroundValue);

                this.addToBot(new ElectroReactionGainPowerAction(this.owner, this.player,block));
            }
            if(activePower==this.owner.getPower(ModHelper.makePath("PyroPower"))){
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    // 这里假设你想要把名为 "特定卡牌" 的卡牌移回手牌
                    if (card.cardID.equals(ModHelper.makePath("RyuukinSaxifrage"))) {
                        this.addToBot(new DiscardToHandAction(card));
                    }
                }
            }
            if(activePower==this.owner.getPower(ModHelper.makePath("CryoPower"))){
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    // 这里假设你想要把名为 "特定卡牌" 的卡牌移回手牌
                    if (card.cardID.equals(ModHelper.makePath("SpringSpiritSummoning"))) {
                        this.addToBot(new DiscardToHandAction(card));
                    }
                }
            }
            if (this.flag) {
                this.addToTop(new DamageAction(this.owner, new DamageInfo(this.player, block * 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.player, this.player, block * 2));

            if (anemoGeoCostPower == null) {
                if (this.owner.getPower(activePower.ID) != null && this.owner.getPower(activePower.ID).amount <= 0) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, activePower.ID));
                }
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, activePower.ID, block));
            }

        }

        this.isDone = true;
    }
}
