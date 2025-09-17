package com.example.exemplo.contoller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@Controller //Controller permite ter VIEW, para aplicações web
@RestController //RestController não pode usar VIEW, mais rapida(para APIs)
@RequestMapping("/")
class PessoaController {

}