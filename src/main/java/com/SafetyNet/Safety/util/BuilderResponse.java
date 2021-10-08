package com.SafetyNet.Safety.util;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class BuilderResponse<obj> {

    private final static Logger logger = LogManager.getLogger("BuilderReponse") ;

    public ResponseEntity<?> customResponse(obj obj){
        if(obj != null){
            logger.info("Requete réalisé avec un code retour : " +HttpStatus.OK);
                return new ResponseEntity<>(obj,HttpStatus.OK);
        }else {
            logger.info("Requete réalisé avec un code retour : "+HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    public  ResponseEntity<?> responseBoolean(Boolean bool){
        JsonObject result = new JsonObject();
        if(bool){
            logger.info("Requete réalisé avec un code retour : " +HttpStatus.OK);
            result.addProperty("Message","L'operation a ete realise avec succes");
            return new ResponseEntity<>(result.toString(),HttpStatus.OK);
        }{
            result.addProperty("Message","L'operation n'a pas ete realise");
            logger.info("Requete réalisé avec un code retour : "+HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(result.toString(),HttpStatus.NO_CONTENT);

        }
    }

}
