package com.SafetyNet.Safety.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class BuilderResponse<obj> {

    public ResponseEntity<?> CustomResponse(obj obj,Boolean toString){
        if(obj != null){
            if(toString){
                return new ResponseEntity<>(obj.toString(),HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(obj,HttpStatus.ACCEPTED);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public  ResponseEntity<?> ResponseBoolean(Boolean bool){
        if(bool){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<?> ResponseBoolean(Boolean bool, String reponseTrue,String reponseFalse){
        if(bool){
            return new ResponseEntity<>(reponseTrue,HttpStatus.ACCEPTED);
        }{
            return new ResponseEntity<>(reponseFalse,HttpStatus.NO_CONTENT);
        }
    }

}
