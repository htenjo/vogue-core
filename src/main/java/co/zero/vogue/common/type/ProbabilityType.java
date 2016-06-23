package co.zero.vogue.common.type;

/**
 * Created by htenjo on 5/30/16.
 */
public enum ProbabilityType {
    FRECUENTE(){
        @Override
        public int getRating() {
            return 5;
        }
    },
    PROBABLE(){
        @Override
        public int getRating() {
            return 4;
        }
    },
    OCASIONAL(){
        @Override
        public int getRating() {
            return 3;
        }
    },
    RARO(){
        @Override
        public int getRating() {
            return 2;
        }
    },
    IMPROBABLE(){
        @Override
        public int getRating() {
            return 1;
        }
    };

    public abstract int getRating();
}
