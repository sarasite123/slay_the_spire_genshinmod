package genshinmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import genshinmod.helper.ModHelper;
import genshinmod.patchs.GameActionManagerPatches;

import java.util.Iterator;
import java.util.Objects;

public class genshinDeadHealingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("DeadHealingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public genshinDeadHealingPower(AbstractCreature owner, int HealingAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = HealingAmt;
        this.updateDescription();
        this.loadRegion("sporeCloud");
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public void onDeath() {
        if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
            CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");

            // 遍历当前房间中的所有怪物
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDying && !m.isEscaping && Objects.equals(m.id, ModHelper.makePath("Apep"))) {
                    AbstractDungeon.actionManager.addToBottom(new HealAction(m, this.owner, this.amount));
                }
            }
        }
    }


}
