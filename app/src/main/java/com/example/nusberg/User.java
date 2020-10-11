package com.example.nusberg;

class User {
    private String Name,State;
    private int Age;
    private int StateSignal;
    User(String name, String state, int age, int stateSignal) {
        Name = name;
        State = state;
        Age = age;
        StateSignal=stateSignal;
    }
public int getStateSignal()
{
    return StateSignal;
}
public void setStateSignal(int stateSignal)
{
 StateSignal = stateSignal;
}
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
