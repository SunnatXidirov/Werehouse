package uz.dev.foodstorage.GlobalURL.userURL;

import uz.dev.foodstorage.GlobalURL.authUrl.AuthUrl;

public class UserUrl {

    public static final String baseUserUrl= AuthUrl.baseUrl+"/user";
    public static final String addAdmin="/addAdmin";
    public static final String addUser="/addUser";
    public static final String getUsers="/getAll";
    public static final String getUser="/getById";
    public static final String getBlockUser="getBlockUser";
}
