package com.challengemeli.challengemeli.ip.controller;

import com.challengemeli.challengemeli.ip.models.IpResponse;
import com.challengemeli.challengemeli.ip.services.ipInterface;
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
    private ipInterface ipInterface;

    @GetMapping(value = "{ip}")
   public ResponseEntity<IpResponse> searchIp(@PathVariable String ip){

        return ipInterface.consultarIp(ip);

    }




}
