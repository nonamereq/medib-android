package com.example.abel.medib.contents;

import java.util.ArrayList;
import java.util.List;

public class MatchContent {
    public static final List<Match> ITEMS = new ArrayList<Match>();

    public static void addItem(Match item) {
        ITEMS.add(item);
    }

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
