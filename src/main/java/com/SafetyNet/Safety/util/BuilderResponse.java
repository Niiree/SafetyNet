package com.SafetyNet.Safety.util;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class BuilderResponse<obj> {

    public ResponseEntity<?> CustomResponse(obj obj){
        if(obj != null){
                return new ResponseEntity<>(obj,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public  ResponseEntity<?> ResponseBoolean(Boolean bool){
        JsonObject result = new JsonObject();
        if(bool){
            result.addProperty("Message","L'operation a ete realise avec succes");
            return new ResponseEntity<>(result.toString(),HttpStatus.OK);
        }{
            result.addProperty("Message","L'operation n'a pas ete realise");
            return new ResponseEntity<>(result.toString(),HttpStatus.NO_CONTENT);

        }
    }

}
