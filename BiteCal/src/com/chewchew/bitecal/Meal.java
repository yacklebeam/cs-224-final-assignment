package com.chewchew.bitecal;


import java.util.ArrayList;

public class Meal
{
    private ArrayList<Food> foods;
    
    public Meal()
    {
        foods = new ArrayList<Food>();
    }
    
    public Meal(ArrayList<Food> food)
    {
        foods = food;
    }

    public ArrayList<Food> getFoods() 
    {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) 
    {
        this.foods = foods;
    }
    
    //Returns the .info of food at given index.
    public String returnFood(int index)
    {
        return foods.get(index).info();
    }
    
    public String returnFoodNameAndServings(int index){
    	return (foods.get(index).getFoodName() + ";" + foods.get(index).getServings());
    }
    
    //Returns the .info of all foods in the meal.
    public String returnFoods()
    {
        String totalFoods = "";
        for(int i = 0; i < foods.size(); i++)
        {
            totalFoods += this.returnFoodNameAndServings(i) + ";";
        }
        return totalFoods;
    }
    
    @Override 
    public String toString()
    {
        return this.returnFoods();
    }
    
    //Adds a given food to the meal
    public void addFood(Food foodToAdd)
    {
        this.foods.add(foodToAdd);
    }
    
    //Returns the total calories of the foods from the meal.
    public int totalCal()
    {
        int tCal = 0;
        for(int i = 0; i < foods.size(); i++)
        {
            tCal += foods.get(i).getFoodCalories();
        }
        return tCal;
    }
    
}
