package com.SafetyNet.Safety.util;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class BuilderResponse<obj> {

    public ResponseEntity<?> CustomResponse(obj obj){
        if(obj != null){
                return new ResponseEntity<>(obj,HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public  ResponseEntity<?> ResponseBoolean(Boolean bool){
        JsonObject result = new JsonObject();

        if(bool){
            result.addProperty("Message","Ok");
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        }{
            result.addProperty("Message","KO");
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);

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
