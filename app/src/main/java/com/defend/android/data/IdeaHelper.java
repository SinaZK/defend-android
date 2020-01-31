package com.defend.android.data;

public class IdeaHelper {
    private static IdeaHelper instance;

    private Idea idea;

    public IdeaHelper() {
        idea = new Idea();
    }

    public static IdeaHelper getInstance() {
        if (instance == null) {
            instance = new IdeaHelper();
        }

        return instance;
    }

    public Idea getIdea() {
        return idea;
    }
}
