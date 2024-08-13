package genshinmod.variable;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import genshinmod.cards.AdeptusArtHeraldofFrost;
import genshinmod.cards.GlacialIllumination;
import genshinmod.cards.YakanEvocationSesshouSakura;

public class MyM2Variable extends DynamicVariable {

    @Override
    public String key() {
        return "M2";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof GlacialIllumination) {
            return ((GlacialIllumination) card).isM2Modified;
        }
        if (card instanceof YakanEvocationSesshouSakura) {
            return ((YakanEvocationSesshouSakura) card).isM2Modified;
        }
        if (card instanceof AdeptusArtHeraldofFrost) {
            return ((AdeptusArtHeraldofFrost) card).isM2Modified;
        }
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof GlacialIllumination) {
            ((GlacialIllumination) card).isM2Modified = v;
        }else if (card instanceof YakanEvocationSesshouSakura) {
            ((YakanEvocationSesshouSakura) card).isM2Modified = v;
        }else if (card instanceof AdeptusArtHeraldofFrost) {
            ((AdeptusArtHeraldofFrost) card).isM2Modified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof GlacialIllumination) {
            return ((GlacialIllumination) card).M2;
        }else if (card instanceof YakanEvocationSesshouSakura) {
            return ((YakanEvocationSesshouSakura) card).M2;
        }else if (card instanceof AdeptusArtHeraldofFrost) {
            return ((AdeptusArtHeraldofFrost) card).M2;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof GlacialIllumination) {
            return ((GlacialIllumination) card).baseM2;
        }else  if (card instanceof YakanEvocationSesshouSakura) {
            return ((YakanEvocationSesshouSakura) card).baseM2;
        }else  if (card instanceof AdeptusArtHeraldofFrost) {
            return ((AdeptusArtHeraldofFrost) card).baseM2;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof GlacialIllumination) {
            return ((GlacialIllumination) card).upgradedM2;
        }else if (card instanceof YakanEvocationSesshouSakura) {
            return ((YakanEvocationSesshouSakura) card).upgradedM2;
        }else if (card instanceof AdeptusArtHeraldofFrost) {
            return ((AdeptusArtHeraldofFrost) card).upgradedM2;
        }
        return false;
    }
}

