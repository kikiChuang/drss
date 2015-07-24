package com.example.drss.dbadapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.drss.bean.RecipeBean;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBAdapter {

    final static String TABLE="food";
    final static String FOOD_ID="food_id";
    final static String FOOD_NAME="food_name";
    final static String FOOD_PRICE="food_price";
    final static String FOOD_CLASS="food_class";
    final static String FOOD_IMG="food_img";
    final static String SHOP_ID="shop_id";

    Context context;

    SQLiteDatabase database;
    DBUtils dbUtils;

    public RecipeDBAdapter(Context context){
        this.context=context;
    }

    public void openDB(){
        dbUtils=new DBUtils(context);
        database=dbUtils.getDB();
    }

    public void closeDB(){
        dbUtils.closeDB();
    }

    public List<RecipeBean> queryAll(String shop_id){
        Cursor cursor=database.query(TABLE,null,SHOP_ID + "=?",new String[]{shop_id},null,null,null);
        return convertToRecipe(cursor);
    }

    public List<RecipeBean> queryByFoodId(String food_id){
        Cursor cursor=database.query(TABLE, null, FOOD_ID+"=?",new String[]{food_id}, null, null, null);
        return convertToRecipe(cursor);

    }

    private List<RecipeBean> convertToRecipe(Cursor cursor){
        List<RecipeBean> foods;
        if (cursor.getCount()==0){
            return null;
        }
        foods=new ArrayList<RecipeBean>();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            RecipeBean recipeBean=new RecipeBean();
            recipeBean.setFood_id(cursor.getString(cursor.getColumnIndex(FOOD_ID)));
            recipeBean.setFood_name(cursor.getString(cursor.getColumnIndex(FOOD_NAME)));
            recipeBean.setFood_price(cursor.getString(cursor.getColumnIndex(FOOD_PRICE)));
            recipeBean.setFood_class(cursor.getString(cursor.getColumnIndex(FOOD_CLASS)));
            recipeBean.setFood_img(cursor.getString(cursor.getColumnIndex(FOOD_IMG)));
            recipeBean.setShop_id(cursor.getString(cursor.getColumnIndex(SHOP_ID)));
            foods.add(recipeBean);
            cursor.moveToNext();
        }
        return foods;
    }
}
