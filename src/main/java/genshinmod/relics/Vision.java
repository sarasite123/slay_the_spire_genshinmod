package genshinmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import genshinmod.patchs.GameActionManagerPatches;
import genshinmod.helper.ModHelper;

// 继承CustomRelic
public class Vision extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("Vision");
    // 图片路径
    private static final String IMG_PATH = "GenshinModResources/img/relics/Vision.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;



    public Vision() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    // 检查elementreactiontypeValue的变化
    @Override
    public void update() {
        super.update();
        int currentElementReactionTypeValue = GameActionManagerPatches.ExtraVariableField.elementreactioningametype.get(AbstractDungeon.actionManager);
        boolean firstelementreactioningameValue =GameActionManagerPatches.ExtraVariableField.firstelementreactioningame.get(AbstractDungeon.actionManager);

        // 如果值从0变成1，则执行能量增加的动作
        if ( !firstelementreactioningameValue && currentElementReactionTypeValue == 1) {
            this.addToBot(new GainEnergyAction(1));
            firstelementreactioningameValue=true;
        }

        GameActionManagerPatches.ExtraVariableField.firstelementreactioningame.set(AbstractDungeon.actionManager,firstelementreactioningameValue);

    }

    public AbstractRelic makeCopy() {
        return new Vision();
    }
}
