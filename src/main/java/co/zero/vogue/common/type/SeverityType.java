package co.zero.vogue.common.type;

/**
 * Created by htenjo on 5/30/16.
 */
public enum SeverityType {
    CATASTROFICO(){
        @Override
        public int getRating() {
            return 5;
        }
    },
    CRITICO(){
        @Override
        public int getRating() {
            return 4;
        }
    },
    SEVERO(){
        @Override
        public int getRating() {
            return 3;
        }
    },
    MENOR(){
        @Override
        public int getRating() {
            return 2;
        }
    },
    INSIGNIFICANTE(){
        @Override
        public int getRating() {
            return 1;
        }
    };

    public abstract int getRating();
}
