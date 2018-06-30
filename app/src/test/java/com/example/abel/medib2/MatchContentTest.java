package com.example.abel.medib2;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abel on 6/30/2018.
 *
 */

public class MatchContentTest {
    public static class MatchContent {
        public static final List<Match> ITEMS = new ArrayList<Match>();

        public void addItem(Match item) {
            ITEMS.add(item);
        }

        public static class Match {
            public final String mTeamName1;
            public final String mTeamName2;
            public final String mTeamOdd1;
            public final String mTeamOdd2;
            public final String eventId;
            public final String token;

            public Match(String teamName1, String teamName2, String teamOdd1, String teamOdd2, String eventId, String token) {
                this.mTeamName1 = teamName1;
                this.mTeamName2 = teamName2;
                this.mTeamOdd1 = teamOdd1;
                this.mTeamOdd2 = teamOdd2;
                this.eventId = eventId;
                this.token = token;
            }

            public ArrayList<String> toArray() {
                ArrayList list = new ArrayList<String>();
                list.add(mTeamName1);
                list.add(mTeamName2);
                list.add(mTeamOdd1);
                list.add(mTeamOdd2);
                list.add(eventId);
                list.add(token);
                return list;
            }

        }
    }

        @Test
        //test addItem() (test if new items are added to MatchContent.ITEMS )
        public void test1() {
            MatchContent mc = new MatchContent();
            MatchContent.Match m = new MatchContent.Match("manchester", "arsenal", "1.1", "1.9", "id1", "tok");
            assertEquals(true , mc.ITEMS.isEmpty()); //initially empty
            mc.addItem(m);
            assertEquals(false , mc.ITEMS.isEmpty());
        }
        @Test
        //test contents of added items
        public void test2(){
            MatchContent mc = new MatchContent();
            MatchContent.Match m = new MatchContent.Match("manchester", "arsenal", "1.1", "1.9", "id1", "tok");
            mc.addItem(m);
            assertEquals("manchester" , mc.ITEMS.get(0).mTeamName1);
            assertEquals("arsenal" , mc.ITEMS.get(0).mTeamName2);
            assertEquals("1.1" , mc.ITEMS.get(0).mTeamOdd1);
            assertEquals("1.9" , mc.ITEMS.get(0).mTeamOdd2);
            assertEquals("id1" , mc.ITEMS.get(0).eventId);
            assertEquals("tok" , mc.ITEMS.get(0).token);

        }

        @Test
        //test toArray() (test if toArray returns a ArrayList)
        public void test3(){
            MatchContent.Match m = new MatchContent.Match("manchester", "arsenal", "1.1", "1.9", "id1", "tok");
            assertEquals(ArrayList.class , m.toArray().getClass());
        }

    }
