package com.lcwd.hotel.services.impl;

import com.lcwd.hotel.entites.Hotel;
import com.lcwd.hotel.exceptions.ResourceNotFoundException;
import com.lcwd.hotel.repositories.HotelRespository;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRespository hotelRespository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String randomId = UUID.randomUUID().toString();
        hotel.setId(randomId);
        return hotelRespository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRespository.findAll();
    }

    @Override
    public Hotel getSingleHotel(String id) {
        return hotelRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel with given id not found"));
    }
}
