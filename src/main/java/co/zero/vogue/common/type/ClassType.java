package co.zero.vogue.common.type;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by htenjo on 6/8/16.
 */
public enum ClassType {
    VERY_HIGH(){
        @Override
        public int getRating() {
            return 5;
        }
    },
    HIGH(){
        @Override
        public int getRating() {
            return 4;
        }
    },
    MEDIUM(){
        @Override
        public int getRating() {
            return 3;
        }
    },
    LOW(){
        @Override
        public int getRating() {
            return 2;
        }
    },
    VERY_LOW(){
        @Override
        public int getRating() {
            return 1;
        }
    };

    public ClassType getClassType(ProbabilityType probabilityType, SeverityType severityType){
        int rating = (probabilityType.getRating() + severityType.getRating());
        //TODO: Ask the politic related to this
        rating = rating % 2 == 0 ? rating / 2 : (rating / 2) + 1;

        for(ClassType type : values()){
            if(type.getRating() == rating){
                return type;
            }
        }

        throw new IllegalStateException("ClassType requires a rating between 1 - 5");
    }

    public abstract int getRating();
}