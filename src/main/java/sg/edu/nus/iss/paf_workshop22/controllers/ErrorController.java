package sg.edu.nus.iss.paf_workshop22.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.paf_workshop22.dto.ErrorResponse;
import sg.edu.nus.iss.paf_workshop22.exception.RecordNotFoundException;

@RestControllerAdvice
public class ErrorController {
    
    // public ModelAndView handleRecordNotFound(HttpServletRequest req, RecordNotFoundException ex){

    //     final ModelAndView mav = new ModelAndView("record_not_found.html");
    //     mav.addObject("tableName",ex.getTableName());
    //     mav.addObject("primaryKey", ex.getPrimaryKey());
    //     mav.addObject("name", ex.getName());
    //     mav.setStatus(HttpStatus.NOT_FOUND);
    //     //mav.setViewName("error");
    //     return mav;
    // }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }

}
