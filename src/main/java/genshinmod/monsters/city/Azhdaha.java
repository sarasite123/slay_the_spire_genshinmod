package genshinmod.monsters.city;

import basemod.devcommands.power.Power;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Metallicize;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import genshinmod.cards.DominusLapidis;
import genshinmod.cards.PlanetBefall;
import genshinmod.helper.ModHelper;
import genshinmod.powers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Azhdaha extends AbstractMonster {
    public static final String ID = ModHelper.makePath("Azhdaha");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String THREE_BYRDS = "3_Byrds";
    public static final String FOUR_BYRDS = "4_Byrds";
    public static final String IMG_PATH = "GenshinModResources/img/monsters/city/Azhdaha.png";;
    private static final int HP= 121;
    private static final int A_2_HP= 133;
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
    private int MetallicizeAmt;
    private int MetallicizeAddAmt;
    private int oneDmg;
    private int MultiDmg;
    private int MultiCount = 5;
    private int blockAmt ;
    private int BlockLoseAmt;
    private int strAmt;
    private int weakAmt=2;
    private List<Byte> specialMoves = new ArrayList<>();
    private boolean firstMove = true;
    private static final String OneDmg_NAME;
    private static final String Block_NAME;
    private static final String MultiDmg_NAME;
    private static final String Hydro_NAME;
    private static final String Pyro_NAME;
    private static final String Electro_NAME;
    private static final String Cryo_NAME;
    public int turn_count;
    
    public Azhdaha(float x, float y) {
        super(NAME, ID, 31, 0.0F, 0.0F, 280.0F, 350.0F, (String)IMG_PATH, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_2_HP);
            this.MetallicizeAmt = 8;
            this.MetallicizeAddAmt = 6;
            this.blockAmt = 14 ;
        } else {
            this.setHp(HP);
            this.MetallicizeAmt = 6;
            this.MetallicizeAddAmt = 4;
            this.blockAmt = 10 ;
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.MultiDmg = 5;
            this.oneDmg = 18;
            this.BlockLoseAmt = 5;
        } else {
            this.MultiDmg = 4;
            this.oneDmg = 15;
            this.BlockLoseAmt = 3;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.strAmt= 2 ;
        } else {
            this.strAmt= 1 ;
        }

        this.specialMoves.add((byte) 4);
        this.specialMoves.add((byte) 5);
        this.specialMoves.add((byte) 6);
        this.specialMoves.add((byte) 7);

        this.turn_count = 0;

        this.damage.add(new DamageInfo(this, this.oneDmg));
        this.damage.add(new DamageInfo(this, this.MultiDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.MetallicizeAmt)));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                useOneDmg();
                System.out.println("————————————————————————————————————————");
                break;
            case 2:
                useBlock();
                System.out.println("————————————————————————————————————————");
                break;
            case 3:
                useMultiDmg();
                System.out.println("————————————————————————————————————————");
                break;
            case 4:
                useHydro();
                System.out.println("————————————————————————————————————————");
                break;
            case 5:
                usePyro();
                System.out.println("————————————————————————————————————————");
                break;
            case 6:
                useCryo();
                System.out.println("————————————————————————————————————————");
                break;
            case 7:
                useElectro();
                System.out.println("————————————————————————————————————————");
                break;
        }
    }


    protected void getMove(int num) {
        ++this.turn_count;
        int a = AbstractDungeon.aiRng.random(1, 100);
        if (this.firstMove) {
            // First turn: shuffle and pick one from [4, 5, 6, 7]
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.LONG, ScreenShake.ShakeIntensity.LOW));

            this.firstMove = false;
            if(a<=25){
                this.setMove(MOVES[3], (byte) 4, Intent.BUFF);
                byte moveIndex = this.specialMoves.remove(0);
            } else if (a<=50) {
                this.setMove(MOVES[4], (byte) 5, Intent.BUFF);
                byte moveIndex = this.specialMoves.remove(1);
            } else if (a<=75) {
                this.setMove(MOVES[5], (byte) 6, Intent.BUFF);
                byte moveIndex = this.specialMoves.remove(2);
            }else {
                this.setMove(MOVES[6], (byte) 7, Intent.BUFF);
                byte moveIndex = this.specialMoves.remove(3);
            }
        }

    }


    public void die() {
        super.die();
        CardCrawlGame.sound.play("BYRD_DEATH");
    }

    //1 造成15/18点伤害
    private void useOneDmg() {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE, true));
        this.setMove(MOVES[1], (byte) 2, Intent.DEFEND_DEBUFF);
    }

    //2 获得10/14点格挡，造成2层虚弱
    private void useBlock() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.weakAmt, true), this.weakAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new genshinBlockLoseHPPower(AbstractDungeon.player, this,this.BlockLoseAmt), this.BlockLoseAmt));
        this.setMove(MOVES[2], (byte) 3, Intent.ATTACK, this.MultiDmg, this.MultiCount, true);
    }

    //3 造成5次4/5点伤害
    private void useMultiDmg() {
        for(int i = 0; i < this.MultiCount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.NONE, true));
        }

        // If there are still special moves left, select one at random and remove it
        if (!this.specialMoves.isEmpty()) {
            int nextSpecialMoveIndex = AbstractDungeon.aiRng.random(this.specialMoves.size() - 1);
            byte move = this.specialMoves.remove(nextSpecialMoveIndex);

            switch (move) {
                case 4:
                    this.setMove(MOVES[3], (byte) 4, Intent.BUFF);
                    break;
                case 5:
                    this.setMove(MOVES[4], (byte) 5, Intent.BUFF);
                    break;
                case 6:
                    this.setMove(MOVES[5], (byte) 6, Intent.BUFF);
                    break;
                case 7:
                    this.setMove(MOVES[6], (byte) 7, Intent.BUFF);
                    break;
            }
        } else {
            // If no special moves are left, continue with the regular 1, 2, 3 cycle
            this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.oneDmg);
        }
    }


    private void useHydro(){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,this.strAmt ), this.strAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.MetallicizeAmt)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinHydroFreePower(this, this)));
        this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.oneDmg);
    }

    private void usePyro(){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,this.strAmt ), this.strAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.MetallicizeAmt)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinPyroFreePower(this, this)));
        this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.oneDmg);
    }

    private void useElectro(){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,this.strAmt ), this.strAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.MetallicizeAmt)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinElectroFreePower(this, this)));
        this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.oneDmg);
    }

    private void useCryo(){
        if(checkcardDnP()) {
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1], 1.0F, 2.0F));
        }else {
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
        }
        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.LONG, ScreenShake.ShakeIntensity.LOW));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this,this.strAmt ), this.strAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.MetallicizeAmt)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinCryoFreePower(this, this)));
        this.setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.oneDmg);
    }

    private boolean checkcardDnP() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        for (int i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (card.cardID.equals(DominusLapidis.ID)) {
                return true;
            }
            if(card.cardID.equals(PlanetBefall.ID)){
                return true;
            }
        }
        return false;
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
        OneDmg_NAME = MOVES[0];
        Block_NAME = MOVES[1];
        MultiDmg_NAME = MOVES[2];
        Hydro_NAME = MOVES[3];
        Pyro_NAME = MOVES[4];
        Electro_NAME = MOVES[5];
        Cryo_NAME = MOVES[6];
    }
}
