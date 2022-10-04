package com.wuqisan.ruijiwaimai.outapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outapi")
public class OutApiController {

    @GetMapping
    public String showVersion(@RequestBody String param){

        return "jinitaimei";
    }
}
