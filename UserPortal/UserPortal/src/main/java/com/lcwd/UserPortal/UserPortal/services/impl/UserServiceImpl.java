package com.lcwd.UserPortal.UserPortal.services.impl;


import com.lcwd.UserPortal.UserPortal.entites.Hotel;
import com.lcwd.UserPortal.UserPortal.entites.Rating;
import com.lcwd.UserPortal.UserPortal.entites.User;
import com.lcwd.UserPortal.UserPortal.exceptions.ResourceNotFoundException;
import com.lcwd.UserPortal.UserPortal.repositories.UserRepository;
import com.lcwd.UserPortal.UserPortal.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserID(randomUserId);
       return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> result = userRepository.findAll();
        List<User> temp = result.stream().map((e) -> {
            Rating[] rating = restTemplate.getForObject("http://localhost:8083/ratings/users/" + e.getUserID(), Rating[].class);
            e.setRatings(Arrays.stream(rating).toList());
            return e;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user= userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with given ID is not found on server !! :"+userId));
      //fetch rating of the above user from rating service
       Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserID(), Rating[].class);
       logger.info("{}",ratingOfUser);
        List<Rating> ratings =Arrays.stream(ratingOfUser).toList();


       //http://localhost:8083/ratings/hotels/5dd448dd-fe86-4f3e-989e-d17e8b0247fc

       List<Rating> ratingList= ratings.stream().map(rating->{
           ResponseEntity<Hotel> forEntity= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
           Hotel hotel = forEntity.getBody();
           rating.setHotel(hotel);
           return rating;
       }).collect(Collectors.toList());
       user.setRatings(ratings);
       return  user;
    }

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }


}
