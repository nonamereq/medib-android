package com.example.abel.medib2.contents;

import java.util.ArrayList;
import java.util.List;

public class MatchContent {
    public static final List<Match> ITEMS = new ArrayList<Match>();
    /*
    private static final int COUNT = 25;
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createMatchItem());
        }
    }
    */
    public static void addItem(Match item) {
        ITEMS.add(item);
    }
    /*private static Match createMatchItem() {
        return new Match("Man United", "Real Madrid","1.5","2.0");
    }
    */

    public static class Match {
        public final String mTeamName1;
        public final String mTeamName2;
        public final String mTeamOdd1;
        public final String mTeamOdd2;
        public final String eventId;
        public final String token;
        public Match(String teamName1, String teamName2, String teamOdd1, String teamOdd2 , String eventId ,String token) {
            this.mTeamName1 = teamName1;
            this.mTeamName2 = teamName2;
            this.mTeamOdd1 = teamOdd1;
            this.mTeamOdd2 = teamOdd2;
            this.eventId = eventId;
            this.token = token;
        }
        public ArrayList<String> toArray(){
            ArrayList list=new ArrayList<String>();
            list.add(mTeamName1); list.add(mTeamName2); list.add(mTeamOdd1); list.add(mTeamOdd2);
            list.add(eventId); list.add(token);
            return list;
        }
    }

}
