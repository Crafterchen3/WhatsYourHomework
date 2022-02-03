package com.deckerpw.whatsyourhomework;

public class Subject {

    String name;
    String contents;
    long hardnessLevel;

    public Subject(String name, String contents, long hardnessLevel) {
        this.name = name;
        this.contents = contents;
        this.hardnessLevel = hardnessLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getHardnessLevel() {
        return hardnessLevel;
    }

    public void setHardnessLevel(int hardnessLevel) {
        this.hardnessLevel = hardnessLevel;
    }

}
