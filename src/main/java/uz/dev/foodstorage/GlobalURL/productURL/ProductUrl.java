package uz.dev.foodstorage.GlobalURL.productURL;

import uz.dev.foodstorage.GlobalURL.authUrl.AuthUrl;

public class ProductUrl {
    public static final String productBaseUrl= AuthUrl.baseUrl+"/product";
    public static final String productAdd="/add";
    public static final String productSearchByName="/SearchByName";
    public static final String productSearchByCategory="/SearchByCategory";
    public static final String productSearchByQuantityUp="/SearchByQuantityUp";
    public static final String productSearchByQuantityDown="/SearchByQuantityDown";
    public static final String productGetAll="/getAll";
    public static final String productGetById="/getById";
    public static final String productUpdate="/update";
    public static final String productDelete="/delete";
}
