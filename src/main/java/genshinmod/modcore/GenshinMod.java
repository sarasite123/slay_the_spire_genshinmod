package genshinmod.modcore;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import genshinmod.Characters.GenshinAether;
import genshinmod.cards.*;
import com.badlogic.gdx.graphics.Color;
import genshinmod.cards.NiwabiFireDance;
import genshinmod.events.genshinwish;
import genshinmod.helper.ModHelper;
import genshinmod.monsters.beyond.Apep;
import genshinmod.monsters.beyond.PerniciousProliferatingOrganism;
import genshinmod.monsters.beyond.PreliminaryProliferatingOrganism;
import genshinmod.monsters.beyond.ProvenderProliferatingOrganism;
import genshinmod.monsters.city.Azhdaha;
import genshinmod.monsters.exordium.Dvalin;
import genshinmod.potions.*;
import genshinmod.relics.*;
import genshinmod.variable.MyM2Variable;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;
import static genshinmod.Characters.GenshinAether.Enums.Genshin_CARD;
import static genshinmod.Characters.GenshinAether.Enums.MY_CHARACTER;


@SpireInitializer
public class GenshinMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, AddAudioSubscriber{
    private static final String MY_CHARACTER_BUTTON = "GenshinModResources/img/char/character/Character_Button1.png";
    private static final String MY_CHARACTER_PORTRAIT = "GenshinModResources/img/char/character/Character_Portrait.png";
    private static final String BG_ATTACK_512 = "GenshinModResources/img/512/bg_attack_512.png";
    private static final String BG_POWER_512 = "GenshinModResources/img/512/bg_power_512.png";
    private static final String BG_SKILL_512 = "GenshinModResources/img/512/bg_skill_512.png";
    private static final String SMALL_ORB = "GenshinModResources/img/char/character/small_orb.png";
    private static final String BG_ATTACK_1024 = "GenshinModResources/img/1024/bg_attack.png";
    private static final String BG_POWER_1024 = "GenshinModResources/img/1024/bg_power.png";
    private static final String BG_SKILL_1024 = "GenshinModResources/img/1024/bg_skill.png";
    private static final String BIG_ORB = "GenshinModResources/img/char/character/card_orb.png";
    private static final String ENERGY_ORB = "GenshinModResources/img/char/character/cost_orb.png";

    public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);


    public GenshinMod() {
        BaseMod.subscribe(this);
        // 这里的Genshin_CARD是人物类里的，应写成MyCharacter.Enums.Genshin_CARD
        BaseMod.addColor(Genshin_CARD, MY_COLOR, MY_COLOR, MY_COLOR,
                MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, BG_ATTACK_512,
                BG_SKILL_512, BG_POWER_512, ENERGY_ORB, BG_ATTACK_1024,
                BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB
        );
    }

    public static void initialize() {
        new GenshinMod();
    }

    public void receiveAddAudio() {
        BaseMod.addAudio("genshin:Dentro", "GenshinModResources/audio/sound/Dentro.wav");
        BaseMod.addAudio("genshin:AdeptusArtHeraldofFrost", "GenshinModResources/audio/sound/AdeptusArtHeraldofFrost.wav");
        BaseMod.addAudio("genshin:AllSchemestoKnow", "GenshinModResources/audio/sound/AllSchemestoKnow.wav");
        BaseMod.addAudio("genshin:AnemoSeed", "GenshinModResources/audio/sound/AnemoSeed.wav");
        BaseMod.addAudio("genshin:AstableAnemohypostasisCreation6308", "GenshinModResources/audio/sound/AstableAnemohypostasisCreation6308.wav");
        BaseMod.addAudio("genshin:BaneofAllEvil", "GenshinModResources/audio/sound/BaneofAllEvil.wav");
        BaseMod.addAudio("genshin:Breastplate", "GenshinModResources/audio/sound/Breastplate.wav");
        BaseMod.addAudio("genshin:CelestialShower", "GenshinModResources/audio/sound/CelestialShower.wav");
        BaseMod.addAudio("genshin:CliffbreakersBanner", "GenshinModResources/audio/sound/CliffbreakersBanner.wav");
        BaseMod.addAudio("genshin:DanceofHaftkarsvar", "GenshinModResources/audio/sound/DanceofHaftkarsvar.wav");
        BaseMod.addAudio("genshin:Dawn", "GenshinModResources/audio/sound/Dawn.wav");
        BaseMod.addAudio("genshin:DominusLapidis", "GenshinModResources/audio/sound/DominusLapidis.wav");
        BaseMod.addAudio("genshin:ExplosivePuppet", "GenshinModResources/audio/sound/ExplosivePuppet.wav");
        BaseMod.addAudio("genshin:FantasticVoyage", "GenshinModResources/audio/sound/FantasticVoyage.wav");
        BaseMod.addAudio("genshin:GaleBlade", "GenshinModResources/audio/sound/GaleBlade.wav");
        BaseMod.addAudio("genshin:Geo", "GenshinModResources/audio/sound/Geo.wav");
        BaseMod.addAudio("genshin:GlacialIllumination", "GenshinModResources/audio/sound/GlacialIllumination.wav");
        BaseMod.addAudio("genshin:GoneWithTheWind", "GenshinModResources/audio/sound/GoneWithTheWind.wav");
        BaseMod.addAudio("genshin:GuhuaSwordRaincutter", "GenshinModResources/audio/sound/GuhuaSwordRaincutter.wav");
        BaseMod.addAudio("genshin:GuidetoAfterlife", "GenshinModResources/audio/sound/GuidetoAfterlife.wav");
        BaseMod.addAudio("genshin:IcetideVortex", "GenshinModResources/audio/sound/IcetideVortex.wav");
        BaseMod.addAudio("genshin:IcyPaws", "GenshinModResources/audio/sound/IcyPaws.wav");
        BaseMod.addAudio("genshin:IllusoryHeart", "GenshinModResources/audio/sound/IllusoryHeart.wav");
        BaseMod.addAudio("genshin:JadeScreen", "GenshinModResources/audio/sound/JadeScreen.wav");
        BaseMod.addAudio("genshin:JumpyDumpty", "GenshinModResources/audio/sound/JumpyDumpty.wav");
        BaseMod.addAudio("genshin:KamisatoArtSoumetsu", "GenshinModResources/audio/sound/KamisatoArtSoumetsu.wav");
        BaseMod.addAudio("genshin:LettheShowBegin", "GenshinModResources/audio/sound/LettheShowBegin.wav");
        BaseMod.addAudio("genshin:LightningRose", "GenshinModResources/audio/sound/LightningRose.wav");
        BaseMod.addAudio("genshin:MoltenInferno", "GenshinModResources/audio/sound/MoltenInferno.wav");
        BaseMod.addAudio("genshin:MusouIsshin", "GenshinModResources/audio/sound/MusouIsshin.wav");
        BaseMod.addAudio("genshin:NereidsAscension", "GenshinModResources/audio/sound/NereidsAscension.wav");
        BaseMod.addAudio("genshin:NiwabiFireDance", "GenshinModResources/audio/sound/NiwabiFireDance.wav");
        BaseMod.addAudio("genshin:PlanetBefall", "GenshinModResources/audio/sound/PlanetBefall.wav");
        BaseMod.addAudio("genshin:RyuukinSaxifrage", "GenshinModResources/audio/sound/RyuukinSaxifrage.wav");
        BaseMod.addAudio("genshin:SearingOnslaught", "GenshinModResources/audio/sound/SearingOnslaught.wav");
        BaseMod.addAudio("genshin:SecretArtMusouShinsetsu", "GenshinModResources/audio/sound/SecretArtMusouShinsetsu.wav");
        BaseMod.addAudio("genshin:SkywardSonnet", "GenshinModResources/audio/sound/SkywardSonnet.wav");
        BaseMod.addAudio("genshin:SparksnSplash", "GenshinModResources/audio/sound/SparksnSplash.wav");
        BaseMod.addAudio("genshin:SpiritBladeChonghuasLayeredFrost", "GenshinModResources/audio/sound/SpiritBladeChonghuasLayeredFrost.wav");
        BaseMod.addAudio("genshin:SpiritSoother", "GenshinModResources/audio/sound/SpiritSoother.wav");
        BaseMod.addAudio("genshin:SpringSpiritSummoning", "GenshinModResources/audio/sound/SpringSpiritSummoning.wav");
        BaseMod.addAudio("genshin:StellarRestoration", "GenshinModResources/audio/sound/StellarRestoration.wav");
        BaseMod.addAudio("genshin:Stormbreaker", "GenshinModResources/audio/sound/Stormbreaker.wav");
        BaseMod.addAudio("genshin:SubjugationKoukouSendou", "GenshinModResources/audio/sound/SubjugationKoukouSendou.wav");
        BaseMod.addAudio("genshin:WandaInternationalHydro", "GenshinModResources/audio/sound/WandaInternationalHydro.wav");
        BaseMod.addAudio("genshin:WandaInternationalPyro", "GenshinModResources/audio/sound/WandaInternationalPyro.wav");
        BaseMod.addAudio("genshin:WildStar", "GenshinModResources/audio/sound/WildStar.wav");
        BaseMod.addAudio("genshin:WindsGrandOde", "GenshinModResources/audio/sound/WindsGrandOde.wav");
        BaseMod.addAudio("genshin:wish", "GenshinModResources/audio/sound/wish.wav");
        BaseMod.addAudio("genshin:YakanEvocationSesshouSakura", "GenshinModResources/audio/sound/YakanEvocationSesshouSakura.wav");
        BaseMod.addAudio("genshin:YamaarashiTailwind", "GenshinModResources/audio/sound/YamaarashiTailwind.wav");
        BaseMod.addAudio("genshin:YoohooArtFuuinDash", "GenshinModResources/audio/sound/YoohooArtFuuinDash.wav");

    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new MyM2Variable());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Hydro());
        BaseMod.addCard(new Pyro());
        BaseMod.addCard(new Dentro());
        BaseMod.addCard(new Cryo());
        BaseMod.addCard(new Electro());
        BaseMod.addCard(new GetTempHP());
        BaseMod.addCard(new GetHealing());
        //BaseMod.addCard(new Anemo());
        //BaseMod.addCard(new Geo());
        BaseMod.addCard(new YamaarashiTailwind());
        BaseMod.addCard(new IllusoryHeart());
        BaseMod.addCard(new AllSchemestoKnow());
        BaseMod.addCard(new LetThePeopleRejoice());
        BaseMod.addCard(new SalonSolitaire());
        BaseMod.addCard(new SecretArtMusouShinsetsu());
        BaseMod.addCard(new MusouIsshin());
        BaseMod.addCard(new DominusLapidis());
        BaseMod.addCard(new PlanetBefall());
        BaseMod.addCard(new SkywardSonnet());
        BaseMod.addCard(new WindsGrandOde());
        BaseMod.addCard(new TheBestTravelCompanionEver());
        BaseMod.addCard(new TheCalloftheAbyss());
        BaseMod.addCard(new SustainerofHeavenlyPrinciples());
        BaseMod.addCard(new OTidesIHaveReturned());
        BaseMod.addCard(new StatuesofTheSeven());
        BaseMod.addCard(new Traveller());
        BaseMod.addCard(new WildStar());
        BaseMod.addCard(new ElementalFurnace());
        BaseMod.addCard(new GoneWithTheWind());
        BaseMod.addCard(new Stormbreaker());
        BaseMod.addCard(new ElementalPrimitive());
        BaseMod.addCard(new LettheShowBegin());
        BaseMod.addCard(new JumpyDumpty());
        BaseMod.addCard(new IcetideVortex());
        BaseMod.addCard(new GlacialIllumination());
        BaseMod.addCard(new AstheSunlitSkysSingingSalute());
        BaseMod.addCard(new CeremonialCrystalshot());
        BaseMod.addCard(new IcyPaws());
        BaseMod.addCard(new SpringSpiritSummoning());
        BaseMod.addCard(new KamisatoArtSoumetsu());
        BaseMod.addCard(new CelestialShower());
        BaseMod.addCard(new AdeptusArtHeraldofFrost());
        BaseMod.addCard(new ElementalRainbow());
        BaseMod.addCard(new SpiritBladeChonghuasLayeredFrost());
        BaseMod.addCard(new AnemoSeed());
        BaseMod.addCard(new MagicTrickAstonishingShift());
        BaseMod.addCard(new GaleBlade());
        BaseMod.addCard(new Breastplate());
        BaseMod.addCard(new StellarRestoration());
        BaseMod.addCard(new YakanEvocationSesshouSakura());
        BaseMod.addCard(new LightningRose());
        BaseMod.addCard(new SanctifyingRing());
        BaseMod.addCard(new SecretRiteTwilightShadowpiercer());
        BaseMod.addCard(new JadeScreen());
        BaseMod.addCard(new AstableAnemohypostasisCreation6308());
        BaseMod.addCard(new HolisticRevivification());
        BaseMod.addCard(new UniversalityAnElaborationonForm());
        BaseMod.addCard(new ParticularFieldFettersofPhenomena());
        BaseMod.addCard(new GuidetoAfterlife());
        BaseMod.addCard(new SpiritSoother());
        BaseMod.addCard(new FantasticVoyage());
        BaseMod.addCard(new AllIsAsh());
        BaseMod.addCard(new SearingOnslaught());
        BaseMod.addCard(new ExplosivePuppet());
        BaseMod.addCard(new NiwabiFireDance());
        BaseMod.addCard(new RyuukinSaxifrage());
        BaseMod.addCard(new SparksnSplash());
        BaseMod.addCard(new MoltenInferno());
        BaseMod.addCard(new NereidsAscension());
        BaseMod.addCard(new WandaInternational());
        BaseMod.addCard(new SuperSaturatedSyringing());
        BaseMod.addCard(new ReboundHydrotherapy());
        BaseMod.addCard(new RiteofProgenitureTectonicTide());
        BaseMod.addCard(new GuhuaSwordRaincutter());
        BaseMod.addCard(new DanceofHaftkarsvar());
        BaseMod.addCard(new ElementalBlessing());
        BaseMod.addCard(new BaneofAllEvil());
        BaseMod.addCard(new SubjugationKoukouSendou());
        BaseMod.addCard(new MeowteorKick());
        BaseMod.addCard(new AromaticExplication());
        BaseMod.addCard(new Dawn());
        BaseMod.addCard(new YoohooArtFuuinDash());
        BaseMod.addCard(new CliffbreakersBanner());
        BaseMod.addCard(new ElementalLocking());
        BaseMod.addCard(new Dendrogranum());
        BaseMod.addCard(new ElementalDriver());

    }

    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new GenshinAether(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MY_CHARACTER);
    }

    public void receiveEditStrings() {
        String lang = checkLanguage();
        BaseMod.loadCustomStringsFile(CardStrings.class, "GenshinModResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "GenshinModResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "GenshinModResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "GenshinModResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, "GenshinModResources/localization/" + lang + "/potions.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "GenshinModResources/localization/" + lang + "/events.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "GenshinModResources/localization/" + lang + "/monsters.json");
    }

    public void receiveEditRelics() {
 // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
        BaseMod.addRelic(new Vision(), RelicType.SHARED);
        BaseMod.addRelic(new Heart(), RelicType.SHARED);
        BaseMod.addRelic(new InstructorsCap(), RelicType.SHARED);
        BaseMod.addRelic(new Hydroculus(), RelicType.SHARED);
        BaseMod.addRelic(new ViridescentVenerer(), RelicType.SHARED);
        BaseMod.addRelic(new WitchsHeartFlames(), RelicType.SHARED);
        BaseMod.addRelic(new FrostWeavedDignity(), RelicType.SHARED);
        BaseMod.addRelic(new DreamSolvent(), RelicType.SHARED);
        BaseMod.addRelic(new PaimonsFoodFund(), RelicType.SHARED);
        BaseMod.addRelic(new TenacityoftheMillelith(), RelicType.SHARED);
        BaseMod.addRelic(new IntertwinedFate(), RelicType.SHARED);
        BaseMod.addRelic(new AcquaintFate(), RelicType.SHARED);

    }


    public void receivePostInitialize() {
        BaseMod.addPotion(UnmovingEssentialOil.class,MY_COLOR,MY_COLOR,MY_COLOR, ModHelper.makePath("UnmovingEssentialOil"),MY_CHARACTER);
        BaseMod.addPotion(GushingEssentialOil.class,MY_COLOR,MY_COLOR,MY_COLOR, ModHelper.makePath("GushingEssentialOil"),MY_CHARACTER);
        BaseMod.addPotion(ForestEssentialOil.class,MY_COLOR,MY_COLOR,MY_COLOR, ModHelper.makePath("ForestEssentialOil"),MY_CHARACTER);
        BaseMod.addPotion(FlamingEssentialOil.class,MY_COLOR,MY_COLOR,MY_COLOR, ModHelper.makePath("FlamingEssentialOil"),MY_CHARACTER);
        BaseMod.addPotion(StreamingEssentialOil.class,MY_COLOR,MY_COLOR,MY_COLOR, ModHelper.makePath("StreamingEssentialOil"),MY_CHARACTER);
        BaseMod.addEvent(ModHelper.makePath("genshinwish"), genshinwish.class);
        receiveEditMonsters();
    }

    private void receiveEditMonsters() {
        // 注册怪物组合，你可以多添加几个怪物

        BaseMod.addMonster(ModHelper.makePath("Dvalin"), Dvalin.NAME, () -> new Dvalin(0.0F, 0.0F));
        BaseMod.addMonster(ModHelper.makePath("Azhdaha"), Dvalin.NAME, () -> new Azhdaha(0.0F, 0.0F));
        BaseMod.addMonster(ModHelper.makePath("Apep"), Dvalin.NAME, () -> new Apep(0.0F, 0.0F));
        BaseMod.addMonster(ModHelper.makePath("PerniciousProliferatingOrganism"), Dvalin.NAME, () -> new PerniciousProliferatingOrganism(0.0F, 0.0F));
        BaseMod.addMonster(ModHelper.makePath("PreliminaryProliferatingOrganism"), Dvalin.NAME, () -> new PreliminaryProliferatingOrganism(0.0F, 0.0F));
        BaseMod.addMonster(ModHelper.makePath("ProvenderProliferatingOrganism"), Dvalin.NAME, () -> new ProvenderProliferatingOrganism(0.0F, 0.0F));

        // 两个异鸟
        // BaseMod.addMonster("ExampleMod:2 Byrds", "", () -> new MonsterGroup(new AbstractMonster[] { new Byrd(-80.0F, MathUtils.random(25.0F, 70.0F)), new Byrd(200.0F, MathUtils.random(25.0F, 70.0F)) }));

        // 添加战斗遭遇
        // 在第二章添加精英遭遇，权重为1.0，权重越高越可能遇到
        BaseMod.addEliteEncounter("Exordium", new MonsterInfo(ModHelper.makePath("Dvalin"), 9.0F));
        BaseMod.addEliteEncounter("City", new MonsterInfo(ModHelper.makePath("Azhdaha"), 9.0F));
        BaseMod.addEliteEncounter("Beyond", new MonsterInfo(ModHelper.makePath("Apep"), 9.0F));
    }
    @Override
    public void receiveEditKeywords() {
        String lang = checkLanguage();
        FileHandle h = Gdx.files.internal("GenshinModResources/localization/" + lang + "/keywords.json");
        String s = h.readString(StandardCharsets.UTF_8.toString());
        Gson gson = new Gson();
        Keywords keywords = (Keywords)gson.fromJson(s, Keywords.class);
        if (keywords != null && keywords.keywords != null) {
            for (Keyword keyword : keywords.keywords) {
                // 这个id要全小写
                BaseMod.addKeyword("genshin", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String checkLanguage() {
        String lang = language.toString();
        if (!"ZHS".equals(lang) && !"ENG".equals(lang)) {
            lang = "ENG";
        }

        return lang;
    }
    class Keywords {
        List<Keyword> keywords;
    }

    class Keyword {
        String[] NAMES;
        String DESCRIPTION;
    }

}