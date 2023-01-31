package com.example.dronesmanager.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum DronesState {
    RETURNING(), DELIVERED(RETURNING), DELIVERING(DELIVERED), LOADED(DELIVERING), LOADING(LOADED), IDLE(LOADING);

    private DronesState next;

    public DronesState getNext(){
        return next != null ? next : IDLE;
    }



}
