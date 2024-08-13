package genshinmod.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import genshinmod.helper.ModHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class genshinwish extends AbstractImageEvent {
    private static final Logger logger = LogManager.getLogger(genshinwish.class.getName());
    public static final String ID = ModHelper.makePath("genshinwish");
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private int relicObtainChance = 1;
    private int gold = 1;

    private int screenNum = 0;
    private static final String DIALOG_1;
    private static final String FAIL_MSG;
    private static final String SUCCESS_MSG;
    private static final String SUCCESS_MSGONCE;
    private static final String ESCAPE_MSG;
    private AbstractRelic relicMetric = null;

    public genshinwish() {
        super(NAME, DIALOG_1, "GenshinModResources/img/events/wish.jpg");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.gold = 7;
        }
        if (AbstractDungeon.player.gold >= this.gold) {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.gold + OPTIONS[1] + this.relicObtainChance + OPTIONS[2], AbstractDungeon.player.gold < this.gold);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[5] + this.gold + OPTIONS[3] + this.relicObtainChance + OPTIONS[2], AbstractDungeon.player.gold < this.gold);
        }

        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_OOZE");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.loseGold(this.gold);
                        CardCrawlGame.sound.play("ATTACK_POISON");

                        int random = AbstractDungeon.miscRng.random(0, 99);
                        if (random >= 99 - this.relicObtainChance) {
                            if(this.gold<10) {
                                this.imageEventText.updateBodyText(SUCCESS_MSGONCE);
                                if (AbstractDungeon.player.hasRelic(ModHelper.makePath("IntertwinedFate"))) {
                                    this.relicMetric = RelicLibrary.getRelic("Circlet").makeCopy();
                                } else {
                                    this.relicMetric = RelicLibrary.getRelic(ModHelper.makePath("IntertwinedFate")).makeCopy();
                                }

                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.relicMetric);

                                this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                                this.imageEventText.removeDialogOption(1);
                                this.screenNum = 1;
                            }
                            else {
                                this.imageEventText.updateBodyText(SUCCESS_MSG);
                                if (AbstractDungeon.player.hasRelic(ModHelper.makePath("AcquaintFate"))) {
                                    this.relicMetric = RelicLibrary.getRelic("Circlet").makeCopy();
                                } else {
                                    this.relicMetric = RelicLibrary.getRelic(ModHelper.makePath("AcquaintFate")).makeCopy();
                                }

                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.relicMetric);

                                this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                                this.imageEventText.removeDialogOption(1);
                                this.screenNum = 1;
                            }

                        } else {
                            this.imageEventText.updateBodyText(FAIL_MSG);
                            this.relicObtainChance += 10;
                            if (AbstractDungeon.ascensionLevel >= 15) {
                                this.gold = 16;
                            }
                            else {
                                this.gold = 10;
                            }
                            if (AbstractDungeon.player.gold >= this.gold) {
                                this.imageEventText.updateDialogOption(0,OPTIONS[4] + this.gold + OPTIONS[1] + this.relicObtainChance + OPTIONS[2], AbstractDungeon.player.gold < this.gold);
                            } else {
                                this.imageEventText.setDialogOption(OPTIONS[5] + this.gold + OPTIONS[3] + this.relicObtainChance + OPTIONS[2], AbstractDungeon.player.gold < this.gold);
                            }
                            this.imageEventText.updateDialogOption(1, OPTIONS[3]);
                        }

                        return;
                    case 1:

                        this.imageEventText.updateBodyText(ESCAPE_MSG);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.removeDialogOption(1);
                        this.screenNum = 1;
                        return;
                    default:
                        return;
                }
            case 1:
                this.openMap();
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ModHelper.makePath("genshinwish"));
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        FAIL_MSG = DESCRIPTIONS[1];
        SUCCESS_MSG = DESCRIPTIONS[2];
        SUCCESS_MSGONCE = DESCRIPTIONS[3];
        ESCAPE_MSG = DESCRIPTIONS[4];

        // Logging to help with debugging
        Logger logger = LogManager.getLogger(genshinwish.class.getName());
        logger.info("Event Name: " + NAME);
        logger.info("Descriptions: " + String.join(", ", DESCRIPTIONS));
        logger.info("Options: " + String.join(", ", OPTIONS));
    }
}
