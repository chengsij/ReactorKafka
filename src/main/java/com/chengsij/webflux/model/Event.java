package com.chengsij.webflux.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private UUID id;
    private String message;
}
