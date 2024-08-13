package genshinmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

// 继承CustomRelic
public class WitchsHeartFlames extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("WitchsHeartFlames");
    // 图片路径
    private static final String IMG_PATH = "GenshinModResources/img/relics/WitchsHeartFlames.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    private int lastTriggeredValue = 0;

    private int pyroreactionValue = 0;

    public WitchsHeartFlames() {
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
        int vaporizeValue = GameActionManagerPatches.ExtraVariableField.vaporizeinround.get(AbstractDungeon.actionManager);
        int burningValue = GameActionManagerPatches.ExtraVariableField.burninginround.get(AbstractDungeon.actionManager);
        int overloadedValue = GameActionManagerPatches.ExtraVariableField.overloadedinround.get(AbstractDungeon.actionManager);
        int meltValue = GameActionManagerPatches.ExtraVariableField.meltinround.get(AbstractDungeon.actionManager);
        int diffusionValue = GameActionManagerPatches.ExtraVariableField.diffusionpyroinround.get(AbstractDungeon.actionManager);
        int crystallizationValue = GameActionManagerPatches.ExtraVariableField.crystallizationpyroinround.get(AbstractDungeon.actionManager);

        pyroreactionValue = vaporizeValue + burningValue + overloadedValue + meltValue+diffusionValue+crystallizationValue;
        this.counter=pyroreactionValue % 10;

        // Check if pyroreactionValue has reached the next multiple of 10
        if (pyroreactionValue >= lastTriggeredValue + 10) {
            lastTriggeredValue = pyroreactionValue - (pyroreactionValue % 10); // Update lastTriggeredValue to the nearest multiple of 10

            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
    }

    public void atTurnStart(){
        lastTriggeredValue = 0;
        pyroreactionValue = 0;
    }
    public void onVictory() {
        lastTriggeredValue = 0;
        pyroreactionValue = 0;
    }

    public AbstractRelic makeCopy() {
        return new WitchsHeartFlames();
    }
}
