package com.challengemeli.challengemeli.ip.controller;

import com.challengemeli.challengemeli.ip.services.ipServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Controller
@RestController
@RequestMapping(path = "/ipCountry")
public class ipController {

    @Autowired
    private ipServices ipServices;

    @GetMapping(value = "{ip}")
    public String searchIp(@PathVariable String ip){

        boolean valid = ipServices.validateIp(ip);

        String resp =  valid?"valida":"invalida";

      return  resp;
    }




}
