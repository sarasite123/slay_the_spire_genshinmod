package genshinmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinViridescentVenererPower;

public class ViridescentVenerer extends CustomRelic {
    public static final String ID = ModHelper.makePath("ViridescentVenerer");
    private static final String IMG_PATH = "GenshinModResources/img/relics/ViridescentVenerer.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public ViridescentVenerer() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        super.atBattleStart();
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hb == null) {
                System.out.println("Player hitbox (hb) is null in atBattleStart");
            } else {
                System.out.println("Applying ViridescentVenererPower to player");
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new genshinViridescentVenererPower(AbstractDungeon.player, 1), 1));
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new ViridescentVenerer();
    }
}
