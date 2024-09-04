package org.example.harmadiktesztfeladat_korbandras;

public class NameFrequency {
    private String name;
    private int frequency;

    public NameFrequency(String name, int frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    @Override
    public String toString() {
        return name + ": " + frequency;
    }
}
