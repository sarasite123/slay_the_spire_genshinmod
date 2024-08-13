package genshinmod.potions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.lang.reflect.Field;

public abstract class BasePotion extends AbstractPotion {
    private static final Field containerImg;
    private static final Field outlineImg;
    private static final Field liquidImg;
    private static final Field hybridImg;
    private static final Field spotsImg;
    public PotionStrings potionStrings;
    public int basePotency;
    public AbstractPlayer.PlayerClass playerClass = null;
    public static AbstractPotion.PotionSize customShape;

    public BasePotion(String id, int potency, AbstractPotion.PotionRarity rarity, AbstractPotion.PotionSize shape, Color liquidColor, Color hybridColor, Color spotsColor) {
        super("", id, rarity, shape, PotionEffect.NONE, liquidColor, hybridColor, spotsColor);
        this.basePotency = potency;
        this.checkColors();
        this.initializeData();
    }

    public BasePotion(String id, int potency, AbstractPotion.PotionRarity rarity, AbstractPotion.PotionSize size, AbstractPotion.PotionColor color) {
        super("", id, rarity, size, color);
        this.basePotency = potency;
        this.checkColors();
        this.initializeData();
    }

    protected void checkColors() {
        if (this.hybridColor != null && this.getHybridImg() == null) {
            throw new RuntimeException("Potion " + this.ID + " has hybridColor but no hybridImg; if this is intentional, override checkColors. Otherwise, set hybridColor to null or provide a Texture with setHybridImg.");
        } else if (this.spotsColor != null && this.getSpotsImg() == null) {
            throw new RuntimeException("Potion " + this.ID + " has spotsColor but no spotsImg; if this is intentional, override checkColors. Otherwise, set spotsColor to null or provide a Texture with setSpotsImg.");
        }
    }

    public void initializeData() {
        this.potency = this.getPotency();
        this.potionStrings = CardCrawlGame.languagePack.getPotionString(this.ID);
        this.name = this.potionStrings.NAME;
        this.description = this.getDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.addAdditionalTips();
    }

    public int getPotency(int ascension) {
        return this.basePotency;
    }

    public abstract String getDescription();

    public void addAdditionalTips() {
    }

    public Texture getOutlineImg() {
        try {
            return (Texture)outlineImg.get(this);
        } catch (IllegalAccessException var2) {
            IllegalAccessException e = var2;
            throw new RuntimeException(e);
        }
    }

    public Texture getContainerImg() {
        try {
            return (Texture)containerImg.get(this);
        } catch (IllegalAccessException var2) {
            IllegalAccessException e = var2;
            throw new RuntimeException(e);
        }
    }

    public Texture getLiquidImg() {
        try {
            return (Texture)liquidImg.get(this);
        } catch (IllegalAccessException var2) {
            IllegalAccessException e = var2;
            throw new RuntimeException(e);
        }
    }

    public Texture getHybridImg() {
        try {
            return (Texture)hybridImg.get(this);
        } catch (IllegalAccessException var2) {
            IllegalAccessException e = var2;
            throw new RuntimeException(e);
        }
    }

    public Texture getSpotsImg() {
        try {
            return (Texture)spotsImg.get(this);
        } catch (IllegalAccessException var2) {
            IllegalAccessException e = var2;
            throw new RuntimeException(e);
        }
    }

    public void setOutlineImg(Texture t) {
        try {
            outlineImg.set(this, t);
        } catch (IllegalAccessException var3) {
            IllegalAccessException e = var3;
            throw new RuntimeException(e);
        }
    }

    public void setContainerImg(Texture t) {
        try {
            containerImg.set(this, t);
        } catch (IllegalAccessException var3) {
            IllegalAccessException e = var3;
            throw new RuntimeException(e);
        }
    }

    public void setLiquidImg(Texture t) {
        try {
            liquidImg.set(this, t);
        } catch (IllegalAccessException var3) {
            IllegalAccessException e = var3;
            throw new RuntimeException(e);
        }
    }

    public void setHybridImg(Texture t) {
        try {
            hybridImg.set(this, t);
        } catch (IllegalAccessException var3) {
            IllegalAccessException e = var3;
            throw new RuntimeException(e);
        }
    }

    public void setSpotsImg(Texture t) {
        try {
            spotsImg.set(this, t);
        } catch (IllegalAccessException var3) {
            IllegalAccessException e = var3;
            throw new RuntimeException(e);
        }
    }

    public AbstractPotion makeCopy() {
        try {
            return (AbstractPotion)this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException var2) {
            throw new RuntimeException("Failed to auto-generate makeCopy for potion: " + this.ID);
        }
    }

    static {
        try {
            containerImg = AbstractPotion.class.getDeclaredField("containerImg");
            outlineImg = AbstractPotion.class.getDeclaredField("outlineImg");
            liquidImg = AbstractPotion.class.getDeclaredField("liquidImg");
            hybridImg = AbstractPotion.class.getDeclaredField("hybridImg");
            spotsImg = AbstractPotion.class.getDeclaredField("spotsImg");
            containerImg.setAccessible(true);
            outlineImg.setAccessible(true);
            liquidImg.setAccessible(true);
            hybridImg.setAccessible(true);
            spotsImg.setAccessible(true);
        } catch (NoSuchFieldException var1) {
            NoSuchFieldException e = var1;
            throw new RuntimeException("Failed to access potion image fields.", e);
        }

        customShape = PotionSize.EYE;
    }
}
