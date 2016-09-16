package co.zero.vogue.common.type;

/**
 * Created by htenjo on 6/8/16.
 */
public enum ClassType {
    VERY_HIGH(){
        @Override
        public int getRating() {
            return ClassType.RATING_VERY_HIGH;
        }

        @Override
        public int getDaysToBeClosed() {
            return ClassType.MAX_DAYS_VERY_HIGH;
        }
    },
    HIGH(){
        @Override
        public int getRating() {
            return ClassType.RATING_HIGH;
        }

        @Override
        public int getDaysToBeClosed() {
            return ClassType.MAX_DAYS_HIGH;
        }
    },
    MEDIUM(){
        @Override
        public int getRating() {
            return ClassType.RATING_MEDIUM;
        }

        @Override
        public int getDaysToBeClosed() {
            return ClassType.MAX_DAYS_MEDIUM;
        }
    },
    LOW(){
        @Override
        public int getRating() {
            return ClassType.RATING_LOW;
        }

        @Override
        public int getDaysToBeClosed() {
            return ClassType.MAX_DAYS_LOW;
        }
    },
    VERY_LOW(){
        @Override
        public int getRating() {
            return ClassType.RATING_VERY_LOW;
        }

        @Override
        public int getDaysToBeClosed() {
            return ClassType.MAX_DAYS_VERY_LOW;
        }
    };


    private static final int RATING_VERY_HIGH = 5;
    private static final int RATING_HIGH = 4;
    private static final int RATING_MEDIUM = 3;
    private static final int RATING_LOW = 2;
    private static final int RATING_VERY_LOW = 1;
    private static final int MAX_DAYS_VERY_HIGH = 7;
    private static final int MAX_DAYS_HIGH = 30;
    private static final int MAX_DAYS_MEDIUM = 365;
    //TODO: To be defined
    private static final int MAX_DAYS_LOW = 1000;
    private static final int MAX_DAYS_VERY_LOW = 1000;


    /**
     *
     * @param probabilityType
     * @param severityType
     * @return
     */
    public ClassType getClassType(ProbabilityType probabilityType, SeverityType severityType){
        if((probabilityType == ProbabilityType.FRECUENTE && severityType == SeverityType.CATASTROFICO)
                || (probabilityType == ProbabilityType.FRECUENTE && severityType == SeverityType.CRITICO)
                || (probabilityType == ProbabilityType.PROBABLE && severityType == SeverityType.CATASTROFICO)
                || (probabilityType == ProbabilityType.PROBABLE && severityType == SeverityType.CRITICO)
                || (probabilityType == ProbabilityType.OCASIONAL && severityType == SeverityType.CATASTROFICO)){
            return ClassType.VERY_HIGH;
        }else if((probabilityType == ProbabilityType.FRECUENTE && severityType == SeverityType.SEVERO)
                || (probabilityType == ProbabilityType.FRECUENTE && severityType == SeverityType.MENOR)
                || (probabilityType == ProbabilityType.PROBABLE && severityType == SeverityType.SEVERO)
                || (probabilityType == ProbabilityType.OCASIONAL && severityType == SeverityType.SEVERO)
                || (probabilityType == ProbabilityType.OCASIONAL && severityType == SeverityType.CRITICO)
                || (probabilityType == ProbabilityType.RARO && severityType == SeverityType.CATASTROFICO)){
            return ClassType.HIGH;
        }else if((probabilityType == ProbabilityType.PROBABLE && severityType == SeverityType.MENOR)
                || (probabilityType == ProbabilityType.OCASIONAL && severityType == SeverityType.MENOR)
                || (probabilityType == ProbabilityType.RARO && severityType == SeverityType.SEVERO)
                || (probabilityType == ProbabilityType.RARO && severityType == SeverityType.CRITICO)){
            return ClassType.MEDIUM;
        }else if((probabilityType == ProbabilityType.FRECUENTE && severityType == SeverityType.INSIGNIFICANTE)
                || (probabilityType == ProbabilityType.RARO && severityType == SeverityType.MENOR)
                || (probabilityType == ProbabilityType.IMPROBABLE && severityType == SeverityType.MENOR)
                || (probabilityType == ProbabilityType.IMPROBABLE && severityType == SeverityType.SEVERO)
                || (probabilityType == ProbabilityType.IMPROBABLE && severityType == SeverityType.CRITICO)
                || (probabilityType == ProbabilityType.IMPROBABLE && severityType == SeverityType.CATASTROFICO)){
            return ClassType.LOW;
        }else if((probabilityType == ProbabilityType.PROBABLE && severityType == SeverityType.INSIGNIFICANTE)
                || (probabilityType == ProbabilityType.OCASIONAL && severityType == SeverityType.INSIGNIFICANTE)
                || (probabilityType == ProbabilityType.RARO && severityType == SeverityType.INSIGNIFICANTE)
                || (probabilityType == ProbabilityType.IMPROBABLE && severityType == SeverityType.INSIGNIFICANTE)){
            return ClassType.VERY_LOW;
        }

        throw new IllegalStateException("ClassType requires a valid combination of ProbabilityType and SeverityType");
    }

    public abstract int getRating();

    public abstract int getDaysToBeClosed();
}