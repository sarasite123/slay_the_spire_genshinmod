package genshinmod.relics;

import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import genshinmod.actions.ApplyElementToRandomEnemyAction;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;
import genshinmod.powers.*;

// 继承CustomRelic
public class DreamSolvent extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("DreamSolvent");
    // 图片路径
    private static final String IMG_PATH = "GenshinModResources/img/relics/DreamSolvent.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;


    public DreamSolvent() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onMonsterDeath(AbstractMonster m) {
        AbstractPower[] elementPowers = {
                m.getPower(ModHelper.makePath("HydroPower")),
                m.getPower(ModHelper.makePath("ElectroPower")),
                m.getPower(ModHelper.makePath("CryoPower")),
                m.getPower(ModHelper.makePath("DentroPower")),
                m.getPower(ModHelper.makePath("PyroPower"))
        };
        AbstractPower activePower = null;

        for (AbstractPower power : elementPowers) {
            if (power != null && power.amount>0) {
                activePower = power;
                break;
            }
        }

        if (activePower!=null) {
            int amount = activePower.amount;
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(m, this));
                switch (activePower.ID) {
                    case "genshin:HydroPower":
                        this.addToBot(new ApplyElementToRandomEnemyAction(AbstractDungeon.player, new genshinHydroPower((AbstractCreature)null, AbstractDungeon.player, amount), amount, true));
                        break;
                    case "genshin:ElectroPower":
                        this.addToBot(new ApplyElementToRandomEnemyAction(AbstractDungeon.player, new genshinElectroPower((AbstractCreature)null, AbstractDungeon.player, amount), amount, true));
                        break;
                    case "genshin:CryoPower":
                        this.addToBot(new ApplyElementToRandomEnemyAction(AbstractDungeon.player, new genshinCryoPower((AbstractCreature)null, AbstractDungeon.player, amount), amount, true));
                        break;
                    case "genshin:DentroPower":
                        this.addToBot(new ApplyElementToRandomEnemyAction(AbstractDungeon.player, new genshinDentroPower((AbstractCreature)null, AbstractDungeon.player, amount), amount, true));
                        break;
                    case "genshin:PyroPower":
                        this.addToBot(new ApplyElementToRandomEnemyAction(AbstractDungeon.player, new genshinPyroPower((AbstractCreature)null, AbstractDungeon.player, amount), amount, true));
                        break;
                }


            }
        }

    }

    public AbstractRelic makeCopy() {
        return new DreamSolvent();
    }
}
