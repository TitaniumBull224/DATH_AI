package com.example.internet.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> result;
    private String firstNumber = "";
    private String secondNumber = "";
    private String operator = "";

    public HomeViewModel() {
        result = new MutableLiveData<>();
        result.setValue("");
    }

    public LiveData<String> getResult() {
        return result;
    }

    public void appendNumber(String number) {
        if (operator.isEmpty()) {
            firstNumber += number;
            result.setValue(firstNumber);
        } else {
            secondNumber += number;
            result.setValue(secondNumber);
        }
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void calculateResult() {
        int first = Integer.parseInt(firstNumber);
        int second = Integer.parseInt(secondNumber);
        int res = 0;

        switch (operator) {
            case "+":
                res = first + second;
                break;
            case "-":
                res = first - second;
                break;
            case "*":
                res = first * second;
                break;
            case "/":
                res = first / second;
                break;
        }

        result.setValue(String.valueOf(res));
        firstNumber = String.valueOf(res);
        secondNumber = "";
        operator = "";
    }

    public void clear() {
        firstNumber = "";
        secondNumber = "";
        operator = "";
        result.setValue("");
    }
}