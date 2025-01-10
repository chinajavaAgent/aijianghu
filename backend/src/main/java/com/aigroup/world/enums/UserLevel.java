package com.aigroup.world.enums;

import lombok.Getter;

@Getter
public enum UserLevel {
    LEVEL_1(1, "搬血境", "搬血初成，需稳扎稳打，按部就班，切忌急功近利，方能血气充盈，根基稳固。", 0, 100),
    LEVEL_2(2, "洞天境", "初入洞天，莫急莫躁，循序渐进，方能稳固根基，洞开新天地。", 100, 500),
    LEVEL_3(3, "化灵境", "灵性初显，需静心养神，以灵驭气，切勿心浮气躁，致灵性受损。", 500, 1000),
    LEVEL_4(4, "铭文境", "铭文刻道，需心细如发，一丝不苟，错一字则满盘皆输，耐心是关键。", 1000, 2000),
    LEVEL_5(5, "列阵境", "阵法千变，需熟记于心，灵活运用，遇敌时方能临危不乱，布阵御敌。", 2000, 5000),
    LEVEL_6(6, "尊者境", "身为尊者，威严自重，待人接物需有度，切勿恃强凌弱，失了尊者风范。", 5000, 10000),
    LEVEL_7(7, "神火境", "神火炽烈，需谨慎掌控，以防灼伤自身，同时也要以火炼心，淬炼道心。", 10000, 20000),
    LEVEL_8(8, "真一境", "真一归元，需摒弃杂念，一心向道，方能领悟真一之妙，突破瓶颈。", 20000, 50000),
    LEVEL_9(9, "圣祭境", "圣祭之路，需虔诚祭炼，心怀敬畏，以圣心行祭事，方得圣力加持。", 50000, 100000),
    LEVEL_10(10, "天神境", "天神高远，需仰望星空，志存高远，不断追求，方能触摸神之领域。", 100000, 200000),
    LEVEL_11(11, "虚道境", "虚道缥缈，需心如止水，洞察虚无，以虚御实，方能领悟虚道真谛。", 200000, 500000),
    LEVEL_12(12, "斩我境", "斩我断念，需狠心决绝，斩断执念，方能突破自我，重塑道心。", 500000, 1000000),
    LEVEL_13(13, "遁一境", "遁一归隐，需隐于无形，藏于虚无，以一遁万，方能遁入无尽虚空。", 1000000, 2000000),
    LEVEL_14(14, "至尊境", "至尊之位，需心怀天下，以德服人，方能统领群雄，成就至尊霸业。", 2000000, 5000000),
    LEVEL_15(15, "仙王境", "仙王临世，需仙心不泯，慈悲为怀，以仙力庇佑众生，方得仙王之名。", 5000000, 10000000),
    LEVEL_16(16, "仙帝境", "仙帝之尊，需掌控仙界，运筹帷幄，以帝道统御仙途，方成仙帝霸业。", 10000000, Integer.MAX_VALUE);

    private final int level;
    private final String title;
    private final String description;
    private final int minCredit;
    private final int maxCredit;

    UserLevel(int level, String title, String description, int minCredit, int maxCredit) {
        this.level = level;
        this.title = title;
        this.description = description;
        this.minCredit = minCredit;
        this.maxCredit = maxCredit;
    }

    public static UserLevel getByLevel(int level) {
        for (UserLevel userLevel : values()) {
            if (userLevel.getLevel() == level) {
                return userLevel;
            }
        }
        return LEVEL_1;
    }

    public static String getTitleByLevel(int level) {
        return getByLevel(level).getTitle();
    }

    public static String getDescriptionByLevel(int level) {
        return getByLevel(level).getDescription();
    }

    public static UserLevel getByCredit(int credit) {
        for (UserLevel level : values()) {
            if (credit >= level.minCredit && credit < level.maxCredit) {
                return level;
            }
        }
        return LEVEL_1;
    }

    public static String getTitleByCredit(int credit) {
        return getByCredit(credit).getTitle();
    }

    public static int getNextLevelCredit(int credit) {
        UserLevel currentLevel = getByCredit(credit);
        return currentLevel.getMaxCredit();
    }

    public static String getDescriptionByCredit(int credit) {
        return getByCredit(credit).getDescription();
    }
} 