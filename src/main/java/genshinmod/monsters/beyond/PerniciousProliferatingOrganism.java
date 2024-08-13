package genshinmod.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import genshinmod.cards.RerivePraise;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDeadHealingPower;
import genshinmod.powers.genshinDentroFreePower;
import genshinmod.powers.genshinExplosivePower;

//破坏的增殖生命体

public class PerniciousProliferatingOrganism extends AbstractMonster {
    public static final String ID = ModHelper.makePath("PerniciousProliferatingOrganism");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String IMG_PATH = "GenshinModResources/img/monsters/beyond/PerniciousProliferatingOrganism1.png";;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 25;
    private static final int STAB_DMG = 10;
    private int HealAmt = 10;
    private static final int SACRIFICE_DMG = 25;
    private static final byte WOUND = 1;
    private static final byte EXPLODE = 2;
    public boolean firstMove = true;
    private int VENT_DEBUFF = 2;
    private static final int ATTACK_DMG = 9;
    private static final int A_2_ATTACK_DMG = 11;
    private int attackDmg;
    private static final int EXPLODE_BASE = 3;
    private int turnCount = 0;

    public PerniciousProliferatingOrganism(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(20, 25), 0.0F, 0.0F, 140.0F, 130.0F, (String)IMG_PATH, x, y);
        //this.initializeAnimation();
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.attackDmg = 8;
        } else {
            this.attackDmg = 7;
        }
        this.damage.add(new DamageInfo(this, this.attackDmg));
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinExplosivePower(this, EXPLODE_BASE)));

    }

    public void takeTurn() {
        ++this.turnCount;
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case 2:
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    protected void getMove(int num) {
        if (this.turnCount < 2) {
            this.setMove((byte) 1, Intent.ATTACK, this.attackDmg, 2, true);
        } else {
            this.setMove((byte)2, Intent.UNKNOWN);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
