package co.zero.vogue.common.type;

/**
 * Created by htenjo on 5/30/16.
 */
public enum SeverityType {
    CATASTROFICO(){
        @Override
        public int getRating() {
            return RATING_VERY_HIGH;
        }
    },
    CRITICO(){
        @Override
        public int getRating() {
            return RATING_HIGH;
        }
    },
    SEVERO(){
        @Override
        public int getRating() {
            return RATING_MEDIUM;
        }
    },
    MENOR(){
        @Override
        public int getRating() {
            return RATING_LOW;
        }
    },
    INSIGNIFICANTE(){
        @Override
        public int getRating() {
            return RATING_VERY_LOW;
        }
    };

    private static final int RATING_VERY_HIGH = 5;
    private static final int RATING_HIGH = 4;
    private static final int RATING_MEDIUM = 3;
    private static final int RATING_LOW = 2;
    private static final int RATING_VERY_LOW = 1;

    public abstract int getRating();
}
