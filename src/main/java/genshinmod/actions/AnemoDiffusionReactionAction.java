package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.patchs.GameActionManagerPatches;
import genshinmod.powers.*;
import genshinmod.helper.ModHelper;

public class AnemoDiffusionReactionAction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractCreature player;
    private int baseapply;
    private int MagicFromAnemoSeed = 0;

    // 构造方法，接受两个参数
    public AnemoDiffusionReactionAction(AbstractCreature owner, AbstractCreature player) {
        this.owner = owner;
        this.player = player;
        this.actionType = ActionType.SPECIAL;
        this.baseapply = 1;
    }

    // 构造方法，接受三个参数
    public AnemoDiffusionReactionAction(AbstractCreature owner, AbstractCreature player, int MagicFromAnemoSeed) {
        this(owner, player); // 调用两个参数的构造方法
        this.MagicFromAnemoSeed = MagicFromAnemoSeed;
    }

    @Override
    public void update() {
        System.out.println("Starting AnemoDiffusionReactionAction update");

        if (this.owner == null) {
            System.out.println("Owner is null");
            this.isDone = true;
            return;
        }

        if (this.player == null) {
            System.out.println("Player is null");
            this.isDone = true;
            return;
        }

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
            System.out.println("No active power found");
            this.isDone = true;
            return;
        } else {
            System.out.println("Active power found: " + activePower.ID);
        }

        AbstractPower anemoGeoCostPower = this.player.getPower(ModHelper.makePath("AnemoGeoCostPower"));
        AbstractPower anemoGainAddCardPower = this.player.getPower(ModHelper.makePath("AnemoGainAddCardPower"));

        if (anemoGeoCostPower == null) {
            System.out.println("AnemoGeoCostPower is null");
        }

        if (anemoGainAddCardPower == null) {
            System.out.println("AnemoGainAddCardPower is null");
        }

        int damage = activePower.amount;
        int diffusionValue = GameActionManagerPatches.ExtraVariableField.diffusion.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(diffusionValue==0){ elementreactiontypeValue++;}
        diffusionValue += activePower.amount;
        GameActionManagerPatches.ExtraVariableField.diffusion.set(AbstractDungeon.actionManager, diffusionValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        // 使用辅助函数检查elementreactiontypeValue的变化

        int diffusionroundValue = GameActionManagerPatches.ExtraVariableField.diffusioninround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(diffusionroundValue==0){ elementreactiontypeinroundValue++;}
        diffusionroundValue += activePower.amount;
        GameActionManagerPatches.ExtraVariableField.diffusioninround.set(AbstractDungeon.actionManager, diffusionroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        if(activePower==this.owner.getPower(ModHelper.makePath("PyroPower"))){
            int diffusionpyroroundValue = GameActionManagerPatches.ExtraVariableField.diffusionpyroinround.get(AbstractDungeon.actionManager);
            diffusionpyroroundValue += activePower.amount;
            GameActionManagerPatches.ExtraVariableField.diffusionpyroinround.set(AbstractDungeon.actionManager, diffusionpyroroundValue);
        }
        if(activePower==this.owner.getPower(ModHelper.makePath("CryoPower"))){
            int diffusioncryoroundValue = GameActionManagerPatches.ExtraVariableField.diffusioncryoinround.get(AbstractDungeon.actionManager);
            diffusioncryoroundValue += activePower.amount;
            GameActionManagerPatches.ExtraVariableField.diffusioncryoinround.set(AbstractDungeon.actionManager, diffusioncryoroundValue);
        }



        if (anemoGainAddCardPower != null) {
            int anemogainaddcardValue = GameActionManagerPatches.ExtraVariableField.anemogainaddcard.get(AbstractDungeon.actionManager);
            anemogainaddcardValue += activePower.amount;
            if (anemogainaddcardValue >= 10) {
                //this.addToBot(new DrawCardAction(this.player, anemoGainAddCardPower.amount * 2));
                anemogainaddcardValue -= 10;
            }
            this.addToBot(new DrawCardAction(this.player, anemoGainAddCardPower.amount ));
            GameActionManagerPatches.ExtraVariableField.anemogainaddcard.set(AbstractDungeon.actionManager, anemogainaddcardValue);
            // 更新描述
            if (anemoGainAddCardPower instanceof genshinAnemoGainAddCardPower) {
                ((genshinAnemoGainAddCardPower) anemoGainAddCardPower).onDiffusionValueChange();
            }
        }



        // 风种子触发扩散反应则抽牌
        this.addToTop(new DrawCardAction(this.player, this.MagicFromAnemoSeed));

        //如果是水雷检查是否会触发临时生命和回能量
        if(activePower==this.owner.getPower(ModHelper.makePath("HydroPower"))) {
            this.addToTop(new HydroReactionGainTempHPaction(this.owner, this.player, damage));
        }
        if(activePower==this.owner.getPower(ModHelper.makePath("ElectroPower"))){
            int diffusionelectroinroundValue = GameActionManagerPatches.ExtraVariableField.diffusionelectroinround.get(AbstractDungeon.actionManager);
            diffusionelectroinroundValue += activePower.amount;
            GameActionManagerPatches.ExtraVariableField.diffusionelectroinround.set(AbstractDungeon.actionManager, diffusionelectroinroundValue);

            this.addToTop(new ElectroReactionGainPowerAction(this.owner, this.player,damage));
        }
        //检测是否有触发火冰回到手牌
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
        AbstractPower Lockpower = AbstractDungeon.player.getPower(ModHelper.makePath("ViridescentVenererPower"));
        if(Lockpower!=null){
            System.out.println("ViridescentVenererPower exist");
            this.addToTop(new ApplyPowerAction(this.owner, this.player, new genshinElemetalLockingPower(this.owner, 1), 1));
        }

        this.addToTop(new DamageAction(this.owner, new DamageInfo(this.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        // 减少并移除元素层数
        if (anemoGeoCostPower == null) {
            if (this.owner.getPower(activePower.ID) != null && this.owner.getPower(activePower.ID).amount <= 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, activePower.ID));
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, activePower.ID, damage));
        }

        // 对怪物造成伤害



        AbstractPower power = AbstractDungeon.player.getPower(ModHelper.makePath("ElementalMasteryPower"));
        if (power != null) {
            this.baseapply = 1 + power.amount;
            if(this.baseapply<=0){
                this.baseapply=0;
            }
        }



        // 给每个怪物施加新的元素并触发元素反应
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {

            AbstractDungeon.actionManager.addToTop(new ElementalReactionAction(monster, this.player));

            switch (activePower.ID) {
                case "genshin:HydroPower":
                    this.addToTop(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("HydroPower"),this.baseapply));
                    break;
                case "genshin:ElectroPower":
                    this.addToTop(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("ElectroPower"),this.baseapply));
                    break;
                case "genshin:CryoPower":
                    this.addToTop(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("CryoPower"),this.baseapply));
                    break;
                case "genshin:DentroPower":
                    this.addToTop(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("DentroPower"),this.baseapply));
                    break;
                case "genshin:PyroPower":
                    this.addToTop(new ApplyElementalPowerAction(monster,this.owner,ModHelper.makePath("PyroPower"),this.baseapply));
                    break;
            }
        }

        this.isDone = true;
    }
}
