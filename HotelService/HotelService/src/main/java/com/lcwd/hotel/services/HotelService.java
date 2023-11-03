package com.lcwd.hotel.services;

import com.lcwd.hotel.entites.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel createHotel(Hotel hotel);

    //getall
    List<Hotel> getAll();

    //getSingle
    Hotel getSingleHotel(String id);
}
