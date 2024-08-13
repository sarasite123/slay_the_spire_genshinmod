package genshinmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import genshinmod.actions.ApplyElementalPowerAction;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinHydroPower;
import genshinmod.powers.genshinPyroPower;

import java.util.Iterator;

// 继承CustomRelic
public class Hydroculus extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("Hydroculus");
    // 图片路径
    private static final String IMG_PATH = "GenshinModResources/img/relics/Hydroculus.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public Hydroculus() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart() {
        this.flash();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var1.next();
            this.addToBot(new RelicAboveCreatureAction(mo, this));
            this.addToBot(new ApplyElementalPowerAction(mo,AbstractDungeon.player,ModHelper.makePath("HydroPower"),3));
        }
    }

    public AbstractRelic makeCopy() {
        return new Hydroculus();
    }
}
