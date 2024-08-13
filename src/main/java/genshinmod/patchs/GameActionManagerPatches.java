package genshinmod.patchs;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.evacipated.cardcrawl.modthespire.lib.*;

public class GameActionManagerPatches {

    @SpirePatch(
            clz = GameActionManager.class,
            method = SpirePatch.CLASS
    )
    public static class ExtraVariableField {
        public static SpireField<Integer> elctrogainenergy = new SpireField<>(() -> 0);
        public static SpireField<Integer> vaporize = new SpireField<>(() -> 0);
        public static SpireField<Integer> burning = new SpireField<>(() -> 0);
        public static SpireField<Integer> overloaded = new SpireField<>(() -> 0);
        public static SpireField<Integer> melt = new SpireField<>(() -> 0);
        public static SpireField<Integer> electroCharged = new SpireField<>(() -> 0);
        public static SpireField<Integer> frozen = new SpireField<>(() -> 0);
        public static SpireField<Integer> bloom = new SpireField<>(() -> 0);
        public static SpireField<Integer> superconduct = new SpireField<>(() -> 0);
        public static SpireField<Integer> catalyze = new SpireField<>(() -> 0);
        public static SpireField<Integer> decay = new SpireField<>(() -> 0);
        public static SpireField<Integer> crystallization = new SpireField<>(() -> 0);
        public static SpireField<Integer> diffusion = new SpireField<>(() -> 0);
        public static SpireField<Integer> vaporizeinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> burninginround = new SpireField<>(() -> 0);
        public static SpireField<Integer> overloadedinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> meltinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> electroChargedinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> frozeninround = new SpireField<>(() -> 0);
        public static SpireField<Integer> bloominround = new SpireField<>(() -> 0);
        public static SpireField<Integer> superconductinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> catalyzeinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> decayinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> crystallizationinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> diffusioninround = new SpireField<>(() -> 0);
        public static SpireField<Integer> elementreactioningametype = new SpireField<>(() -> 0);
        public static SpireField<Integer> elementreactioninroundtype = new SpireField<>(() -> 0);
        public static SpireField<Integer> anemogainaddcard = new SpireField<>(() -> 0);
        public static SpireField<Integer> maxstrength = new SpireField<>(() -> 0);
        public static SpireField<Boolean> firstelementreactioninround = new SpireField<>(() -> false);
        public static SpireField<Boolean> firstelementreactioningame = new SpireField<>(() -> false);

        public static SpireField<Integer> diffusionpyroinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> diffusioncryoinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> diffusionelectroinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> crystallizationpyroinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> crystallizationcryoinround = new SpireField<>(() -> 0);
        public static SpireField<Integer> crystallizationelectroinround = new SpireField<>(() -> 0);
    }

    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class GetNextActionPatch {

        @SpireInsertPatch(
                rloc = 244, // Adjust this according to the relative line number in getNextAction method
                localvars = {"this"}
        )
        public static void insertPatch(GameActionManager _instance) {
            // Additional code if needed
            ExtraVariableField.elementreactioninroundtype.set(_instance, 0);
            ExtraVariableField.vaporizeinround.set(_instance, 0);
            ExtraVariableField.burninginround.set(_instance, 0);
            ExtraVariableField.overloadedinround.set(_instance, 0);
            ExtraVariableField.meltinround.set(_instance, 0);
            ExtraVariableField.electroChargedinround.set(_instance, 0);
            ExtraVariableField.frozeninround.set(_instance, 0);
            ExtraVariableField.bloominround.set(_instance, 0);
            ExtraVariableField.superconductinround.set(_instance, 0);
            ExtraVariableField.catalyzeinround.set(_instance, 0);
            ExtraVariableField.decayinround.set(_instance, 0);
            ExtraVariableField.crystallizationinround.set(_instance, 0);
            ExtraVariableField.diffusioninround.set(_instance, 0);

            ExtraVariableField.diffusionpyroinround.set(_instance, 0);
            ExtraVariableField.diffusioncryoinround.set(_instance, 0);
            ExtraVariableField.diffusionelectroinround.set(_instance, 0);
            ExtraVariableField.crystallizationpyroinround.set(_instance, 0);
            ExtraVariableField.crystallizationcryoinround.set(_instance, 0);
            ExtraVariableField.crystallizationelectroinround.set(_instance, 0);
        }
    }

    @SpirePatch(
            clz = GameActionManager.class,
            method = "clear"
    )
    public static class ClearPatch {
        @SpirePostfixPatch
        public static void resetAllFields(GameActionManager _instance) {
            ExtraVariableField.elctrogainenergy.set(_instance, 0);
            ExtraVariableField.vaporize.set(_instance, 0);
            ExtraVariableField.burning.set(_instance, 0);
            ExtraVariableField.overloaded.set(_instance, 0);
            ExtraVariableField.melt.set(_instance, 0);
            ExtraVariableField.electroCharged.set(_instance, 0);
            ExtraVariableField.frozen.set(_instance, 0);
            ExtraVariableField.bloom.set(_instance, 0);
            ExtraVariableField.superconduct.set(_instance, 0);
            ExtraVariableField.catalyze.set(_instance, 0);
            ExtraVariableField.decay.set(_instance, 0);
            ExtraVariableField.crystallization.set(_instance, 0);
            ExtraVariableField.diffusion.set(_instance, 0);
            ExtraVariableField.vaporizeinround.set(_instance, 0);
            ExtraVariableField.burninginround.set(_instance, 0);
            ExtraVariableField.overloadedinround.set(_instance, 0);
            ExtraVariableField.meltinround.set(_instance, 0);
            ExtraVariableField.electroChargedinround.set(_instance, 0);
            ExtraVariableField.frozeninround.set(_instance, 0);
            ExtraVariableField.bloominround.set(_instance, 0);
            ExtraVariableField.superconductinround.set(_instance, 0);
            ExtraVariableField.catalyzeinround.set(_instance, 0);
            ExtraVariableField.decayinround.set(_instance, 0);
            ExtraVariableField.crystallizationinround.set(_instance, 0);
            ExtraVariableField.diffusioninround.set(_instance, 0);
            ExtraVariableField.elementreactioningametype.set(_instance, 0);
            ExtraVariableField.elementreactioninroundtype.set(_instance, 0);
            ExtraVariableField.anemogainaddcard.set(_instance, 0);
            ExtraVariableField.maxstrength.set(_instance, 0);
            ExtraVariableField.firstelementreactioninround.set(_instance, false);
            ExtraVariableField.firstelementreactioningame.set(_instance, false);

            ExtraVariableField.diffusionpyroinround.set(_instance, 0);
            ExtraVariableField.diffusioncryoinround.set(_instance, 0);
            ExtraVariableField.diffusionelectroinround.set(_instance, 0);
            ExtraVariableField.crystallizationpyroinround.set(_instance, 0);
            ExtraVariableField.crystallizationcryoinround.set(_instance, 0);
            ExtraVariableField.crystallizationelectroinround.set(_instance, 0);
        }
    }
}
