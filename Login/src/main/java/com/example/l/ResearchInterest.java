package com.example.l;

import java.util.ArrayList;
import java.util.List;


class ResearchInterest {
    private List<String> researchInterestsList;

    public ResearchInterest() {
        this.researchInterestsList = new ArrayList<>();
    }

    public List<String> getResearchInterestsList() {
        return researchInterestsList;
    }

    public void updateResearchInterests(List<String> newResearchInterestsList) {
        researchInterestsList.clear();
        researchInterestsList.addAll(newResearchInterestsList);
    }
}