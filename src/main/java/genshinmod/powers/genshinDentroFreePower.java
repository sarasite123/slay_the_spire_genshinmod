package genshinmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;


public class genshinDentroFreePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("DentroFreePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AbstractCreature player; // Add this line to store the player

    public genshinDentroFreePower(AbstractCreature m, AbstractCreature p) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.player = p; // Store the player
        this.type = PowerType.BUFF;
        this.updateDescription();
        // 添加一大一小两张能力图
        String path128 = "GenshinModResources/img/powers/84/Dentro84.png";
        String path48 = "GenshinModResources/img/powers/32/Dentro32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

}
