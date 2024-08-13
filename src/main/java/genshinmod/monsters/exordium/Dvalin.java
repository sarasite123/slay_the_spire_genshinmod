package genshinmod.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import genshinmod.helper.ModHelper;

public class Dvalin extends AbstractMonster {
    public static final String ID = ModHelper.makePath("Dvalin");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String THREE_BYRDS = "3_Byrds";
    public static final String FOUR_BYRDS = "4_Byrds";
    public static final String IMG_PATH = "GenshinModResources/img/monsters/exordium/Dvalin3.png";
    private static final int HP= 140;
    private static final int A_2_HP= 150;
    private static final float HB_X_F = 0.0F;
    private static final float HB_X_G = 10.0F;
    private static final float HB_Y_F = 50.0F;
    private static final float HB_Y_G = -50.0F;
    private static final float HB_W = 240.0F;
    private static final float HB_H = 180.0F;
    private static final int PECK_DMG = 3;
    private static final int PECK_COUNT = 5;
    private static final int A_2_PECK_COUNT = 6;
    private static final int SWOOP_DMG = 12;
    private static final int A_2_SWOOP_DMG = 14;
    private int peckDmg;
    private int peckCount;
    private int swoopDmg;
    private int swoopCount;
    private int flightAmt;
    private int blockAmt = 9;
    private int goundAmt;
    private int VENT_DEBUFF = 2;
    private int strAmt;
    private int flightAmtAdd;
    private static final int HEADBUTT_DMG = 3;
    private static final int CAW_STR = 1;
    private static final byte PECK = 1;
    private static final byte GO_AIRBORNE = 2;
    private static final byte SWOOP = 3;
    private static final byte STUNNED = 4;
    private static final byte HEADBUTT = 5;
    private static final byte CAW = 6;
    private boolean firstMove = true;
    private boolean isFlying = true;
    public static final String FLY_STATE = "FLYING";
    public static final String GROUND_STATE = "GROUNDED";
    private static final String DragonsBreath_NAME;
    private static final String MeteorShower_NAME;
    private static final String DragonDance_NAME;
    private static final String CaelestinumFinaleTermini_NAME;
    private static final String StormVortex_NAME;
    private static final String Vertigo_NAME;

    public Dvalin(float x, float y) {
        super(NAME, ID, 31, 0.0F, 0.0F, 280.0F, 350.0F, (String)IMG_PATH, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(120);
        } else {
            this.setHp(110);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.flightAmt = 4;
            this.flightAmtAdd = 2;
            this.strAmt= 2 ;
        } else {
            this.flightAmt = 3;
            this.flightAmtAdd = 1;
            this.strAmt= 1 ;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.peckDmg = 3;
            this.peckCount = 6;
            this.swoopDmg = 6;
            this.swoopCount = 3;
        } else {
            this.peckDmg = 3;
            this.peckCount = 5;
            this.swoopDmg = 5;
            this.swoopCount = 3;
        }

        this.damage.add(new DamageInfo(this, this.peckDmg));
        this.damage.add(new DamageInfo(this, this.swoopDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                useDragonsBreath();
                System.out.println("——————————————————useDragonsBreath——————————————————————");
                break;
            case 2:
                useStormVortex();
                System.out.println("——————————————————useStormVortex——————————————————————");
                break;
            case 3:
                useMeteorShower();
                System.out.println("——————————————————useMeteorShower——————————————————————");
                break;
            case 4:
                useDragonDance();
                System.out.println("——————————————————useDragonDance——————————————————————");
                break;
            case 5:
                useCaelestinumFinaleTermini();
                System.out.println("——————————————————useCaelestinumFinaleTermini——————————————————————");
                return;
            case 6:
                this.setMove(Vertigo_NAME,(byte)5, Intent.BUFF);
                break;
            default:
                System.out.println("——————————————————Wrong——————————————————————");
           }

    }

    private void playRandomBirdSFx() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_BYRD_ATTACK_" + MathUtils.random(0, 5)));
    }

    public void changeState(String stateName) {
        AnimationState.TrackEntry e;
        switch (stateName) {
            case "FLYING":
                this.updateHitbox(0.0F, 0.0F, 280.0F, 300.0F);
                break;
            case "GROUNDED":
                this.setMove((byte)6, Intent.STUN);
                this.createIntent();
                this.isFlying = false;

                this.updateHitbox(0.0F, 0.0F, 280.0F, 300.0F);
        }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove( (byte)1, Intent.STRONG_DEBUFF);
        } else {
            if (this.isFlying=false) {
                this.setMove( (byte)5, Intent.BUFF);
            }
        }
    }

    public void die() {
        super.die();
        CardCrawlGame.sound.play("BYRD_DEATH");
    }

    //1 2层脆弱2层易伤
    private void useDragonsBreath() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        this.setMove(DragonsBreath_NAME,(byte)3, Intent.ATTACK, this.swoopDmg, this.swoopCount, true);
    }

    //3 造成3次5/6点伤害
    private void useMeteorShower() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        for(int i = 0; i < this.swoopCount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));

        }
        this.setMove(MeteorShower_NAME,(byte)4, Intent.DEFEND_BUFF);
    }

    //4 清除身上所有的dubuff获得9点格挡
    private void useDragonDance() {
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Shackled"));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.blockAmt));
        this.setMove(DragonDance_NAME,(byte)2, Intent.ATTACK, this.peckDmg, this.peckCount, true);
    }

    //5 获得1点力量，强化1点飞行,重回飞行状态
    private void useCaelestinumFinaleTermini() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 3.0F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,this.strAmt ), this.strAmt));
        this.flightAmt += 1;
        this.isFlying = true;
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "FLYING"));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, this.flightAmt)));

        this.setMove(CaelestinumFinaleTermini_NAME,(byte)2, Intent.ATTACK, this.peckDmg, this.peckCount, true);
    }

    //2 ，造成5/6次3点伤害
    private void useStormVortex() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        for(int i = 0; i < this.peckCount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE, true));

        }
        this.setMove(StormVortex_NAME,(byte)1,Intent.STRONG_DEBUFF);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        if (monsterStrings == null) {
            System.out.println("monsterStrings is null!");
        } else {
            System.out.println("MOVES length: " + monsterStrings.MOVES.length);
        }

        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        DragonsBreath_NAME = MOVES[0];
        MeteorShower_NAME = MOVES[1];
        DragonDance_NAME = MOVES[2];
        CaelestinumFinaleTermini_NAME = MOVES[3];
        StormVortex_NAME = MOVES[4];
        Vertigo_NAME = MOVES[5];
    }

}
