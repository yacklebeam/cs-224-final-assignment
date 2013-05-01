package com.chewchew.bitecal;

public class Food 
{
    private String foodName;
    private double  servings;
    private double    calOneServing;
    
    public Food()
    {
        foodName = "carrot";
        servings = -1;
        calOneServing = -1; 
    }
    
    public Food(String name, double serving, double cal)
    {
        foodName = name;
        servings = serving;
        calOneServing = cal;
    }

    public String getFoodName() 
    {
        return foodName;
    }

    public double getServings()
    {
        return servings;
    }

    public int getCalOneServing() 
    {
        return (int) (calOneServing*1);
    }

    public void setFoodName(String foodName)
    {
        this.foodName = foodName;
    }

    public void setServings(double servings)
    {
        this.servings = servings;
    }

    public void setCalOneServing(int calOneServing) 
    {
        this.calOneServing = calOneServing;
    }
    
    //Returns the total calories of the entered food.
    public int getFoodCalories(/*int calOneServing, double servings*/)
    {
        int foodCalories = (int) (this.calOneServing * this.servings);
        return foodCalories;
    }
    
    @Override
    public String toString()
    {
        String info = "Food Name: " + foodName + 
                "\nServings: " + servings + 
                "\nCalories of 1 Serving: " + calOneServing + 
                "\nTotal Calories of Food: " + this.getFoodCalories(/*calOneServing, servings*/);
        return info;
    }
 
    //Returns the name, servings, and total calories of the food with spacing
    public String info()
    {
        String cutServings = "" + servings;
        if(cutServings.substring(cutServings.indexOf('.')).length() >= 3)
        {
            cutServings = cutServings.substring(0, cutServings.indexOf('.') + 3);
        }
        String info = "" + foodName + "   " + cutServings + "   " + this.getFoodCalories(/*calOneServing, servings*/);
        return info;
    }
    
}
