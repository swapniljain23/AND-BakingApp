package com.swapniljain.bakingapp.model;

import java.util.List;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {

    // Private members.

    @SerializedName("id")
    @Expose
    private Integer recipeId;

    @SerializedName("name")
    @Expose
    private String recipeName;

    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> recipeIngredients = null;

    @SerializedName("steps")
    @Expose
    private List<Step> recipeSteps = null;

    @SerializedName("servings")
    @Expose
    private Integer noOfServings;

    @SerializedName("image")
    @Expose
    private String recipeImage;


    // Getters, Setters.
    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<Step> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public Integer getNoOfServings() {
        return noOfServings;
    }

    public void setNoOfServings(Integer noOfServings) {
        this.noOfServings = noOfServings;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    // Parcelable interface methods.

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.recipeId);
        dest.writeString(this.recipeName);
        dest.writeList(this.recipeIngredients);
        dest.writeList(this.recipeSteps);
        dest.writeValue(this.noOfServings);
        dest.writeString(this.recipeImage);
    }

    public Recipe() {

    }

    protected Recipe(Parcel in) {
        this.recipeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.recipeName = in.readString();
        this.recipeIngredients = new ArrayList<Ingredient>();
        in.readList(this.recipeIngredients, Ingredient.class.getClassLoader());
        this.recipeSteps = new ArrayList<Step>();
        in.readList(this.recipeSteps, Step.class.getClassLoader());
        this.noOfServings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.recipeImage = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public String toString() {
        return this.recipeId + ": " + this.recipeName + ", " + this.recipeImage + ".";
    }
}
