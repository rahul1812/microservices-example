package com.lcwd.hotel.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

   public ResourceNotFoundException(){
        super();
    }

   public ResourceNotFoundException(String message){
        super(message);
    }
}
