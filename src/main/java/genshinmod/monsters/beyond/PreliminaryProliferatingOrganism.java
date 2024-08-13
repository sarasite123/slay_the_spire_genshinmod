package genshinmod.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.SporeCloudPower;
import genshinmod.cards.RerivePraise;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDeadHealingPower;
import genshinmod.powers.genshinDentroFreePower;

//基础增殖生命体
public class PreliminaryProliferatingOrganism extends AbstractMonster {
    public static final String ID = ModHelper.makePath("PreliminaryProliferatingOrganism");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String IMG_PATH = "GenshinModResources/img/monsters/beyond/PreliminaryProliferatingOrganism.png";;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 25;
    private static final int STAB_DMG = 10;
    private int HealAmt = 10;
    private static final int SACRIFICE_DMG = 25;
    private static final byte WOUND = 1;
    private static final byte EXPLODE = 2;
    public boolean firstMove = true;
    private int VENT_DEBUFF = 2;


    public PreliminaryProliferatingOrganism(float x, float y) {
        super(NAME, ID, AbstractDungeon.monsterHpRng.random(20, 25), 0.0F, 0.0F, 140.0F, 130.0F, (String)IMG_PATH, x, y);
        //this.initializeAnimation();
        this.damage.add(new DamageInfo(this, 10));
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
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new RerivePraise(), 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, 10);
        } else {
            if(num<70) {
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, 10);
            }
            else {
                this.setMove((byte)2, Intent.DEBUFF);
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
