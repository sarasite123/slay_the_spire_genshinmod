package genshinmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;
import genshinmod.powers.genshinTenacityPower;

import java.util.function.Consumer;

public class ElementalReactionAction extends AbstractGameAction {
    private AbstractCreature owner;
    private AbstractCreature player;
    private Consumer<String> callback; // 回调函数

    public ElementalReactionAction(AbstractCreature owner, AbstractCreature player, Consumer<String> callback) {
        this.owner = owner;
        this.player = player;
        this.callback = callback; // 设置回调函数
        this.actionType = ActionType.SPECIAL;
    }
    // 不带回调函数的构造函数
    public ElementalReactionAction(AbstractCreature owner, AbstractCreature player) {
        this(owner, player, null); // 调用带回调函数的构造函数，回调函数设为null
    }
    @Override
    public void update() {
        AbstractPower hydroPower = this.owner.getPower(ModHelper.makePath("HydroPower"));
        AbstractPower electroPower = this.owner.getPower(ModHelper.makePath("ElectroPower"));
        AbstractPower cryoPower = this.owner.getPower(ModHelper.makePath("CryoPower"));
        AbstractPower dentroPower = this.owner.getPower(ModHelper.makePath("DentroPower"));
        AbstractPower pyroPower = this.owner.getPower(ModHelper.makePath("PyroPower"));
        AbstractPower[] powers = {hydroPower, electroPower, cryoPower, dentroPower, pyroPower};

        AbstractPower firstPower = null;
        AbstractPower secondPower = null;

        for (AbstractPower power : powers) {
            if (power != null) {
                if (firstPower == null) {
                    firstPower = power;
                } else {
                    secondPower = power;
                    break;
                }
            }
        }
        String reactionType = null;

        if (firstPower != null && secondPower != null) {
            String firstID = firstPower.ID;
            String secondID = secondPower.ID;

            String reactionKey = firstID + "_" + secondID;
            if (firstID.compareTo(secondID) > 0) {
                reactionKey = secondID + "_" + firstID;
            }

            System.out.println("FirstPowerID: " + firstID);
            System.out.println("SecondPowerID: " + secondID);

            switch (reactionKey) {
                case "genshin:HydroPower_genshin:PyroPower":
                    System.out.println("——————————————————Vaporize——————————————————————");
                    VaporizeReaction(firstPower, secondPower);
                    triggerPyroReaction();
                    reactionType = "Vaporize";
                    break;
                case "genshin:DentroPower_genshin:PyroPower":
                    System.out.println("——————————————————Burning——————————————————————");
                    BurningReaction(firstPower, secondPower);
                    triggerPyroReaction();
                    reactionType = "Burning";
                    break;
                case "genshin:ElectroPower_genshin:PyroPower":
                    System.out.println("——————————————————Overloaded——————————————————————");
                    OverloadedReaction(firstPower, secondPower);
                    triggerPyroReaction();
                    reactionType = "Overloaded";
                    break;
                case "genshin:CryoPower_genshin:PyroPower":
                    System.out.println("——————————————————Melt——————————————————————");
                    MeltReaction(firstPower, secondPower);
                    triggerCryoReaction();
                    triggerPyroReaction();
                    reactionType = "Melt";
                    break;
                case "genshin:ElectroPower_genshin:HydroPower":
                    System.out.println("——————————————————Electro-Charged——————————————————————");
                    ElectroChargedReaction(firstPower, secondPower);
                    reactionType = "Electro-Charged";
                    break;
                case "genshin:CryoPower_genshin:HydroPower":
                    System.out.println("——————————————————Frozen——————————————————————");
                    FrozenReaction(firstPower, secondPower);
                    triggerCryoReaction();
                    reactionType = "Frozen";
                    break;
                case "genshin:DentroPower_genshin:HydroPower":
                    System.out.println("——————————————————Bloom——————————————————————");
                    BloomReaction(firstPower, secondPower);
                    reactionType = "Bloom";
                    break;
                case "genshin:CryoPower_genshin:ElectroPower":
                    System.out.println("——————————————————Superconduct——————————————————————");
                    SuperconductReaction(firstPower, secondPower);
                    triggerCryoReaction();
                    reactionType = "Superconduct";
                    break;
                case "genshin:DentroPower_genshin:ElectroPower":
                    System.out.println("——————————————————Catalyze——————————————————————");
                    CatalyzeReaction(firstPower, secondPower);
                    reactionType = "Catalyze";
                    break;
                case "genshin:CryoPower_genshin:DentroPower":
                    System.out.println("——————————————————Decay——————————————————————");
                    DecayReaction(firstPower, secondPower);
                    triggerCryoReaction();
                    reactionType = "Decay";
                    break;
                default:
                    System.out.println("Unknown reaction type");
                    reactionType = null;
                    break;
            }
        }
        // 执行回调函数
        if (callback != null) {
            callback.accept(reactionType);
        }

        this.isDone = true;
    }



    private void VaporizeReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        this.addToBot(new HydroReactionGainTempHPaction(this.owner, this.player,minValue));
        int vaporizeValue = GameActionManagerPatches.ExtraVariableField.vaporize.get(AbstractDungeon.actionManager);
        int vaporizeroundValue = GameActionManagerPatches.ExtraVariableField.vaporizeinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(vaporizeValue==0){ elementreactiontypeValue++;}
        if(vaporizeroundValue==0){ elementreactiontypeinroundValue++;}
        vaporizeValue += minValue;
        vaporizeroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.vaporizeinround.set(AbstractDungeon.actionManager, vaporizeroundValue);
        GameActionManagerPatches.ExtraVariableField.vaporize.set(AbstractDungeon.actionManager, vaporizeValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        int damage =applyElementLocking(this.owner, minValue*2);

        //firstHydro sencondPyro

        AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));

        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));
        }



    private void  BurningReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int burningValue = GameActionManagerPatches.ExtraVariableField.burning.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(burningValue==0){ elementreactiontypeValue++;}
        burningValue += minValue;
        GameActionManagerPatches.ExtraVariableField.burning.set(AbstractDungeon.actionManager, burningValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int burningroundValue = GameActionManagerPatches.ExtraVariableField.burninginround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(burningroundValue==0){ elementreactiontypeinroundValue++;}
        burningroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.burninginround.set(AbstractDungeon.actionManager, burningroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstDendro sencondPyro
        this.addToTop(new ApplyPowerAction(this.player, this.player, new LoseStrengthPower(this.player, minValue), minValue));
        this.addToTop(new ApplyPowerAction(this.player, this.player, new StrengthPower(this.player, minValue), minValue));

        //firstDentro secondPyro
        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void  OverloadedReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int overloadedValue = GameActionManagerPatches.ExtraVariableField.overloaded.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(overloadedValue==0){ elementreactiontypeValue++;}
        overloadedValue += minValue;
        GameActionManagerPatches.ExtraVariableField.overloaded.set(AbstractDungeon.actionManager, overloadedValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int overloadedroundValue = GameActionManagerPatches.ExtraVariableField.overloadedinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(overloadedroundValue==0){ elementreactiontypeinroundValue++;}
        overloadedroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.overloadedinround.set(AbstractDungeon.actionManager, overloadedroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstElectro secondPyro

        int[] damageArray = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        for (int i = 0; i < damageArray.length; i++) {
            AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            damageArray[i] = applyElementLocking(mo, minValue); // Set the damage for each enemy
        }

        this.addToTop(new MakeTempCardInDiscardAction(new Burn(), 1));
        this.addToTop(new ElectroReactionGainPowerAction(this.owner, this.player,minValue));
        this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damageArray, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
        this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damageArray, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));


        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));
    }

    private void  MeltReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int meltValue = GameActionManagerPatches.ExtraVariableField.melt.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(meltValue==0){ elementreactiontypeValue++;}
        meltValue += minValue;
        GameActionManagerPatches.ExtraVariableField.melt.set(AbstractDungeon.actionManager, meltValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int meltroundValue = GameActionManagerPatches.ExtraVariableField.meltinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(meltroundValue==0){ elementreactiontypeinroundValue++;}
        meltroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.meltinround.set(AbstractDungeon.actionManager, meltroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstCryo secondPyro
        int damage =applyElementLocking(this.owner, minValue*2);

        AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, new DamageInfo(this.owner, damage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));


        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void  ElectroChargedReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int electroChargedValue = GameActionManagerPatches.ExtraVariableField.electroCharged.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(electroChargedValue==0){ elementreactiontypeValue++;}
        electroChargedValue += minValue;
        GameActionManagerPatches.ExtraVariableField.electroCharged.set(AbstractDungeon.actionManager, electroChargedValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int electroChargedroundValue = GameActionManagerPatches.ExtraVariableField.electroChargedinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(electroChargedroundValue==0){ elementreactiontypeinroundValue++;}
        electroChargedroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.electroChargedinround.set(AbstractDungeon.actionManager, electroChargedroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstElectro secondHydro

        int[] damageArray = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        for (int i = 0; i < damageArray.length; i++) {
            AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            damageArray[i] = applyElementLocking(mo, minValue);
        }

        this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damageArray, DamageInfo.DamageType.THORNS, AttackEffect.FIRE));
        this.addToTop(new HydroReactionGainTempHPaction(this.owner, this.player,minValue));
        this.addToTop(new ElectroReactionGainPowerAction(this.owner, this.player,minValue));

        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void  FrozenReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int frozenValue = GameActionManagerPatches.ExtraVariableField.frozen.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(frozenValue==0){ elementreactiontypeValue++;}
        frozenValue += minValue;
        GameActionManagerPatches.ExtraVariableField.frozen.set(AbstractDungeon.actionManager, frozenValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int frozenroundValue = GameActionManagerPatches.ExtraVariableField.frozeninround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(frozenroundValue==0){ elementreactiontypeinroundValue++;}
        frozenroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.frozeninround.set(AbstractDungeon.actionManager, frozenroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstHydro secondPyro

        this.addToTop(new HydroReactionGainTempHPaction(this.owner, this.player,minValue));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.player, new WeakPower(this.owner, minValue, false), minValue));


        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void BloomReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int bloomValue = GameActionManagerPatches.ExtraVariableField.bloom.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(bloomValue==0){ elementreactiontypeValue++;}
        bloomValue += minValue;
        GameActionManagerPatches.ExtraVariableField.bloom.set(AbstractDungeon.actionManager, bloomValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int bloomroundValue = GameActionManagerPatches.ExtraVariableField.bloominround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(bloomroundValue==0){ elementreactiontypeinroundValue++;}
        bloomroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.bloominround.set(AbstractDungeon.actionManager, bloomroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstHydro secondDentro

        this.addToTop(new HydroReactionGainTempHPaction(this.owner, this.player,minValue));
        AbstractDungeon.actionManager.addToTop(new DendrogranumGenerateAction(minValue));

        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));


    }

    private void  SuperconductReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int superconductValue = GameActionManagerPatches.ExtraVariableField.superconduct.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(superconductValue==0){ elementreactiontypeValue++;}
        superconductValue += minValue;
        GameActionManagerPatches.ExtraVariableField.superconduct.set(AbstractDungeon.actionManager, superconductValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int superconductroundValue = GameActionManagerPatches.ExtraVariableField.superconductinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(superconductroundValue==0){ elementreactiontypeinroundValue++;}
        superconductroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.superconductinround.set(AbstractDungeon.actionManager, superconductroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstCyro secondElectro

        this.addToTop(new ElectroReactionGainPowerAction(this.owner, this.player,minValue));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.player, new VulnerablePower(this.owner, minValue, false), minValue));


        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void  CatalyzeReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        int minValue = Math.min(firstAmount, secondAmount);
        int catalyzeValue = GameActionManagerPatches.ExtraVariableField.catalyze.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(catalyzeValue==0){ elementreactiontypeValue++;}
        catalyzeValue += minValue;
        GameActionManagerPatches.ExtraVariableField.catalyze.set(AbstractDungeon.actionManager, catalyzeValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int catalyzeroundValue = GameActionManagerPatches.ExtraVariableField.catalyzeinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(catalyzeroundValue==0){ elementreactiontypeinroundValue++;}
        catalyzeroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.catalyzeinround.set(AbstractDungeon.actionManager, catalyzeroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstDentro secondElectro

        this.addToTop(new ElectroReactionGainPowerAction(this.owner, this.player,minValue));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.player, this.player, new VigorPower(this.player, minValue), minValue));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.player, this.player, new genshinTenacityPower(this.player, minValue), minValue));

        if (firstAmount > secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));

    }

    private void  DecayReaction(AbstractPower firstPower, AbstractPower secondPower) {
        int firstAmount = firstPower.amount;
        int secondAmount = secondPower.amount;
        System.out.println("FirstPowerAmount: " + firstAmount);
        System.out.println("SecondPowerAmount: " + secondAmount);
        int minValue = Math.min(firstAmount, secondAmount);
        int decayValue = GameActionManagerPatches.ExtraVariableField.decay.get(AbstractDungeon.actionManager);
        int elementreactiontypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        if(decayValue==0){ elementreactiontypeValue++;}
        decayValue += minValue;
        GameActionManagerPatches.ExtraVariableField.decay.set(AbstractDungeon.actionManager, decayValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioningametype.set(AbstractDungeon.actionManager,elementreactiontypeValue);

        int decayroundValue = GameActionManagerPatches.ExtraVariableField.decayinround.get(AbstractDungeon.actionManager);
        int elementreactiontypeinroundValue = GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.get(AbstractDungeon.actionManager);
        if(decayroundValue==0){ elementreactiontypeinroundValue++;}
        decayroundValue += minValue;
        GameActionManagerPatches.ExtraVariableField.decayinround.set(AbstractDungeon.actionManager, decayroundValue);
        GameActionManagerPatches.ExtraVariableField.elementreactioninroundtype.set(AbstractDungeon.actionManager,elementreactiontypeinroundValue);

        //firstCryo secondDentro

        this.addToTop(new ApplyPowerAction(this.owner, this.player, new StrengthPower(this.owner, -minValue), -minValue, true, AttackEffect.NONE));
        if (!this.owner.hasPower("Artifact")) {
            this.addToTop(new ApplyPowerAction(this.owner, this.player, new GainStrengthPower(this.owner, minValue), minValue, true, AttackEffect.NONE));
        }

        if (firstAmount > secondAmount) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        } else if (firstAmount < secondAmount) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
        } else {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, firstPower.ID));
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, secondPower.ID));
        }
        this.addToTop(new ReducePowerAction(this.owner, this.owner, firstPower.ID, minValue));
        this.addToTop(new ReducePowerAction(this.owner, this.owner, secondPower.ID, minValue));


    }

    public void triggerCryoReaction() {
        // 你可以在这里定义具体的 Superconduct 反应逻辑
        // 比如，触发某个效果，然后将特定卡牌移回手牌

        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            // 这里假设你想要把名为 "特定卡牌" 的卡牌移回手牌
            if (card.cardID.equals(ModHelper.makePath("SpringSpiritSummoning"))) {
                this.addToBot(new DiscardToHandAction(card));
            }
        }
    }
    public void triggerPyroReaction() {
        // 你可以在这里定义具体的 Superconduct 反应逻辑
        // 比如，触发某个效果，然后将特定卡牌移回手牌

        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            // 这里假设你想要把名为 "特定卡牌" 的卡牌移回手牌
            if (card.cardID.equals(ModHelper.makePath("RyuukinSaxifrage"))) {
                this.addToBot(new DiscardToHandAction(card));
            }
        }
    }
    public static int applyElementLocking(AbstractCreature target, int dmg) {
        int retVal = dmg;
        if (target.hasPower(ModHelper.makePath("ElemetalLockingPower"))) {
            retVal = (int)((float)retVal * 1.5F);
        }
        return retVal;
    }
}