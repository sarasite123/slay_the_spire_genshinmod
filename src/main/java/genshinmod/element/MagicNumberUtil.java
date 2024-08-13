package genshinmod.element;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinNextTurnDoubleElementPower;

public class MagicNumberUtil {

    public static int calculateMagicNumber(int truebase) {
        int calculatedMagicNumber = truebase;
        AbstractPower power = AbstractDungeon.player.getPower(ModHelper.makePath("ElementalMasteryPower"));
        AbstractPower doubleElementPower = AbstractDungeon.player.getPower(genshinNextTurnDoubleElementPower.POWER_ID);

        if (power != null) {
            calculatedMagicNumber += power.amount;
            if (calculatedMagicNumber < 0) {
                calculatedMagicNumber = 0;
            }
        }

        if (doubleElementPower != null && doubleElementPower.amount > 0) {
            calculatedMagicNumber *= 2;
        }

        return calculatedMagicNumber;
    }
}
