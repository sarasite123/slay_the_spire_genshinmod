package genshinmod.monsters.beyond;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import genshinmod.cards.RerivePraise;
import genshinmod.helper.ModHelper;
import genshinmod.powers.genshinDeadHealingPower;
import genshinmod.powers.genshinDentroFreePower;
import genshinmod.powers.genshinElectroFreePower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Apep extends AbstractMonster {
    public static final String ID = ModHelper.makePath("Apep");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String THREE_BYRDS = "3_Byrds";
    public static final String FOUR_BYRDS = "4_Byrds";
    public static final String IMG_PATH = "GenshinModResources/img/monsters/beyond/Apep4.png";
    public static final float[] POSX;
    public static final float[] POSY;
    private static final int HP = 174;
    private static final int A_2_HP = 186;
    private static final int BITE_DMG = 28;
    private static final int A_2_BITE_DMG = 32;
    private static final int MULTI_DMG = 10;
    private static final int A_2_MULTI_DMG = 12;
    private static final int MULTI_COUNT = 3;
    private static final int Organism_PER_SPAWN = 1;
    private static final int ASC_2_Organism_PER_SPAWN = 2;
    private int OrganismPerSpawn;
    private int StatusAmt;
    private int HealAmt;
    private AbstractMonster[] Organisms = new AbstractMonster[4];
    public int turn_count;
    private boolean firstMove = true;

    public Apep(float x, float y) {
        super(NAME, ID, 31, 0.0F, 0.0F, 280.0F, 350.0F, (String) IMG_PATH, x, y);

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.OrganismPerSpawn = 2;
            this.StatusAmt = 5;
        } else {
            this.OrganismPerSpawn = 1;
            this.StatusAmt = 3;
        }

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(185, 195);
            this.HealAmt=15;
        } else {
            this.setHp(170, 180);
            this.HealAmt=10;
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 32));
        } else {
            this.damage.add(new DamageInfo(this, 10));
            this.damage.add(new DamageInfo(this, 28));
        }
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new genshinDentroFreePower(this, this)));
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.id.equals(this.id)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new genshinDeadHealingPower(m, HealAmt)));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new genshinDentroFreePower(m, m)));

            }

            if (m instanceof PreliminaryProliferatingOrganism) {
                if (AbstractDungeon.getMonsters().monsters.indexOf(m) > AbstractDungeon.getMonsters().monsters.indexOf(this)) {
                    this.Organisms[0] = m;
                } else {
                    this.Organisms[1] = m;
                }
            }
        }

    }
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                // Attack action (unchanged)
                for (int i = 0; i < MULTI_COUNT; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;

            case 2:
                // Spawning logic
                int organismsSpawned = 0;
                int i = 0;

                while (organismsSpawned < this.OrganismPerSpawn && i < this.Organisms.length) {
                    if (this.Organisms[i] == null || this.Organisms[i].isDeadOrEscaped()) {
                        AbstractMonster OrganismToSpawn = getRandomOrganismToSpawn(i);

                        if (OrganismToSpawn instanceof ProvenderProliferatingOrganism && isProvenderAlreadyInPlay()) {
                            // If a ProvenderProliferatingOrganism is already in play, choose between the other two options
                            OrganismToSpawn = getRandomOrganismToSpawnWithoutProvender(i);
                        }

                        this.Organisms[i] = OrganismToSpawn;
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(OrganismToSpawn, true));
                        OrganismToSpawn.usePreBattleAction();
                        organismsSpawned++;
                    }

                    i++;
                }
                break;

            case 3:
                // Strong attack action (unchanged)
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                break;

            case 4:
                // Debuff action (unchanged)
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new RerivePraise(), StatusAmt));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private AbstractMonster getRandomOrganismToSpawn(int i) {
        // Randomly choose one of the three organism types
        int choice = MathUtils.random(2); // 0 = Pernicious, 1 = Preliminary, 2 = Provender

        if (choice == 0) {
            return new PerniciousProliferatingOrganism(POSX[i], POSY[i]);
        } else if (choice == 1) {
            return new PreliminaryProliferatingOrganism(POSX[i], POSY[i]);
        } else {
            return new ProvenderProliferatingOrganism(POSX[i], POSY[i]);
        }
    }

    private AbstractMonster getRandomOrganismToSpawnWithoutProvender(int i) {
        // Randomly choose between PerniciousProliferatingOrganism and PreliminaryProliferatingOrganism
        int choice = MathUtils.random(1); // 0 = Pernicious, 1 = Preliminary

        if (choice == 0) {
            return new PerniciousProliferatingOrganism(POSX[i], POSY[i]);
        } else {
            return new PreliminaryProliferatingOrganism(POSX[i], POSY[i]);
        }
    }

    private boolean isProvenderAlreadyInPlay() {
        // Check if there is already a ProvenderProliferatingOrganism in play
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m instanceof ProvenderProliferatingOrganism && !m.isDeadOrEscaped()) {
                return true;
            }
        }
        return false;
    }


    private boolean canSpawn() {
        int aliveCount = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while (var2.hasNext()) {
            AbstractMonster m = (AbstractMonster) var2.next();
            if (m != this && !m.isDying) {
                ++aliveCount;
            }
        }

        if (aliveCount > 3) {
            return false;
        } else {
            return true;
        }
    }


    public void die() {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte) 2, Intent.UNKNOWN);
        } else {
            if (num < 25) {
                if (!this.lastMove((byte) 1)) {
                    this.setMove((byte) 1, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(0)).base, 3, true);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(25, 99));
                }
            } else if (num < 50) {
                if (!this.lastTwoMoves((byte) 2)) {
                    if (this.canSpawn()) {
                        this.setMove((byte) 2, Intent.UNKNOWN);
                    } else {
                        this.setMove((byte) 1, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(0)).base, 3, true);
                    }
                } else {
                    this.setMove((byte) 1, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(0)).base, 3, true);
                }
            } else if (num < 75) {
                    if(!this.lastMove((byte) 3)){
                        this.setMove((byte) 3, Intent.ATTACK, ((DamageInfo) this.damage.get(1)).base);
                    } else {
                        this.setMove((byte) 4, Intent.STRONG_DEBUFF);
                    }
            }
            else if(!this.lastMove((byte) 4)) {
                    this.setMove((byte) 4, Intent.STRONG_DEBUFF);
            }
            else {
                this.getMove(AbstractDungeon.aiRng.random(65));
            }

        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        POSX = new float[]{210.0F, -220.0F, 180.0F, -250.0F};
        POSY = new float[]{75.0F, 115.0F, 345.0F, 335.0F};
    }
}
