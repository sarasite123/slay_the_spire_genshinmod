package genshinmod.monsters.beyond;

import basemod.devcommands.power.Power;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDeadHealingPower;
import genshinmod.powers.genshinDentroFreePower;
import genshinmod.powers.genshinExplosivePower;

import java.util.Iterator;

//养育的增殖生命体
public class ProvenderProliferatingOrganism extends AbstractMonster {
    public static final String ID = ModHelper.makePath("ProvenderProliferatingOrganism");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String IMG_PATH = "GenshinModResources/img/monsters/beyond/ProvenderProliferatingOrganism1.png";;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 25;
    private static final int STAB_DMG = 10;
    private int HealAmt = 10;
    public boolean firstMove = true;
    private int strAmt;

    public ProvenderProliferatingOrganism(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(20, 25), 0.0F, 0.0F, 140.0F, 130.0F, (String)IMG_PATH, x, y);
        //this.initializeAnimation();
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.strAmt = 4;
        } else {
            this.strAmt = 6;
        }
        this.damage.add(new DamageInfo(this, 25));
        this.damage.add(new DamageInfo(this, 25, DamageInfo.DamageType.HP_LOSS));
    }

    public void initializeAnimation() {
        this.loadAnimation("images/monsters/theForest/mage_dagger/skeleton.atlas", "images/monsters/theForest/mage_dagger/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinDentroFreePower(this, this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinDeadHealingPower(this, HealAmt)));
    }

    public void takeTurn() {
        Iterator<AbstractMonster> var1;
        AbstractMonster m;

        switch (this.nextMove) {
            case 1:
                var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var1.hasNext()) {
                    m = var1.next();
                    if (m != this && !m.isDying && !m.isEscaping) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, this.strAmt), this.strAmt));
                    }
                }
                this.setMove((byte) 2, Intent.BUFF);
                break;

            case 2:
                var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while (var1.hasNext()) {
                    m = var1.next();
                    // 判断目标是否为自己，如果是自己则跳过，不施加 IntangiblePower
                    if (m != this && !m.isDying && !m.isEscaping) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new IntangiblePower(m, 1), 1));
                    }
                }
                this.setMove((byte) 1, Intent.BUFF);
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }



    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)1, Intent.BUFF);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
