package com.example.library.services;

import com.example.library.dtos.UserResponseDto;
import com.example.library.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class UserServiceClient {
    private final RestClient restClient;

    public UserServiceClient(RestClient restClient){
        this.restClient=restClient;
    }

   public UserResponseDto getUserById(String userId){
       try{
           return restClient.get().uri("/users/{userId}",userId)
                   .retrieve().body(UserResponseDto.class);
       } catch(RestClientResponseException ex){
           if(ex.getStatusCode().value()==404){
               throw new UserNotFoundException("User not found with id + "+ userId);
           }
           throw ex;
       }

   }
}
