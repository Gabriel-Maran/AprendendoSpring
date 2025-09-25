package br.com.gabrielmaran.aprendendoSpring.controllers;


import br.com.gabrielmaran.aprendendoSpring.exception.UnsupportedMethodOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    //    http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sumOperation(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        validOperation(numberOne, numberTwo);
        return parseDouble(numberOne) + parseDouble(numberTwo);
    }

    //    http://localhost:8080/math/subt/3/5
    @RequestMapping("/subt/{numOne}/{numTwo}")
    public Double subtOperation(
            @PathVariable("numOne") String numOne,
            @PathVariable("numTwo") String numTwo
    ) {
        validOperation(numOne, numTwo);
        return parseDouble(numOne) - parseDouble(numTwo);
    }

    //    http://localhost:8080/math/mult/3/5
    @RequestMapping("/mult/{numOne}/{numTwo}")
    public Double multOperation(
            @PathVariable("numOne") String numOne,
            @PathVariable("numTwo") String numTwo
    ) {
        validOperation(numOne, numTwo);
        return parseDouble(numOne) * parseDouble(numTwo);
    }

    //    http://localhost:8080/math/div/6/3
    @RequestMapping("/div/{numOne}/{numTwo}")
    public Double divOperation(
            @PathVariable("numOne") String numOne,
            @PathVariable("numTwo") String numTwo
    ) {
        validOperation(numOne, numTwo);
        if (parseDouble(numTwo) == 0D) {
            throw new UnsupportedMethodOperationException("Please, dont try to divide by zero");
        }
        return parseDouble(numOne) / parseDouble(numTwo);
    }

    //    http://localhost:8080/math/average/6/3
    @RequestMapping("/average/{numOne}/{numTwo}")
    public Double avaregeOperatio(
            @PathVariable("numOne") String numOne,
            @PathVariable("numTwo") String numTwo
    ){
        validOperation(numOne, numTwo);
        return (parseDouble(numOne) + parseDouble(numTwo)) / 2;
    }


    //    http://localhost:8080/math/sqrt/6
    @RequestMapping("/sqrt/{num}")
    public Double squareRoot(
            @PathVariable("num") String num
    ){
        if (!isNumeric(num)) {
            throw new UnsupportedMethodOperationException("Please, set only numeric values");
        }
        return Math.sqrt(parseDouble(num));
    }

    //Auxiliar methods
    private void validOperation(String strNumOne, String strNumTwo) {
        if (!isNumeric(strNumOne) || !isNumeric(strNumTwo)) {
            throw new UnsupportedMethodOperationException("Please, set only numeric values");
        }
    }

    private Boolean isNumeric(String strNum) {
        if (strNum.isBlank()) return false;
        String number = strNum.replace(",", ".");
        return number.matches("\\d+|\\d+.\\d+");
    }

    private Double parseDouble(String strNum) {
        String number = strNum.replace(",", ".");
        return Double.parseDouble(number);
    }
}
