package com.example.abel.lib;
public final class Constants{
    public static final String URL = "http://10.42.0.1:3000/";

    //api urls
    public static final String SIGNUP_URL = URL + "api/signup";
    public static final String LOGIN_URL = URL + "api/login";

    //user urls
    public static final String USERINFO_URL = URL + "user/userinfo";
    public static final String INDEX_URL = URL + "user/index";
    public static final String VIEW_URL = URL + "user/view";
    public static final String FINISHED_INDEX_URL = "user/finishedEvents";
    public static final String BET_URL = URL + "user/bet";
    public static final String CASHOUT_URL = URL + "user/cashout";
    public static final String UPDATEBALANCE_URL = URL + "user/updateBalance";

    //admin urls
    public static final String POST_EVENT = URL + "admin/postEvent";
    public static final String EDIT_EVENT = URL + "admin/editEvent";
    public static final String ASSIGN_WINNER = URL + "admin/assignWinner";
}
