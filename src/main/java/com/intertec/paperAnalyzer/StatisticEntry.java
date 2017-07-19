package com.intertec.paperAnalyzer;


public class StatisticEntry {
    private Person person;
    private String label;
    private int amount;

    public StatisticEntry(Person person, int amount) {
        this.person = person;
        this.amount = amount;
    }

    public StatisticEntry(String label, int amount) {
        this.label = label;
        this.amount = amount;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
