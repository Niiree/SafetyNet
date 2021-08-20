package com.SafetyNet.Safety.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


public class BuilderResponseTest<obj> {

    private static BuilderResponse builderResponse = new BuilderResponse();

    private obj objNull = null;
    private  String[] objNotNull = {"test","test2"};

    @Test
    public void CustomResponseNoContent(){
        ResponseEntity reponse = builderResponse.CustomResponse(objNull, false);
        assertEquals(HttpStatus.NO_CONTENT,reponse.getStatusCode());
    }

    @Test
    public void CustomResponseAccepted(){
        ResponseEntity reponse = builderResponse.CustomResponse(objNotNull, false);
        assertEquals(HttpStatus.ACCEPTED,reponse.getStatusCode());
        assertEquals(objNotNull,reponse.getBody());
    }

    @Test
    public void CustomResponseAcceptedToString(){
        ResponseEntity reponse = builderResponse.CustomResponse(objNotNull, true);
        assertEquals(HttpStatus.ACCEPTED,reponse.getStatusCode());
        assertEquals(objNotNull.toString(),reponse.getBody());
    }

    @Test
    public void ReponseBoolean(){
        ResponseEntity reponseTrue = builderResponse.ResponseBoolean(true);
        ResponseEntity reponseFalse = builderResponse.ResponseBoolean(false);

        assertEquals(HttpStatus.ACCEPTED,reponseTrue.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT,reponseFalse.getStatusCode());
    }

    @Test
    public void ResponseBoolean(){
        ResponseEntity reponseTrue = builderResponse.ResponseBoolean(true, "Test 1","Test 2");
        ResponseEntity reponseFalse = builderResponse.ResponseBoolean(false,"Test 1","Test 2");
        assertEquals("Test 1",reponseTrue.getBody());
        assertEquals(HttpStatus.ACCEPTED,reponseTrue.getStatusCode());
        assertEquals("Test 2",reponseFalse.getBody());
        assertEquals(HttpStatus.NO_CONTENT,reponseFalse.getStatusCode());


    }

}
