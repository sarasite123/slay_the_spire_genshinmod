package genshinmod.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import genshinmod.actions.ElementalReactionAction;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinElementalMasteryPower;
import genshinmod.powers.genshinPyroPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionEffect;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionSize;
import java.lang.reflect.Field;
import java.util.Iterator;


public class FlamingEssentialOil extends BasePotion {
    public static final String ID = ModHelper.makePath("FlamingEssentialOil");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);;
    private static final String NAME = potionStrings.NAME;
    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;
    private static final PotionSize SIZE = PotionSize.S;
    private static final PotionColor PotionColor =  AbstractPotion.PotionColor.STRENGTH;
    private static final String IMG_PATH = "GenshinModResources/img/potions/64/FlamingEssentialOil.png";
    private static final String IMG_OUTLINE_PATH = "GenshinModResources/img/potions/64/Outline.png";
    private static final String BlankImage = "GenshinModResources/img/potions/64/BlankImage.png";
    private static final Color LIQUID_COLOR;
    private static final Color HYBRID_COLOR;
    private static final Color SPOTS_COLOR;

    public FlamingEssentialOil() {
        super(ID, 10, PotionRarity.COMMON, BasePotion.customShape, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        this.labOutlineColor = Settings.PURPLE_RELIC_COLOR;
        this.isThrown = true;

        this.setContainerImg(new Texture(IMG_PATH));
        this.setLiquidImg(new Texture(BlankImage));
        this.setOutlineImg(new Texture(IMG_OUTLINE_PATH));
        this.labOutlineColor = new Color(1.0F, 1.0F, 0.7254902F, 1.0F);
        this.isThrown = true;
    }

    public String getDescription() {
        return this.potionStrings.DESCRIPTIONS[0];
    }

    public void use(AbstractCreature target) {
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (!m.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                this.addToBot(new ApplyPowerAction(m,AbstractDungeon.player,new genshinPyroPower(m,AbstractDungeon.player,this.potency),this.potency));
                this.addToBot(new ElementalReactionAction(m, AbstractDungeon.player));
            }
        }

        this.addToBot(new WaitAction(0.5F));
    }

    public void addAdditionalTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
        String title = BaseMod.getKeywordTitle("genshin:火");
        String description = BaseMod.getKeywordDescription("genshin:火");
        this.tips.add(new PowerTip(title, description));
    }

    public int getPotency(int ascensionLevel) {
        return 10;
    }

    public AbstractPotion makeCopy() { return new FlamingEssentialOil(); }

    static {
        LIQUID_COLOR = Color.CLEAR;
        HYBRID_COLOR = null;
        SPOTS_COLOR = null;
    }
}
