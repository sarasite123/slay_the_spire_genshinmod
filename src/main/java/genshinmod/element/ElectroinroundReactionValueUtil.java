package genshinmod.element;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import genshinmod.patchs.GameActionManagerPatches;



public class ElectroinroundReactionValueUtil {

    public static int calculateElectroValue() {
        int electroChargedroundValue = GameActionManagerPatches.ExtraVariableField.electroChargedinround.get(AbstractDungeon.actionManager);
        int overloadedinroundValue = GameActionManagerPatches.ExtraVariableField.overloadedinround.get(AbstractDungeon.actionManager);
        int superconductinroundValue = GameActionManagerPatches.ExtraVariableField.superconductinround.get(AbstractDungeon.actionManager);
        int catalyzeinroundValue = GameActionManagerPatches.ExtraVariableField.catalyzeinround.get(AbstractDungeon.actionManager);
        int crystallizationelectroinroundValue = GameActionManagerPatches.ExtraVariableField.crystallizationelectroinround.get(AbstractDungeon.actionManager);
        int diffusionelectroinroundValue = GameActionManagerPatches.ExtraVariableField.diffusionelectroinround.get(AbstractDungeon.actionManager);

        int electroValue = electroChargedroundValue + overloadedinroundValue + superconductinroundValue + catalyzeinroundValue + crystallizationelectroinroundValue + diffusionelectroinroundValue;
        return electroValue;
    }
}
